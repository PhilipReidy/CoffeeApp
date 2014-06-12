package org.zeero.coffeeshopmap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FragmentTwo extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			

		View view = inflater.inflate(R.layout.info_page, container,
				false);
		
		final String mimeType = "text/html";
	    final String encoding = "utf-8";
	    final String html = "<h1>Header</h1><p>This is HTML in a string</p>";
//	    html += "<body><h1>Image?</h1><img src="icon.png" /></body></html>";
	    
//	    WebView wv = (WebView) view.findViewById(R.id.webview);
//	    wv.loadDataWithBaseURL("file:///android_asset/images/photo_3.jpg", html, mimeType, encoding, "");

		return view;
	
	}
	

}
