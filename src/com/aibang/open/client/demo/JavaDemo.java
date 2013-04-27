package com.aibang.open.client.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
		 * //System.out.println(jsonObject2.get) JSONArray array =
		 * jsonObject2.getJSONArray("biz"); System.out.println(array);
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
	private String GetJsonBizsString() throws JSONException, AibangException {
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

	private void server_on() {
		String line=null;
		System.out.println("**********************************");
		System.out.println("**********************************");
		try {
			ServerSocket server = null;
			try {
				server = new ServerSocket(4700);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error" + e);
			}
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error2");
			}
			
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader sin = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("Client:" + is.readLine());
		    line = new String(GetJsonBizsString());
		    System.out.println(line); 
		
				os.println(line);
				os.flush();
				System.out.println("Server" + line);
				System.out.println("Client:" + is.readLine());
				line = sin.readLine();
		
			os.close();
			is.close();
			socket.close();
			server.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error3");
		}
		
	}

	private String loadConvert(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	private AibangApi aibang;
	// 这里请使用您自己申请的API KEY
	private static final String API_KEY = "212d00fbd2799ead7c6be51b067598f2";
}
