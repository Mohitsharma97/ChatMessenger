package com.example.mohitsharma.chatmessenger;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
public Button mLoginbutton,mSigninbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mLoginbutton = (Button)findViewById(R.id.signinbtn);
        mSigninbtn= (Button)findViewById(R.id.signupbtn);


        mLoginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                if(!isConnected(StartActivity.this)){
                    buildDialog(StartActivity.this).show();
                }else {
                    startActivity(intent);
                }

            }
        });
        mSigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,RegisterActivity.class);
                if(!isConnected(StartActivity.this)){
                    buildDialog(StartActivity.this).show();
                }else {
                    startActivity(intent);
                }
            }
        });
    }


    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


            if((mobile!=null&&mobile.isConnectedOrConnecting())||(wifi != null && wifi.isConnectedOrConnecting())){return  true;}
            else {return false;}

        }else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder =new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check you internet Connection");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder;
    }

}
