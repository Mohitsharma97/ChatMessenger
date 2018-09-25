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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private TextView logintext;
private EditText nameText,emailText,passwordText;
private Button signupButton;
private ProgressDialog mprogressDialog;
private FirebaseAuth mAuth;
private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        logintext = (TextView)findViewById(R.id.link_login);
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        mprogressDialog  = new ProgressDialog(this);
        nameText = (EditText)findViewById(R.id.input_name);
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);
        signupButton=(Button)findViewById(R.id.btn_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String displayname = nameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if(!TextUtils.isEmpty(displayname)||!TextUtils.isEmpty(email)|| !TextUtils.isEmpty(password)){



                    if(validate()== true){

                        if(!isConnected(RegisterActivity.this)){
                            buildDialog(RegisterActivity.this).show();
                        }else {
                        mprogressDialog.setTitle("Registering User");
                        mprogressDialog.setMessage("Please Wait...");
                        mprogressDialog.setCanceledOnTouchOutside(false);
                        mprogressDialog.show();
                        registeruser(displayname,email,password);}
                    }



                }else {
                    mprogressDialog.hide();
                    Toast.makeText(RegisterActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }




            }
        });
    }




    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private void registeruser(final String displayname, String email, String password) {



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String,String> usermap= new HashMap<>();
                            usermap.put("name",displayname);
                            usermap.put("status","I am Using ChatApp");
                            usermap.put("image","default");
                            usermap.put("thum_image","default");

                            mDatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mprogressDialog.dismiss();
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent  mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });





                        } else {
                            // If sign in fails, display a message to the user.
                            mprogressDialog.hide();
                            Toast.makeText(RegisterActivity.this, "Account Already Exist",
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
