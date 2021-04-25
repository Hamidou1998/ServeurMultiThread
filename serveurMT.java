import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serveurMT extends Thread {

	private int nbClients;
	public static void main(String[] args) {
		new serveurMT().start();
	}
	public void run() {
		try {
			ServerSocket ss=new ServerSocket(1234);
			System.out.println("Demarrage du serveur .................");
			while (true)
			{
				Socket s=ss.accept();
				++nbClients;
				new conversation(s,nbClients).start();
			}
			
			} 
		catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}
	class conversation extends Thread{
		private Socket socket;
		private int numClient;
		
		public conversation(Socket socket,int numC) {
			super();
			this.numClient=numC;
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				InputStream is=socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(is);
				BufferedReader bf=new BufferedReader(isr);
				
				OutputStream os=socket.getOutputStream();
				PrintWriter pw=new PrintWriter(os,true);
				
				String ip=socket.getRemoteSocketAddress().toString();
				System.out.println("Connexion avec le client numero "+numClient+" Ip="+ip);
				pw.print("Bienvenue vous etes le numero"+numClient);
				
				while(true) {
					String req=bf.readLine();
					pw.print(req.length());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			super.run();
		}
	}
}
