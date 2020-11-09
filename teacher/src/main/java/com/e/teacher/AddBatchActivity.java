package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AddBatchActivity extends AppCompatActivity {

    private EditText txtbatchname;
    private ListView listView;
    private ArrayList<Student> studentlist;
    private Button btnaddstudent;


    DatabaseReference dbreference;
    Intent myfileIntent;
    int newbatchid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);

        txtbatchname=(EditText) findViewById(R.id.etbatchname);
        listView=(ListView) findViewById(R.id.lvstudentlist);
        btnaddstudent=(Button) findViewById(R.id.btaddstudent);
        btnaddstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });

        dbreference = FirebaseDatabase.getInstance().getReference();
        System.out.println("2222222222222222");
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newbatchid = snapshot.child("flagstatus").child("batchid").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addStudent() {

        if(txtbatchname.getText().toString().equals("")) {

            Toast.makeText(this, "Fill the Class name First", Toast.LENGTH_LONG).show();
            return;
        }

        if (newbatchid != -1) {
            Batch batch = new Batch();
            batch.setBatchid(newbatchid);
            batch.setBatchname(txtbatchname.getText().toString());

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
            databaseReference.child("batches").child(String.valueOf(newbatchid)).setValue(batch);
            DatabaseReference databasereference1=FirebaseDatabase.getInstance().getReference().child("Students");
            for (Student student : studentlist) {
                student.setBatchid(newbatchid);
                databasereference1.child(String.valueOf(student.getScholar_number())).setValue(student);
            }
            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference();
            databaseReference2.child("flagstatus").child("batchid").setValue(newbatchid+1);
            Toast.makeText(AddBatchActivity.this,"Batch Created Successfully",Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void load(String filepath) {
        File csvfile = new File(filepath);
        try {
            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
            studentlist = new ArrayList<>();
            String[] nextLine;
            int flg = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (flg == 0) {
                    flg = 1;
                    continue;
                }
                Student t = new Student();
                t.setName(nextLine[1]);
                t.setScholar_number(Integer.parseInt(nextLine[2]));
                t.setEmail(nextLine[3]);
                t.setContact(nextLine[4]);
                t.setDepartment(nextLine[5]);
                String encpassword = "";
                try {
                    encpassword = AESCrypt.encrypt("Student123");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                t.setImage("NA");
                t.setPassword(encpassword);
                t.setFinger_print("NA");
                t.setBatchid(-1);
                studentlist.add(t);
            }

            ItemListAdapter customAdapter = new ItemListAdapter(AddBatchActivity.this, R.layout.itemlayout, studentlist);

            listView.setAdapter(customAdapter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCSV(View view) {
        myfileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myfileIntent.setType("*/*");  //select file format
        startActivityForResult(myfileIntent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 10:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();

                    String[] arrOfStr = path.split(":");

                    File file = new File(Environment.getExternalStorageDirectory() + "/" + arrOfStr[1]);
                    String filepath = file.getAbsolutePath();

                    int i = filepath.length() - 1;
                    String tmp = "";
                    while (filepath.charAt(i) != '.' && i >= 0) {
                        tmp += filepath.charAt(i);
                        i--;
                    }
                    if (tmp.equals("vsc")) {

                        load(filepath);
                    } else {
                        Toast.makeText(AddBatchActivity.this, "Not a CSV file! Select CSV File", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


}