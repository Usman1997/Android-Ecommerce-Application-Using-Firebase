package com.example.user.visco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.user.visco.Adapters.CustomGridViewAdapter;
import com.example.user.visco.Adapters.CustomSwipeAdapter;
import com.example.user.visco.Model.GridModel;

import java.util.ArrayList;

/**
 * Created by User on 9/15/2016.
 */
public class HomeFragment extends android.support.v4.app.Fragment {
    ViewPager viewpager;
    private int[] image = {R.mipmap.home_back,R.mipmap.home_back,R.mipmap.home_back};
    CustomSwipeAdapter customswipeadapter;
    GridView gridView,gridView1;
    ArrayList<GridModel> arrayList = new ArrayList<GridModel>();
    ArrayList<GridModel> arrayLis1 = new ArrayList<GridModel>();
    PrinterListFragment fragment = new PrinterListFragment();
    CustomGridViewAdapter customGridViewAdapter,customGridViewAdapter1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);


        viewpager =(ViewPager)view.findViewById(R.id.home_viewpager);
        customswipeadapter = new CustomSwipeAdapter(getActivity(),image);
        viewpager.setAdapter(customswipeadapter);
        gridView =(GridView) view.findViewById(R.id.gridView);
        gridView1 = (GridView)view.findViewById(R.id.gridView1);



        Printers();
        Filaments();





        customGridViewAdapter = new CustomGridViewAdapter(getActivity(),arrayList);
        customGridViewAdapter1 = new CustomGridViewAdapter(getActivity(),arrayLis1);
        customGridViewAdapter1.notifyDataSetChanged();
        customGridViewAdapter.notifyDataSetChanged();
        gridView.setAdapter(customGridViewAdapter);
        gridView1.setAdapter(customGridViewAdapter1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView printer = (TextView)view.findViewById(R.id.gridview_text);
                String key  = printer.getText().toString();
                Bundle bundle = new Bundle();
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

    public void Printers() {

        GridModel gridModel1 = new GridModel("Xplorer 3D", R.mipmap.home_xplorer);
        arrayList.add(gridModel1);
        GridModel gridModel2 = new GridModel("Create Bot", R.mipmap.home_create_bot);
        arrayList.add(gridModel2);
        GridModel gridModel3 = new GridModel("Shining 3D", R.mipmap.home_shining_3d);
        arrayList.add(gridModel3);
        GridModel gridModel4 = new GridModel("Flash Forge", R.mipmap.home_flash);
        arrayList.add(gridModel4);

    }
    public void Filaments(){

        GridModel gridModel1 = new GridModel("PLA",R.mipmap.pla);
        arrayLis1.add(gridModel1);

        GridModel gridModel2 = new GridModel("ABS",R.mipmap.abs);
        arrayLis1.add(gridModel2);

        GridModel gridModel3 = new GridModel("PET-G",R.mipmap.pet_g);
        arrayLis1.add(gridModel3);
        GridModel gridModel4 = new GridModel("CarbonFiber",R.mipmap.carbonfiber);
        arrayLis1.add(gridModel4);
        GridModel gridModel5 = new GridModel("Flex",R.mipmap.flex);
        arrayLis1.add(gridModel5);
        GridModel gridModel6 = new GridModel("Bronze",R.mipmap.bronze);
        arrayLis1.add(gridModel6);
        GridModel gridModel7 = new GridModel("Wood",R.mipmap.wood);
        arrayLis1.add(gridModel7);
        GridModel gridModel8 = new GridModel("PC",R.mipmap.pc);
        arrayLis1.add(gridModel8);

    }






}
