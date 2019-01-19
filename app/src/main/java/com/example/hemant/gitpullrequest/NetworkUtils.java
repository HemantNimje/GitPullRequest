package com.example.hemant.gitpullrequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * Checks if connected to the network
     *
     * @return boolean which indicated if the device is connected to the internet
     */
    static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
