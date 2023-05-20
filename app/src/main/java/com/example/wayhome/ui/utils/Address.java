package com.example.wayhome.ui.utils;

import android.content.Context;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Address {
    public static String getAddress(Context context, double LATITUDE, double LONGITUDE){
        //Set Address
        String address = "-";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
//                Log.d(TAG, "getAddress:  address" + address);
//                Log.d(TAG, "getAddress:  city" + city);
//                Log.d(TAG, "getAddress:  state" + state);
//                Log.d(TAG, "getAddress:  postalCode" + postalCode);
//                Log.d(TAG, "getAddress:  knownName" + knownName);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return address;
    }
}
