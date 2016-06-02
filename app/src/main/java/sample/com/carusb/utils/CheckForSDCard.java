package sample.com.carusb.utils;

import android.os.Environment;

public class CheckForSDCard {
	public CheckForSDCard() {

	}

	public boolean isSDCardPresent() {
		if (Environment.getExternalStorageState().equals(

		Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
