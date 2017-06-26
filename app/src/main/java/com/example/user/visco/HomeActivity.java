package com.example.user.visco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.visco.Model.ExpandedMenuModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bitmap bitmap;
    String photopath;
    ImageButton imageButton;
    ImageView userImage;
    boolean temp = false;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView = null;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    Toolbar toolbar = null;
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();

    DatabaseReference databaseReference, user_db;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomeFragment(), "homeFragment");
        fragmentTransaction.commit();
        final Helper helper = new Helper();


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Registration");
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        user_db = databaseReference.child(user_id);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        View headerContainer = navigationView.getHeaderView(0);
        imageButton = (ImageButton) headerContainer.findViewById(R.id.camera_icon);
        userImage = (ImageView) headerContainer.findViewById(R.id.user_image);


        if (firebaseAuth.getCurrentUser() != null) {
            user_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = (Map) dataSnapshot.getValue();
                    String user_image_code = map.get("user_image");

                    Picasso.with(HomeActivity.this).load(user_image_code).into(userImage);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_image(HomeActivity.this);
            }
        });

        prepareListData();
//        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        mMenuAdapter = new com.example.user.lists.adapters.ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                switch (groupPosition) {
                    case 0:
                        if(homeFragment!=null && homeFragment.isVisible()){
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else {
                            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, homeFragment, "homefragment");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case 1:
                        Toast.makeText(HomeActivity.this, "Prodcuts", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Bundle bundle = new Bundle();

                        bundle.putString("email", user_id);
                        cartFragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.container, cartFragment, "fragment");
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        break;

                    case 3:
                        Toast.makeText(HomeActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(HomeActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        firebaseAuth.signOut();
                        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                        finish();
                        startActivity(i);
                        break;

                }


                return false;
            }
        });

        expandableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Object i = parent.getItemAtPosition(position);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String key = user.getEmail();

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {

                photopath = cameraPhoto.getPhotoPath();
//                try {
//                    bitmap = ImageLoader.init().from(photopath).requestSize(120,80).getBitmap();
//                    userImage.setImageBitmap(bitmap);
//                    userImage.setVisibility(View.VISIBLE);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(HomeActivity.this,"Error Loadig Image",Toast.LENGTH_LONG).show();
//                }


                StorageReference filepath = storageReference.child(key);
                try {
                    bitmap = ImageLoader.init().from(photopath).requestSize(120, 80).getBitmap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap2 = getCircularBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data1 = baos.toByteArray();

                UploadTask uploadTask = filepath.putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String image_uri = downloadUrl.toString();


                        user_db.child("user_image").setValue(image_uri);
                        user_db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map<String, String> map = (Map) dataSnapshot.getValue();
                                String user_image = map.get("user_image");
                                Picasso.with(HomeActivity.this).load(user_image).into(userImage);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
//
                    }
                });
            }

         else if (requestCode == GALLERY_REQUST) {
            Uri uri = data.getData();
            galleryPhoto.setPhotoUri(uri);
            photopath = galleryPhoto.getPath();

            StorageReference filepath = storageReference.child(key);
            try {
                bitmap = ImageLoader.init().from(photopath).requestSize(120, 80).getBitmap();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap2 = getCircularBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data1 = baos.toByteArray();

            UploadTask uploadTask = filepath.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String image_uri = downloadUrl.toString();


                    user_db.child("user_image").setValue(image_uri);
                    user_db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = (Map) dataSnapshot.getValue();
                            String user_image = map.get("user_image");
                            Picasso.with(HomeActivity.this).load(user_image).into(userImage);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//
                }
            });
        }

    }

}



    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Home");
//        item1.setIconImg(R.mipmap.plus);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Products");
//        item2.setIconImg(R.mipmap.plus);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Cart");
//        item3.setIconImg(android.R.drawable.ic_delete);
        listDataHeader.add(item3);


        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("About Us");
//        item3.setIconImg(android.R.drawable.ic_delete);
        listDataHeader.add(item4);


        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("Contact Us");
//        item3.setIconImg(android.R.drawable.ic_delete);
        listDataHeader.add(item5);


        ExpandedMenuModel item6 = new ExpandedMenuModel();
        item6.setIconName("Logout");
//        item3.setIconImg(android.R.drawable.ic_delete);
        listDataHeader.add(item6);

        // Adding child data
        List<String> Products = new ArrayList<String>();
        Products.add("3D Printers");
        Products.add("Filaments");
        Products.add("3D Pens");




        listDataChild.put(listDataHeader.get(1), Products);
        // Header, Child data
//        listDataChild.put(listDataHeader.get(1), heading2);

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            super.onBackPressed();
        }
    }
//


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }





}
