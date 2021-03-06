package com.arps.imean;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;


public class HomeScreen extends ActionBarActivity {

    MyDBHandler myDB;

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

                        }
                        else{

                            String flabel = myDB.searchWord(searchEditText.getText().toString());

                            if (!nullcheck.equals(flabel)) {

                                // TOAST
                                Toast.makeText(HomeScreen.this, "word already exists in " + flabel, Toast.LENGTH_LONG).show();
                            }
                            else {
                                //MEANING ACTIVITY LAUNCHED
                                Intent launchMeaningIntent = new Intent(HomeScreen.this, MeaningDisplay.class);
                                launchMeaningIntent.putExtra("search", searchEditText.getText().toString());
                                startActivity(launchMeaningIntent);
                            }
                        }
                    }

                }
        );

        //String[] labels = {"positive words","negative words","harsh words","physical description"};

        myDB = new MyDBHandler(this);

        ArrayList<String> labelList = myDB.getAllLabels();

        ArrayAdapter<String> labelsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,labelList);


        ListView labelsListView = (ListView)findViewById(R.id.labelsListView);
        labelsListView.setAdapter(labelsAdapter);




        labelsListView.setOnItemLongClickListener(

                new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        String star = String.valueOf(parent.getItemAtPosition(position));
                        Intent launchWordListIntent = new Intent(HomeScreen.this, LabelWordList.class);
                        launchWordListIntent.putExtra("star", star);
                        startActivity(launchWordListIntent);

                        return true;
                    }

                }

        );
        labelsListView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent,View view, int position, long id) {

                        String label = String.valueOf(parent.getItemAtPosition(position));
                        Intent launchWordListIntent = new Intent(HomeScreen.this, LabelWordList.class);
                        launchWordListIntent.putExtra("label",label);
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
