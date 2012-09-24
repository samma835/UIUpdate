package com.socogame.uiupdate;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

public class ProcessThread extends Thread {

	private static final int BUFF_SIZE = 1024;
	private Handler handler;
	private ArrayList<byte[]> bytesList = null;
	boolean running = true;

	public ProcessThread(Handler handler) {
		this.handler = handler;
		bytesList = new ArrayList<byte[]>();
	}

	public void run() {
		while (running) {
			byte[] buff = new byte[BUFF_SIZE];
			Recv(buff, BUFF_SIZE);
			Message msg = handler
					.obtainMessage(UIUpdateActivity.RECV_DATA);
			msg.sendToTarget();
		}
	}

	private void Recv(byte[] buff, int buffSize) {
		// TODO 数据接收的具体处理方法
		AddOneBuffer("test".getBytes());
		try {
			Thread.sleep(330);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void AddOneBuffer(byte[] data) {
		// 线程同步
		synchronized (bytesList) {
			bytesList.add(data);
		}
	}

	public byte[] getOneBuffer() {
		// 线程同步
		byte[] rdata = null;
		synchronized (bytesList) {
			if (bytesList.size() > 0) {
				rdata = bytesList.remove(0);
			}
		}
		return rdata;
	}
}
