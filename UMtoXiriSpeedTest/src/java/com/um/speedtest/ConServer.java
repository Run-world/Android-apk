package com.um.speedtest;

import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;    
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConServer {
     private static   String s;
     private static   String ss;
     private static   String url;
     private static   String nn;
     private final static  String TAG = "ConServer---------";



     public static String SecSendPlatform()
     {
        GetBaseMsg msg = new GetBaseMsg(); 
          JSONObject  obj0;
           obj0 = msg.SecMsgToJson();
	    Log.i("lgs", "sec------msg" + obj0);


        //传输json数据
        try{
            URL url = new URL("http://weixin.sd.chinamobile.com:9081/networkspeedtest/paramurl/acceptSpeedResult.do");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    Log.i("lgs", "sec------connect to server!" + obj0);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
		
	    OutputStream OS = conn.getOutputStream();     
	    OutputStreamWriter  osw = new OutputStreamWriter(OS,"utf-8");
            BufferedWriter bw  = new BufferedWriter(osw);
            bw.write(String.valueOf(obj0));
	    bw.flush();


            int code = conn.getResponseCode();
	    Log.i(TAG,"code  =   "+ code);
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb  =  new StringBuilder();
            while((ss = reader.readLine()) != null){
                sb.append(ss);
            }
            reader.close();
            ss = sb.toString();
            Log.i(TAG, "s === "+ss);
		


        } catch (MalformedURLException e)  {              
                     e.printStackTrace();           
        }catch (IOException e) {
            e.printStackTrace();
        }
	return ss;
    }
				

     public static String  FirSendPlatform(){
         JSONObject obj;
         GetBaseMsg msg = new GetBaseMsg(); 
         obj = msg.FirMsgToJson();   
         Log.i("lgs", "connect to server!" + obj);
        //传输json数据
        try{
            URL url = new URL("http://weixin.sd.chinamobile.com:9081/networkspeedtest/paramurl/paramTest.do");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    Log.i(TAG, "connect to server!");
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
		
	    OutputStream OS = conn.getOutputStream();     
	    OutputStreamWriter  osw = new OutputStreamWriter(OS,"utf-8");
            BufferedWriter bw  = new BufferedWriter(osw);
            bw.write(String.valueOf(obj));
	    bw.flush();


            int code = conn.getResponseCode();
	    Log.i(TAG,"code  =   "+ code);
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb  =  new StringBuilder();
            while((s = reader.readLine()) != null){
                sb.append(s);
            }
            reader.close();
            s = sb.toString();
            Log.i(TAG, "s === "+s);
		


        } catch (MalformedURLException e)  {              
                     e.printStackTrace();           
        }catch (IOException e) {
            e.printStackTrace();
        }

        
        return s;

    }
}
