package com.jdjoum.speechtotextdatabaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    // Member Variables
    private static final String TAG = "EditDataActivity";
    private Button btnSave, btnDelete;
    private EditText editable_item;
    private String selectedText;
    private int selectedID;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        // Mapping Member Variables to View Elements
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        editable_item = findViewById(R.id.editable_item);

        mDatabaseHelper = new DatabaseHelper(this);

        // Get the intent extras from the ListDataActivity
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedText = receivedIntent.getStringExtra("speechText");

        // Set the editText field to show the current selected text
        editable_item.setText(selectedText);

        // btnSave onClickListener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                if(!item.equals("")) {
                    mDatabaseHelper.updateName(item, selectedID, selectedText);
                    toastMessage("Entry saved in the database.");
                } else {
                    toastMessage("You must enter text");
                }
            }
        });

        // btnDelete onClickListener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedID, selectedText);
                editable_item.setText("");
                toastMessage("Entry removed from database.");
            }
        });
    }

    // Customizable Toast Message
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
