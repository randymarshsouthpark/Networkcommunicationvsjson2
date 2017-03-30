package com.example.a2cricg55.networkcommunicationvsjson2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button download = (Button) findViewById(R.id.thebutton);
        download.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        EditText songTitleEE = (EditText) findViewById(R.id.songTitleet);
        String songTitle = songTitleEE.getText().toString();
        EditText artistEE = (EditText) findViewById(R.id.artistet);
        String artist = artistEE.getText().toString();
        EditText yearEE = (EditText) findViewById(R.id.yearet);
        String year = yearEE.getText().toString();
        String postData = "song=" + songTitle + "&artist=" + artist + "&year=" + year;
        new AddSongAsyncTask().execute(postData);

    }

    class AddSongAsyncTask extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... params) {
            String postData = params[0];
            try {
                //set up URL; output data
                URL urlobject = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                HttpURLConnection connection = (HttpURLConnection) urlobject.openConnection();

                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(postData.length());

                OutputStream out = connection.getOutputStream();
                out.write(postData.getBytes());



                if(connection.getResponseCode() == 200){
                    InputStream in = connection.getInputStream();
                    BufferedReader br = new BufferedReader((new InputStreamReader(in)));
                    String response = "";
                    String line = br.readLine();

                    while (line != null){
                        response += line;
                        line = br.readLine();
                    }
                    return response;
                }
                else {
                    return connection.getResponseCode() + " Error ";
                }
            } catch (IOException e) {
                return "Error! " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView resultsText = (TextView) findViewById(R.id.textView);
            resultsText.setText(s);

        }

    }
}