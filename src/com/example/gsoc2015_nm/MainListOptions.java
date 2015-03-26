package com.example.gsoc2015_nm;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

public class MainListOptions extends ListActivity {
    private final static String TAG = "ProcPic_Main";
    String[] values = new String[] { "TakePic","SamplePic","ProcessPic" };
    static String picPath = null; //Path to the picture selected for processing

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        

        //Set array List
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        Log.i(TAG, "Done the onCreate() method");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	//Take Item
        String item = (String) getListAdapter().getItem(position);
        Log.i(TAG, "Entered the onListItemClick() method , ITEM :::"+item);
        
        //Switch ITEM
        switch (item) {
            case "TakePic":
                //TakePic (call TakePic activity, camera);
                Log.i(TAG, "Entered TakePIc");
                Intent intent_tp = new Intent(this, TakePic.class);
                this.startActivity(intent_tp);
                break;
                
            case "SamplePic":
            	//Sample Pic (call SamplePic activity, use pic from default samples at drawable folder);            	
                Log.i(TAG, "Entered PicFromGallery");
                Intent intent_gp = new Intent(this, DefaultGallery.class);
                this.startActivity(intent_gp);
                break;
                
            case "ProcessPic":
            	//Process Pic (call Process Pic activity);  
                Log.i(TAG, "Entered ProcessPic");
                Intent intent_pp = new Intent(this, ImgP_OpenCV.class);
                this.startActivity(intent_pp);
                break;
            
            default:
                Log.i(TAG, "NoItem");
                break;

        }

    }
    
}