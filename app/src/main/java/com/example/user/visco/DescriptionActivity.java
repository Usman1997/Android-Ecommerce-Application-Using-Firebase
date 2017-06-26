package com.example.user.visco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 5/11/2017.
 */

public class DescriptionActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title,description;
    DatabaseReference firebaseDatabase,userdatabase;
    Button buy;
    String key;
    String userkey;
      Helper helper;
    FirebaseAuth firebaseAuth;
    String title1,desc1,image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        imageView = (ImageView)findViewById(R.id.description_image);
        title =(TextView)findViewById(R.id.description_title);
        description = (TextView)findViewById(R.id.description_desc);
        buy = (Button)findViewById(R.id.buy);
        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("3D Printers");
        userdatabase = FirebaseDatabase.getInstance().getReference().child("Registration");
        helper = new Helper();
        key = getIntent().getExtras().getString("key");
        firebaseDatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title1 =(String)dataSnapshot.child("title").getValue();
                desc1  =(String)dataSnapshot.child("desc").getValue();
                image1 = (String)dataSnapshot.child("image").getValue();

                title.setText(title1);
                description.setText(desc1);
                Picasso.with(DescriptionActivity.this).load(image1).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firebaseAuth.getCurrentUser()!=null){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    userkey = firebaseAuth.getCurrentUser().getUid();
                    final String email = user.getEmail();

                    userdatabase.child(userkey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name1 = (String)dataSnapshot.child("FirstName").getValue();
                                   helper.showCustomDialog(DescriptionActivity.this);
                                   helper.SendData(email,name1);
                                   helper.storeData(title1,desc1,image1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }
}
