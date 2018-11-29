package com.um.speedtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.util.Log;

public class ReadFile {
	public static InputStream stream = null;
	public static URLConnection con = null;
	public static Boolean mFlag = true;
	public static byte[] getFileFromUrl(String url, NetWorkSpeedInfo netWorkSpeedInfo) {
		int currentByte = 0;
		int fileLength = 0;
		long startTime = 0;
		long intervalTime = 0;
		
		byte[] b = null;

		int bytecount = 0;
		URL urlx = null;
		try {
			Log.d("lwn", url);
			urlx = new URL(url);
			con = urlx.openConnection();
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			fileLength = con.getContentLength();
			stream = con.getInputStream();
			Log.d("lwn------","stream  ==== "+stream);
			netWorkSpeedInfo.totalBytes = fileLength;
			//b = new byte[fileLength];
			byte[] buf=new byte[1024];
			startTime = System.currentTimeMillis();
			Log.d("lwn----------","stream.read() == "+stream.read());
			while (((currentByte = stream.read(buf)) != -1) && netWorkSpeedInfo.flag) {
	//			Log.d("lwn-------------------","test flag === "+netWorkSpeedInfo.flag);
	//			Log.d("lwn----------","mFlag === "+mFlag);
				netWorkSpeedInfo.hadFinishedBytes++;
			        Log.d("lwn----------", "fasdfas" + netWorkSpeedInfo.hadFinishedBytes);
				intervalTime = System.currentTimeMillis() - startTime;
				 Log.d("lwn----------", "time" +   intervalTime );
				if (intervalTime == 0) {
					netWorkSpeedInfo.speed = 1000;
				} else {
					netWorkSpeedInfo.speed = ((netWorkSpeedInfo.hadFinishedBytes*1024) / intervalTime) * 1000;
			        Log.d("lwn----------", "lwn---" +  netWorkSpeedInfo.speed);
				}
//				if (bytecount < fileLength) {
//					bytecount = bytecount + currentByte;
//				}
//				Log.d("lwn-------------------","test stream");
			}
		} catch (Exception e) {
			Log.e("exception : ", e.getMessage() + "");
		} finally {
			try {
				Log.d("lwn>>>","stream  ==== "+stream);
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
				Log.e("exception : ", e.getMessage());
			}
		}
		return null;
	}
	public static void StopDownLoad(NetWorkSpeedInfo netWorkSpeedInfo){
		try {			
			if (stream != null) {
				mFlag = false;
			//	stream.close();
			//Log.d("lwn--","stream  ==== "+stream);	
			}
		} catch (Exception e) {
		//	Log.e("exception : ", e.getMessage());
		}
	}

}
