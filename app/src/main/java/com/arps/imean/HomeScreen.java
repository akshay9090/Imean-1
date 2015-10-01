package com.arps.imean;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HomeScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //THE SEARCH EDIT TEXT
        final String nullcheck = "";// A NULL STRING
        final EditText searchEditText = (EditText)findViewById(R.id.searchEditText);



        //SEARCH BUTTON
        Button searchButton = (Button)findViewById(R.id.searchButton);
        //EVENT HANDLING
        searchButton.setOnClickListener(

                //Starting up an interface for an event
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        if (nullcheck.equals(searchEditText.getText().toString())) {

                            // TOAST
                            Toast.makeText(HomeScreen.this, "enter a word to search for", Toast.LENGTH_LONG).show();

                        } else {

                            //MEANING ACTIVITY LAUNCHED
                            Intent launchMeaningIntent = new Intent(HomeScreen.this, MeaningDisplay.class);
                            startActivity(launchMeaningIntent);
                        }
                    }

                }
        );

        String[] labels = {"positive words","negative words","harsh words","physical description"};

        ListAdapter labelsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,labels);
        ListView labelsListView = (ListView)findViewById(R.id.labelsListView);


        labelsListView.setAdapter(labelsAdapter);

        labelsListView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent,View view, int position, long id) {

                       // String label = String.valueOf(parent.getItemAtPosition(position));
                        Intent launchWordListIntent = new Intent(HomeScreen.this, LabelWordList.class);
                        startActivity(launchWordListIntent);

                    }

                }

        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
}
