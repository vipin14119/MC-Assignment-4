package com.app.vipin.infogetter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mDialog;
    private String url = "https://www.iiitd.ac.in/about";
    static String htmlData = new String();
    private static final String SAVED_CONTENT = "Saved Content";
    private TextView buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buffer = (TextView)findViewById(R.id.buffer);
        if (savedInstanceState != null){
            htmlData = savedInstanceState.getString(SAVED_CONTENT);
            buffer.setText(htmlData);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CONTENT, htmlData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fetch_button){
            new MyTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyTask extends AsyncTask<Void, Void,Void >{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setTitle("Downloading Content");
            mDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            buffer.setText(htmlData);
            mDialog.dismiss();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("#node-10 .content p");
                String para1 = elements.get(2).text();
                String para2 = elements.get(3).text();
                Log.d("MG","==================="+para1);
                Log.d("MG","==================="+para2);
                htmlData= para1+"\n\n\n"+para2;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
