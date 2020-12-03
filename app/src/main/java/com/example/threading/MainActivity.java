package com.example.threading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pgbStatus);

        ProgressbarTask progressbarTask = new ProgressbarTask(this);
        progressbarTask.execute(100);


    }

    public class ProgressbarTask extends Task<Integer,Integer,Integer> {

        public ProgressbarTask(Activity activityContext) {
            super(activityContext);
        }

        @Override
        public void onPreExecute() {
            value = 0;
            progressBar.setProgress(value);
        }

        @Override
        public Integer doInBackground(Integer param) {
            while (true) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    publishProgress(value);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }

            return value;
        }

        @Override
        public void onPostExecute(Integer s) {
            progressBar.setProgress(0);
        }

        @Override
        public void onProgressUpdate(Integer s) {
            progressBar.setProgress(s);
        }
    }

}