package com.example.android.sunshine.utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.sunshine.BuildConfig;
import com.example.android.sunshine.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static String getAdBannerId(Context context) {
        if (BuildConfig.DEBUG)
            return "ca-app-pub-3940256099942544/6300978111";
        int max = getLimit();
        Log.d("tag", " max limit: " + max);
        int r = new Random().nextInt(max);
        if (r < 2)
            return context.getString(R.string.banner_id_Petru);
        return "ca-app-pub-3931793949981809/9792691746";
    }

    private static int getLimit() {
        long dtMars = 1551458576000L;
        long sinceMars = System.currentTimeMillis() - dtMars;
        int daysSinceMars = (int) TimeUnit.MILLISECONDS.toDays(sinceMars);
        int monthsSinceMars = daysSinceMars / 30;
        int val = 2 + monthsSinceMars;
        if (val > 20) val = 20;
        return val;
    }


}
