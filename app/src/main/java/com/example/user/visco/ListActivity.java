package com.example.user.visco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.visco.Model.ListModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 5/9/2017.
 */

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("3D Printers").child("Xplorer 3D");
        databaseReference.keepSynced(true);
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ListModel,BlogViewHodler> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ListModel, BlogViewHodler>(
                ListModel.class,
                R.layout.list_item,
                BlogViewHodler.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(BlogViewHodler viewHolder, ListModel model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                final String key= getRef(position).getKey();

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(ListActivity.this,DescriptionActivity.class);
                        i.putExtra("key",key);
                        startActivity(i);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHodler extends RecyclerView.ViewHolder{
        View view;
        public BlogViewHodler(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setTitle(String title){
            TextView list_title = (TextView)view.findViewById(R.id.listview_title);
            list_title.setText(title);
        }

        public void setDesc(String Desc){
            TextView list_desc = (TextView)view.findViewById(R.id.listview_desc);
            list_desc.setText(Desc);
        }
        public void setImage(final Context ctx, final String image){
            final ImageView imageView = (ImageView)view.findViewById(R.id.listview_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(imageView);
                }
            });
        }
    }
}
