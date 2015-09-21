package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.ui.adapter.ViewPagerAdapter;
import com.adrielcafe.recifebomdebola.ui.tab.MatchTabFragment;
import com.adrielcafe.recifebomdebola.ui.view.DatePicker;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchesFragment extends Fragment {
    private static DateTime currentDate;

    @Bind(R.id.date_picker)
    DatePicker datePicker;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_match, container, false);
        ButterKnife.bind(this, root);
        datePicker.setStartAndEndDateWithParts(2015, 9, 19, 2015, 12, 31);
        datePicker.setDayViewOnClickListener(new DatePicker.DayViewOnClickListener() {
            @Override
            public void onDaySelected(DateTime date) {
                if(currentDate == null || currentDate.getYear() != date.getYear() ||
                        currentDate.getMonthOfYear() != date.getMonthOfYear() ||
                        currentDate.getDayOfMonth() != date.getDayOfMonth()) {
                    currentDate = date;
                    setupViewPager();
                }
            }
        });
        datePicker.setSelectedDay(LocalDateTime.now(), false, 0);
        currentDate = DateTime.now();
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
            Fragment fragment = MatchTabFragment.newInstance(category, rpa, currentDate);
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