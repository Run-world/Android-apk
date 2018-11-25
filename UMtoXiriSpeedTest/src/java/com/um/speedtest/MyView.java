/**
 *
 */
package com.um.speedtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  @163.com
 * @version  1.0
 */
public class MyView extends View
{
	
	public int XPoint=300;   //原点的X坐标
    public int YPoint=350;      //原点的Y坐标
    public int XScale=55;     //X的刻度长度
    public int YScale=40;     //Y的刻度长度
    public int XLength=500;        //X轴的长度
    public int YLength=300;          //Y轴的长度
    
	private final static String X_KEY = "Xpos";
	private final static String Y_KEY = "Ypos";
	
	private List<Map<String, Integer>> mListPointX = new ArrayList<Map<String,Integer>>();
	private List<Map<String, Float>> mListPointY = new ArrayList<Map<String,Float>>();
    
	
	public MyView(Context context, AttributeSet set)
	{
		super(context, set);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
//		//重写onDraw方法
//		canvas.drawColor(Color.WHITE);
//		Paint paint = new Paint();
//		// ȥ���
//		paint.setAntiAlias(true);
//		paint.setColor(Color.BLUE);
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeWidth(3);
		
//		canvas.drawColor(Color.BLUE);
        Paint paint= new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);//颜色
//        Paint paint1=new Paint();
//        paint1.setStyle(Paint.Style.STROKE);
//        paint1.setAntiAlias(true);//ȥ���
//        paint1.setColor(Color.DKGRAY);
        paint.setTextSize(18);  //设置轴文字大小
        //设置Y轴
        canvas.drawLine(XPoint, YPoint-YLength, XPoint, YPoint, paint);   //轴线
      //设置X轴
        canvas.drawLine(XPoint,YPoint,XPoint+XLength,YPoint,paint);          

        canvas.drawLine(XPoint,YPoint - 30,XPoint+XLength,YPoint - 30,paint);  

        canvas.drawLine(XPoint,YPoint - 60,XPoint+XLength,YPoint - 60,paint);   
    
        canvas.drawLine(XPoint,YPoint - 90,XPoint+XLength,YPoint - 90,paint);  
    
        canvas.drawLine(XPoint,YPoint - 120,XPoint+XLength,YPoint - 120,paint);  
        
        canvas.drawLine(XPoint,YPoint - 180,XPoint+XLength,YPoint - 180,paint); 
        
        canvas.drawLine(XPoint,YPoint - 240,XPoint+XLength,YPoint - 240,paint);   
        
        canvas.drawText("0", XPoint - 20, YPoint, paint);  //文字   0  
        canvas.drawText("2M带宽", XPoint- 70 , YPoint -30, paint);  //文字  2M
        canvas.drawText("4M带宽", XPoint- 70 , YPoint -60, paint);  ///文字  4M
        canvas.drawText("8M带宽", XPoint- 70 , YPoint -90, paint);  //文字  8M
        canvas.drawText("20M带宽", XPoint- 80 , YPoint -120, paint);  //文字  20M
        canvas.drawText("50M带宽", XPoint- 80 , YPoint -180, paint);  //文字 50M
        canvas.drawText("100M带宽", XPoint- 88 , YPoint -240, paint);  //文字  100M
        
		
        
        
        
        
        
        
        
        for (int index=0; index<mListPointX.size(); index++)
		{
			if (index > 0)
			{
				canvas.drawCircle(mListPointX.get(index-1).get(X_KEY),mListPointY.get(index-1).get(Y_KEY), 2, paint);
				canvas.drawLine(mListPointX.get(index-1).get(X_KEY), mListPointY.get(index-1).get(Y_KEY),
						mListPointX.get(index).get(X_KEY), mListPointY.get(index).get(Y_KEY), paint);
				canvas.drawCircle(mListPointX.get(index).get(X_KEY),mListPointY.get(index).get(Y_KEY), 2, paint);
				canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));					
			}
		}
        
        
	}
	
	public void setLinePoint(int curX, float curY)
	{
		Map<String, Integer> tempX = new HashMap<String, Integer>();
		Map<String, Float> tempY = new HashMap<String, Float>();
		tempX.put(X_KEY, curX);
		tempY.put(Y_KEY, curY);
		mListPointX.add(tempX);
		mListPointY.add(tempY);
		invalidate();		
	}
	
	
	public void clear(){
		Log.d("lwn","mListPointX:" + mListPointX);
		Log.d("lwn","mListPointY:" + mListPointY);
		if(!mListPointX.toString().equals("[]")){
			Log.d("lwn","no null,so clear");
			mListPointX.clear();
			mListPointY.clear();
		}
	}
}
