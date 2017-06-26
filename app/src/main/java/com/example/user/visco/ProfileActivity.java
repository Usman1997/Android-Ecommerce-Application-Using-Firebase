package com.example.user.visco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by User on 5/9/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    TextView email,password;
    Button logout,list;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email  = (TextView)findViewById(R.id.showEmail);
        password=(TextView)findViewById(R.id.showPass);
        firebaseAuth = FirebaseAuth.getInstance();
        logout  =(Button)findViewById(R.id.logout);
        list = (Button)findViewById(R.id.list);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent i = new Intent(ProfileActivity.this,ShowActivity.class);
                finish();
                startActivity(i);

            }
        });


        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(firebaseAuth.getCurrentUser()!=null) {
            String emailTxt = user.getEmail();
            password.setText(emailTxt);

        }
        else{
            Intent i = new Intent(ProfileActivity.this,ShowActivity.class);
            startActivity(i);
        }

list.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i  = new Intent(ProfileActivity.this,ListActivity.class);
        startActivity(i);
    }
});
    }
}
