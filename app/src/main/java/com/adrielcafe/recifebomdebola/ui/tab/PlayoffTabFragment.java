package com.adrielcafe.recifebomdebola.ui.tab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Playoff;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.PlayoffAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayoffTabFragment extends Fragment {
    private static MainActivity activity;
    private String category;
    private int rpa;
    private int key;

    @Bind(android.R.id.list)
    ListView listView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;

    public static PlayoffTabFragment newInstance(Category category, int rpa, int key) {
        Bundle args = new Bundle();
        args.putString(App.EXTRA_CATEGORY, category.getName());
        args.putInt(App.EXTRA_RPA, rpa);
        args.putSerializable(App.EXTRA_KEY, key);
        PlayoffTabFragment frag = new PlayoffTabFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString(App.EXTRA_CATEGORY);
        rpa = getArguments().getInt(App.EXTRA_RPA);
        key = getArguments().getInt(App.EXTRA_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_playoff, container, false);
        ButterKnife.bind(this, v);
        listView.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        loadLeaderBoard();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(PlayoffTabFragment.activity == null) {
            PlayoffTabFragment.activity = (MainActivity) activity;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadLeaderBoard() {
        activity.setLoading(true);
        Db.getPlayoff(activity, category, rpa, key, new FindCallback<Playoff>() {
            @Override
            public void done(final List<Playoff> list, ParseException e) {
                if (list != null && !list.isEmpty()) {
                    ParseObject.pinAllInBackground(list);
                    listView.setAdapter(new PlayoffAdapter(activity, list));
	                listView.setVisibility(View.VISIBLE);
	                emptyLayout.setVisibility(View.GONE);
                } else {
	                try {
		                listView.setVisibility(View.GONE);
		                emptyLayout.setVisibility(View.VISIBLE);
	                } catch (Exception e2){ }
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

}