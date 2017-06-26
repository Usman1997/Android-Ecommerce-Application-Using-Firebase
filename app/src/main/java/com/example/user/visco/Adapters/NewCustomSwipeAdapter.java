package com.example.user.visco.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.user.visco.R;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 5/22/2017.
 */

public class NewCustomSwipeAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    ImageView imageView;
    private String[] image;


    public NewCustomSwipeAdapter(Context context,String[] image){
        this.context = context;
        this.image = image;
    }



    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.item_viewpager,container,false);
        imageView  = (ImageView)view.findViewById(R.id.viewpager_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(image[position]).into(imageView);
        container.addView(view);
        return view;
    }

    @Override

    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((LinearLayout)object);
    }

}
