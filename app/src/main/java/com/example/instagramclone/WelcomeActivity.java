package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity {
    private TextView txtWelcom;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtWelcom=findViewById(R.id.txtWelcome);
        txtWelcom.setText("Welcome " + ParseUser.getCurrentUser().getUsername());
        btnLogout=findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null) {
                            ParseUser.logOut();
                            FancyToast.makeText(WelcomeActivity.this, "Loged out.", FancyToast.SUCCESS, FancyToast.LENGTH_SHORT, true);
                            Intent intent= new Intent(WelcomeActivity.this,SignUp.class);
//                            startActivity(intent); //Helps us jump to the desired activity
                            finish();//takes you the previous activity
                        }else{
                            FancyToast.makeText(WelcomeActivity.this,"Error",FancyToast.ERROR,FancyToast.LENGTH_SHORT,true);
                        }
                    }
                });
            }
        });
    }
}
