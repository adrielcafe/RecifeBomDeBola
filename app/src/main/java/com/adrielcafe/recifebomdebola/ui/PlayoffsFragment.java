package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.ui.adapter.ViewPagerAdapter;
import com.adrielcafe.recifebomdebola.ui.tab.PlayoffTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayoffsFragment extends Fragment implements View.OnClickListener {
	private static int currentKey;

	@Bind(R.id.key_1)
	Button key1View;
	@Bind(R.id.key_2)
	Button key2View;
	@Bind(R.id.key_3)
	Button key3View;
	@Bind(R.id.key_4)
	Button key4View;
	@Bind(R.id.key_5)
	Button key5View;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playoffs, container, false);
        ButterKnife.bind(this, root);

		key1View.setOnClickListener(this);
		key2View.setOnClickListener(this);
		key3View.setOnClickListener(this);
		key4View.setOnClickListener(this);
		key5View.setOnClickListener(this);

	    onClick(key1View);
        setupViewPager();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setupViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        for(Category category : Db.categories) {
            Fragment fragment = PlayoffTabFragment.newInstance(category, MainActivity.currentRpa, currentKey);
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

	@Override
	public void onClick(View v) {
		key1View.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		key2View.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		key3View.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		key4View.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		key5View.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		key1View.setTextColor(getResources().getColor(android.R.color.white));
		key2View.setTextColor(getResources().getColor(android.R.color.white));
		key3View.setTextColor(getResources().getColor(android.R.color.white));
		key4View.setTextColor(getResources().getColor(android.R.color.white));
		key5View.setTextColor(getResources().getColor(android.R.color.white));

		Button selectedKeyView = (Button) v;
		selectedKeyView.setBackgroundColor(getResources().getColor(R.color.accent));
		selectedKeyView.setTextColor(getResources().getColor(android.R.color.black));

		switch (v.getId()){
			case R.id.key_1:
				currentKey = 1;
				break;
			case R.id.key_2:
				currentKey = 2;
				break;
			case R.id.key_3:
				currentKey = 3;
				break;
			case R.id.key_4:
				currentKey = 4;
				break;
			case R.id.key_5:
				currentKey = 5;
				break;
		}

		setupViewPager();
	}
}