package com.example.gsoc2015_nm;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;

public class ImgP_OpenCV extends ActionBarActivity {
    //Setting variables
	static ImageView picture;
    static String pic_path; //original pic path
    static String pic_modify_name = "ProcessPic"; //path to image modified 1
    static String pic_modify_path = null;
    static String pic_modify_Undoname = "ProcessPic2"; //path to image modified 2
    static String pic_modify_Undopath = null;
    static Bitmap pic1 = null;
    
    private MenuItem    mItemPreviewCanny;
    private MenuItem    mItemPreviewSobel;
    private MenuItem	mItemPreviewMedian;
    private MenuItem	mItemPreviewMean;
    private MenuItem	mItemPreviewGaussian;
    private MenuItem	mItemPreviewErode;
    private MenuItem	mItemPreviewDilate;
    private MenuItem	mItemPreviewGetR;
    private MenuItem	mItemPreviewGetG;
    private MenuItem	mItemPreviewGetB;
    private MenuItem	mItemPreviewColor2Grey;
    private MenuItem	mItemPreviewOtsu;
    private MenuItem	mItemPreviewWatershed; 
    private SubMenu		mFilter; 
    private SubMenu		mMorphology; 
    private SubMenu		mColorOp; 
    
    private static String TAG = "ImgP_OpenCV";

    
    
    
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
    
