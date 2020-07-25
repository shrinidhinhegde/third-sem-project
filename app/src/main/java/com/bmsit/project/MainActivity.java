package com.bmsit.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 9090 ;
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out, btn_sign_in, btn_continue;
    ImageView user_image,profile;
    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sign_out = (Button)findViewById(R.id.btn_sign_out);
        user_image = (ImageView)findViewById(R.id.user_image);
        profile = (ImageView)findViewById(R.id.profile);
        btn_continue = (Button)findViewById(R.id.btn_continue);
        btn_sign_in = (Button)findViewById(R.id.btn_sign_in);
        details = (TextView)findViewById(R.id.details);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setVisibility(View.GONE);
                                showSignInOptions();
                                user_image.setVisibility(View.VISIBLE);
                                profile.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        showSignInOptions();
        //topics for notifications
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
        if (currentUser!=null){
        Intent intent = new Intent(MainActivity.this, HOME.class);
        finish();
        startActivity(intent);
        }
    }

    private void showSignInOptions(){
        btn_sign_in.setVisibility(View.VISIBLE);
        btn_continue.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
        details.setText("");
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setTheme(R.style.MyTheme)
                                .build(),MY_REQUEST_CODE
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                btn_sign_out.setVisibility(View.VISIBLE);
                btn_continue.setVisibility(View.VISIBLE);
                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, HOME.class);
                        startActivity(intent);
                    }
                });
                profile.setVisibility(View.VISIBLE);
                user_image.setVisibility(View.GONE);
                btn_sign_in.setVisibility(View.GONE);
                details.append("Name: "+user.getDisplayName()+"\nE-Mail: "+user.getEmail());
                details.setVisibility(View.VISIBLE);
                Picasso.with(this).load(user.getPhotoUrl()).into(profile);
            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
