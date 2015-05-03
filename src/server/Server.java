
package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 *
 *
 */
public class Server extends Thread {

	Socket s;
	int num;
	//private ServerSocket servers;
	//private BufferedReader input;
	//private PrintStream output;
	private Scanner scan = new Scanner(System.in);
		
	
	

	public static void main(String[] args) {

		try {
			int i = 0; // counter for connected clients.

			// Connect socket to localhost , port 8080
			ServerSocket servers = new ServerSocket(8080, 0,
					InetAddress.getByName("localhost"));

			System.out.println("Server is started");

			// listing for port.
			while (true) {
				// waiting for new connection , after it running the client in
				// new socket connection
				// we increase i by 1
				new Server(i, servers.accept());
				i++;
			}

		} catch (Exception e) 
		{System.out.println("Init error: " + e);} // printing errors
		
	}
	
	public Server(int num  , Socket s)
	{
		//copy parameters
		this.num = num;
		this.s = s;
		
		// starting net thread :) haleluya
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}
	
	public void run()
	{
		try
		{
			//s = servers.accept(); // maybe must be uncomment !!!
			InputStream is = s.getInputStream(); // getting data from client socket
            
            OutputStream os = s.getOutputStream();	// getting data from server to client 
            
            //output = new PrintStream(s.getOutputStream());
            
            
            
			while(s.isConnected())
			{
			    byte buf[] = new byte[64*1024]; // read 64kb from client. how much information received from client	            
			    int r = is.read(buf);	            
	            String data = new String(buf, 0, r); 	// create data mess received from client
	            data = ""+num+": "+"\n"+data;	//adding all to data
	            os.write(data.getBytes());	//print on screen
	            
	            
	            String reply = scan.nextLine();
	            os.write(reply.getBytes());
			}
			
				
		}catch(Exception e)
        {System.out.println("init error: "+e);}
		
	}
	

	/*	*//**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub // Spawn listener... try { listener(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * private static void listener() throws IOException {
	 * System.out.println("Hi!"); ServerSocket listener = new
	 * ServerSocket(15001, 1000); try { while (true) { Socket socket =
	 * listener.accept(); try { Create new thread for each incoming connection
	 * // PrintWriter out = // new PrintWriter(socket.getOutputStream(), true);
	 * // out.println("Hello!"); } finally { socket.close(); } } } finally {
	 * listener.close(); } }
	 */

}
