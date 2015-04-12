package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTester {

	public static void main(String[] args) throws IOException {
		System.out.println("Hi!");
        ServerSocket listener = new ServerSocket(15001);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hello Ofir!");
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
