package com.socogame.uiupdate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class UIUpdateActivity extends Activity {
	public static final int RECV_DATA = 1;

	Button btn = null;
	ProcessThread pThread = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pThread = new ProcessThread(msgHandler);
				new Thread(pThread).start();
			}
		});

	}

	private final Handler msgHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case RECV_DATA:
				if (pThread != null) {
					byte[] data = pThread.getOneBuffer();
					UpdateUI(data);
				}
				break;

			}
			super.handleMessage(msg);
		}

		private void UpdateUI(byte[] data) {
			// TODO 更新UI线程的具体实现
			btn.setText(data.toString());
			btn.setClickable(false);
		}
	};
}