package com.sharpandroid.update;

import android.content.Context;
import android.content.pm.PackageManager;

import com.sharpandroid.BuildConfig;

public class UtilsCheckApp {

	public static String getInstalledAppVersion(Context context) {
		String version = "";

		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		int subNum = version.lastIndexOf("_");
		if (subNum != -1) {
			return version.substring(0, subNum);
		} else {
			return version;
		}
	}


	public static Boolean isUpdateAvailable(Context context, String updateVersion) {
		if (BuildConfig.DEBUG || "INSTANT_RUN".equals(BuildConfig.VERSION_NAME)) {
			return false;
		} else {
			Version update = new Version(updateVersion);
			String version = getInstalledAppVersion(context);
			Version latest = new Version(version);
			return update.compareTo(latest) > 0;
		}
	}

}
