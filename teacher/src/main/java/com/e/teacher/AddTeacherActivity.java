package com.e.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacherActivity extends AppCompatActivity {

    EditText txtname;
    EditText txtemail;
    EditText txtregistrationno;
    EditText txtcontact;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        txtname=(EditText)findViewById(R.id.etname);
        txtemail=(EditText)findViewById(R.id.etemail);
        txtregistrationno=(EditText)findViewById(R.id.etregistrationno);
        txtcontact=(EditText)findViewById(R.id.etcontactno);
    }
    public boolean validationCheck(){
        String msg="field can not be empty";
        if(TextUtils.isEmpty(txtname.getText().toString())) {
            txtname.setError("Name "+msg);
            return false;
        }
        if(TextUtils.isEmpty(txtemail.getText().toString())) {
            txtemail.setError("Email "+msg);
            return false;
        }
        if(TextUtils.isEmpty(txtcontact.getText().toString())) {
            txtcontact.setError("Contact "+msg);
            return false;
        }
        if(TextUtils.isEmpty(txtregistrationno.getText().toString())) {
            txtregistrationno.setError("Registration Number "+msg);
            return false;
        }
        return true;
    }
    public void clearField(){
        txtname.setText("");
        txtemail.setText("");
        txtcontact.setText("");
        txtcontact.setText("");
    }
    public void addTeacher(View view){
        if(validationCheck()){
            databaseReference=FirebaseDatabase.getInstance().getReference("Teachers");
            Teacher teacher=new Teacher();
            teacher.setName(txtname.getText().toString());
            teacher.setEmail(txtemail.getText().toString());
            teacher.setContact(txtcontact.getText().toString());
            teacher.setRegistrationno(Long.valueOf(txtregistrationno.getText().toString()));
            String encpassword="";
            try {
                encpassword=AESCrypt.encrypt("Teacher123");
            } catch (Exception e) {
                e.printStackTrace();
            }
            teacher.setPassword(encpassword);
            databaseReference.child(String.valueOf(teacher.getRegistrationno())).setValue(teacher);
            Toast.makeText(this,"Teacher Added Successfully",Toast.LENGTH_LONG).show();
            clearField();
        }
    }

}