package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button btnSave;
    private EditText punchPower, punchSpeed,kickPower,kickSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SignUp.this);

        kickPower=findViewById(R.id.kickPower);
        kickSpeed=findViewById(R.id.kickSpeed);
        punchPower=findViewById(R.id.punchPower);
        punchSpeed=findViewById(R.id.punchSpeed);
    }


    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickboxer = new ParseObject("KickBoxer");
            kickboxer.put("punchPower", Integer.parseInt(punchPower.getText().toString()));
            kickboxer.put("kickSpeed", Integer.parseInt(kickSpeed.getText().toString()));
            kickboxer.put("punchSpeed", Integer.parseInt(punchSpeed.getText().toString()));
            kickboxer.put("kickPower", Integer.parseInt(kickPower.getText().toString()));
            kickboxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, "KickBOxer object has been saved!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                    } else {
                        FancyToast.makeText(SignUp.this, "Error", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e){
            FancyToast.makeText(SignUp.this,e+"",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }
}


