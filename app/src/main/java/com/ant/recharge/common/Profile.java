package com.ant.recharge.common;

/**
 * 配置文件
 */
public class Profile {

	//调试模式
	public final static boolean DEBUGABLE = true;

	public final static String WX_APP_ID = "wx933a95a5eb1fbfe6";


//	String C1E0F09B22CE7FFC1AFD7EA0975F5DA5 = "C1E0F09B22CE7FFC1AFD7EA0975F5DA5";
	//服务器地址  测试
//	public final static String SERVER_ADDRESS = "http://192.168.172.189:8080/shunhecore/";
	//正式
//public final static String SERVER_ADDRESS = "http://115.28.85.97/";
	public final static String SERVER_ADDRESS = "http://www.ypbill.com/";
//	public final static String SERVER_ADDRESS_DEV = "http://192.168.1.43:8081/mayisale";
	public final static String SERVER_ADDRESS_DEV = "http://47.92.122.37:8088/mayisale";

//	public final static String SERVER_ADDRESS = "http://115.28.85.97/test_1102";
//	public final static String SERVER_ADDRESS = "http://115.28.85.97/mc/login";
	//	public final static String SERVER_ADDRESS = "http://115.28.85.97/index/member/home";

//	public final static String SP_HTTP = "HTTP";

//	public final static String SERVER_ADDRESS_mayi = "http://mayi.shunher.com/";

	/**
	 * timeout for request
	 *
	 */
	public final static int TIMEOUT = 15;
	public final static boolean VERIFYCERT = false;// 检查证书
	public final static boolean VERIFYHOSTSSL = false;// 检查host
	public final static String CERTFILE = "server.cert";// 证书cert
	public final static String CERTSECRET = "ASDFASDF";// 证书密钥
}
