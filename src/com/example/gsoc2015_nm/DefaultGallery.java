package com.example.gsoc2015_nm;

import android.support.v7.app.ActionBarActivity;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class DefaultGallery extends ActionBarActivity {
String TAG = "GALLERY";
static ImageView mImageView;
public static String photoPath = null;
static String sampleName = "sampleG";
Bitmap image1 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_default_gallery);
		mImageView = (ImageView) this.findViewById(R.id.imgGallery);
		
		
        // IMG1 Button
        Button Img1Button = (Button) findViewById(R.id.buttImg1);
        Img1Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View w) {
                Log.i(TAG,"Img1");
                //SET IMAGE
                image1 = BitmapFactory.decodeResource(getResources(), R.drawable.cy3_cy5_overlay_progenesis);
                Log.i(TAG,"large Icon decoded" + image1.getHeight());
                mImageView.setImageBitmap(image1);
            }
        });       
        
        // IMG2 Button
        Button Img2Button = (Button) findViewById(R.id.buttImg2);
        Img2Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View w) {
                Log.i(TAG,"Img1");
                //SET IMAGE
                image1 = BitmapFactory.decodeResource(getResources(), R.drawable.celula);
                Log.i(TAG,"large Icon decoded" + image1.getHeight());
                mImageView.setImageBitmap(image1);
            }
        });
        
        // IMG3 Button
        Button Img3Button = (Button) findViewById(R.id.buttImg3);
        Img3Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View w) {
                Log.i(TAG,"Img3");
                //SET IMAGE
                image1 = BitmapFactory.decodeResource(getResources(), R.drawable.globulosa);
                Log.i(TAG,"large Icon decoded" + image1.getHeight());
                mImageView.setImageBitmap(image1);
            }
        });
				
		
        // BACK BUTTON
        Button backButton = (Button) findViewById(R.id.buttDone1);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG,"Vuelvo al menú");
                //If IMAGE save to process
                if (image1 != null){
                	photoPath = PicUtils.saveToFile(image1, sampleName);
                	MainListOptions.picPath = photoPath;
                	PicUtils.addImageToGallery(photoPath, DefaultGallery.this);
                }
                finish();
            }
        });
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.default_gallery, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
