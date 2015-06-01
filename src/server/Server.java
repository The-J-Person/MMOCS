package server;

import common.*;

import java.io.*;
import java.net.*;
import java.util.Random;
//import java.util.Scanner;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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
	// private Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		get_sender_email();

		try {

			int i = 0; // counter for connected clients.
			String Salt, Auth_Code, Password;
			String[] Info;

			// Connect socket to localhost , port 8080
			ServerSocket m_ServerSocket = new ServerSocket(8080);
			// ServerSocket servers = new ServerSocket(8080, 0,
			// InetAddress.getByName("localhost"));

			System.out.println("Server is started");
			// // listing for port.
			// // waiting for new connection , after it running the client in
			// // new socket connection
			// // we increase i by 1

			while (true) {
				Socket s = m_ServerSocket.accept();
				ObjectInputStream lois = new ObjectInputStream(
						s.getInputStream());

				ObjectOutputStream loos = new ObjectOutputStream(
						s.getOutputStream());

				Request to = null;

				try {
					to = (Request) lois.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println("broke");
					e.printStackTrace();
				}

				Info = (String[]) to.getData(); // insert all received data from
												// "lois" to Info

				switch (to.getType()) {
				case LOG_IN:

					Salt = DataBase.GetSalt(Info[1]);
					Password = Cryptography.encrypt(Info[1], Salt);
					if (server.Access.login(Info[0], Password)) {
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.LOG_IN)));
						new Server(i, s);
						i++;
					}
					break;
				case REGISTER:

					Salt = randomString();
					Auth_Code = randomString();
					Password = Cryptography.encrypt(Info[1], Salt);

					if (server.Access.newUser(Info[0], Password, Salt, Info[2],
							Auth_Code) == 0) {
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.REGISTER)));
						SendMail(Info[0], Info[2], Auth_Code);
						s.close();
					} else {
						loos.writeObject(new Update(
								UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(false, RequestType.REGISTER)));
						s.close();
					}
					break;

				case CONFIRM:
					if (server.Access.confirm(Info[0], Info[1])) {
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.CONFIRM)));
						s.close(); // need return message !!!
					} else {
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(false, RequestType.CONFIRM)));
						s.close(); // need return message !!!
					}
					break;

				default:
					s.close();
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Init error: " + e);
		} // printing errors

	}

	public Server(int num, Socket s) {
		// copy parameters
		this.num = num;
		this.s = s;
		// starting new thread
		String name = "" + Access.id;
		setDaemon(true);
		setName(name);
		setPriority(NORM_PRIORITY);
		start();
	}

	public void run() {
		try {

			Coordinate co;
			Player pl = server.DataBase.GetPlayerByID(Integer.parseInt(this
					.getName()));

			ObjectOutputStream oos1 = new ObjectOutputStream(
					s.getOutputStream());// getting data from server
											// to client

			oos1.writeObject(pl.Coordinates());
			oos1.writeObject(pl.Health());

			while (s.isConnected()) {

				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());// getting data from client
											// socket

				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());// getting data from server
												// to client
				int check = s.getInputStream().available();

				if (check > 0) {

					Request re = null;
					Update up = null;

					re = (Request) ois.readObject();

					// Update up = (Update)oos.writeObject(obj);
					try {
						switch (re.getType()) {
						case ATTACK:
							co = (Coordinate) re.getData();
							if (pl.object_in_tile(co)) {

							} else {

							}

							// TODO here must be an function
							break;
						case CRAFT:

							// TODO here must be an function
							break;
						case HARVEST:
							co = (Coordinate) re.getData();
							if (pl.object_in_tile(co)) {
								if (pl.attack(co)) {
									oos.writeObject(new Update(
											UpdateType.ACKNOWLEDGMENT,
											new Acknowledgement(true,
													RequestType.HARVEST)));
									// 2.......must return update

								} else {
									oos.writeObject(new Update(
											UpdateType.ACKNOWLEDGMENT,
											new Acknowledgement(false,
													RequestType.HARVEST)));
								}

							} else {
								if (pl.gather_ground(co)) {
									oos.writeObject(new Update(
											UpdateType.ACKNOWLEDGMENT,
											new Acknowledgement(true,
													RequestType.HARVEST)));
									// must return update

								} else {
									oos.writeObject(new Update(
											UpdateType.ACKNOWLEDGMENT,
											new Acknowledgement(false,
													RequestType.HARVEST)));
								}
							}
							break;

						case LOG_OUT:
							oos.writeObject(new Update(
									UpdateType.ACKNOWLEDGMENT,
									new Acknowledgement(true,
											RequestType.LOG_OUT)));
							s.close(); // need return message !!!
							if (s.isConnected()) {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.LOG_OUT)));
							}
							break;

						case MOVE:
							co = (Coordinate) re.getData();
							if (pl.Move(co)) {
								up = new Update(UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(true,
												RequestType.MOVE));
								oos.writeObject(up);
							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.MOVE)));
							}
							break;

						case TILE:
							co = (Coordinate) re.getData();
							if (pl.see_Tile(co)) {

								Tile toClient = WorldMap.getInstance()
										.get_tile_at(co, true);
								up = new Update(UpdateType.TILE, toClient);
								oos.writeObject(up);
							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.TILE)));
							}
							break;

						case UPDATE_TILE:
							co = (Coordinate) re.getData();
							Tile toChange = WorldMap.getInstance().get_tile_at(
									co, true);
							if (pl.change_Tile(toChange)) {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(true,
												RequestType.UPDATE_TILE)));
							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.UPDATE_TILE)));
							}
							break;

						default:
							break;
						}

					} catch (Exception e) {
						System.out.print(e.getMessage());
					}

				}// end if
					// if user Deactivation , we will check events
				else {
					Update newUP = pl.getEvents();
					if (pl.getEvents() == null)
						continue;
					else {
						oos.writeObject(newUP);
					}

				}
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
	public static void SendMail(String username, String email, String Auth_Code) {
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
	}

	public static String randomString() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
			sb.append(c);
		}
		return sb.toString();
	}
}
