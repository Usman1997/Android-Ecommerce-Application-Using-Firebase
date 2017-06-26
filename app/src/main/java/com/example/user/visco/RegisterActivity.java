package com.example.user.visco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 5/9/2017.
 */

public class RegisterActivity extends BaseActivity {
    Button submit;
    EditText email,password,fname,lname,address,phone;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit = (Button)findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Registration");
        email = (EditText)findViewById(R.id.reg_email);
        password  = (EditText)findViewById(R.id.reg_pass);
        fname = (EditText)findViewById(R.id.reg_fname);
        lname = (EditText)findViewById(R.id.reg_lname);
        address = (EditText) findViewById(R.id.reg_address);
        phone =(EditText)findViewById(R.id.reg_phone);
        if(firebaseAuth.getCurrentUser()!=null){
            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            finish();
            startActivity(i);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
    }


    public void SignUp(){
        String emailTxt = email.getText().toString().trim();
        String passTxt = password.getText().toString().trim();
        final String fname1 = fname.getText().toString().trim();
        final String lname1 = lname.getText().toString().trim();
        final String phone1 = phone.getText().toString().trim();
        final String address1 = address.getText().toString().trim();
        if(emailTxt.equals("")){
            Toast.makeText(RegisterActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
        }
        else if(passTxt.equals("")){
            Toast.makeText(RegisterActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String user_id = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference user_db = databaseReference.child(user_id);
                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                        finish();
                        startActivity(i);
                        user_db.child("FirstName").setValue(fname1);
                        user_db.child("LastName").setValue(lname1);
                        user_db.child("Address").setValue(address1);
                        user_db.child("Phone No").setValue(phone1);
                        user_db.child("user_image").setValue("https://firebasestorage.googleapis.com/v0/b/visco-8ecc8.appspot.com/o/user.png?alt=media&token=44ad1982-de99-4a94-a101-53558b7cf589");



                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }


    }

}
