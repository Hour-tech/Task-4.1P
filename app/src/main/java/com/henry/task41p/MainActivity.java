package com.henry.task41p;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> taskId, taskTitle, taskUnit, taskDescription, taskDate;

    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.floatingActionButton);
        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        taskId = new ArrayList<>();
        taskTitle = new ArrayList<>();
        taskUnit = new ArrayList<>();
        taskDescription = new ArrayList<>();
        taskDate = new ArrayList<>();

        DataInArray();

        myAdapter = new MyAdapter(MainActivity.this, this, taskId, taskTitle, taskUnit, taskDescription, taskDate);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            recreate();
        }
    }

    void DataInArray() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                taskId.add(cursor.getString(0));
                taskTitle.add(cursor.getString(1));
                taskUnit.add(cursor.getString(2));
                taskDescription.add(cursor.getString(3));
                taskDate.add(cursor.getString(4));

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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
