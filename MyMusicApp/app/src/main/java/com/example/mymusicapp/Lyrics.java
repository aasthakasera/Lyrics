package com.example.mymusicapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Lyrics extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        Button startlyrics = (Button) findViewById(R.id.get_button);
        startlyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(MainActivity.this,Lyrics.class);
                //startActivity(k);
                DetailAsyncTask detail = new DetailAsyncTask();
                detail.execute();

                FetchAsyncTask task = new FetchAsyncTask ();
                task.execute();
            }
        });

    }

    private void updateLyricsUi(Do f1) {
        TextView trackTextView = (TextView) findViewById(R.id.song_title);
        trackTextView.setText(f1.t);
        TextView songTextView = (TextView) findViewById(R.id.name);
        songTextView.setText(f1.a);
        TextView albumTextView = (TextView) findViewById(R.id.album);
        albumTextView.setText(f1.al);
    }

    private class DetailAsyncTask extends AsyncTask <java.net.URL, Void , Do> {

        @Override
        protected Do doInBackground(URL... urls) {
            EditText art = (EditText) findViewById(R.id.artist_name);
            String artistName = art.getText().toString();
            EditText song = (EditText) findViewById(R.id.song);
            String songName = song.getText().toString();
            final String URL1 = ("https://theaudiodb.com/api/v1/json/1/searchtrack.php?s="+artistName+"&t="+songName);
            URL url1 = createUrl(URL1);

            String jsonResponse1 = "";
            try {
                jsonResponse1 = makeHttpRequest(url1);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            Do f1 = extractFeatureFromJson(jsonResponse1);


            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return f1;
        }
        @Override
        protected void onPostExecute(Do f1) {
            if (f1 == null) {
                return;
            }

            updateLyricsUi (f1);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
        private Do extractFeatureFromJson(String detailJSON) {
            try {
                JSONObject Response = new JSONObject(detailJSON);

                JSONArray trackTitleArray = Response.getJSONArray("track");

                JSONObject firstFeature = trackTitleArray.getJSONObject(0);

                String string = firstFeature.getString("strTrack");
                String artist = firstFeature.getString ("strArtist");
                String album = firstFeature.getString ("strAlbum");
                return new Do(string,artist,album);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the detail JSON results", e);
            }
            return null;
        }
    }

    private void updateUi(Event f) {
        TextView titleTextView = (TextView) findViewById(R.id.artist);
        titleTextView.setText(f.title);
    }

    private class FetchAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            // Create URL object
            EditText art = (EditText) findViewById(R.id.artist_name);
            String artistName = art.getText().toString();
            Log.v("MainActivity","get name" + artistName);
            EditText song = (EditText) findViewById(R.id.song);
            String songName = song.getText().toString();

            final String URL = "https://api.lyrics.ovh/v1/"+ artistName +"/" + songName +"";

            URL url = createUrl (URL);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest (url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            Event f = extractFeatureFromJson (jsonResponse);
            return f;
        }

        @Override
        protected void onPostExecute(Event f) {
            if (f == null) {
                return;
            }

            updateUi(f);
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private Event extractFeatureFromJson(String eJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(eJSON);

                String string = baseJsonResponse.getString ("lyrics");
                return new Event(string);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
