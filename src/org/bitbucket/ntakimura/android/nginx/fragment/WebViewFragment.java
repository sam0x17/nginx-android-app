package org.bitbucket.ntakimura.android.nginx.fragment;

import org.bitbucket.ntakimura.android.nginx.R;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * WebView Fragment.
 */
public class WebViewFragment extends Fragment {

	@SuppressLint("SetJavaScriptEnabled")
    @Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_webview, null);
		view.setBackgroundColor(getResources().getColor(android.R.color.background_light));

		WebView webview = (WebView) view.findViewById(R.id.fragment_webview_webview);
		webview.setWebViewClient(new LocalHostWebViewClient());
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.loadUrl("http://localhost:8080/");

		Button backButton = (Button) view.findViewById(R.id.fragment_webview_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
				if (webview.canGoBack()) {
					webview.goBack();
				}
			}
		});

		Button forwardButton = (Button) view.findViewById(R.id.fragment_webview_forward);
		forwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
				if (webview.canGoForward()) {
					webview.goForward();
				}
			}
		});

		ImageButton reloadButton = (ImageButton) view.findViewById(R.id.fragment_webview_reload);
		reloadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
				webview.reload();
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
		webview.resumeTimers();
	}

	@Override
	public void onPause() {		
		super.onPause();

		WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
		webview.pauseTimers();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (getView() != null) {
			WebView webview = (WebView) getView().findViewById(R.id.fragment_webview_webview);
			if (webview != null) {
				webview.destroy();
			}
		}
	}

	/**
	 * localhost webview client.
	 */
	private class LocalHostWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
			if (Uri.parse(url).getHost().equals("localhost")) {
				return false;
			}

			return true;
		}
	}
}
