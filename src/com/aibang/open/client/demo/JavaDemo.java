package com.aibang.open.client.demo;

import java.net.ServerSocket;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.aibang.open.client.AibangApi;
import com.aibang.open.client.exception.AibangException;

/**
 * 一个简单的使用aibang java版本开放sdk的demo.
 * 
 */
public class JavaDemo {
	/*
	 * private String jsonString; private JSONObject jsonObject; private int
	 * place_num;
	 */
	private List<place> places;

	public static void main(String[] args) throws AibangException {
		JavaDemo demo = new JavaDemo();
		demo.printUsage();
		/*
		 * if (args.length == 0) { demo.printUsage(); return; } String[]
		 * real_args = new String[args.length - 1]; for (int i = 1; i <
		 * args.length; i++) { real_args[i - 1] = args[i]; } demo.run(args[0],
		 * real_args);
		 */
	}

	/**
	 * 构造Api对象.
	 */
	public JavaDemo() {
		aibang = new AibangApi(API_KEY);
	}

	/**
	 * 测试部分
	 */

	private void printUsage() {
		try {
			places = place.jsonStringToList("西安", "东大街");
			System.out.println(places.get(0).toString());
			server_on();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AibangException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * System.out.println("Usage:"); System.out.println("search: ");
		 * System.out.println("    ./demo search <city> <addr>");
		 * System.out.println("biz: ");
		 * System.out.println("    ./demo biz <bid>");
		 * System.out.println("bus: ");
		 * System.out.println("    ./demo bus <city> <start> <end>");
		 * System.out.println("post_comment: ");
		 * System.out.println("    ./demo post_comment <bid> <score> <content>"
		 * );
		 */
		/*
		 * try { jsonString = search("西安", "东大街"); JSONObject jsonObject_temp =
		 * new JSONObject(jsonString); place_num =
		 * jsonObject_temp.getInt("total"); do {
		 * 
		 * } while (jsonObject_temp.getInt("result_num")>0);
		 * showResult(jsonString); try { jsonObject = new
		 * JSONObject(jsonString); place_num = jsonObject.getInt("total"); //
		 * System.out.println(jsonObject);
		 * System.out.println(jsonObject.getString("bizs")); JSONObject
		 * jsonObject2 = new JSONObject(jsonObject.getString("bizs"));
		 * //System.out.println(jsonObject2.get) JSONArray array
		 * =jsonObject2.getJSONArray("biz"); System.out.println(array);
		 * System.out.println(place.JsonToPlaceList(array).get(0).toString()); }
		 * catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * } catch (AibangException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (JSONException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
	}

	// 得到地点jsonstring 下一步即将此发送到客户端，可以直接转化为jsonarray
	// 这里暂时仅作为测试所用，发送jsonstring对于客户端解析起来比自己序列话要简单一些
	public String GetJsonBizsString() throws JSONException, AibangException {
		String jsonString = search("西安", "西大街");
		JSONObject jsonObject = new JSONObject(jsonString);
		String jsonString_bizs = jsonObject.getString("bizs");
		return jsonString_bizs;
	}

	private String search(String city, String addr) throws AibangException {
		return aibang.search(city, addr, null, null, null, null, null, null,
				null, null);
	}

	public String search(String city, String addr, int from)
			throws AibangException {
		return aibang.search(city, addr, null, null, null, null, null, null,
				from, null);
	}

	// 查询商户详情页，传入参与为商铺id（bid），返回json string
	private String biz(String bid) throws AibangException {
		return aibang.biz(bid);
	}

	// 查询公交
	private String bus(String city, String start, String end)
			throws AibangException {
		return aibang.bus(city, start, end, null, null, null, null, null, null,
				null);
	}

	// 提交评论
	private String postComment(String bid, String score, String content)
			throws AibangException {
		Integer int_score = 5;
		try {
			int_score = Integer.parseInt(score, 5);
		} catch (Exception e) {
		}
		return aibang.postComment("username", bid, int_score, null, content);
	}

	/**
	 * 展示服务器返回的结果.
	 * 
	 * @param result
	 */
	private void showResult(String result) {
		System.out.println(result);
	}

	public void server_on() {
		try
		{
			
		int clientcount = 0; // 统计客户端总数

		boolean listening = true; // 是否对客户端进行监听

		ServerSocket server = null; // 服务器端Socket对象
		try {
			// 创建一个ServerSocket在端口4700监听客户请求
			server = new ServerSocket(4700);
			System.out.println("Server starts...");
		} catch (Exception e) {
			System.out.println("Can not listen to. " + e);
		}
		  while (listening) {
		      // 客户端计数
		      clientcount++;
		      
		      // 监听到客户请求,根据得到的Socket对象和客户计数创建服务线程,并启动之
		      new ServerThread(server.accept(), clientcount).start();
		     }
		    } catch (Exception e) {
		     System.out.println("Error. " + e);
		    }
	}

	

	private AibangApi aibang;
	// 这里请使用自己申请的API KEY
	private static final String API_KEY = "212d00fbd2799ead7c6be51b067598f2";
}
