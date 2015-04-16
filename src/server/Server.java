/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Spawn listener...
		try {
			listener();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void listener() throws IOException
	{
	System.out.println("Hi!");
      ServerSocket listener = new ServerSocket(15001);
      try {
          while (true) {
              Socket socket = listener.accept();
              try {
            	  /*Create new thread for each incoming connection*/
//                  PrintWriter out =
//                      new PrintWriter(socket.getOutputStream(), true);
//                  out.println("Hello!");
              } finally {
                  socket.close();
              }
          }
      }
      finally {
    	  listener.close();
      }
	}

}
