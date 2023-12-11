package com.example.lab5;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;


public class PlantsList extends Activity {

    Button button2 ;
    Button button3 ;
    Button buttonback;
    String data;
    String plant;
    private Intent intent;
    private DatabaseHandler db;
    User user = new User();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantslist);

         button2 = findViewById(R.id.add);
         //button2.setOnClickListener(this);
         button3 = findViewById(R.id.del);
         //button3.setOnClickListener(this);
         buttonback = findViewById(R.id.back);
         //buttonback.setOnClickListener(this);

        ListView list1 = findViewById(R.id.list);
        EditText text1 = findViewById(R.id.editText);

        ArrayList<String> datalist = DatabaseHandler.getAllNames();

        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, datalist);

        DatabaseHandler db = new DatabaseHandler(this);
        intent = new Intent(this, MainScene.class);


        list1.setAdapter(TextAdapter);
        //intent = new Intent(this, MainScene.class);
        //Intent intent = new Intent(this, RegScene.class);
        //Bundle recData = this.getIntent().getExtras();

        //String[] inData = recData.getStringArray("key");
        //Collections.addAll(plant);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addPlant(user, plant);
                data = text1.getText().toString();
                if (!data.isEmpty()) {
                    datalist.add(data);
                    text1.setText("");
                    TextAdapter.notifyDataSetChanged();
                }


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.deleteObjectFromDB(plant);
                SparseBooleanArray selected = list1.getCheckedItemPositions();

                int offset = 0;
                for (int i = 0; i < selected.size(); i++) {
                    if (selected.valueAt(i)) {
                        int position = selected.keyAt(i);
                        list1.setItemChecked(position, false);
                        datalist.remove(position - offset);
                        offset++;
                    }
                }
                TextAdapter.notifyDataSetChanged();
            }
        });
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
    /*@Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                startActivity(intent);
                break;

            case R.id.del:
                db.deleteObjectFromDB(plant);
                break;

            case R.id.add:
                db.addPlant(user, plant);
                break;
                
            default:
                break;
        }
    }*/
}
