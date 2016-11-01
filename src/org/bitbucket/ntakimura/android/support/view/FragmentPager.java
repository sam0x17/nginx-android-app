package org.bitbucket.ntakimura.android.support.view;

import java.util.ArrayList;
import java.util.List;

import org.bitbucket.ntakimura.android.nginx.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Fragment view pager.
 */
public class FragmentPager extends ViewPager {

	/**
	 * Fragment list.
	 */
	private List<Fragment> mFragmentList = new ArrayList<Fragment>(); 

	/**
	 * Slide pager adapger.
	 */
	private ScreenSlidePagerAdapter mPagerAdapter;

	/**
	 * Constructor with context.
	 * @param context context.
	 */
	public FragmentPager(final Context context) {
		this(context, null);
	}

	/**
	 * Constructor with context and attributes.
	 * @param context context
	 * @param attrs attributes
	 */
	public FragmentPager(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		if (context instanceof FragmentActivity) {
	        setPageTransformer(true, new DepthPageTransformer());
	        mPagerAdapter = new ScreenSlidePagerAdapter(
	        		((FragmentActivity) context).getSupportFragmentManager());
	        setAdapter(mPagerAdapter);
	        setOnPageChangeListener((ViewPager.OnPageChangeListener) mPagerAdapter);
		}
	}

	/**
	 * Get current fragment.
	 * @return current fragment
	 */
	public Fragment getCurrentFragment() {
		return mPagerAdapter.getItem(getCurrentItem());
	}

	/**
	 * Set fragment list.
	 * @param fragments fragment list
	 */
	public void setFragmentList(final List<Fragment> fragments) {
		mFragmentList.clear();
		mFragmentList.addAll(fragments);
		mPagerAdapter.notifyDataSetChanged();

		if (fragments.size() > 0) {
			setCurrentItem(0, false);
			mPagerAdapter.onPageSelected(0);
		}
	}

	/**
	 * Slider pager adapter.
	 */
    private class ScreenSlidePagerAdapter
    		extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    	/**
    	 * Constructor.
    	 * @param fm fragment manager
    	 */
        public ScreenSlidePagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
        	return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(final Object object) {
            return POSITION_NONE;
        }

		@Override
		public void onPageScrollStateChanged(final int state) {
		}

		@Override
		public void onPageScrolled(
				final int position, final float positionOffset, final int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(final int position) {
			String title;

			Bundle args = mFragmentList.get(position).getArguments();
			if (args != null) {
				title = args.getString(Intent.EXTRA_TITLE,
						getContext().getString(R.string.app_name));
			} else {
				 title = getContext().getString(R.string.app_name);
			}
			
			((FragmentActivity) getContext()).getActionBar().setTitle(title);
		}
    }

    /**
     * Page transformer.
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {

    	@Override
        public void transformPage(final View view, final float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
            	
            	/*
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                */

                view.setTranslationX(pageWidth * -position + (pageWidth / 2) * position);

                view.setScaleX(1);
                view.setScaleY(1);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
    
}
