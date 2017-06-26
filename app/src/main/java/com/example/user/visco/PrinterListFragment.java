package com.example.user.visco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.visco.Adapters.CustomSwipeAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 5/20/2017.
 */

public class PrinterListFragment extends android.support.v4.app.Fragment {
    ViewPager viewpager;
    CustomSwipeAdapter customswipeadapter;
    DatabaseReference databaseReference;
    ListView listView;
    private int[] image = {R.mipmap.home_back,R.mipmap.home_back,R.mipmap.home_back};
    PrinterShowFragment fragment = new PrinterShowFragment();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.printer_list_fragment, container, false);
        viewpager = (ViewPager) view.findViewById(R.id.list_viewpager);
        customswipeadapter = new CustomSwipeAdapter(getActivity(),image);
        viewpager.setAdapter(customswipeadapter);
        String key = getArguments().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("3D Printers").child(key);
        databaseReference.keepSynced(true);
        listView = (ListView) view.findViewById(R.id.printer_list);
        FirebaseListAdapter<listViewModel> firebaseListAdapter = new FirebaseListAdapter<listViewModel>(getActivity(), listViewModel.class, R.layout.list_item, databaseReference) {
            @Override
            protected void populateView(View v, final listViewModel model, int position) {
                TextView title = (TextView) v.findViewById(R.id.listview_title);
                TextView desc = (TextView) v.findViewById(R.id.listview_desc);
                final ImageView imageView = (ImageView) v.findViewById(R.id.listview_image);
                title.setText(model.getTitle());
                desc.setText(model.getDesc());
//                Picasso.with(getActivity()).load(model.getImage()).into(imageView);
                Picasso.with(getActivity()).load(model.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity()).load(model.getImage()).into(imageView);
                    }
                });

            }
        };
        listView.setAdapter(firebaseListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.listview_title);
                String key = textView.getText().toString();
                Bundle bundle  = new Bundle();
                bundle.putString("key",key);
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;

    }


}
