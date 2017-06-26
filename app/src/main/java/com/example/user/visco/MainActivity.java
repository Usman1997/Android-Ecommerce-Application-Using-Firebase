package com.example.user.visco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText item ,value;
    TextView view;
    Firebase mRef;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
//        button  = (Button)findViewById(R.id.button);
//        view = (TextView)findViewById(R.id.view);
//        item = (EditText)findViewById(R.id.item);
//        value = (EditText)findViewById(R.id.value);
        mRef = new Firebase("https://visco-8ecc8.firebaseio.com/");
        listView = (ListView)findViewById(R.id.listview);

//       button.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               String item1 = item.getText().toString();
//               String value1 = value.getText().toString();
//               Firebase mRefChild = mRef.child(item1);
//               mRefChild.setValue(value1);
//           }
//       }
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, String> map = dataSnapshot.getValue(Map.class);
//                String name = map.get("name");
//                String age = map.get("age");
//                String prrofession = map.get("profession");
//                Log.v("LogValue","name:"+name);
//                Log.v("LogValue","age:"+age);
//                Log.v("LogValue","profession"+prrofession);
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value  = dataSnapshot.getValue(String.class);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
}
