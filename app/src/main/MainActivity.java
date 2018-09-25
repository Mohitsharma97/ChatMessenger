package com.example.mohitsharma.chatmessenger;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private TextView mChatslabel;
    private TextView mFriendslabel;
    private TextView mRequestslabel;
    private ViewPager mMainpager;
    private PagerViewAdapter mPagerViewAdapter;
    private FirebaseAuth mAuth;

    private static final String TAG = "MyFirebaseToken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChatslabel=(TextView)findViewById(R.id.chatlabels);
        mFriendslabel=(TextView)findViewById(R.id.Friendslabels);
        mRequestslabel=(TextView)findViewById(R.id.Requestslabels);
        mMainpager = (ViewPager)findViewById(R.id.mainpager);
        mAuth = FirebaseAuth.getInstance();

       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "MyFirbaseToken " + refreshedToken);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainpager.setAdapter(mPagerViewAdapter);

        mChatslabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainpager.setCurrentItem(0);
            }
        });

        mFriendslabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainpager.setCurrentItem(1);
            }
        });

        mRequestslabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainpager.setCurrentItem(2);
            }
        });
        mMainpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                        changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent startintent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(startintent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {
        if(position==0){
            mChatslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mChatslabel.setTextSize(22);

            mFriendslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mFriendslabel.setTextSize(16);

            mRequestslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mRequestslabel.setTextSize(16);



        }
        if(position==1){
            mChatslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mChatslabel.setTextSize(16);

            mFriendslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mFriendslabel.setTextSize(22);

            mRequestslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mRequestslabel.setTextSize(16);



        }
        if(position==2){
            mChatslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mChatslabel.setTextSize(16);

            mFriendslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mFriendslabel.setTextSize(16);

            mRequestslabel.setTextColor(getColor(R.color.cast_expanded_controller_text_color));
            mRequestslabel.setTextSize(22);



        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

}
