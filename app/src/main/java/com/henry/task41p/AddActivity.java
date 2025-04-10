package com.henry.task41p;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText titleInput, unitInput, descriptionInput, dueDateInput;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleInput = findViewById(R.id.editTextText);
        unitInput = findViewById(R.id.editTextText3);
        descriptionInput = findViewById(R.id.editTextText2);
        dueDateInput = findViewById(R.id.editTextDate);
        add_button = findViewById(R.id.button);

        add_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
            myDB.addTask(titleInput.getText().toString().trim(),
                unitInput.getText().toString().trim(),
                descriptionInput.getText().toString().trim(),
                dueDateInput.getText().toString().trim());
        });

    }
}
