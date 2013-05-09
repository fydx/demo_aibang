package com.aibang.open.client.demo;

import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	private static int number = 0; // 保存本进程的客户计数
	Socket socket = null; // 保存与本线程相关的Socket对象

	/*
	 * 构造函数
	 */
	public ServerThread(Socket socket, int clientnum) {

		this.socket = socket;
		number = clientnum;
		System.out.println("当前在线的用户数: " + number);
	}

	public void run() {
		try {
			//构造PrintWriter
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			//获取string
			String line = new JavaDemo().GetJsonBizsString();
			System.out.println(line);
			//把输出流加载到PrintWriter中
			os.println(line);
			//刷新
			os.flush();
			os.close();
			//关闭socket
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error in run");
		}

	}
}
