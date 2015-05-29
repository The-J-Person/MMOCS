package server;

import common.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 *
 */
public class Server extends Thread {

	Socket s;
	int num;
	private static String s_usr; // Sender user for email sending
	private static String s_pwd; // Sender password for email sending
	private static String s_host;// Sender host for email sending
	private static final String data_file = "email.txt";
	// private ServerSocket servers;
	// private BufferedReader input;
	// private PrintStream output;
	private Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		get_sender_email();
		try {
			int i = 0; // counter for connected clients.

			// Connect socket to localhost , port 8080
			ServerSocket m_ServerSocket = new ServerSocket(8080);
			//ServerSocket servers = new ServerSocket(8080, 0,
				//	InetAddress.getByName("localhost"));

			System.out.println("Server is started");

			while(true)
			{
				Socket s = m_ServerSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());

				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());

				Access to = null;
				try {
					to = (Access) ois.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println("broke");
					e.printStackTrace();
				}

				switch (to.getAction()) {
				case "Login":
					if (common.Access.login(to.getUser(), to.getPass())) {
						new Server(i, s);
						i++;
					}
					break;
				case "New":
					if(common.Access.newUser(to.getUser(), to.getPass(), to.getEmail()) == 0){
						s.close(); //need return message !!!
					}
					break;

				case "Confirm":
					//if(common.Access.confirm(to.getCode())){
					//	s.close();	//need return message !!!
					//}
					break;

				default:
					break;
				}
			}

//			// listing for port.
//			while (true) {
//				// waiting for new connection , after it running the client in
//				// new socket connection
//				// we increase i by 1
//
//			}

		} catch (Exception e) {
			System.out.println("Init error: " + e);
		} // printing errors

	}

	public Server(int num, Socket s) {
		// copy parameters
		this.num = num;
		this.s = s;

		// starting new thread
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}

	public void run() {
		try {
		
			while (s.isConnected()) {
				
				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());// getting data from client
											// socket

				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());// getting data from server
											// to client

				// byte buf[] = new byte[64*1024]; // read 64kb from client. how
				// much information received from client
				// int r = is.read(buf);
				// String data = new String(buf, 0, r); // create data mess
				// received from client
				// data = ""+num+": "+"\n"+data; //adding all to data
				// os.write(data.getBytes()); //print on screen
				//
				//
				// String reply = scan.nextLine();
				// os.write(reply.getBytes());
			}

		} catch (Exception e) {
			System.out.println("init error: " + e);
		}

	}

	public static void get_sender_email() {
		String line;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(data_file);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			line = bufferedReader.readLine();
			s_host = new String(line);

			line = bufferedReader.readLine();
			s_usr = new String(line);

			line = bufferedReader.readLine();
			s_pwd = new String(line);
			// System.out.println(line);

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + data_file + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + data_file + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}

	/**
	 * This function registers a new user and sends them a verification email
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param Salt
	 * @param Auth_Code
	 */
	public static void Register(String username, String email, String password,
			String Salt, String Auth_Code) {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", s_host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(s_usr, s_pwd);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreply@mocs.il"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Thank you for registering!");
			message.setText("Greeting "
					+ username
					+ ",\n\n Your authentication code is:"
					+ Auth_Code
					+ "Please copy and paste this into the client to complete your registration!");

			Transport.send(message);

			System.out.println("Sent mail to " + email + "...\n");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		// SQL.add_new_user(blahblah)//This function not implemented yet
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
