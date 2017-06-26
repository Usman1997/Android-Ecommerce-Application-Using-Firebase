package com.example.user.visco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 5/9/2017.
 */

public class LoginActivity extends AppCompatActivity {
    TextView register;
    Button submit;
    EditText email,password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button)findViewById(R.id.submit);
        register = (TextView)findViewById(R.id.text_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        if(firebaseAuth.getCurrentUser()!=null){
            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            finish();
            startActivity(i);
        }
        email = (EditText)findViewById(R.id.email);
        password  = (EditText)findViewById(R.id.password);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    public void SignIn(){
        String emailTxt = email.getText().toString().trim();
        String passTxt = password.getText().toString().trim();
        if(emailTxt.equals("")){
            Toast.makeText(LoginActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
        }
        else if(passTxt.equals("")){
            Toast.makeText(LoginActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        finish();
                        startActivity(i);

//                        checkUser();

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }

    }
    public void checkUser(){
        final String user_id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    finish();
                    startActivity(i);

                }
                else{
                    Toast.makeText(LoginActivity.this,"Not Working",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this,"You need to create an account",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
