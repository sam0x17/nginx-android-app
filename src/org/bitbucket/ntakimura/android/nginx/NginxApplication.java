package org.bitbucket.ntakimura.android.nginx;

import java.io.File;

import org.bitbucket.ntakimura.android.nginx.utils.NginxConfigureTask;
import org.bitbucket.ntakimura.nginx.Nginx;

import android.app.Application;

/**
 * Nginx Application.
 */
public class NginxApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		File sdcard = Nginx.create().getPrefix();
		(new NginxConfigureTask(this, sdcard)).execute(
				NginxConfigureTask.NGINX_CONF_FILENAME,
				NginxConfigureTask.NGINX_MIMETYPES_FILENAME);
	}

}
