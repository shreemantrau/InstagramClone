package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText txtUsernameLogin, txtUserNameSignUP, txtPasswordSignUP, txtPasswordLogin;
    Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        txtUsernameLogin=findViewById(R.id.txtUsernameLogin);
        txtUserNameSignUP=findViewById(R.id.txtUsernameSignup);
        txtPasswordLogin=findViewById(R.id.txtPasswordLogin);
        txtPasswordSignUP=findViewById(R.id.txtPasswordSignUp);

        btnSignUp=findViewById(R.id.btnSignUP);
        btnLogin=findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser=new ParseUser();
                appUser.setUsername(txtUserNameSignUP.getText().toString());
                appUser.setPassword(txtPasswordSignUP.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignUpLoginActivity.this, appUser.getUsername()+": Signed Successfully!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            FancyToast.makeText(SignUpLoginActivity.this, "Error", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final ParseUser appUser=new ParseUser();
                appUser.logInInBackground(txtUsernameLogin.getText().toString(), txtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(SignUpLoginActivity.this,  txtUsernameLogin.getText()+ ": Logged Successfully!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(SignUpLoginActivity.this, "error", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

    }
}

