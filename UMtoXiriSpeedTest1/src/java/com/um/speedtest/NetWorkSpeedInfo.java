package com.um.speedtest;

public class NetWorkSpeedInfo {
	/** Network speed */
	public float speed = 0;
	/** Had finished bytes */
	public long hadFinishedBytes = 0;
	/** Total bytes of a file, default is 1024 bytes,1K */
	public long totalBytes = 1024;

	/** The net work type, 3G or GSM and so on */
	public int networkType = 0;

	/** Down load the file percent 0----100 */
	public int downloadPercent = 0;
	public static String speedtest_id;
        public static String  speed_max;
        public static String  speed_avg;

	public boolean flag = true;
}
