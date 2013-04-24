package com.aibang.open.client.demo;

import com.aibang.open.client.AibangApi;
import com.aibang.open.client.exception.AibangException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 一个简单的使用aibang java版本开放sdk的demo.
 * 
 * <pre>
 * 使用方法: 
 * 
 * 搜索某个地点的商户:
 *  ./demo search <city> <addr> 
 * 查询商户详情页:
 *  ./demo biz <bid>
 * 查询公交:
 *  ./demo bus <city> <start> <end> 
 * 发表点评：  
 *  ./demo post_comment <bid> <score> <content>
 * </pre>
 */
public class JavaDemo {
	private String jsonString;
	private JSONObject jsonObject;

	public static void main(String[] args) throws AibangException {
		JavaDemo demo = new JavaDemo();
		if (args.length == 0) {
			demo.printUsage();
			return;
		}
		String[] real_args = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			real_args[i - 1] = args[i];
		}
		demo.run(args[0], real_args);
	}

	/**
	 * 构造Api对象.
	 */
	public JavaDemo() {
		aibang = new AibangApi(API_KEY);
	}

	/**
	 * 解析命令并调用对应模块执行.
	 * 
	 * @param command
	 * @param args
	 * @throws AibangException
	 */
	public void run(String command, String[] args) throws AibangException {
		if ("search".equals(command) && args.length == 2) {
			showResult(search(args[0], args[1]));
			return;
		} else if ("biz".equals(command) && args.length == 1) {
			showResult(biz(args[0]));
			return;
		} else if ("bus".equals(command) && args.length == 3) {
			showResult(bus(args[0], args[1], args[2]));
		} else if ("post_comment".equals(command) && args.length == 3) {
			showResult(postComment(args[0], args[1], args[2]));
		}
		System.out.println("Unknown command or parameters: " + command);
		printUsage();
	}

	/**
	 * 显示使用说明.
	 */
	private void printUsage() {
		System.out.println("Usage:");
		System.out.println("search: ");
		System.out.println("    ./demo search <city> <addr>");
		System.out.println("biz: ");
		System.out.println("    ./demo biz <bid>");
		System.out.println("bus: ");
		System.out.println("    ./demo bus <city> <start> <end>");
		System.out.println("post_comment: ");
		System.out.println("    ./demo post_comment <bid> <score> <content>");
		try {
			jsonString = search("西安", "东大街");
			showResult(jsonString);
			try {
				jsonObject = new JSONObject(jsonString);
			//	System.out.println(jsonObject);
				System.out.println(jsonObject.getString("bizs"));
		    	JSONArray array = jsonObject.getJSONArray("bizs");
	            System.out.println(array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (AibangException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String search(String city, String addr) throws AibangException {
		return aibang.search(city, addr, null, null, null, null, null, null,
				null, null);
	}

	private String biz(String bid) throws AibangException {
		return aibang.biz(bid);
	}

	private String bus(String city, String start, String end)
			throws AibangException {
		return aibang.bus(city, start, end, null, null, null, null, null, null,
				null);
	}

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

	private AibangApi aibang;
	// 这里请使用您自己申请的API KEY
	private static final String API_KEY = "f41c8afccc586de03a99c86097e98ccb";
}