    public ImgP_OpenCV() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_p__open_cv);
		
		//Set ImageView Image//
		picture = (ImageView) this.findViewById(R.id.Img_OCV_1);
		Log.i(TAG, "Main List options Pic PATH::: " + MainListOptions.picPath);
		
		//GET MAIN PICTURE
		pic_path = MainListOptions.picPath;
        if ((pic_path != null) && (pic1 == null)){
        	pic1 = PicUtils.bitmaprough(pic_path,pic_path,1000,1000);
        	picture.setImageBitmap(pic1);       
        }
        else {
        		Log.i(TAG, "No Picture Selected");
        		return ;
        	}
        if (pic1 != null){
        	pic_modify_path = PicUtils.saveToFile(pic1, pic_modify_name);
        	Log.i(TAG, "Pic1 Saved");
        }
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.img_p__open_cv, menu);
	        Log.i(TAG, "called onCreateOptionsMenu");
	        //add subMenu
	        mFilter = menu.addSubMenu("Filter");
	        mMorphology = menu.addSubMenu("Morphology");
	        mColorOp = menu.addSubMenu("ColorFilters");
	        //add Menu items
	        mItemPreviewGetR = mColorOp.add("Channel R");
	        mItemPreviewGetG = mColorOp.add("Channel G");
	        mItemPreviewGetB = mColorOp.add("Channel B");
	        mItemPreviewColor2Grey =  mColorOp.add("RGB2Grey");
	        mItemPreviewCanny = mFilter.add("Canny");
	        mItemPreviewSobel = mFilter.add("Sobel");
	        mItemPreviewMedian = mFilter.add("Median");
	        mItemPreviewMean = mFilter.add("Mean");
	        mItemPreviewGaussian = mFilter.add("Gaussian");
	        mItemPreviewErode = mMorphology.add("Erode (Ball)");
	        mItemPreviewDilate = mMorphology.add("Dilate (Ball)");
	        mItemPreviewOtsu = menu.add("Otsu threshold");
	        //mItemPreviewWatershed = menu.add("Watershed");
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (pic1 != null){
			
			if (item == mItemPreviewSobel) {
				
				Log.i(TAG,"Trying to Load Mat");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
	        	Mat m=new Mat();
				Utils.bitmapToMat(pic1, m);
				Log.i(TAG,"Load Mat");
				Imgproc.cvtColor(m, m, Imgproc.COLOR_BGR2GRAY, 4);
				Imgproc.Sobel(m, m, CvType.CV_8U, 1, 1);
				Core.convertScaleAbs(m, m, 30, 0);
				Imgproc.cvtColor(m, m, Imgproc.COLOR_GRAY2BGR, 4);
				Utils.matToBitmap(m,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Sobel done");
				m.release();
				
				return true;
			}
			
			if (item == mItemPreviewCanny) {
				Log.i(TAG,"Trying Canny filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
	        	Mat m=new Mat();
				Utils.bitmapToMat(pic1, m);
	            Imgproc.Canny(m, m, 80, 90);
	            Imgproc.cvtColor(m, m, Imgproc.COLOR_GRAY2BGRA, 4);
				Utils.matToBitmap(m,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Canny edge detection done");
				m.release();	
				
				return true;
			}
			
			if (item == mItemPreviewMedian) {
				Log.i(TAG,"Trying Median filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
	        	Mat m=new Mat();
				Utils.bitmapToMat(pic1, m);
	            Imgproc.medianBlur(m, m, 5);
				Utils.matToBitmap(m,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Median Filter done");
				m.release();		
				
				return true;
			}
			
			if (item == mItemPreviewMean) {
				Log.i(TAG,"Trying Mean filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				int kernelSize = 5;
	        	Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Mat kernel = Mat.ones(kernelSize,kernelSize, CvType.CV_32F);	      
		         for(int i=0; i<kernel.rows(); i++){
		            for(int j=0; j<kernel.cols(); j++){
		            
		               double[] m = kernel.get(i, j);
		               
		               for(int k =0; k<m.length; k++){
		                  m[k] = m[k]/(kernelSize * kernelSize);
		               }
		               kernel.put(i,j, m);
		            }
		         }	   
		         
		        Imgproc.filter2D(mpic, mpic, -1, kernel);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Mean Filter done");
				mpic.release();	
				kernel.release();
				
				return true;
			}
			
			if (item == mItemPreviewGaussian) {
				Log.i(TAG,"Trying Gaussian filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				int kernelSize = 11;
	        	Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
		        Imgproc.GaussianBlur(mpic, mpic,new Size(kernelSize,kernelSize), 0);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Gaussian Filter done");
				mpic.release();			
				
				return true;
			}
			
			
			if (item == mItemPreviewOtsu) {
				Log.i(TAG,"Trying Image Integral");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
	        	Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Imgproc.cvtColor(mpic, mpic, Imgproc.COLOR_BGR2GRAY, 4);
				Imgproc.threshold(mpic, mpic, 0, 255, Imgproc.THRESH_OTSU);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Image Integral done");
				mpic.release();			
				
				return true;
			}
			
			if (item == mItemPreviewErode) {
				Log.i(TAG,"Trying Erode filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				int erosion_size = 5;
	        	Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(erosion_size, erosion_size));
				Imgproc.erode(mpic, mpic, element);				
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Erode Filter done");
				mpic.release();	
				
				return true;
			}
			
			if (item == mItemPreviewDilate) {
				Log.i(TAG,"Trying Dialte filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				int erosion_size = 5;
	        	Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(erosion_size, erosion_size));
				Imgproc.dilate(mpic, mpic, element);				
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				Log.i(TAG,"Dilate Filter done");
				mpic.release();	
				
				return true;
			}
			
			if (item == mItemPreviewGetR){
				Log.i(TAG,"Trying Get R");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				Mat mpic = new Mat();
				Utils.bitmapToMat(pic1, mpic);
				List<Mat> lRGB = new ArrayList<Mat>(3);
				Core.split(mpic, lRGB);
				mpic = lRGB.get(1);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				mpic.release();	
				lRGB.clear();
				
				Log.i(TAG,"Done Get R");				
				return true;
				
			}
			if (item == mItemPreviewGetB){
				Log.i(TAG,"Trying Get B");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				Mat mpic = new Mat();
				Utils.bitmapToMat(pic1, mpic);
				List<Mat> lRGB = new ArrayList<Mat>(3);
				Core.split(mpic, lRGB);
				mpic = lRGB.get(3);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				mpic.release();	
				lRGB.clear();
				
				Log.i(TAG,"Done Get B");				
				return true;
				
			}
			if (item == mItemPreviewGetG){
				Log.i(TAG,"Trying Get G");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				Mat mpic = new Mat();
				Utils.bitmapToMat(pic1, mpic);
				List<Mat> lRGB = new ArrayList<Mat>(3);
				Core.split(mpic, lRGB);
				mpic = lRGB.get(2);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				mpic.release();	
				lRGB.clear();
				
				Log.i(TAG,"Done Get G");				
				return true;
				
			}
			if (item == mItemPreviewColor2Grey){

	        	
				Log.i(TAG,"Trying RGB2Gray Filter");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				Mat mpic =new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Imgproc.cvtColor(mpic, mpic, Imgproc.COLOR_BGR2GRAY, 4);
				Utils.matToBitmap(mpic,pic1);
				picture.setImageBitmap(pic1);
				
				Log.i(TAG,"RGB2Gray Filter done");
				mpic.release();
				return true;
				
			}
			
			/*if (item == mItemPreviewWatershed){

	        	
				Log.i(TAG,"Trying Watershed");
				pic_modify_Undopath = PicUtils.saveToFile(pic1, pic_modify_Undoname);
				Mat mpic =new Mat();
				Mat mfin = new Mat();
				Utils.bitmapToMat(pic1, mpic);
				Utils.bitmapToMat(pic1, mfin);
				mfin.convertTo(mfin,CvType.CV_32S);
				mpic.convertTo(mpic,CvType.CV_8UC3);
				Imgproc.watershed(mpic, mfin);
				Log.i(TAG,"Watershed done");
				mfin.convertTo(mfin,CvType.CV_8U);
				Utils.matToBitmap(mfin,pic1);
				picture.setImageBitmap(pic1);
				
				Log.i(TAG,"Watershed done");
				mfin.release();
				mpic.release();
				return true;
				
			}*/
			
			
			//SAVING _ RESET _ UNDO//
			
			if (id == R.id.save){
				//Save file with default name
				if (pic1 != null) {
					//pic1 = PicUtils.bitmaprough(pic_modify_path,pic_modify_path,1000,1000);				
					PicUtils.addImageToGallery(PicUtils.saveToFile(pic1, null), ImgP_OpenCV.this);					
				}
				else {
					Log.i(TAG,"No file to save");
				}
				
				return true;
				
			}
			
			if (id == R.id.resetP){
				//Save file with default name
				if (pic_path != null){
		        	pic1 = PicUtils.bitmaprough(pic_path,pic_path,1000,1000);
		        	picture.setImageBitmap(pic1);       
		        }
		        else {
		        		Log.i(TAG, "No Picture Selected");
		        	}   
				return true;
				
			}			
			if (id == R.id.undoP){
				if (pic_modify_Undopath != null){
					Bitmap buffPic = pic1; 
					pic1 = PicUtils.bitmaprough(pic_modify_Undopath,pic_modify_Undopath,1000,1000);
					pic_modify_Undopath = PicUtils.saveToFile(buffPic, pic_modify_Undoname);
					buffPic.recycle();
					picture.setImageBitmap(pic1);  
					Log.i(TAG, "Pic1 Undo");
					
				}
				return true;
				
				
			}
			
			if (id == R.id.DoneP){
                Log.i(TAG, "Done");
                pic1 = null;
                finish();
			}
			
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }
    
}
    
    
    
  
