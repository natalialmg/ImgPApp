package com.example.gsoc2015_nm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

class PicUtils {
	File file;
	static String TAG = "PicUtil";
	static String file_path;
	static Context context;
	
  public static String saveToFile(Bitmap bmp, String name) {
	  file_path = null;
    	  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    	  Log.i(TAG,"El timeStamp es: " +  timeStamp);
    	  
    	  if (name == null){
    		  name = timeStamp;
    	  };
    	  
    	  File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "ProcessedImages");
    	
    	  // Create the storage directory if it does not exist
          if (! storageDir.exists()){
              Log.d(TAG,"No existe storageDir");
              if (! storageDir.mkdirs()){
                  Log.d(TAG,"MyCameraApp failed to create directory");
                  return null;
              }
          }
    	  Log.i(TAG,"storage PATH:.."+ storageDir.getPath());
          File file = new File(storageDir.getPath()+ File.separator +
                  "IMG_"+ name + ".jpg");
          OutputStream fout = null;
          
          try {
        	  fout = new FileOutputStream(file);
        	  bmp.compress(CompressFormat.JPEG, 85, fout);
              fout.flush();
              fout.close();
              //MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
              Log.i(TAG,"Hubo Try.....");
              
          }
          catch(Exception e) {
        	  Log.i(TAG,"Hubo Catch....");
        	  e.printStackTrace();
          }
          Log.i(TAG,"El file path es :" + file.getAbsolutePath());
          file_path = file.getAbsolutePath();
      return file_path;
  }
  
  
  public static void addImageToGallery(final String filePath, final Context context) {

	    ContentValues values = new ContentValues();

	    values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
	    values.put(Images.Media.MIME_TYPE, "image/jpeg");
	    values.put(MediaStore.MediaColumns.DATA, filePath);

	    context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
	}
  
  public PicUtils(){	  
  };
  
  /* Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
      String state = Environment.getExternalStorageState();
      if (Environment.MEDIA_MOUNTED.equals(state)) {
          return true;
      }
      return false;
  }

  /* Checks if external storage is available to at least read */
  public boolean isExternalStorageReadable() {
      String state = Environment.getExternalStorageState();
      if (Environment.MEDIA_MOUNTED.equals(state) ||
          Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
          return true;
      }
      return false;
  }
  
  public File getAlbumStorageDir(String albumName) {
	    // Get the directory for the user's public pictures directory.
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
	        Log.e(TAG, "Directory not created");
	    }
	    return file;
	}
  
	public static Bitmap bitmaprough(String pathOfInputImage,String pathOfOutputImage, int dstWidth , int dstHeight){
		Bitmap resizedBitmap = null;
		try
		{
		    int inWidth = 0;
		    int inHeight = 0;

		    InputStream in = new FileInputStream(pathOfInputImage);

		    // decode image size (decode metadata only, not the whole image)
		    BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeStream(in, null, options);
		    in.close();
		    in = null;

		    // save width and height
		    inWidth = options.outWidth;
		    inHeight = options.outHeight;
		    // decode full image pre-resized
		    in = new FileInputStream(pathOfInputImage);
		    options = new BitmapFactory.Options();
		    // calc rought re-size (this is no exact resize)
		    if ((inWidth>dstWidth) || (inHeight>dstHeight)){
			    options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);		    	
		    }
		    else {
		    	options.inSampleSize = 1;
		    
		    }
		    // decode full image
		    Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

		    // calc exact destination size
		    Matrix m = new Matrix();
		    RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
		    RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
		    m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
		    float[] values = new float[9];
		    m.getValues(values);

		    // resize bitmap
		    if ((inWidth>dstWidth) || (inHeight>dstHeight)){
		    	resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);	    	
		    }
		    else {
		    	resizedBitmap = roughBitmap;
		    }
		    
		    // save image
		    try
		    {
		        FileOutputStream out = new FileOutputStream(pathOfOutputImage);
		        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
		    }
		    catch (Exception e)
		    {
		        Log.e("Image", e.getMessage(), e);
		    }
		}
		catch (IOException e)
		{
		    Log.e("Image", e.getMessage(), e);
		}
		return resizedBitmap;
	}

}