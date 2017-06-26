package com.example.user.visco;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 6/14/2017.
 */

public class BaseActivity extends AppCompatActivity {
     final  int CAMERA_REQUEST = 12321;
     final  int GALLERY_REQUST = 21234;
     CameraPhoto cameraPhoto;
     GalleryPhoto galleryPhoto;

    public void Dialog_image(final Context context) {


        ImageView gallery, camera;
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_box_image);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        gallery = (ImageView) dialog.findViewById(R.id.gallery);
        camera = (ImageView) dialog.findViewById(R.id.camera);
        cameraPhoto = new CameraPhoto(context);
        galleryPhoto = new GalleryPhoto(context);
        dialog.show();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean diditwork = true;
                try {

                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error in capturing picture", Toast.LENGTH_SHORT).show();
                    diditwork = false;
                } finally {
                    if (diditwork) {
                        dialog.dismiss();
                    }
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean diditwork = true;
                try {
                    startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUST);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error in capturing picture", Toast.LENGTH_SHORT).show();
                    diditwork = false;
                } finally {
                    if (diditwork) {
                        dialog.dismiss();
                    }

                }
            }
        });


    }


    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void replaceFragmnet(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,"fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}



