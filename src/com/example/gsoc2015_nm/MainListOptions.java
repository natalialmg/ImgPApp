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
    static ImageView picture;
    static String picPath = null;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //setContentView(R.layout.activity_my_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        Log.i(TAG, "Entered the onCreate() method");
        //picture = (ImageView) this.findViewById(R.id.imageView2);
        //picPath = TakePic.PhotoPath;
        //if (picPath != null){
        //    this.setImage(picture,picPath);
        //}

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Entered the onListItemClick() method"+item);
        switch (item) {
            case "TakePic":
                //openSearch();

                Log.i(TAG, "Entered TakePIc");
                Intent intent_tp = new Intent(this, TakePic.class);
                this.startActivity(intent_tp);
                break;
            case "SamplePic":
                //openSettings();
                Log.i(TAG, "Entered PicFromGallery");
                Intent intent_gp = new Intent(this, DefaultGallery.class);
                this.startActivity(intent_gp);
                break;
            case "ProcessPic":
                Log.i(TAG, "Entered ProcessPic");
                Intent intent_pp = new Intent(this, ImgP_OpenCV.class);
                this.startActivity(intent_pp);
                break;
            default:
                Log.i(TAG, "NoItem");
                break;

        }

    }

    public static void setImage(ImageView mImageView, String PhotoPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(PhotoPath);
        mImageView.setImageBitmap(bitmap);
    }
    
}