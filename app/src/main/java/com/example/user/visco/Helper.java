package com.example.user.visco;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 5/12/2017.
 */

public class Helper extends android.support.v4.app.Fragment {
    String name1,email1;
    String settitle,setdesc,setimg;
    DatabaseReference cartDatabase;

    CartFragment fragment = new CartFragment();
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
    public void replaceFragmnet(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,"fragment");
        fragmentTransaction.commit();

    }


    public void showCustomDialog(final Context context) {
        final TextView done, cancel;
        ImageView email_icon;
        Button cart;
        cartDatabase = FirebaseDatabase.getInstance().getReference("Cart Database");
        final Dialog dialog_forgot;
        dialog_forgot = new Dialog(context,
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
                DatabaseReference databaseReference  = cartDatabase.child(email1);
                DatabaseReference newitem = databaseReference.push();
                newitem.child("title").setValue(settitle);
                newitem.child("desc").setValue(setdesc);
                newitem.child("image").setValue(setimg);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(context,CartActivity.class);
//                i.putExtra("name",name1);
//                i.putExtra("email",email1);
//
//
//                context.startActivity(i);

                Bundle bundle  = new Bundle();
                bundle.putString("name",name1);
                bundle.putString("email",email1);
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


    }


}
