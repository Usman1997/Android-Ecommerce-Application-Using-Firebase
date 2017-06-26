package com.example.user.visco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 5/23/2017.
 */

public class show_description_fragment extends android.support.v4.app.Fragment {
     TextView description;
    String desc;
    DatabaseReference databaseReference;
    public show_description_fragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.show_description_fragment,container,false);
        description = (TextView)view.findViewById(R.id.printer_description);
//        Bundle bundle = getArguments();
//       String key= bundle.getString("key");
//        Toast.makeText(getActivity(),key,Toast.LENGTH_SHORT).show();
//        String key =getArguments().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference("3D Printers").child("Xplorer 3D").child(PrinterShowFragment.key);
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 desc =(String)dataSnapshot.child("desc").getValue();
                 description.setText(desc);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;


    }
}
