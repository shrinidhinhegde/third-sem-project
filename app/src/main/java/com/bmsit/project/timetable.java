package com.bmsit.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import static com.bmsit.project.HOME.branch;
import static com.bmsit.project.HOME.section;
import static com.bmsit.project.HOME.year;

public class timetable extends Fragment {
    View myView;
    int br = branch;
    int yr = year;
    int sec = section;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.timetable,container,false);
        ImageView img = (ImageView)myView.findViewById(R.id.timtabimg);
        String url;
        if((sec==1)&&(yr==2)&&(br==2)){
            url="https://firebasestorage.googleapis.com/v0/b/project-9f3a2.appspot.com/o/2.jpg?alt=media&token=2ef34a6f-666d-4447-b45e-b58b0b045105";
        }
        else if((sec==2)&&(yr==2)&&(br==2)){
            url="https://firebasestorage.googleapis.com/v0/b/project-9f3a2.appspot.com/o/1.jpg?alt=media&token=c49bf6d0-da85-404c-a619-89b33ad02e21";
        }
        else if((sec==3)&&(yr==2)&&(br==2)) {
            url="https://firebasestorage.googleapis.com/v0/b/project-9f3a2.appspot.com/o/3.jpg?alt=media&token=859fe116-1f6f-4bcc-95e5-ae420842a909";
        }
        else {
            url = "https://firebasestorage.googleapis.com/v0/b/project-9f3a2.appspot.com/o/image18.png?alt=media&token=3ab4d478-585a-4ef2-9a7e-8e733d584dfb";
        }
        Picasso.with(getActivity()).load(url).into(img);
        return myView;
    }

}
