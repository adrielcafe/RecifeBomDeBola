package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.ui.adapter.ViewPagerAdapter;
import com.adrielcafe.recifebomdebola.ui.tab.LeaderBoardTabFragment;
import com.joanzapata.android.iconify.Iconify;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderBoardFragment extends Fragment {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.red_cards)
    IconTextView redCardsView;
    @Bind(R.id.yellow_cards)
    IconTextView yellowCardsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        ButterKnife.bind(this, root);
        redCardsView.setTypeface(Iconify.getTypeface(getActivity()));
        yellowCardsView.setTypeface(Iconify.getTypeface(getActivity()));
        setupViewPager();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        int rpa = MainActivity.currentRpa;
        for(Category category : Db.categories) {
            Fragment fragment = LeaderBoardTabFragment.newInstance(category, rpa);
            adapter.addFrag(fragment, category.getName());
        }

        viewPager.clearOnPageChangeListeners();
        viewPager.setAlwaysDrawnWithCacheEnabled(false);
        viewPager.setDrawingCacheEnabled(false);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);

        MainActivity activity = (MainActivity) getActivity();
        try {
            activity.tabLayout.setTabsFromPagerAdapter(null);
            activity.tabLayout.setOnTabSelectedListener(null);
        } catch (Exception e){ }
        activity.tabLayout.removeAllTabs();
        activity.tabLayout.setupWithViewPager(viewPager);
    }
    
}