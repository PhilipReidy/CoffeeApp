package org.zeero.coffeeshopmap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FragmentThree extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			

		View view = inflater.inflate(R.layout.info_page, container,
				false);
		
		WebView mWebView;
//	    mWebView = (WebView) view.findViewById(R.id.webview);
//	    mWebView.getSettings().setJavaScriptEnabled(true);
//	    mWebView.loadUrl("file:///android_asset/new.html");
//	    mWebView.getSettings().setBuiltInZoomControls(true);

		return view;
	
	}

}