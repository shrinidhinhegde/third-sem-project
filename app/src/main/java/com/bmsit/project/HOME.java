package com.bmsit.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HOME extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    int i = 0;
    static int year,branch,section;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
        if (currentUser==null){
            Intent intent = new Intent(HOME.this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        // for refresh button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(i==1){

                    fragmentManager.beginTransaction().replace(R.id.content_frame, new events()).commit();
                }
                else if(i==2){
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new holidays()).commit();
                }
                else if(i==3){
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new timetable()).commit();
                }
                else{
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        check();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headView=navigationView.getHeaderView(0);
        TextView nm = headView.findViewById(R.id.nm);
        TextView eml = headView.findViewById(R.id.eml);
        nm.setText(currentUser.getDisplayName());
        eml.setText(currentUser.getEmail());
        ImageView img = headView.findViewById(R.id.img);
        Picasso.with(this).load(currentUser.getPhotoUrl()).into(img);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        final Spinner spin = (Spinner)findViewById(R.id.spin);
        final Spinner spin1 = (Spinner)findViewById(R.id.spin1);
        final Spinner spin2= (Spinner)findViewById(R.id.spin2);
        spin2.setVisibility(View.GONE);
        spin1.setVisibility(View.GONE);
        spin.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.section,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.branch,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.year,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int yr, long l) {
                if(yr!=0){
                    spin1.setVisibility(View.GONE);
                    spin2.setVisibility(View.VISIBLE);
                    year=yr;
                    mEditor.putInt("year", year);
                    mEditor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do NOTHING
            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int sec, long l) {
                if(sec!=0) {
                    section = sec;
                    mEditor.putInt("section", section);
                    mEditor.commit();
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int br, long l) {
                if(br!=0){
                    spin.setVisibility(View.GONE);
                    spin1.setVisibility(View.VISIBLE);
                    branch=br;
                    mEditor.putInt("branch",branch);
                    mEditor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do NOTHING
            }
        });

        switch(branch)
        {
            case 1: {
                FirebaseMessaging.getInstance().subscribeToTopic("CSE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CSE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CSE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CSE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CSE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CSE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                FirebaseMessaging.getInstance().subscribeToTopic("ECE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ECE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ECE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ECE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ECE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ECE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 3: {
                FirebaseMessaging.getInstance().subscribeToTopic("ISE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ISE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ISE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ISE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ISE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ISE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 4: {
                FirebaseMessaging.getInstance().subscribeToTopic("ME");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ME_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ME_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ME_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ME_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ME_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 5: {
                FirebaseMessaging.getInstance().subscribeToTopic("ETE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ETE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ETE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ETE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("ETE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("ETE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 6: {
                FirebaseMessaging.getInstance().subscribeToTopic("EEE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("EEE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("EEE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("EEE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("EEE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("EEE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 7: {
                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("AI&ML_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 8: {
                FirebaseMessaging.getInstance().subscribeToTopic("CE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("CE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("CE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 9: {
                FirebaseMessaging.getInstance().subscribeToTopic("MCA");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().subscribeToTopic("MCA_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().subscribeToTopic("MCA_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().subscribeToTopic("MCA_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().subscribeToTopic("MCA_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().subscribeToTopic("MCA_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }


    }

    private void check() {
        branch = mPreferences.getInt("branch", 0);
        year = mPreferences.getInt("year",0);
        section = mPreferences.getInt("section",0);
        final Spinner spin = (Spinner)findViewById(R.id.spin);
        if(branch==0){
            spin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        final Spinner spin = (Spinner)findViewById(R.id.spin);
        final Spinner spin1 = (Spinner)findViewById(R.id.spin1);
        final Spinner spin2= (Spinner)findViewById(R.id.spin2);
        if((year!=0)&&(branch!=0)&&(section!=0)){
            spin1.setVisibility(View.GONE);
            spin.setVisibility(View.GONE);
            spin2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Spinner spinner = (Spinner)findViewById(R.id.spin);
        spinner.setVisibility(View.GONE);
        Spinner spinner1= (Spinner)findViewById(R.id.spin1);
        spinner1.setVisibility(View.GONE);
        Spinner spinner2= (Spinner)findViewById(R.id.spin2);
        spinner2.setVisibility(View.GONE);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new events()).commit();
            i=1;
        } else if (id == R.id.nav_holidays) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new holidays()).commit();
            i=2;
        } else if (id == R.id.nav_timetable) {
            if((branch!=0)&&(section!=0)&&(year!=0)) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new timetable()).commit();
                i = 3;
            }
            else{
                Toast.makeText(this, "Select Your Class!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        } else if (id == R.id.nav_sign_out) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logout() {
        final FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance().signOut(HOME.this);

       switch(branch)
        {
            case 1: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CSE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ECE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 3: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ISE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 4: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ME_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 5: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("ETE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 6: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("EEE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 7: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("AI&ML_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 8: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("CE_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 9: {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA");
                switch(year){
                    case 1: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_1ST");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_1ST_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_1ST_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_1ST_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_2ND");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_2ND_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_2ND_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_2ND_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_3RD");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_3RD_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_3RD_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_3RD_C");
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_4TH");
                        switch (section){
                            case 1: {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_4TH_A");
                                break;
                            }
                            case 2:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_4TH_B");
                                break;
                            }
                            case 3:{
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("MCA_4TH_C");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        branch=0;
        year=0;
        section=0;
        mEditor.putInt("branch",0);
        mEditor.putInt("year",0);
        mEditor.putInt("section",0);
        mEditor.commit();
        if (i == 0){
        Toast.makeText(this, "Press Sign Out again if you're sure!", Toast.LENGTH_SHORT).show();}
        i++;

            if (currentuser == null) {
                Intent intent = new Intent(HOME.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select Your Branch", Toast.LENGTH_SHORT).show();
    }
}
