package com.example.cluster;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cluster.recycler.RecyclerAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerCallback {

    DatabaseHelper dbHelper;
    Button btnAdd, btnList;
    EditText etName;
    RecyclerView rv;

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

        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        etName = findViewById(R.id.etName);
        rv = findViewById(R.id.rv);

        pickDate();

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v->{
            if(dbHelper.add(etName.getText().toString())){
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
                etName.setText("");
            };
        });

        btnList.setOnClickListener(v->{
            Cursor cursor = dbHelper.getList();
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(1);
                    Log.i("myTag", "name: " + name);
                }while(cursor.moveToNext());
                cursor.close();
            }else{
                Log.i("myTag", "EMPTY");
            }
        });

        setupRecyclerView();
    }

    private void pickDate(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    Toast.makeText(getApplicationContext(), "date: " + formattedDate, Toast.LENGTH_LONG ).show();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }

    private void setupRecyclerView(){
        RecyclerAdapter adapter = new RecyclerAdapter(dbHelper.getList(), getApplicationContext(), this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @Override
    public void onTap(int id, String name) {
        Toast.makeText(this, "Name: " + name + "\nID: " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(int id) {
        Toast.makeText(this, "Deleting ID: " + id, Toast.LENGTH_SHORT).show();
    }
}