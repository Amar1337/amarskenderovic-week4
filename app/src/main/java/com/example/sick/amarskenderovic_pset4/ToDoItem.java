package com.example.sick.amarskenderovic_pset4;

import android.content.Intent;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Created by Amar Skenderovic 11196041
*15-5-2016
*/
public class ToDoItem extends MainActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    // Read the items in the arraylist(needed to save items)
    private void readItems2() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todoitems.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    // Write the items(after reading needed to write them for the next time the app gets launched)
    private void writeItems2() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todoitems.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        TextView txtProduct = (TextView) findViewById(R.id.textView3);
        Intent i = getIntent();
        // Getting the data
        String product = i.getStringExtra("item");
        // Show selected item
        txtProduct.setText(product);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems2();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        // Setup remove listener
        setupListViewListener2();
    }

    // Add a listener to the listview
    private void setupListViewListener2() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems2();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    // Create the option to be able to add new items to the itemlist
    public void onAddItem(View v) {
        // Find the EditText by the id
        EditText iltext = (EditText) findViewById(R.id.etNewItem);
        String itemText = iltext.getText().toString();
        // When the add button is pressed the input is put in the list
        itemsAdapter.add(itemText);
        iltext.setText("");
        writeItems2();
    }
}