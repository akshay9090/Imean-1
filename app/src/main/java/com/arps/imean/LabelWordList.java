package com.arps.imean;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class LabelWordList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_word_list);


        String[] words = {"words1","word2","word3","word4"};
        ListAdapter wordsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,words);
        ListView wordsListView = (ListView)findViewById(R.id.wordsListView);

        wordsListView.setAdapter(wordsAdapter);

        wordsListView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent,View view, int position, long id) {

                        String word = String.valueOf(parent.getItemAtPosition(position));
                        Intent changeScreenIntent3 = new Intent(LabelWordList.this, OneWordMeaning.class);
                        startActivity(changeScreenIntent3);

                    }

                }

        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_label_word_list, menu);
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
