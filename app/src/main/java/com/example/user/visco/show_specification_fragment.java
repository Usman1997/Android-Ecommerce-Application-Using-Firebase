package com.example.user.visco;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

/**
 * Created by User on 5/23/2017.
 */

public class show_specification_fragment extends android.support.v4.app.Fragment {
    TextView specification;
    String spec;
    Button buy_button;
    DatabaseReference databaseReference,userdatabase,cartDatabase;
    String userkey;
    Helper helper;
    FirebaseAuth firebaseAuth;
    CartFragment cart_fragment = new CartFragment();
    String title1,desc1,image1;

    String name1,email1;
    String settitle,setdesc,setimg;









    public show_specification_fragment(){

      }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.show_specification_fragment,container,false);
        databaseReference = FirebaseDatabase.getInstance().getReference("3D Printers").child("Xplorer 3D").child(PrinterShowFragment.key);
        databaseReference.keepSynced(true);
        userdatabase = FirebaseDatabase.getInstance().getReference().child("Registration");
        specification  = (TextView)view.findViewById(R.id.spec);
        firebaseAuth  = FirebaseAuth.getInstance();
        buy_button = (Button)view.findViewById(R.id.buy_button);
        helper= new Helper();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spec = (String)dataSnapshot.child("specification").getValue();
                title1 =(String)dataSnapshot.child("title").getValue();
                desc1  =(String)dataSnapshot.child("desc").getValue();
                image1 = (String)dataSnapshot.child("image").getValue();
                specification.setText(spec);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buy_button.setOnClickListener(new View.OnClickListener() {
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

                            showCustomDialog();
//                            SendData(email,name1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
        return view;
    }








    public void showCustomDialog() {
        final TextView done, cancel;
        ImageView email_icon;
        final Button cart;
        cartDatabase = FirebaseDatabase.getInstance().getReference("Cart Database");
        final Dialog dialog_forgot;
        dialog_forgot = new Dialog(getActivity(),
                android.R.style.Theme_Light_NoTitleBar);
        dialog_forgot.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_forgot.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);//

        dialog_forgot.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_forgot.setCancelable(false);
        dialog_forgot.setContentView(R.layout.forgot_dialog_layout);
        dialog_forgot.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        done = (TextView) dialog_forgot.findViewById(R.id.dialog_done_btn);
        cart = (Button)dialog_forgot.findViewById(R.id.activity_signin_email);
        cancel = (TextView) dialog_forgot.findViewById(R.id.dialog_cancel_btn);


        email_icon = (ImageView) dialog_forgot
                .findViewById(R.id.activity_signin_email_icon);


        dialog_forgot.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_forgot.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference  = cartDatabase.child(userkey);

                DatabaseReference newitem = databaseReference.push();
                String item_key  = newitem.getKey();
                newitem.child("title").setValue(title1);
                newitem.child("desc").setValue(desc1);
                newitem.child("image").setValue(image1);
                newitem.child("item_key").setValue(item_key);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle  = new Bundle();
                bundle.putString("name",name1);
                bundle.putString("email",userkey);
                cart_fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, cart_fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                dialog_forgot.dismiss();

            }
        });


    }


    public void SendData(String name,String email){

        name1 = name;
        email1 = email;

    }
    public void storeData(String title,String desc,String image)
    {
        settitle = title;
        setdesc = desc;
        setimg = image;
    }








}
