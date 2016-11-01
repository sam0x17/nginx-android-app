package org.bitbucket.ntakimura.android.support.activity;

import org.bitbucket.ntakimura.android.nginx.R;
import org.bitbucket.ntakimura.android.support.fragment.DrawerListFragment;
import org.bitbucket.ntakimura.android.support.view.FragmentPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Fragment View Pager Activity.
 */
public class FragmentPagerActivity extends FragmentActivity {

    /**
     * Drawer layout.
     */
    private DrawerLayout mDrawerLayout;

    /**
     * Action bar toggle.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // initialize drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(final View view) {
                super.onDrawerClosed(view);
                FragmentPagerActivity.this.onDrawerClosed(view);
            }
            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                FragmentPagerActivity.this.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // init drawer
        DrawerListFragment drawer = (DrawerListFragment)
                getSupportFragmentManager().findFragmentById(
                        R.id.activity_main_drawer_fragment);
        drawer.setDataListSelectedListener(new DrawerListFragment.OnDataListSelectedListener() {
            @Override
            public void onDataListSelected(
                    final ListView parent, final View view, final int position,  final long id) {
                ((FragmentPager) findViewById(
                        R.id.activity_main_pager)).setCurrentItem(position);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * Drawer closed handler.
     * @param view closed view
     */
    protected void onDrawerClosed(final View view) {
        invalidateOptionsMenu();

        Fragment fragment = ((FragmentPager) findViewById(
                R.id.activity_main_pager)).getCurrentFragment();
        if (fragment.getArguments() != null) {
            getActionBar().setTitle(fragment.getArguments().getString(
                    Intent.EXTRA_TITLE, fragment.toString()));
        } else {
            getActionBar().setTitle(fragment.toString());
        }
    }

    /**
     * Drawer opened handler.
     * @param drawerView drawer view
     */
    protected void onDrawerOpened(final View drawerView) {
        getActionBar().setTitle(R.string.app_name);
        invalidateOptionsMenu();
    }

}
