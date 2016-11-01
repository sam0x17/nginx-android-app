package org.bitbucket.ntakimura.android.nginx.fragment;

import java.util.concurrent.ExecutorService;

import org.bitbucket.ntakimura.android.nginx.R;
import org.bitbucket.ntakimura.nginx.Nginx;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v4.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Manager Fragment.
 */
public class NginxManagerFragment extends PreferenceFragment {

	/**
	 * Nginx instance.
	 */
	private Nginx nginx;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.nginx_manager);

        SwitchPreference statusPreference = (SwitchPreference) findPreference(
        		getString(R.string.key_nginx_manager_nginx_status));
        statusPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(final Preference preference, final Object newValue) {
				if (((SwitchPreference) preference).isChecked() != (Boolean) newValue) {
					if ((Boolean) newValue) {
						nginx.start();
					} else {
						nginx.stop(0);
					}
				}
				return true;
			}
		});
		nginx = Nginx.create();
	}
    
    @Override
    public View onCreateView(final LayoutInflater inflater,
    		final ViewGroup container, final Bundle savedInstanceState) {
    	View view = super.onCreateView(inflater, container, savedInstanceState);
		view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
		return view;
    }

	@Override
	public void onResume() {
		super.onResume();
		
		SwitchPreference statusPref = (SwitchPreference) getPreferenceManager()
				.findPreference(getString(R.string.key_nginx_manager_nginx_status));
		statusPref.setChecked(!((ExecutorService) nginx.getExecutor()).isTerminated());
	}

}
