package com.arps.imean;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MeaningDisplay extends ActionBarActivity{

    EditText addLabelEditText;
    Button addLabelButton;
    TextView meaningTextView;
    SQLiteDatabase db;


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


       db = openOrCreateDatabase("imeanDB", Context.MODE_PRIVATE,null);
        db.execSQL("DROP TABLE IF EXISTS words");
        db.execSQL("CREATE TABLE IF NOT EXISTS words(_word VARCHAR, _meaning VARCHAR, _label VARCHAR);");

        meaningTextView.setText("");




        //EVENT HANDLING
        addLabelButton.setOnClickListener(

                //Starting up an interface for an event
                new Button.OnClickListener() {

                    public void onClick(View view) {

                        // TOAST
                        //Toast.makeText(MeaningDisplay.this, "button clicked", Toast.LENGTH_SHORT).show();
                        String newLabel = addLabelEditText.getText().toString();
                        String newMeaning = "meaning to be added";
                        db.execSQL("INSERT INTO words(_word, _meaning, _label) VALUES('"+search+"','"+newLabel+"','"+newMeaning+"');");


                        printDatabase();
                       // Toast.makeText(MeaningDisplay.this, "word added", Toast.LENGTH_SHORT).show();


                    }


                }
        );




    }

    public void printDatabase(){

        //Toast.makeText(MeaningDisplay.this, "edsaafafa", Toast.LENGTH_SHORT).show();

        Cursor c=db.rawQuery("SELECT * FROM words", null);
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
