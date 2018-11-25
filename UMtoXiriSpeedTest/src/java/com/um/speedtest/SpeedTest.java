package com.um.speedtest;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

import java.lang.Thread;
import java.lang.String;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import java.text.DecimalFormat;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class SpeedTest extends Activity {

    private static final String TAG = "SpeedTest------";
    private Button btn_speed;
    private String msg = "";
    private MyView mLineView;
    public volatile boolean exitb = true;
    private final int UPDATE_SPEED = 1;
    private final int UPDATE_DNOE = 0;
    private final int CON_PLM = 3;
    byte[] imageData = null;
    private boolean button_flag = false;
    private NetWorkSpeedInfo netWorkSpeedInfo = null;
    private TextView nowSpeed, averageSpeed,maxSpeed;
    private String  t_averageSpeed = "";
    private String  t_maxSpeed = "";
    public static ThreadA ta;
    public static ThreadB tb; 
    public static ThreadC tc;
    public static String speed_URL;
    //draw  speed
    List<Float> list = new ArrayList<Float>();
	final private float Current_2_M = 2.0f;
	final private float Current_4_M = 4.0f;
	final private float Current_8_M = 8.0f;
	final private float Current_20_M = 20.0f;
	final private float Current_50_M = 50.0f;
	final private float Current_100_M = 100.0f;
	final private float Current_200_M = 200.0f;
       
    //Init
    public void InitData()
    {
	
	btn_speed = (Button)findViewById(R.id.btn_speed);
        nowSpeed = (TextView)findViewById(R.id.now_speed);
        averageSpeed = (TextView)findViewById(R.id.average_speed);
        maxSpeed = (TextView)findViewById(R.id.max_speed);
        mLineView = (MyView) this.findViewById(R.id.line);   
        nowSpeed.setText(getResources().getString(R.string.init_data));
        averageSpeed.setText(getResources().getString(R.string.init_data));
        maxSpeed.setText(getResources().getString(R.string.init_data));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedtest);
        InitData();	
             

        btn_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
	     if(!button_flag){
              Toast.makeText(SpeedTest.this,"3s后开始计算",Toast.LENGTH_LONG).show();      
              btn_speed.setText(getResources().getString(R.string.cancel_test));
              button_flag = true;
              exitb = true;
  	      list.clear();
              mLineView.clear();
	      new Thread(new Runnable() {   
             @Override                 
             public void run() {       
	      String str  = ConServer.FirSendPlatform();
                 Log.i("lgs", "lgs  URL 11111" + speed_URL);
              speed_URL = subString(str, "speedtest_url");
                 Log.i("lgs", "lgs  URL 22222" +speed_URL);
              NetWorkSpeedInfo.speedtest_id = subString(str, "speedtest_id");
	      Message msge = new Message();
	      msge.what = CON_PLM;
	      handler.sendMessage(msge);
              }                         
  	   }).start();
           }else{
              btn_speed.setText(getResources().getString(R.string.begin_test));
              button_flag = false;
	      if(ta != null && ta.isAlive()){
		      Log.i(TAG, "onInvisible: ta != null && ta.isAlive()");
		      netWorkSpeedInfo.flag = false;
		      ReadFile.StopDownLoad(netWorkSpeedInfo);
	      }
	      if(tb != null && tb.isAlive()){
		      Log.i(TAG, "onInvisible: tb != null && tb.isAlive()");
		      exitb = false;
	      }
           }                            

            }
        });
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
			    DecimalFormat decimalFormat = new DecimalFormat(".00#");
			    Float Kep_dec =Float.parseFloat(decimalFormat.format(tms)) ;
			    Kep_dec=Kep_dec*8;
			    Log.d("lwn","Kep_dec:" + Kep_dec);
			    if(Kep_dec < Current_2_M){
				curY = 350 -Kep_dec * (30 / Current_2_M);
				Log.d("lwn","Current_2_M");
			    }else if(Kep_dec < Current_4_M){
				curY = 350 -Kep_dec * (60 / Current_4_M);
				Log.d("lwn","Current_4_M");
			    }else if(Kep_dec < Current_8_M){
				curY = 350 -Kep_dec * (90 / Current_8_M);
				Log.d("lwn","Current_8_M");
			    }else if(Kep_dec < Current_20_M){
				curY = 350 - 90 - (Kep_dec-8)* (30 / (Current_20_M - Current_8_M));
				Log.d("lwn","Current_20_M");
			    }else if(Kep_dec < Current_50_M){
				curY = 350 - 120 - (Kep_dec-20) * (60 / (Current_50_M - Current_20_M));
				Log.d("lwn","Current_50_M");
			    }else if(Kep_dec < Current_100_M){
				curY = 350 - 180 - (Kep_dec-50) * (60 / (Current_100_M - Current_50_M));
				Log.d("lwn","Current_100_M");
			    }else {
				curY = 350 - 240 - (Kep_dec-100) * (60 / (Current_200_M - Current_100_M));
				Log.d("lwn","Current_200_M");
			    }
			    Log.d("lwn","curY:" + curY + "msg.arg1" + msg.arg1);

			    mLineView.setLinePoint(msg.arg1,curY);
			    nowSpeed.setText(transform(tem));
			    averageSpeed.setText(transform(falg));
			    maxSpeed.setText(transform(max));
			    t_maxSpeed =  transformMS(max);
			    t_averageSpeed =  transformMS(falg);
			    
			break;
		   case UPDATE_DNOE:
			    btn_speed.setText(getResources().getString(R.string.begin_test));
                            button_flag = false;		

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
	  
        }
    }

   
   //deal speeddata
   public String transform(Float temp){
        String speed = null;
        if(temp < 1024){
            speed = get2point(temp) + "K/S";
        }else {
            temp = get2point(temp/1024);
            speed = temp.toString() + "M/S";
        }
        return speed;
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













