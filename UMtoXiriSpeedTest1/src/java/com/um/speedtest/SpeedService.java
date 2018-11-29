package com.um.speedtest;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread;
import java.lang.String;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import java.text.DecimalFormat;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class SpeedService extends Service {
	private final String TAG = "SpeedService------";
	private String  t_averageSpeed = "";
	private String  t_maxSpeed = "";
	public static ThreadA ta;
	public static ThreadB tb; 
	public static ThreadC tc;
	public static String speed_URL;
	public volatile boolean exitb = true;
	private final int UPDATE_SPEED = 1;
	private final int UPDATE_DNOE = 0;
	private final int CON_PLM = 3;
        byte[] imageData = null;
        private NetWorkSpeedInfo netWorkSpeedInfo = null;
         List<Float> list = new ArrayList<Float>();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable(){
		
		public void run(){
		String str  = ConServer.FirSendPlatform();
		Log.i("lgs", "lgs  URL 11111" + str);
		speed_URL = subString(str, "speedtest_url");
		Log.i("lgs", "lgs  URL 22222" +speed_URL);
		NetWorkSpeedInfo.speedtest_id = subString(str, "speedtest_id");
		Message msge = new Message();
		msge.what = CON_PLM;
		handler.sendMessage(msge);
}
}).start();

                return flags;
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "---onDestroy---");
		super.onDestroy();
	}

    //reciver   msg
       Handler handler = new Handler(){
             @Override
             public void handleMessage(Message msg) {
                float tem = 0.0f;
                float tms = 0.0f;
                float curY = 0.0f;
		float max = 0.0f;
                float falg = 0.0f;
                float numberTotal = 0.0f;
               switch (msg.what){
                   case CON_PLM:
			//start dwonload test
		        netWorkSpeedInfo = new NetWorkSpeedInfo();
			ta = new ThreadA();
                        tb = new ThreadB();
                        ta.start();
                        tb.start();
			break;
                   case UPDATE_SPEED:
			    tem = (float)(netWorkSpeedInfo.speed / 1024.0f);
                            list.add(tem);
			    Log.i("lwn", "tem****" + tem + "k/s");
			    tms = tem / 1024;
                            max = Collections.max(list);
			    Log.i("lwn", "tms****" + tms + "m/s");
			    for (Float numberLong : list) {
				numberTotal += numberLong;
			    }
			    falg = numberTotal / list.size();
			    numberTotal = 0;
			    t_maxSpeed =  transformMS(max);
			    t_averageSpeed =  transformMS(falg);
			    
			break;
		   case UPDATE_DNOE:

			    if(ta != null && ta.isAlive()){
				    netWorkSpeedInfo.flag = false;
				    //	ta.interrupt();
				    ReadFile.mFlag = false;
				    ReadFile.StopDownLoad(netWorkSpeedInfo);
                    } 
			   //测速要求十秒  getdata
			   if(11 == msg.arg1){
			   NetWorkSpeedInfo.speed_max =  t_maxSpeed;
			   NetWorkSpeedInfo.speed_avg =  t_averageSpeed;
			   tc = new ThreadC();
                           tc.start();

		   }
                    exitb = true;
			break;
		   default:
			break;
               }
             }
         };        

    //start download
    class ThreadA extends Thread{
        @Override
        public void run() {
         imageData = ReadFile.getFileFromUrl(speed_URL, netWorkSpeedInfo);
        }
    }

   //start   
    class ThreadB extends Thread{
          @Override
          public void run() {
	        try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
      
	  int mX = 300;  
          int time = 0;
	  while(time <= 10 && exitb){
          Message message = new Message();
          message.what = UPDATE_SPEED;
          message.arg1 = mX;
		 try {
                     sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 mX += 30;
	         time++;	
	   handler.sendMessage(message);
 	      
	  }
              
             Log.i("lgs", "time -----" + time);
          Message message = new Message();
	  message.what = UPDATE_DNOE;
          message.arg1 = time;
	  handler.sendMessage(message);
          }
      }

    //second request
    class ThreadC extends Thread{
        @Override
        public void run() {
             Log.i("lgs", "enter  msgg -----" );
	  String  msgg  = ConServer.SecSendPlatform();
             Log.i("lgs", "msgg -----" + msgg);
	  System.exit(0);
        }
    }
  
      //deal speeddata
    public String transformMS(Float temp){
         String speed = null;
         temp = get2point(temp/1024);
         speed = temp.toString() + "M/S";
         return speed;
     }
 
     //set dataformat    
     public  Float get2point(float value){
         DecimalFormat decimalFormat = new DecimalFormat(".0#");
         Float Kep_dec =Float.parseFloat(decimalFormat.format(value)) ;
         return Kep_dec;
     }
   // analyze   url
    public static String subString(String str, String strStart) {
       String symbol = "\":\"";
       String strEnd = ""; 
       if("speedtest_id" == strStart){
                 strStart = strStart + symbol;
                 strEnd = "\",\"statu";
       }
       else if("speedtest_url" == strStart){
                 strStart = strStart + symbol;
		 strEnd  ="\"}}";
       }
       else if("desc" == strEnd){
                    strStart = strStart + symbol;
		    strEnd = "\"}";
       }
       
       int start = str.indexOf(strStart);
       int end = str.indexOf(strEnd);
       
       String mystr = str.substring(start + strStart.length(), end);
       return mystr;
    }

}
