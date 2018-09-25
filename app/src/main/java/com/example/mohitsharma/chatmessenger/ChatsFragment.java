package com.example.mohitsharma.chatmessenger;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends android.support.v4.app.Fragment {


FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);


        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.floating_account);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent SettingsIntent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(SettingsIntent);

            }
        });
        floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.floating_Users);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent UsersIntent = new Intent(getActivity(),UsersActivity.class);
                startActivity(UsersIntent);

            }
        });
        floatingActionButton3 = (FloatingActionButton) view.findViewById(R.id.floating_logout);
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                buildDialog(getActivity()).show();


            }
        });
        return view;






    }
    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder =new AlertDialog.Builder(c);
        builder.setTitle("LogOut");
        builder.setMessage("Do You Want To Logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();

                Intent startintent = new Intent(getActivity(),MainActivity.class);
                startActivity(startintent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();
            }
        });

        return builder;
    }

}
