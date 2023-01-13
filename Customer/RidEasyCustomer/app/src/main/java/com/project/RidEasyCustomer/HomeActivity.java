package com.project.RidEasyCustomer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.RidEasyCustomer.Utils.UserUtils;
import com.project.RidEasyCustomer.R;
import com.project.RidEasyCustomer.databinding.ActivityDriverHomeBinding;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 7172;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDriverHomeBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private AlertDialog waitingDialog;
    private Uri imageUri;
    private ImageView img_avatar;

    //database
    private StorageReference storageReference;



    private void init() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("InfoCustomer").child(userId);

        waitingDialog=new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Waiting...")
                .create();

        storageReference = FirebaseStorage.getInstance().getReference();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_sign_out)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Sign out").setMessage("Do you really want to sign out?")
                            .setPositiveButton("Sign Out", (dialogInterface, i) -> {
                            mAuth.signOut();
                            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                    })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                            })
                            .setCancelable(false);
                    AlertDialog dialog=builder.create();
                    dialog.setOnShowListener(dialogInterface -> {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(getResources().getColor(android.R.color.primary_text_light));
                    });
                    dialog.show();
                }
                return true;
            }
        });

        //set data for user
        View headerView=navigationView.getHeaderView(0);
        TextView txt_name=(TextView) headerView.findViewById(R.id.txt_name);
        TextView txt_email=(TextView) headerView.findViewById(R.id.txt_email);
        img_avatar=(ImageView)headerView.findViewById(R.id.img_avatar);

        img_avatar.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstname=snapshot.child("firstname").getValue(String.class);
                String lastname=snapshot.child("lastname").getValue(String.class);
                String email=snapshot.child("email").getValue(String.class);
                String image=snapshot.child("avatar").getValue(String.class);
                txt_name.setText(firstname+" "+lastname);
                txt_email.setText(email);
                Glide.with(getBaseContext()).load(image).into(img_avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDriverHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDriverHome.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode== Activity.RESULT_OK)
        {
            if(data!=null&&data.getData()!=null)
            {
                imageUri = data.getData();
                img_avatar.setImageURI(imageUri);

                showDialogUpload();
            }
        }
    }

    private void showDialogUpload() {
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Change avatar")
                .setMessage("Do you really want to change avatar?")
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Upload", (dialogInterface, i) -> {
                    if(imageUri!=null)
                    {
                        waitingDialog.setMessage("Uploading...");
                        waitingDialog.show();

                        String unique_name = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        StorageReference avatarFolder = storageReference.child("avatars/"+unique_name);

                        avatarFolder.putFile(imageUri)
                                .addOnFailureListener(e -> {
                                    waitingDialog.dismiss();
                                    Snackbar.make(drawer,e.getMessage(),Snackbar.LENGTH_SHORT).show();
                                }).addOnCompleteListener(task -> {
                                    if(task.isSuccessful())
                                    {
                                        avatarFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Map<String, Object> updateData = new HashMap<>();
                                                updateData.put("avatar", uri.toString());

                                                UserUtils.updateUser(drawer,updateData);
                                            }
                                        });
                                    }
                                    waitingDialog.dismiss();
                                }).addOnProgressListener(snapshot -> {
                                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                    waitingDialog.setMessage(new StringBuilder("Uploading: ").append(progress).append("%"));
                                });
                    }
                })
                .setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getResources().getColor(android.R.color.primary_text_light));
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}