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
import android.widget.IconTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Team;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.LeaderBoardAdapter;
import com.joanzapata.android.iconify.Iconify;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Collections;
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
        Db.getTeams(activity, category, rpa, new FindCallback<Team>() {
            @Override
            public void done(final List<Team> list, ParseException e) {
                if (list != null && !list.isEmpty()) {
                    ParseObject.pinAllInBackground(list);
                    HashMap<String, List<Team>> groupTeams = getGroupTeams(list);
                    List<String> sortedGroups = new ArrayList<>(groupTeams.keySet());
                    Collections.sort(sortedGroups);

                    for (String group : sortedGroups) {
                        ListView listView = (ListView) activity.getLayoutInflater().inflate(R.layout.fragment_leaderboard_list, null);
                        listView.setAdapter(new LeaderBoardAdapter(activity, groupTeams.get(group)));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Team team = list.get(position);
                                //openPlayersDialog(team);
                            }
                        });
                        addListHeader(listView, group);
                        contentLayout.addView(listView);
                        Util.setListViewHeightBasedOnChildren(listView);
                    }

                    contentLayout.setGravity(Gravity.LEFT | Gravity.TOP);
                } else {
                    View emptyView = Util.getEmptyView(activity);
                    contentLayout.addView(emptyView);
                    contentLayout.setGravity(Gravity.CENTER);
                }

//                Collections.sort(list, new Comparator<Team>() {
//                    @Override
//                    public int compare(Team lhs, Team rhs) {
//                        return Integer.valueOf(rhs.getScore()).compareTo(lhs.getScore());
//                    }
//                });
//                for(int i = 0; i < list.size(); i++){
//                    list.get(i).setPositon(i + 1);
//                }

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

    private HashMap<String, List<Team>> getGroupTeams(List<Team> teams){
        HashMap<String, List<Team>> groupTeams = new HashMap<>();
        for (Team team : teams){
            if (!Util.isNullOrEmpty(team.getGroup())) {
                if (!groupTeams.containsKey(team.getGroup())) {
                    groupTeams.put(team.getGroup(), new ArrayList<Team>());
                }
                groupTeams.get(team.getGroup()).add(team);
            }
        }
        return groupTeams;
    }

    private void addListHeader(ListView listView, String group){
        View headerView = activity.getLayoutInflater().inflate(R.layout.list_header_leaderboard, null);
        TextView groupView = (TextView) headerView.findViewById(R.id.group);
        IconTextView redCardsView = (IconTextView) headerView.findViewById(R.id.red_cards);
        IconTextView yellowCardsView = (IconTextView) headerView.findViewById(R.id.yellow_cards);

        groupView.setText(getString(R.string.group) + " " + group);
        redCardsView.setTypeface(Iconify.getTypeface(activity));
        yellowCardsView.setTypeface(Iconify.getTypeface(activity));

        listView.addHeaderView(headerView, null, false);
    }
}