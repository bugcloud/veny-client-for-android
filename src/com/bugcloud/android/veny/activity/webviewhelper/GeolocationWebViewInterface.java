package com.bugcloud.android.veny.activity.webviewhelper;

import android.app.Activity;

public class GeolocationWebViewInterface extends BaseWebViewInterface {
	private String mLatitudeStr;
	private String mLongitudeStr;
	
	public GeolocationWebViewInterface(Activity activity) {
		super(activity);
	}
	
	public String getLatitude() {
		return this.mLatitudeStr;
	}
	
	public String getLongitude() {
		return this.mLongitudeStr;
	}
	
	public void setLatitude(String s) {
		this.mLatitudeStr = s;
	}
	
	public void setLongitude(String s) {
		this.mLongitudeStr = s;
	}
}