package com.example.instagramclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.http.ParseHttpResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
   private Button btnSignUp;
   private Button btnLogin;
   private EditText txtUsername,txtPassword,txtPassword2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp=findViewById(R.id.btnSignUp);
        btnLogin=findViewById(R.id.btnLogin);

        txtPassword=findViewById(R.id.txtPassword);
        txtPassword2=findViewById(R.id.txtPassword2);
        txtUsername=findViewById(R.id.txtEmail);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        //we wont be needing this as the transition to next activity happens right away. This was required when we were testing the login feature

//        if(ParseUser.getCurrentUser()!=null){
//            Log.i("**********","!!!!!!!!!!!!!1");
//            ParseUser.getCurrentUser().logOut();
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                ParseUser.logInInBackground(txtUsername.getText().toString(), txtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null && e == null){
                            FancyToast.makeText(SignUp.this,ParseUser.getCurrentUser().getUsername()+" logged in.",FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,true).show();
                            transitionToSocialMediaActivity();
                        }else{
                            FancyToast.makeText(SignUp.this,"Invalid Credentials", FancyToast.ERROR,FancyToast.LENGTH_SHORT,true).show();
                        }
                    }
                });

                break;

            case R.id.btnSignUp:

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing Up " + txtUsername.getText()+toString());
                progressDialog.show();


                final ParseUser appUser=new ParseUser();
                appUser.setUsername(txtUsername.getText().toString());
                appUser.setPassword(txtPassword.getText().toString());


                if(txtPassword2.getText().toString().equals(txtPassword.getText().toString()) &&
                        txtPassword2.getText().toString() != "" && txtPassword.getText().toString()!=null
                        && txtUsername.getText().toString() != null ) {
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " has signed in", FancyToast.SUCCESS, FancyToast.LENGTH_SHORT, true).show();

                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage() + "", FancyToast.ERROR, FancyToast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                }else{
                    FancyToast.makeText(SignUp.this,"Passwords do not match and/or Username is empty",FancyToast.ERROR,FancyToast.LENGTH_SHORT,true).show();
                }

                progressDialog.dismiss();

                break;
        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent=new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
    }

    //this method helps to get to rd of method when click on the screen
    public void onClickConstraint(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }


}





