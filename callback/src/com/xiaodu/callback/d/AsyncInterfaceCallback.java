package com.xiaodu.callback.d;

/**
 * ���ڽӿڵ��첽�ص�,ÿ�ν����µ��߳�
 * @author xiaodu.email@gmail.com
 *
 */
public class AsyncInterfaceCallback {
	/**
	 * ʹ���ڲ����ʵ�ַ�ʽ,�˴��ɼ��ص�����
	 * @author xiaodu.email@gmail.com
	 */
	private static void innerMain() {
		Server server = new Server();

		new Thread(new Runnable() {
			@Override
			public void run() {
				server.answer(new IClient() {
					@Override
					public void recvAnswer(final String answer) {
						System.out.println(answer);
					}
				}, "What is the answer to life, the universe and everything?");
			}
		}).start();
		System.out.println("asked ! waiting for the answer...");
	}

	public static void main(final String[] args) {
		Server server = new Server();
		ClientAsync client = new ClientAsync(server);
		client.ask("What is the answer to life, the universe and everything?");
		System.out.println("asked ! waiting for the answer...");

		innerMain();
	}
}
