package org.bitbucket.ntakimura.android.support.fragment;

import java.util.List;

import org.bitbucket.ntakimura.android.nginx.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Drawer List Fragment.
 */
public class DrawerListFragment extends ListFragment {

    /**
     * settings enabled flag.
     */
    private boolean mSettingsEnabled;

    /**
     * help enabled flag.
     */
    private boolean mHelpEnabled;

	/**
	 * Array adapter for list.
	 */
	private ArrayAdapter<Object> mListAdapter = null;

	/**
	 * List selected listener.
	 */
	private OnDataListSelectedListener mListListener = null;

	/**
	 * Get new instance.
	 * @param devices data list
	 * @return fragment
	 */
	public static DrawerListFragment newInstance(final List<Object> devices) {
		DrawerListFragment fragment = new DrawerListFragment();
		return fragment;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListAdapter = new DrawerAdapter(
				getActivity(),
				R.layout.drawer_list_item);
		setListAdapter(mListAdapter);
	}

	@Override
	public void onListItemClick(final ListView parent,
			final View view, final int position, final long id) {
		super.onListItemClick(parent, view, position, id);

        if (mListListener != null) {
            mListListener.onDataListSelected(parent, view, position, id);
        }
	}

	/**
	 * Set list data.
	 * @param data list data
	 */
	public void setDataList(final List<? extends Object> data) {
		mListAdapter.clear();
		mListAdapter.addAll(data);

		if (mSettingsEnabled) {
	        Bundle settings = new Bundle();
	        settings.putString(Intent.EXTRA_SUBJECT, getString(R.string.action_settings));
	        settings.putInt(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	                android.R.drawable.ic_menu_info_details);
	        mListAdapter.add(settings);
		}

        if (mHelpEnabled) {
            Bundle settings = new Bundle();
            settings.putString(Intent.EXTRA_SUBJECT, getString(R.string.action_help));
            settings.putInt(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    android.R.drawable.ic_menu_help);
            mListAdapter.add(settings);
        }

		mListAdapter.notifyDataSetChanged();
	}

	/**
	 * Set data selected listener.
	 * @param listener listener
	 */
	public void setDataListSelectedListener(final OnDataListSelectedListener listener) {
		mListListener = listener;
	}

	/**
	 * Set enable settings menu.
	 * @param enabled menu enabled
	 */
	public void setSettingsEnabled(final boolean enabled) {
	    mSettingsEnabled = enabled;
	}

	/**
	 * Set enable help menu.
	 * @param enabled help menu enabled
	 */
	public void setHelpEnabled(final boolean enabled) {
	    mHelpEnabled = enabled;
	}

	/**
	 * List selected listener.
	 */
	public interface OnDataListSelectedListener {
		/**
		 * Data selected handler.
		 * @param parent parent list view
		 * @param view selected view
		 * @param position position
		 * @param id ID
		 */
		void onDataListSelected(final ListView parent,
				final View view, final int position, final long id);
	}

	/**
	 * Drawer Adapter.
	 */
	private class DrawerAdapter extends ArrayAdapter<Object> {

	    /**
	     * Layout resource id.
	     */
	    private int mResourceId;

	    /**
	     * Constructor.
	     * @param context context
	     * @param resource resource id
	     */
        public DrawerAdapter(final Context context, final int resource) {
            super(context, resource);
            mResourceId = resource;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            View view;
            if (convertView == null) {
                view = getLayoutInflater(null).inflate(mResourceId, null);
            } else {
                view = convertView;
            }

            Object item = getItem(position);

            if (item instanceof Fragment && ((Fragment) item).getArguments() != null) {
                Bundle args = ((Fragment) item).getArguments();

                setItemTitle(view, args, item.toString());
                setItemOptions(view, args);
            } else if (item instanceof Bundle) {
                setItemTitle(view, (Bundle) item, item.toString());
                setItemOptions(view, (Bundle) item);
            } else {
                ((TextView) view.findViewById(android.R.id.text1))
                        .setText(item.toString());
            }

            if ((mSettingsEnabled && mHelpEnabled && position >= getCount() - 2)
                    || ((mSettingsEnabled || mHelpEnabled) && position >= getCount() - 1)) {
                TextView titleView = ((TextView) view.findViewById(android.R.id.text1));
                titleView.setTextAppearance(getActivity(),
                                android.R.style.TextAppearance_Small);
            }

            return view;
        }

        /**
         * Set item title into view.
         * @param view view
         * @param args argument
         * @param title default title
         */
        private void setItemTitle(final View view, final Bundle args, final String title) {
            if (args.containsKey(Intent.EXTRA_TITLE)) {
                ((TextView) view.findViewById(android.R.id.text1))
                        .setText(args.getString(Intent.EXTRA_TITLE, title));
            } else if (args.containsKey(Intent.EXTRA_SUBJECT)) {
                ((TextView) view.findViewById(android.R.id.text1))
                        .setText(args.getString(Intent.EXTRA_SUBJECT, title));
            }
        }

        /**
         * Set item options into view.
         * @param view view
         * @param args argument
         */
        private void setItemOptions(final View view, final Bundle args) {

            // set text as description
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            if (textView != null) {
                if (args.containsKey(Intent.EXTRA_TEXT)) {
                    textView.setText(args.getString(Intent.EXTRA_TEXT, null));
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            // set icon
            ImageView iconView = (ImageView) view.findViewById(android.R.id.icon);
            if (iconView != null) {
                if (args.containsKey(Intent.EXTRA_SHORTCUT_ICON)) {
                    iconView.setImageBitmap((Bitmap) args.get(Intent.EXTRA_SHORTCUT_ICON));
                    iconView.setVisibility(View.VISIBLE);
                } else if (args.containsKey(Intent.EXTRA_SHORTCUT_ICON_RESOURCE)) {
                    iconView.setImageResource(args.getInt(Intent.EXTRA_SHORTCUT_ICON_RESOURCE));
                    iconView.setVisibility(View.VISIBLE);
                } else {
                    iconView.setVisibility(View.GONE);
                }
            }

        }

	}

}
