package com.example.user.visco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 5/12/2017.
 */

public class CartActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        String key = getIntent().getExtras().getString("email");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart Database").child(key);
        databaseReference.keepSynced(true);
        listView = (ListView) findViewById(R.id.listview);
        FirebaseListAdapter<listViewModel> firebaseListAdapter = new FirebaseListAdapter<listViewModel>(this, listViewModel.class, R.layout.listview_list_item, databaseReference) {
            @Override
            protected void populateView(View v, listViewModel model, int position) {
                TextView title = (TextView) v.findViewById(R.id.listview_title);
                TextView desc = (TextView) v.findViewById(R.id.listview_desc);
                ImageView imageView = (ImageView) v.findViewById(R.id.listview_image);
                title.setText(model.getTitle());
                desc.setText(model.getDesc());
//                Picasso.with(CartActivity.this).load(model.getImage()).into(imageView);

            }
        };
        listView.setAdapter(firebaseListAdapter);
    }

    }


