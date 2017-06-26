package com.example.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.visco.R;
import com.example.user.visco.listViewModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by User on 6/23/2017.
 */

public class CheckoutFragment extends android.support.v4.app.Fragment{

    DatabaseReference cartdatabase,userdatabase,orderdatabase;
    FirebaseAuth firebaseAuth;
    String user_id;
    ListView listView;
    Button order;
   EditText Address,phone_NO,lastname;
    EditText firstname;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle SavedInstance){
        final View view = inflater.inflate(R.layout.checkout_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.listview_product);
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        cartdatabase = FirebaseDatabase.getInstance().getReference().child("Cart Database").child(user_id);
        orderdatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(user_id);
        cartdatabase.keepSynced(true);
        firstname = (EditText) view.findViewById(R.id.fname_box);
        lastname = (EditText) view.findViewById(R.id.lname_box);
        Address = (EditText) view.findViewById(R.id.address_box);
        phone_NO = (EditText) view.findViewById(R.id.phone_box);
        order = (Button)view.findViewById(R.id.order);
        listView = (ListView)view.findViewById(R.id.listview_product);
        cartdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        userdatabase = FirebaseDatabase.getInstance().getReference().child("Registration").child(user_id);
        userdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fname = (String)dataSnapshot.child("FirstName").getValue();
                String lname = (String)dataSnapshot.child("LastName").getValue();
                String address = (String) dataSnapshot.child("Address").getValue();
                String phone = (String)dataSnapshot.child("Phone No").getValue();
                firstname.setText(fname);
                lastname.setText(lname);
                Address.setText(address);
                phone_NO.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        FirebaseListAdapter<listViewModel> firebaseListAdapter = new FirebaseListAdapter<listViewModel>(getActivity(), listViewModel.class, R.layout.list_item, cartdatabase) {
            @Override
            protected void populateView(View v, final listViewModel model, int position) {

                double item_price;
                item_price = model.getPrice();
                String price = String.valueOf(item_price);
                final TextView title = (TextView) v.findViewById(R.id.listview_title);
                TextView price_info = (TextView) v.findViewById(R.id.listview_desc);
                ImageView imageView = (ImageView) v.findViewById(R.id.listview_image);
                title.setText(model.getTitle());
//                desc.setText(model.getDesc());
                price_info.setText(price);

                Picasso.with(getActivity()).load(model.getImage()).into(imageView);




            }
        };
        listView.setAdapter(firebaseListAdapter);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartdatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            HashMap<String, Object> value = (HashMap<String, Object>) child.getValue();
                            String title = (String) value.get("title");
                            orderdatabase.child(title).setValue(title);





                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });












        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}


