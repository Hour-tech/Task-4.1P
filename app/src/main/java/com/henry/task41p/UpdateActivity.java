package com.henry.task41p;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText title_Input, unit_Input, description_Input, dueDate_Input;
    Button updateButton, deleteButton;

    String id, title, unit, description, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title_Input = findViewById(R.id.editTextName);
        unit_Input = findViewById(R.id.editTextNumber2);
        description_Input = findViewById(R.id.editTextDescription);
        dueDate_Input = findViewById(R.id.editTextDate2);
        updateButton = findViewById(R.id.updateButton2);
        deleteButton = findViewById(R.id.DeleteButton);


        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        updateButton.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            title = title_Input.getText().toString().trim();
            unit = unit_Input.getText().toString().trim();
            description = description_Input.getText().toString().trim();
            date = dueDate_Input.getText().toString().trim();
            myDB.updateData(id, title, unit, description, date);

            Intent intent = new Intent();
            setResult(1, intent);  // Set resultCode to 1
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            confirmDialog();
        });

    }
    void getIntentData() {
        if(getIntent().hasExtra("id") &&
            getIntent().hasExtra("title") &&
            getIntent().hasExtra("unit") &&
            getIntent().hasExtra("description") &&
            getIntent().hasExtra("date")) {
            //get data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            unit = getIntent().getStringExtra("unit");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");

            //set intent data
            title_Input.setText(title);
            unit_Input.setText(unit);
            description_Input.setText(description);
            dueDate_Input.setText(date);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
