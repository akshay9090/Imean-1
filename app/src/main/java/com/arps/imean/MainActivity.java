package com.arps.imean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rishabhButton = (Button)findViewById(R.id.RishabhButton);
        Button rishabhNextButton = (Button)findViewById(R.id.RishabhNextActButton);

        //EVENT HANDLING
        rishabhButton.setOnClickListener(

                //Starting up an interface for an event
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        TextView rishabhText = (TextView) findViewById(R.id.RishabhText);
                        rishabhText.setText("OH YOU CLICKED THE BUTTON");

                    }
                }
        );

        //ANOTHER EVENT TO BE HANDLED BY THE SAME VIEW - BUTTON

        rishabhButton.setOnLongClickListener(

                new Button.OnLongClickListener(){

                    public boolean onLongClick( View v){

                        TextView rishabhText = (TextView) findViewById(R.id.RishabhText);
                        rishabhText.setText("LONG LONG LONG LONG");
                        return true;
                    }
                }
        );

        rishabhNextButton.setOnClickListener(

                new Button.OnClickListener(){

                    public void onClick(View v) {

                        Intent changeScreenIntent = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(changeScreenIntent);
                    }
                }
        );

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
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        switch (item.getItemId())
        {
            case R.id.preferences:
                startActivity(new Intent(this, EditPreferences.class));
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
