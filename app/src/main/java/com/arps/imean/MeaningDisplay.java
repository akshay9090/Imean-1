package com.arps.imean;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MeaningDisplay extends ActionBarActivity {

    EditText addLabelEditText;
    Button addLabelButton;
    TextView meaningTextView;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_display);

        addLabelEditText = (EditText)findViewById(R.id.addLabelEditText);
        addLabelButton = (Button)findViewById(R.id.addLabelButton);

        meaningTextView = (TextView)findViewById(R.id.meaningTextView);

        dbHandler = new MyDBHandler(this,null,null,1);


        //EVENT HANDLING
        addLabelButton.setOnClickListener(

                //Starting up an interface for an event
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        // TOAST
                        Toast.makeText(MeaningDisplay.this, "button clicked", Toast.LENGTH_LONG).show();

                        WordsAndLabels wordNew = new WordsAndLabels(meaningTextView.getText().toString());

                        dbHandler.addRow(wordNew);


                    }


                }
        );

        printDatabase();


    }

    public void printDatabase(){

        String dbString = dbHandler.databaseToString();
        meaningTextView.setText(dbString);
        addLabelEditText.setText("");


    }

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
}
