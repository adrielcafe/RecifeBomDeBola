package com.adrielcafe.recifebomdebola.ui.tab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Agenda;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.ui.MainActivity;
import com.adrielcafe.recifebomdebola.ui.adapter.AgendaAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgendaTabFragment extends Fragment {
    private MainActivity activity;
    private String category;
    private int rpa;

    @Bind(android.R.id.list)
    ListView listView;

    public static AgendaTabFragment newInstance(Category category, int rpa) {
        Bundle args = new Bundle();
        args.putString(App.EXTRA_CATEGORY, category.getName());
        args.putInt(App.EXTRA_RPA, rpa);
        AgendaTabFragment frag = new AgendaTabFragment();
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
        View v = inflater.inflate(R.layout.tab_agenda, container, false);
        ButterKnife.bind(this, v);
        loadAgenda();
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

    private void loadAgenda() {
        activity.setLoading(true);
        Db.getAgenda(activity, category, rpa, new FindCallback<Agenda>() {
            @Override
            public void done(final List<Agenda> list, ParseException e) {
                ParseObject.pinAllInBackground(list);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new AgendaAdapter(activity, list));
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