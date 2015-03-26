package com.example.gsoc2015_nm;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.String;

/**
 * Created by nayru on 3/17/15.
 */

public class TakePic extends Activity {

    private final static String TAG = "TakePic_Activity";
    static ImageView mImageView;
    public static String mCurrentPhotoPath;
    public static String PhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takepic_layout);
        
        Log.i(TAG,"On Create_TakePic");
        Log.i(TAG,"  PhotoPath Init: " + PhotoPath );
        if (PhotoPath != null){
            Log.i(TAG,"Photo File Absolute Path" + PhotoPath);
             setPic();
        }
        
        //Back Done menu button
        Button backButton = (Button) findViewById(R.id.backmenu);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG,"Vuelvo al menú");
                if (PhotoPath != null){
                MainListOptions.picPath = PhotoPath;
                Log.i(TAG,"Path not null");
                }
                
                finish();
            }
        });

        //CAMERA BUTTON
        Button camButton = (Button) findViewById(R.id.cameraButton);
        camButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View w) {
                Log.i(TAG,"Open cam");
                dispatchTakePictureIntent();

            }
        });

    }

    //CAMERA


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                String ex2 = ex.getMessage();
                Log.i(TAG,ex2);
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.i(TAG,"Photo File not null");
                mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                galleryAddPic();
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                PhotoPath = photoFile.getAbsolutePath();
                PicUtils.addImageToGallery(PhotoPath,TakePic.this);
                //Log.i(TAG,"Photo File Absolute Path" + PhotoPath);

            }
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
            Log.i(TAG,"Cam Result");
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp;
        Log.i(TAG,"String-FILE");
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // Create the storage directory if it does not exist
        if (! storageDir.exists()){
            Log.d(TAG,"No existe storageDir");
            if (! storageDir.mkdirs()){
                Log.d(TAG,"MyCameraApp failed to create directory");
                return null;
            }
        }

        //File image
        File image = new File(storageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Log.i(TAG,"Gallery Add PIC");

        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    private void setPic() {
        // Get the dimensions of the View
        mImageView = (ImageView) this.findViewById(R.id.imageView);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        Log.i(TAG, "SET PIC_targetW:   " + targetW);
        Log.i(TAG, "SET PIC_targetH:   " + targetH);
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        BitmapFactory.decodeFile(PhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        Log.i(TAG, "SET PIC_mCurrentPhotoPath:   " + mCurrentPhotoPath);
        Log.i(TAG, "SET PIC_photoW:   " + photoW);
        Log.i(TAG, "SET PIC_photoH:   " + photoH);

        // Determine how much to scale down the image
        //int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        //Log.i(TAG, "SET PIC_scaleFactor:   " + scaleFactor);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        //bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(PhotoPath,bmOptions);
        mImageView.setImageBitmap(bitmap);
        Log.i(TAG, " DONE SET PIC   ");
    }

    public void onStart() {
        super.onStart();
        // Emit LogCat message
        Log.i(TAG, "Entered the onStart() method");
    }

    public void onResume(){
        super.onResume();

    }

}
