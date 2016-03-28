					����һ����ʵ���̳߳��첽�ص�</br>
</br>
1.���������ϵĻص�</br>
������˼�����ص�,����A����B,B�ع�ͷ���ٵ���A,���ǻص�.��Ȼ������,��Ȼ��Ҫ��A����B,B����A.����:</br>
</br>
class A {
	public void ask(final B b, final String question) {
		b.answer(this, question);
	}
</br>
	public void processResult(final String answer) {
		System.out.println(answer);
	}
}

class B {
	public void answer(final A a, final String question) {
		if (question.equals("What is the answer to life, the universe and everything?")) {
			a.processResult("42");
		}
	}
}

public class SyncObjectCallback {
	public static void main(final String[] args) {
		B b = new B();
		A a = new A();
</br>
		a.ask(b, "What is the answer to life, the universe and everything?");
	}
}
</br>
2.�������Ļص�</br>
�����д����,B�Ķ���ֻ�ڷ����б�������.ʵ����,���B��������ֵ�����A�еķ���,��������Ӧ�ò�ֹ������һ��������,��Ӧ����A��һ������.Ҳ����,�����д������"�������",������������һ��:</br>
</br>
class A {
	private final B b;

	public A(final B b) {
		this.b = b;
	}
</br>
	public void ask(final String question) {
		this.b.answer(this, question);
	}
</br>
	public void processResult(final String answer) {
		System.out.println(answer);
	}
}
</br>
class B {
	public void answer(final A a, final String question) {
		if (question.equals("What is the answer to life, the universe and everything?")) {
			a.processResult("42");
		}
	}
}
</br>
public class SyncOOCallback {
	public static void main(final String[] args) {
		B b = new B();
		A a = new A(b);
		a.ask("What is the answer to life, the universe and everything?");
	}
}
</br>
3.����ӿڵĻص�</br>
�������������,����û�˻����Ҳ�ǻص���.��Ϊ��ûʲô����.����������̶������ص��Ǻ���Ҫ��.��ʵ�ص��������õĵط�,��������"Ԥ��"����.</br>
������չ����һ��.�������������е�B,ΪA�ṩ�˺ܶ����֮��ͻȻ����,��Ϊ����Ķ����ṩ����,����һ��,B�ͱ����Server.���һ�Ҫ�ƶ�����.������ʲô��,����ҪServer�ṩ�������,�Է�һ��Ҫ��һ��recvAnswer�ӿڹ�Server���ò���,����Server���ܰѽ�����ظ�Client.��������ƶ�������?ͨ��Interface.����:</br>
</br>
public interface IClient {
	void recvAnswer(String answer);
}
</br>
public class Server {
	public void answer(final IClient client, final String question) {
		if (question.equals("What is the answer to life, the universe and everything?")) {
			calclating();
			client.recvAnswer("42");
		}
	}
</br>
	private void calclating() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
</br>
public class ClientSync implements IClient {
	private final Server server;
</br>
	public ClientSync(final Server server) {
		this.server = server;
	}
</br>
	public void ask(final String question) {
		this.server.answer(this, question);
	}
</br>
	@Override
	public void recvAnswer(final String answer) {
		System.out.println(answer);
	}
}
</br>
public class SyncInterfaceCallback {
	public static void main(final String[] args) {
		Server server = new Server();
		ClientSync client = new ClientSync(server);
		client.ask("What is the answer to life, the universe and everything?");
	}
}
</br>
ע��,�ӿ�IClientʵ����Ӧ��������Server�˵�,������Server�ƶ���,��ҪClient��ʵ�ֵĽӿ�,��Ȼ����ȥ����Client�ܽ�.</br>
Ϊʲô˵��"Ԥ��"������?������һ������.Server������һ���ײ����,����ײ����֪��������һ����и߲��������Ҫ����,��������������ϴ�����?�ײ���Գ�ŵ,ֻ����ʵ��IClient�ӿ�,�Ҿͻ�������е�recvAnswer����,�����ݴ�����.���ڵײ�Ҳ���Ե��ø߲�ķ���,������"Ԥ��"������?</br>
</br>
4.�첽�ص�</br>
����ĵ��ö���ͬ����.����Server��������Ҫ�ϳ���ʱ��,��һ��ϣ��������һ���������߳��б�ִ��,���ǾͿ��԰�ask�����ĵ������̰߳�װһ��:</br>
</br>
public class ClientAsync implements IClient {
	private final Server server;
</br>
	public ClientAsync(final Server server) {
		this.server = server;
	}
</br>
	public void ask(final String question) {
		new Thread(new Runnable() {
</br>
			@Override
			public void run() {
				server.answer(ClientAsync.this, question);
			}
		}).start();
	}
</br>
	@Override
	public void recvAnswer(final String answer) {
		System.out.println(answer);
	}
}
</br>
public class AsyncInterfaceCallback {
	public static void main(final String[] args) {
		Server server = new Server();
		ClientAsync client = new ClientAsync(server);
		client.ask("What is the answer to life, the universe and everything?");
		System.out.println("asked ! waiting for the answer...");
	}
}
</br>
5.�̳߳��첽�ص�</br>
ÿ�ν����µ��̺߳ķ���Դ�޴�,Ϊ�������߳�,ʹ���̳߳ع����첽����,��ʱ���Ҫ��Client����Ҫʵ��IClient�ӿ�,��Ҫͬʱ��һ������,���ܱ��̳߳�ִ��,����:</br>
</br>
public class ClientRunnable implements IClient, Runnable {
	private final Server server;
	private final String question;
	private final int id;
</br>
	public ClientRunnable(final Server server, final String question, final int id) {
		this.server = server;
		this.question = question;
		this.id = id;
	}
</br>
	@Override
	public void recvAnswer(final String answer) {
		System.out.println("clinet " + this.id + " got answer: " + answer);
	}
</br>
	@Override
	public void run() {
		server.answer(ClientRunnable.this, this.question);
	}
}
</br>
public class ThreadpoolCallback {
	public static void main(final String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
</br>
		Server server = new Server();
</br>
		for (int i = 0; i < 100; i++) {
			ClientRunnable cr = new ClientRunnable(server, "What is the answer to life, the universe and everything?",
					i);
			es.execute(cr);
			System.out.println("client " + i + " asked !");
		}
</br>
		es.shutdown();
	}
}
</br>
����,���Ǿ�ʵ�����̳߳��첽�ص�.