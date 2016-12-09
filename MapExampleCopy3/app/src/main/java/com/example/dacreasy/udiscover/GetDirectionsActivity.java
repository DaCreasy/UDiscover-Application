package com.example.dacreasy.udiscover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

/**
 * This activity prompts the user to choose a destination location from a list of locations on or close to the Ursinus College campus.
 */

public class GetDirectionsActivity extends Activity {

    private boolean firstSpinnerSelection = true; //I have to set it here since there is no method that runs only once

    /** 
    * Creates the search bar for people who know the name of their destination and a drop down list of locations for people who might not.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        String[] locations = getResources().getStringArray(R.array.locations_array); //Alphabetical list of locations (from strings.xml)
        setUpSearchBar(locations);
        setUpChoiceList(locations);

    }

    /**
    * Displays the search bar a user can type a destination in.
    */
    public void setUpSearchBar(String[] locations)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
        final AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.places_list);
        textView.setThreshold(1);   //when 1 letter is typed, options show up in the View

        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //When an item is clicked in the View, the listener runs this method
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                String destination = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                textView.setText("");

                intent.putExtra("DESTINATION_CHOICE", destination);
                startActivity(intent);
            }
        });
    }

    /**
    * Displays the drop down list of locations (a Spinner) that a user can choose from.
    */
    private void setUpChoiceList(String[] locations)
    {
        final Spinner spinner = (Spinner) findViewById(R.id.destination_spinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id) {
                if (firstSpinnerSelection)
                {
                    firstSpinnerSelection = false;
                    return; //This is a bug in android where when you open an activity with a Spinner, it automatically selects
                            //the first item in a Spinner.
                }
                String destination = (String) spinner.getSelectedItem();
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);

                intent.putExtra("DESTINATION_CHOICE", destination);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

