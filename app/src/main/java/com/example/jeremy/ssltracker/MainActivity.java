package com.example.jeremy.ssltracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    //Array

    //List View: {views:items.xml}

    public static SerializableArrayList serializableData;
    String webTrackerItemName = new String();
    String webTrackerItemType = new String();
    GregorianCalendar webTrackerItemDate = new GregorianCalendar();

    private static boolean initialAppOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (initialAppOpen)
        {
            try {
                FileInputStream fileStream = this.getApplicationContext().openFileInput("tracker_data_array_06.bin");
                ObjectInputStream obj = new ObjectInputStream(fileStream);
                serializableData = (SerializableArrayList) obj.readObject();
                fileStream.close();
                obj.close();
            }
            catch (FileNotFoundException ex)
            {
                serializableData = new SerializableArrayList();
            }
            catch (IOException ex)
            {
                Toast.makeText(MainActivity.this, "Data Load Error", Toast.LENGTH_SHORT).show();
            }
            catch (ClassNotFoundException ex) {
                Toast.makeText(MainActivity.this, "Class Not Found", Toast.LENGTH_SHORT).show();
            }

            initialAppOpen = false;
        }

        updateDisplayList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                updateIntent.putExtra("data_index", -1000);
                startActivity(updateIntent);
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void updateDisplayList()
    {
        boolean colourSwap = false;

        TableLayout t = (TableLayout) findViewById(R.id.mainTableLayout);
        t.removeAllViews();

        Collections.sort(serializableData.dataList, new Comparator<WebTrackerData>() {
            @Override
            public int compare(WebTrackerData p1, WebTrackerData p2) {
                return p1.getRenewal().compareTo(p2.getRenewal());
            }

        });

        System.out.println("dataList count: " + serializableData.dataList.size());
        for(int index = 0; index < serializableData.dataList.size(); index++){
            final int indexExtra = index;
            WebTrackerView w = new WebTrackerView(this.getApplicationContext(), null);

            if (colourSwap) {
                w.setBackgroundColor(Color.parseColor("#f5f5f0"));
            }
            else {
                w.setBackgroundColor(Color.parseColor("#e0e0d1"));
            }

            colourSwap = !colourSwap;

            w.updateViewData(serializableData.dataList.get(index));

            TableRow r = new TableRow(this.getApplicationContext(), null);
            r.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));


            t.addView(r);
            r.addView(w);

            w.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                    //updateIntent.putExtra("data_object", temp);

                    updateIntent.putExtra("data_index", indexExtra);
                    startActivity(updateIntent);
                }
            });
        }
    }

    public class CustomComparator implements Comparator<WebTrackerData>
    {
        @Override
        public int compare(WebTrackerData lhs, WebTrackerData rhs) {
            return lhs.getRenewal().compareTo(rhs.getRenewal());
        }
    }

}
