package com.adrielcafe.recifebomdebola.ui.tab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Match;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.MatchAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchTabFragment extends Fragment {
    private static MainActivity activity;
    private String category;
    private int rpa;
    private DateTime date;

    @Bind(R.id.content)
    LinearLayout contentLayout;

    public static MatchTabFragment newInstance(Category category, int rpa, DateTime date) {
        Bundle args = new Bundle();
        args.putString(App.EXTRA_CATEGORY, category.getName());
        args.putInt(App.EXTRA_RPA, rpa);
        args.putSerializable(App.EXTRA_DATE, date);
        MatchTabFragment frag = new MatchTabFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString(App.EXTRA_CATEGORY);
        rpa = getArguments().getInt(App.EXTRA_RPA);
        date = (DateTime) getArguments().getSerializable(App.EXTRA_DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_match, container, false);
        ButterKnife.bind(this, v);
        loadMatches();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(MatchTabFragment.activity == null) {
            MatchTabFragment.activity = (MainActivity) activity;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadMatches() {
        activity.setLoading(true);
        Db.getMatches(activity, category, rpa, date, new FindCallback<Match>() {
	        @Override
	        public void done(final List<Match> list, ParseException e) {
		        if (list != null && !list.isEmpty()) {
			        ParseObject.pinAllInBackground(list);
			        HashMap<String, List<Match>> groupTeams = getGroupMatches(list);
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
				        ListView listView = (ListView) activity.getLayoutInflater().inflate(R.layout.fragment_match_list, null);
				        listView.setAdapter(new MatchAdapter(activity, groupTeams.get(group)));
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

    private HashMap<String, List<Match>> getGroupMatches(List<Match> matches){
        HashMap<String, List<Match>> groupMatches = new HashMap<>();
        for (Match match : matches){
            if (!Util.isNullOrEmpty(match.getGroup())) {
                if (!groupMatches.containsKey(match.getGroup())) {
                    groupMatches.put(match.getGroup(), new ArrayList<Match>());
                }
                groupMatches.get(match.getGroup()).add(match);
            }
        }
        return groupMatches;
    }

    private void addListHeader(ListView listView, String group){
        try {
            View headerView = activity.getLayoutInflater().inflate(R.layout.list_header_match, null);
            TextView groupView = (TextView) headerView.findViewById(R.id.group);
            groupView.setText(activity.getString(R.string.group) + " " + group);
            listView.addHeaderView(headerView, null, false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}