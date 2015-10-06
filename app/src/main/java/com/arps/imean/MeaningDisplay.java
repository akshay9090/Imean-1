package com.arps.imean;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MeaningDisplay extends ActionBarActivity{

    EditText addLabelEditText;
    Button addLabelButton;
    TextView meaningTextView;
    MyDBHandler myDB;

    private String newMeaning;
    private static final String TAG = "Http Connection";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_display);

        addLabelEditText = (EditText)findViewById(R.id.addLabelEditText);
        addLabelButton = (Button)findViewById(R.id.addLabelButton);
        meaningTextView = (TextView)findViewById(R.id.meaningTextView);

        //making the textView scrollable
        meaningTextView.setMovementMethod(new ScrollingMovementMethod());
        Intent incomingIntent = getIntent();
        final String search = incomingIntent.getStringExtra("search");

        myDB = new MyDBHandler(this);
        //final String newMeaning = new String();
        final String url = "http://api.wordnik.com:80/v4/word.json/" + search + "/definitions?limit=5&includeRelated=false&sourceDictionaries=wiktionary%2Cwebster%2Cwordnet&useCanonical=false&includeTags=false&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5\n";


        new AsyncHttpTask().execute(url);
        // db = openOrCreateDatabase("imeanDB", Context.MODE_PRIVATE,null);
        //db.execSQL("DROP TABLE IF EXISTS words");
        //db.execSQL("CREATE TABLE IF NOT EXISTS words(_word VARCHAR, _meaning VARCHAR, _label VARCHAR);");

        //meaningTextView.setText(newMeaning);




        //EVENT HANDLING
        addLabelButton.setOnClickListener(

                //Starting up an interface for an event
                new Button.OnClickListener() {

                    public void onClick(View view) {

                        // TOAST
                        //Toast.makeText(MeaningDisplay.this, "button clicked", Toast.LENGTH_SHORT).show();
                        String newLabel = addLabelEditText.getText().toString();
                        //String newMeaning = "meaning to be added";
                        //String newMeaning = new String();
                        myDB.insertWord(search, newMeaning, newLabel);
                        //db.execSQL("INSERT INTO words(_word, _meaning, _label) VALUES('"+search+"','"+newLabel+"','"+newMeaning+"');");
                        Intent launchLabelListIntent = new Intent(MeaningDisplay.this, HomeScreen.class);
                        startActivity(launchLabelListIntent);

                        //Database();
                        // Toast.makeText(MeaningDisplay.this, "word added", Toast.LENGTH_SHORT).show();


                    }


                }
        );




    }

    /*public void printDatabase(){

        //Toast.makeText(MeaningDisplay.this, "edsaafafa", Toast.LENGTH_SHORT).show();

        Cursor c=m.rawQuery("SELECT * FROM words", null);
        if(c.getCount()==0)
        {
            Toast.makeText(MeaningDisplay.this, "error,no word found", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer=new StringBuffer();

        while(c.moveToNext())
        {
            buffer.append(c.getString(0)+" ");
            buffer.append(c.getString(1)+" ");
            buffer.append(c.getString(2)+"\n");
        }
        meaningTextView.setText(buffer);
        addLabelEditText.setText("");


    }*/

   /* public void addButtonCLicked(View view){

        WordsAndLabels wordNew = new WordsAndLabels(meaningTextView.getText().toString());

        dbHandler.addRow(wordNew);

    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meaning_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }


        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */

            if(result == 1){

                //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newMeaning);

                meaningTextView.setText(newMeaning);
            }else{
                Log.e(TAG, "Failed to fetch data!");
            }
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

            String line = "";
            String result = "";

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            /* Close Stream */
            if(null!=inputStream){
                inputStream.close();
            }

            return result;
        }
        private void parseResult(String result) {

            try{
                JSONArray response = new JSONArray(result);
                newMeaning = new String();

                for (int i = 0; i < response.length(); i++) {
                    JSONObject definition = response.getJSONObject(i);
                    if(i==0) {
                        newMeaning = definition.getString("text") + "\n\n";
                    }
                    else{
                        newMeaning = newMeaning + definition.getString("text") + "\n\n";
                    }

                }


            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
