package com.um.speedtest;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.os.SystemProperties;

import android.provider.Settings;
import android.provider.Settings.Secure;
import org.json.JSONObject;
import com.um.speedtest.NetWorkSpeedInfo;

public class GetBaseMsg {

    private  String user_ip;
    public String getIp() {
        String eth0IP = SystemProperties.get("dhcp.eth0.ipaddress");
        if (!eth0IP.equals("")) {
            user_ip = eth0IP;
        } else {
            String wlan0IP = SystemProperties.get("dhcp.wlan0.ipaddress");
            if (!wlan0IP.equals("")) {
               user_ip = wlan0IP;
            }
        }
        return user_ip;
    }




    public String getCity(){
	return SystemProperties.get("persist.sys.sdcity.id", "0");
    }


    public String getMac() {
        return SystemProperties.get("ro.mac", "");
    }

    public String getAccount(){
	
	return  Settings.Secure.getString(MyApplication.getContext().getContentResolver(), "bluetooth_name");
    }

    public JSONObject FirMsgToJson() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("account","18453111433");
            obj.put("city_code","531");
            obj.put("bandwidth","50M");
            obj.put("terminal_type","1");
            obj.put("terminal_no",getIp());
            obj.put("user_ip",getMac());
	    Log.i("lgs", "1111111" + Settings.Secure.getString(MyApplication.getContext().getContentResolver(), "bluetooth_name"));    
        }catch (JSONException e) {
            e.printStackTrace();
        }
	return obj;
    }
    
    public JSONObject SecMsgToJson() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("speedtest_id",NetWorkSpeedInfo.speedtest_id);
            obj.put("account","18453111433");
            obj.put("speed_max",NetWorkSpeedInfo.speed_max);
            obj.put("speed_avg",NetWorkSpeedInfo.speed_avg);
        }catch (JSONException e) {
            e.printStackTrace();
        }
	return obj;
    }

}
