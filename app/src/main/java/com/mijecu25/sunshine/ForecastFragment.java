package com.mijecu25.sunshine;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private static final String CLASS_NAME = ForecastFragment.class.getSimpleName();

    public ForecastFragment() {
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

    public class FetchWeatherTask extends AsyncTask {

        private final String CLASS_NAME = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw json response
            String forecastJsonString = "";

            try {
                // Construct the URL for the OpenWeatherMap query. Possible parameters are available
                // at OWM's forecast API page.
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/" +
                        "daily?q=94043&mode=json&units=metric&cnt=7");

                // Create the request to OpenWatherMap, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null) {
                    // Nothing to do
//                    forecastJsonString = null;
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";

                while((line = reader.readLine()) != null) {
                    // Since it is JSON, adding a newline is not necessary. It will not affect parsing.
                    // But it does make debugging a lot easier if you print out the completed buffer
                    // for debugging.
                    buffer.append(line + '\n');
                }

                if(buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
//                    forecastJsonString = null;
                    return null;
                }

                forecastJsonString = buffer.toString();

            }
            catch(IOException ioe) {
                Log.e(ForecastFragment.CLASS_NAME, "Error", ioe);

                // If the code did not successfully get the weather data, there is no point in
                // attempting to parse it.
//                forecastJsonString = null;
                return null;
            }
            finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }

                if(reader != null) {
                    try {
                        reader.close();
                    }
                    catch (final IOException ioe) {
                        Log.e(ForecastFragment.CLASS_NAME, "Error closing stream", ioe);
                    }
                }
            }

            return null;
        }
    }

}


