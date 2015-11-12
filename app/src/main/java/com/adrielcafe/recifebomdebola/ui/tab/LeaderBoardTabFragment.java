package com.adrielcafe.recifebomdebola.ui.tab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.LeaderBoard;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.LeaderBoardAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderBoardTabFragment extends Fragment {
    private static MainActivity activity;
    private String category;
    private int rpa;

    @Bind(R.id.content)
    LinearLayout contentLayout;

    public static LeaderBoardTabFragment newInstance(Category category, int rpa) {
        Bundle args = new Bundle();
        args.putString(App.EXTRA_CATEGORY, category.getName());
        args.putInt(App.EXTRA_RPA, rpa);
        LeaderBoardTabFragment frag = new LeaderBoardTabFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString(App.EXTRA_CATEGORY);
        rpa = getArguments().getInt(App.EXTRA_RPA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_leaderboard, container, false);
        ButterKnife.bind(this, v);
        loadLeaderBoard();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(LeaderBoardTabFragment.activity == null) {
            LeaderBoardTabFragment.activity = (MainActivity) activity;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadLeaderBoard() {
        activity.setLoading(true);
        Db.getLeaderBoard(activity, category, rpa, new FindCallback<LeaderBoard>() {
            @Override
            public void done(final List<LeaderBoard> list, ParseException e) {
                if (list != null && !list.isEmpty()) {
                    ParseObject.pinAllInBackground(list);
                    HashMap<String, List<LeaderBoard>> groupTeams = getGroupTeams(list);
                    List<String> sortedGroups = new ArrayList<>(groupTeams.keySet());
                    Collections.sort(sortedGroups, new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            if (Util.isNumeric(lhs) && lhs.length() == 1) {
                                lhs = "0" + lhs;
                            }
                            if (Util.isNumeric(rhs) && rhs.length() == 1) {
                                rhs = "0" + rhs;
                            }
                            return lhs.compareTo(rhs);
                        }
                    });

                    for (String group : sortedGroups) {
                        List<LeaderBoard> leaderBoards = groupTeams.get(group);
                        Collections.sort(leaderBoards, new Comparator<LeaderBoard>() {
                            @Override
                            public int compare(LeaderBoard lhs, LeaderBoard rhs) {
	                            String lhsStr = lhs.getPointsScored() < 10 ? "0" + lhs.getPointsScored() : "" + lhs.getPointsScored();
	                            String rhsStr = rhs.getPointsScored() < 10 ? "0" + rhs.getPointsScored() : "" + rhs.getPointsScored();
                                return rhsStr.compareTo(lhsStr);
                            }
                        });

                        ListView listView = (ListView) activity.getLayoutInflater().inflate(R.layout.fragment_leaderboard_list, null);
                        listView.setAdapter(new LeaderBoardAdapter(activity, groupTeams.get(group)));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LeaderBoard leaderBoard = list.get(position);
                                Util.openPlayersDialog(activity, leaderBoard.getTeam(), leaderBoard.getCategory(), leaderBoard.getRpa());
                            }
                        });

                        addListHeader(listView, group);
                        contentLayout.addView(listView);
                        Util.setListViewHeightBasedOnChildren(listView);
                    }

                    contentLayout.setGravity(Gravity.LEFT | Gravity.TOP);
                } else if (contentLayout != null) {
                    View emptyView = Util.getEmptyView(activity);
                    contentLayout.addView(emptyView);
                    contentLayout.setGravity(Gravity.CENTER);
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activity.setLoading(false);
                            }
                        }, 1000);
                    }
                });
            }
        });
    }

    private HashMap<String, List<LeaderBoard>> getGroupTeams(List<LeaderBoard> leaderBoards){
        HashMap<String, List<LeaderBoard>> groupLeaderBoard = new HashMap<>();
        for (LeaderBoard leaderBoard : leaderBoards){
            if (!Util.isNullOrEmpty(leaderBoard.getGroup())) {
                if (!groupLeaderBoard.containsKey(leaderBoard.getGroup())) {
                    groupLeaderBoard.put(leaderBoard.getGroup(), new ArrayList<LeaderBoard>());
                }
                groupLeaderBoard.get(leaderBoard.getGroup()).add(leaderBoard);
            }
        }
        return groupLeaderBoard;
    }

    private void addListHeader(ListView listView, String group){
        View headerView = activity.getLayoutInflater().inflate(R.layout.list_header_leaderboard, null);
        TextView groupView = (TextView) headerView.findViewById(R.id.group);
        groupView.setText(getString(R.string.group) + " " + group);
        listView.addHeaderView(headerView, null, false);
    }
}