package com.example.user.visco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.user.visco.Adapters.NewCustomSwipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 5/22/2017.
 */
public class PrinterShowFragment extends android.support.v4.app.Fragment {
    ViewPager viewPager;
    NewCustomSwipeAdapter swipeAdapter;
    TabLayout tabLayout;
    String image1, image2, image3;

    DatabaseReference databaseReference;


    static String key;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.printer_show_fragment, container, false);
         key = getArguments().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference("3D Printers").child("Xplorer 3D").child(key);
        databaseReference.keepSynced(true);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();
                image1 = map.get("image1");
                image2 = map.get("image2");
                image3 = map.get("image3");

                String[] images = {image1, image2, image3};

                viewPager = (ViewPager) view.findViewById(R.id.show_viewpager);
                swipeAdapter = new NewCustomSwipeAdapter(getActivity(), images);
                viewPager.setAdapter(swipeAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ViewPager description_viewpager = (ViewPager) view.findViewById(R.id.description_viewpager);
        description_viewpager.setAdapter(SetUpViewPager(/*description_viewpager*/));
//        SectionPagerAdapter adapter = new SectionPagerAdapter(getFragmentManager());
//        adapter.addFragment(new show_description_fragment(), "One");
//        description_viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(description_viewpager);
        setupTabIcons();


        return view;
    }

    private void setupTabIcons() {

        RelativeLayout tabOne = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab1, null);

        tabLayout.getTabAt(0).setCustomView(tabOne);

        RelativeLayout tabTwo = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab2, null);

        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }

//    private void SetUpViewPager(ViewPager desc_viewPager) {
//        SectionPagerAdapter adapter = new SectionPagerAdapter(getFragmentManager());
//        adapter.addFragment(new show_description_fragment(), "One");
//        adapter.addFragment(new show_specification_fragment(), "Two");
//        desc_viewPager.setAdapter(adapter);
//
//

//}

    private SectionPagerAdapter SetUpViewPager(/*ViewPager desc_viewPager*/) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getFragmentManager());
        adapter.addFragment(new show_description_fragment(), "One");
        adapter.addFragment(new show_specification_fragment(), "Two");
        //desc_viewPager.setAdapter(adapter);
        return adapter;

    }


    class SectionPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);

        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

    }

}

