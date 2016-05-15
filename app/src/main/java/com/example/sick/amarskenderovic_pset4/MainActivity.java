package com.example.sick.amarskenderovic_pset4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*Created by Amar Skenderovic 11196041
*15-5-2016
*/
public class MainActivity extends Activity {

    // Declare variables(will be used later)
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView testList;

    // Read the items in the arraylist(needed to save items)
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    // Write the items(after reading needed to write them for the next time the app gets launched)
    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        // Statement that might lead to an exception, so insert it in a try/catch block
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the listview by id
        testList = (ListView) findViewById(R.id.testList);
        items = new ArrayList<String>();
        // Read from disk
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        testList.setAdapter(itemsAdapter);
        // Setup remove listener
        setupListViewListener();

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Selected item
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), ToDoItem.class);
                // sending data to new activity
                i.putExtra("item", product);
                startActivity(i);
            }
        });
    }

    // Add a listener to the listview
    private void setupListViewListener() {
        // Create a listener for a LongClick
        testList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Update the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }

    // Create the option to be able to add new items to the TO-DO list
    public void onAddItem(View v) {
        // Find the EditText by the id
        EditText editText = (EditText) findViewById(R.id.editText_items);
        String itemText = editText.getText().toString();
        // When the add button is pressed the input is put in the list
        itemsAdapter.add(itemText);
        editText.setText("");
        writeItems();
    }
}
