package com.example.mohitsharma.chatmessenger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
public Button mloginbtn;
private AutoCompleteTextView mEmail,mPassword;
    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mLoginProgress = new ProgressDialog(this);
        mloginbtn = (Button)findViewById(R.id.loginbtn);
        mEmail = (AutoCompleteTextView)findViewById(R.id.useremail);
        mPassword = (AutoCompleteTextView)findViewById(R.id.userpassword);


        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){


                    if(validate()==true){


                        if(!isConnected(LoginActivity.this)){
                            buildDialog(LoginActivity.this).show();
                        }else {




                        mLoginProgress.setTitle("Logging In");
                        mLoginProgress.setMessage("Please Wait...");
                        mLoginProgress.setCanceledOnTouchOutside(false);
                        mLoginProgress.show();
                    LoginUser(email,password);}}
                }else {
                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }
    private void LoginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mLoginProgress.dismiss();
                            Intent mainintent = new Intent(LoginActivity.this,MainActivity.class);
                            mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainintent);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            mLoginProgress.hide();
                            Toast.makeText(LoginActivity.this, "Account do not exist",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
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
