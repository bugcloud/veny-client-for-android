package com.bugcloud.android.veny.activity.webviewhelper;

import android.app.Activity;
import android.util.Log;

public class BaseWebViewInterface {
	Activity activity;
	
	public BaseWebViewInterface(Activity activity) {
		this.activity = activity;
	}
	
	public void log(String str) {
		Log.d("BaseWebViewInterface", str);
	}
}
