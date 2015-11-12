package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.joanzapata.android.iconify.Iconify;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int DELAY = 275;

    private Fragment currentFragment;
    private MatchesFragment matchesFragment;
    private LeaderBoardFragment leaderBoardFragment;

    public MenuItem rpaMenuItem;
    private MenuItem facebookMenuItem;
    private MenuItem instagramMenuItem;

    private CharSequence title;
    public static int currentRpa;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout containerLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_drawer)
    NavigationView navigationView;
    @Bind(R.id.progress)
    CircleProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        progressView.setCircleBackgroundEnabled(true);
        progressView.setColorSchemeColors(R.color.primary_dark);
        progressView.setBackgroundColor(getResources().getColor(R.color.primary_dark));
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initNavigationDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        drawerLayout.closeDrawers();
        switch (menuItem == null ? R.id.drawer_matches : menuItem.getItemId()) {
            case R.id.drawer_matches:
                if(currentFragment instanceof MatchesFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_agenda);
                matchesFragment = new MatchesFragment();
                currentFragment = matchesFragment;
                break;
            case R.id.drawer_leaderboard:
                if(currentFragment instanceof LeaderBoardFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_leaderboard);
                leaderBoardFragment = new LeaderBoardFragment();
                currentFragment = leaderBoardFragment;
                break;
            case R.id.drawer_fields:
                if(currentFragment instanceof FieldsFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_fields);
                currentFragment = new FieldsFragment();
                break;
//            case R.id.drawer_judgments:
//                if(currentFragment instanceof JudgmentsFragment){
//                    return false;
//                }
//                title = getString(R.string.navigation_drawer_item_judgments);
//                currentFragment = new JudgmentsFragment();
//                break;
            case R.id.drawer_rules:
                if(currentFragment instanceof RulesFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_rules);
                currentFragment = new RulesFragment();
                break;
            case R.id.drawer_photos:
                if(currentFragment instanceof PhotosFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_photos);
                currentFragment = new PhotosFragment();
                break;
            case R.id.drawer_contact:
                if(currentFragment instanceof ContactFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_contact);
                currentFragment = new ContactFragment();
                break;
            case R.id.drawer_about:
                if(currentFragment instanceof AboutFragment){
                    return false;
                }
                title = getString(R.string.navigation_drawer_item_about);
                currentFragment = new AboutFragment();
                break;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, currentFragment)
                        .commit();
            }
        }, DELAY);

        if(menuItem != null) {
            menuItem.setChecked(!menuItem.isChecked());
        }
        updateToolbar();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        facebookMenuItem = menu.findItem(R.id.action_facebook);
        instagramMenuItem = menu.findItem(R.id.action_instagram);
        rpaMenuItem = menu.findItem(R.id.rpas);

        Util.configMenuItem(this, facebookMenuItem, Iconify.IconValue.fa_facebook_square);
        Util.configMenuItem(this, instagramMenuItem, Iconify.IconValue.fa_instagram);

        Spinner rpaSpinner = (Spinner) MenuItemCompat.getActionView(rpaMenuItem);
        rpaSpinner.setAdapter(new ArrayAdapter<>(getSupportActionBar().getThemedContext(), R.layout.spinner_item_rpa, Db.rpas));
        rpaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		        currentRpa = Db.getRpaAtIndex(position);
		        try {
			        if (currentFragment instanceof MatchesFragment) {
				        matchesFragment.setupViewPager();
			        } else if (currentFragment instanceof LeaderBoardFragment) {
				        leaderBoardFragment.setupViewPager();
			        }
		        } catch (Exception e) {
		        }
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        }
        });

        updateToolbar();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_facebook:
                Util.openUrl(this, App.FACEBOOK_URL);
                break;
            case R.id.action_instagram:
                Util.openUrl(this, App.INSTAGRAM_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNavigationDrawer(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        onNavigationItemSelected(null);
        actionBarDrawerToggle.syncState();
    }

    private void updateToolbar() {
        SpannableString customTitle = Util.addCustomFont(title);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(customTitle);

        try {
            tabLayout.setVisibility(View.GONE);
            rpaMenuItem.setVisible(false);
            facebookMenuItem.setVisible(false);
            instagramMenuItem.setVisible(false);
            if(currentFragment instanceof MatchesFragment || currentFragment instanceof LeaderBoardFragment){
                tabLayout.setVisibility(View.VISIBLE);
                rpaMenuItem.setVisible(true);
            } else if(currentFragment instanceof ContactFragment || currentFragment instanceof PhotosFragment){
                facebookMenuItem.setVisible(true);
                instagramMenuItem.setVisible(true);
            }
        } catch (Exception e){ }
    }

    public void setLoading(boolean isLoading){
        progressView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}