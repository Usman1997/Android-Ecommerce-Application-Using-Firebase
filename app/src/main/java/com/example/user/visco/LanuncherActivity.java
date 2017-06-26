package com.example.user.visco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by User on 5/9/2017.
 */

public class LanuncherActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            Intent i = new Intent(LanuncherActivity.this,ProfileActivity.class);
            finish();
            startActivity(i);
        }
        else{
            Intent i = new Intent(LanuncherActivity.this,ShowActivity.class);
            startActivity(i);
        }
    }


}
