package com.arps.imean;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class OneWordMeaning extends ActionBarActivity {

    MyDBHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_word_meaning);

        Intent incomingIntent = getIntent();
        final String word = incomingIntent.getStringExtra("word");

        myDB = new MyDBHandler(this);

        final String meaning = myDB.getMeaning(word);

        TextView meaningTextView = (TextView)findViewById(R.id.meaningTextView);
        meaningTextView.setText(word+"\n\n ------------------- \n"+"Meaning: "+meaning);

        //making text view scrollable
        meaningTextView.setMovementMethod(new ScrollingMovementMethod());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_one_word_meaning, menu);
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
