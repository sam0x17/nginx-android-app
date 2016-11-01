package org.bitbucket.ntakimura.android.nginx;

import java.util.ArrayList;
import java.util.List;

import org.bitbucket.ntakimura.android.nginx.activity.SettingsActivity;
import org.bitbucket.ntakimura.android.nginx.fragment.NginxManagerFragment;
import org.bitbucket.ntakimura.android.nginx.fragment.WebViewFragment;
import org.bitbucket.ntakimura.android.support.activity.FragmentPagerActivity;
import org.bitbucket.ntakimura.android.support.fragment.DrawerListFragment;
import org.bitbucket.ntakimura.android.support.view.FragmentPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Nginx Application Main Activity.
 */
public class MainActivity extends FragmentPagerActivity {

	/**
	 * Drawer layout.
	 */
	private DrawerLayout mDrawerLayout;

	/**
	 * Action bar toggle.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
        		MainActivity.this.onDrawerClosed(view);
        	}
        	@Override
        	public void onDrawerOpened(final View drawerView) {
        		super.onDrawerOpened(drawerView);
        		MainActivity.this.onDrawerOpened(drawerView);
        	}
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        
        NginxManagerFragment managerFragment = new NginxManagerFragment();
        Bundle args = new Bundle();
        args.putString(Intent.EXTRA_TITLE, "Nginx Manager");
        managerFragment.setArguments(args);

        WebViewFragment webviewFragment = new WebViewFragment();
        args = new Bundle();
        args.putString(Intent.EXTRA_TITLE, "Web Browser");
        webviewFragment.setArguments(args);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(managerFragment);
        fragments.add(webviewFragment);
        
        // init view pager
        ((FragmentPager) findViewById(R.id.activity_main_pager)).setFragmentList(fragments);

        // init drawer
        DrawerListFragment drawer = (DrawerListFragment)
        		getSupportFragmentManager().findFragmentById(
        				R.id.activity_main_drawer_fragment);
        drawer.setHelpEnabled(true);
        drawer.setSettingsEnabled(true);
        drawer.setDataList(fragments);
        drawer.setDataListSelectedListener(new DrawerListFragment.OnDataListSelectedListener() {
			@Override
			public void onDataListSelected(
					final ListView parent, final View view, final int position,  final long id) {
			    if (position == parent.getAdapter().getCount() - 2) {
		            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			    } else if (position == parent.getAdapter().getCount() - 1) {
			        Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setData(Uri.parse(getString(R.string.help_uri)));
			        startActivity(intent);
			    } else {
	                ((FragmentPager) findViewById(
	                        R.id.activity_main_pager)).setCurrentItem(position);
			    }
				mDrawerLayout.closeDrawers();
			}
		});
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
