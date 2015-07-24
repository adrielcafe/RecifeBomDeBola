package com.adrielcafe.recifebomdebola.ui.tab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.IconTextView;
import android.widget.ListView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Player;
import com.adrielcafe.recifebomdebola.model.Team;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.LeaderBoardAdapter;
import com.adrielcafe.recifebomdebola.ui.adapter.PlayerAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.joanzapata.android.iconify.Iconify;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderBoardTabFragment extends Fragment {
    private MainActivity activity;
    private String category;
    private int rpa;

    @Bind(android.R.id.list)
    ListView listView;

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
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadLeaderBoard() {
        activity.setLoading(true);
        Db.getTeams(getActivity(), category, rpa, new FindCallback<Team>() {
            @Override
            public void done(final List<Team> list, ParseException e) {
                ParseObject.pinAllInBackground(list);
                Collections.sort(list, new Comparator<Team>() {
                    @Override
                    public int compare(Team lhs, Team rhs) {
                        return Integer.valueOf(rhs.getScore()).compareTo(lhs.getScore());
                    }
                });
                for(int i = 0; i < list.size(); i++){
                    list.get(i).setPositon(i + 1);
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new LeaderBoardAdapter(getActivity(), list));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Team team = list.get(position);
                                openPlayersDialog(team);
                            }
                        });
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

    private void openPlayersDialog(final Team team){
        activity.setLoading(true);
        Db.getTeamPlayers(activity, team.getName(), new FindCallback<Player>() {
            @Override
            public void done(final List<Player> list, ParseException e) {
                final View playersView = getLayoutInflater(null).inflate(R.layout.dialog_players, null, false);
                final ListView playersList = (ListView) playersView.findViewById(android.R.id.list);
                IconTextView redCardsView = (IconTextView) playersView.findViewById(R.id.red_cards);
                IconTextView yellowCardsView = (IconTextView) playersView.findViewById(R.id.yellow_cards);

                redCardsView.setTypeface(Iconify.getTypeface(getActivity()));
                yellowCardsView.setTypeface(Iconify.getTypeface(getActivity()));

                ParseObject.pinAllInBackground(list);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playersList.setAdapter(new PlayerAdapter(getActivity(), list));

                        final NiftyDialogBuilder playerDialog = NiftyDialogBuilder.getInstance(getActivity());
                        playerDialog.setCustomView(playersView, getActivity())
                                .withTitle(team.getName())
                                .withMessage(null)
                                .withDuration(300)
                                .withEffect(Effectstype.Fadein)
                                .withDialogColor(getResources().getColor(R.color.accent))
                                .withButton1Text(getString(R.string.close))
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        playerDialog.dismiss();
                                    }
                                });
                        playerDialog.show();
                        activity.setLoading(false);
                    }
                });
            }
        });
    }
}