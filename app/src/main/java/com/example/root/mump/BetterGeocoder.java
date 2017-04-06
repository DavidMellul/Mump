package com.example.root.mump;

/**
 * Created by root on 06/04/17.
 */


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;

/**
 * Created by David on 26/02/2017.
 */

// This class provides convenient methods to work with the Geocoder, and it works even if the Geocoder native service isn't available
public class BetterGeocoder {

    // This method returns a Location object based on an address
    public static String getNameFromCoordinates(Activity a, double l, double L) {
        Geocoder g = new Geocoder(a);
        // If the use of GeoCoder is supported on this phone, use it
        if (g.isPresent()) {
            try {
                System.out.println(l+" MA:BITE "+L);
                Address place = g.getFromLocation(l, L,1).get(0);
                return place.getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Else, make a workaround with the Google Maps API
        else {
            return null;
        }

        return null;
    }
}