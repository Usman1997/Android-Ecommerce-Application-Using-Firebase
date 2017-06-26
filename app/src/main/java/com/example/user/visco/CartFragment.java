package com.example.user.visco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.CheckoutFragment;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by User on 6/2/2017.
 */

public class CartFragment extends android.support.v4.app.Fragment{
    long total ;
    DatabaseReference databaseReference;
    ListView listView;
    Button checkout;
    public CartFragment(){

    }
    CheckoutFragment fragment = new CheckoutFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle SavedInstance){
        final View view = inflater.inflate(R.layout.cart_fragment,container,false);
        String key = getArguments().getString("email");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart Database").child(key);
        databaseReference.keepSynced(true);
        listView = (ListView) view.findViewById(R.id.listview);
        checkout = (Button)view.findViewById(R.id.checkout);
        FirebaseListAdapter<listViewModel> firebaseListAdapter = new FirebaseListAdapter<listViewModel>(getActivity(), listViewModel.class, R.layout.cartview_item, databaseReference) {
            @Override
            protected void populateView(View v, final listViewModel model, int position) {

                double item_price;
                item_price = model.getPrice();
//                total+=model.getPrice();
                String price = String.valueOf(item_price);
                final TextView title = (TextView) v.findViewById(R.id.listview_title);
                TextView desc = (TextView) v.findViewById(R.id.listview_desc);
                ImageView imageView = (ImageView) v.findViewById(R.id.listview_image);
                title.setText(model.getTitle());
//                desc.setText(model.getDesc());
                desc.setText(price);

                Picasso.with(getActivity()).load(model.getImage()).into(imageView);
                ImageView delete = (ImageView)v.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = model.getItem_key();
                        databaseReference.child(key).removeValue();
                        total=0;

                    }
                });


            }



        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    HashMap<String, Object> value = (HashMap<String, Object>) child.getValue();
                    Long amount = (Long) value.get("price");

                    total+=amount;




                }
                TextView final_total = (TextView)view.findViewById(R.id.text_price);
                String total_price = String.valueOf(total);
                final_total.setText(total_price);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listView.setAdapter(firebaseListAdapter);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
