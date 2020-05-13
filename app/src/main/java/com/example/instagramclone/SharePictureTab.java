package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {
    private Button btnShareImage;
    private EditText edtDescription;
    private ImageView imageShare;
    Bitmap receivedImageBitmap;
    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        imageShare=view.findViewById(R.id.imgShare);
        edtDescription=view.findViewById(R.id.edtDescription);
        btnShareImage=view.findViewById(R.id.btnShareImage);

        imageShare.setOnClickListener(this);
        btnShareImage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.i("Before Switch Statement",v.getId()+"");
        switch (v.getId()){

            case R.id.btnShareImage:
                if(receivedImageBitmap==null){
                    if(edtDescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Error", Toast.LENGTH_SHORT,FancyToast.ERROR,true);
                    }
                }else {
//                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//                    byte[] bytes=byteArrayOutputStream.toByteArray();
//                    ParseFile parseFile = new ParseFile("pic.png",bytes);
//                    ParseObject parseObject = new ParseObject("Photo");
//                    parseObject.put("picture",parseFile);
//                    parseObject.put("image-des",edtDescription.getText().toString());
//                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
//
//
//                    parseObject.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if(e == null){
//                                FancyToast.makeText(getContext(),"Success",Toast.LENGTH_SHORT,FancyToast.SUCCESS,true);
//                            }else{
//                                FancyToast.makeText(getContext(),"Error",Toast.LENGTH_SHORT,FancyToast.ERROR,true);
//                            }
//
//                        }
//                    });
                    Log.i("Testing","*************************");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("img.png", bytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture", parseFile);
                    parseObject.put("image_des", edtDescription.getText().toString());
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }

                        }
                    });

                }
                break;
            case R.id.imgShare:
                if(Build.VERSION.SDK_INT >= 25 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else{
                    getChosenImage();
                }
                break;
        }
    }

    private void getChosenImage() {
       // FancyToast.makeText(getContext(),"Image uploaded Successfully", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(requestCode==1000){
             if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 getChosenImage();
             }
         }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2000){
            if(resultCode== Activity.RESULT_OK){
                try{
                    Uri selectedImage=data.getData();
                    String[]filePathColumn={MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath=cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap= BitmapFactory.decodeFile(picturePath);
                    imageShare.setImageBitmap(receivedImageBitmap);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
