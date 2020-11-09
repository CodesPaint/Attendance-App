package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateClassActivity extends AppCompatActivity {

    ArrayList<String> batchArrayList;
    ArrayList<String> teacherlist;

    Spinner selectbatch;
    Spinner selectteacher;
    DatabaseReference dbrefernce;

    Button btcreateclass;
    int newclassid=-1;
    EditText txtclassname;

    long teacherid=-1;
    int batchid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        selectbatch=(Spinner)findViewById(R.id.spbatch);
        selectteacher=(Spinner)findViewById(R.id.spteachers);
        btcreateclass=(Button)findViewById(R.id.btcreateclass);
        txtclassname=(EditText)findViewById(R.id.etclassname);

        selectbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str[]=parent.getItemAtPosition(position).toString().split("-");
                batchid=Integer.valueOf(str[1]);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        selectteacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str[]=parent.getItemAtPosition(position).toString().split("-");
                teacherid=Long.valueOf(str[1]);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        btcreateclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClass();
            }
        });
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref1,ref2,ref3;
        ref2=databaseReference;
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newclassid = snapshot.child("flagstatus").child("classid").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ref1=databaseReference.child("batches");
        batchArrayList=new ArrayList<>();
        teacherlist=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childDataSnapshot : snapshot.child("batches").getChildren()){
                    System.out.println("1212122112121221");
                    String str=childDataSnapshot.child("batchname").getValue(String.class);
                    str+="-";
                    System.out.println("2332332233223");
                    str+=String.valueOf(childDataSnapshot.child("batchid").getValue(Integer.class));
                    System.out.println("45545454445445");
                    batchArrayList.add(str);
                }
                System.out.println("1111111111111");
                for (DataSnapshot childDataSnapshot : snapshot.child("Teachers").getChildren()){
                    String str=childDataSnapshot.child("name").getValue(String.class);
                    str+="-";
                    System.out.println("22222222222222222");
                    Long val=childDataSnapshot.child("registrationno").getValue(Long.class);
                    System.out.println("3333333333333333");
                    str+=String.valueOf(val);
                    System.out.println("444444444444");
                    teacherlist.add(str);
                }
                initializeUI();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeUI() {

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, batchArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectbatch.setAdapter(aa);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectteacher.setAdapter(arrayAdapter);

    }

    private void createClass(){
        Class cls=new Class();
        cls.setClassid(newclassid);
        cls.setClassname(txtclassname.getText().toString());
        cls.setBatchid(batchid);
        cls.setTeacherregistrationno(teacherid);
        if(cls!=null) {
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("classes");
            databaseReference1.child(String.valueOf(cls.getClassid())).setValue(cls);
            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference();
            databaseReference2.child("flagstatus").child("classid").setValue(newclassid+1);
            Toast.makeText(CreateClassActivity.this,"Class Created Successfully",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(CreateClassActivity.this,"Class Creation Failed",Toast.LENGTH_LONG).show();
        }

    }
}