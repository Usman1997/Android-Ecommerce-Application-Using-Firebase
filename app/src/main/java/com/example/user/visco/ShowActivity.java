package com.example.user.visco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by User on 5/9/2017.
 */

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    Button login, register;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth  = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            Intent i = new Intent(ShowActivity.this,ProfileActivity.class);
            finish();
            startActivity(i);
        }
        setContentView(R.layout.activity_show);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.login:


                i = new Intent(ShowActivity.this,LoginActivity.class);
                startActivity(i);
                break;

            case R.id.register:
                i = new Intent(ShowActivity.this,RegisterActivity.class);
                startActivity(i);
                break;

        }
    }
}
