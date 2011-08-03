package com.bugcloud.android.veny.activity;

import com.bugcloud.android.veny.R;
import com.bugcloud.android.veny.activity.webviewhelper.GeolocationWebViewInterface;
import com.bugcloud.android.veny.share.Constants;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions;

public class TopActivity extends BaseActivity implements LocationListener {
	private WebView webView;
	private Activity mActivity;
	private LocationManager locationManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.top);
        initWebView();
    }
    
    @Override
	public void onStart() {
		super.onStart();
		setProgressBarIndeterminateVisibility(true);
		setLocationAndLoad();
    }
    
    private void initWebView() {
    	webView = (WebView) findViewById(R.id.web_view);
        WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		int scale = (int)((float)display.getWidth() / 640 * 100);
		webView.setInitialScale(scale);
		webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setGeolocationEnabled(true);
		setting.setBuiltInZoomControls(true);
		mActivity = this;
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView wv, int progress){
				if(progress == 100) {
					mActivity.setProgressBarIndeterminateVisibility(false);
				} else if (progress < 90) {
					mActivity.setProgressBarIndeterminateVisibility(true);
				}
			}

		    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
		        callback.invoke(origin, true, false);
		    }
		});
    }
    
    private void setLocationAndLoad() {
    	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

	@Override
	public void onLocationChanged(Location location) {
		GeolocationWebViewInterface jsInterface = new GeolocationWebViewInterface(this);
		jsInterface.setLatitude(String.valueOf(location.getLatitude()));
		jsInterface.setLongitude(String.valueOf(location.getLongitude()));
		webView.addJavascriptInterface(jsInterface, "_android_activity");
		locationManager.removeUpdates(this);
		webView.loadUrl(Constants.VENY_ROOT_URL);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}