package com.example.user.visco.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.visco.Model.GridModel;
import com.example.user.visco.R;

import java.util.ArrayList;

/**
 * Created by User on 5/19/2017.
 */

public class CustomGridViewAdapter extends BaseAdapter {

    Context context;
    String[] mobilevalue;
    ArrayList<GridModel> arrayList;
    public CustomGridViewAdapter(Context context, ArrayList<GridModel> arrayList) {
        this.context = context;
//        this.mobilevalue = mobile;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public GridModel getItem(int position) {


        return arrayList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.gridview_item, null);
            TextView text = (TextView) gridView.findViewById(R.id.gridview_text);
            ImageView image = (ImageView) gridView.findViewById(R.id.gridview_image);
            GridModel gridModel = getItem(position);
            text.setText(gridModel.getTitle());
            image.setImageResource(gridModel.getImage());


        } else {
            gridView = (View) convertView;
        }


            return gridView;


        }
    }

