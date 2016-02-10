package com.mijecu25.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create array list of fake data
        ArrayList<String> weekForecast = new ArrayList<>();

        weekForecast.add("Today - Sunny - 88/63");
        weekForecast.add("Tomorrow - Snow - 20/12");
        weekForecast.add("Weds - Cloudy - 52/40");
        weekForecast.add("Thur - Rain - 42/35");
        weekForecast.add("Fri - Snow - 20/15");
        weekForecast.add("Sat - Rain - 42/35");

        // Create array adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(),
                                                               R.layout.list_item_forecast,
                                                               R.id.list_item_forecast_textview,
                                                               weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Reference to fragment's list view
        ListView listviewForecast = (ListView) rootView.findViewById(R.id.listview_forecast);

        // Set array adapter to listview
        listviewForecast.setAdapter(arrayAdapter);

        return rootView;
    }
}
