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
import javax.mail.internet.NewsAddress;

/**
 * 
 * 
 *
 */

public class Server extends Thread {

	Socket s;
	int num;
	ObjectInputStream lois;
	ObjectOutputStream loos;
	private static String s_usr; // Sender user for email sending
	private static String s_pwd; // Sender password for email sending
	private static String s_host;// Sender host for email sending
	private static final String data_file = "email.txt";

	public static void main(String[] args) {
		get_sender_email();

		try {

			int i = 0; // counter for connected clients.
			String Salt, Auth_Code, Password;
			String[] Info;

			// Connect socket to localhost , port 15001
			ServerSocket m_ServerSocket = new ServerSocket(15001);

			System.out.println("Server is started");
			/*
			 * listing for port. waiting for new connection , after it running
			 * the client in new socket connection we increase i by 1
			 */

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
					System.out
							.println("Log in attempt acquired. Confirming...\n");
					Salt = DataBase.GetSalt(Info[0]);
					Password = Cryptography.encrypt(Info[1], Salt);
					if (server.Access.login(Info[0], Password)) {
						System.out.println("Login successful for user "
								+ Info[0] + "...\n");
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.LOG_IN)));
						System.out.println("Sent ACK to : " + Info[0]);
						new Server(i, s, loos, lois);
						i++;
					} else
						System.out
								.println("Login error for " + Info[0] + "!\n");
					break;
				case REGISTER:
					System.out
							.println("Registration attempt acquired. Validating...\n");
					// Salt = randomString();
					Salt = AuthCode.generatKey();
					Auth_Code = AuthCode.generatKey();
					Password = Cryptography.encrypt(Info[1], Salt);

					if (server.Access.newUser(Info[0], Password, Salt, Info[2],
							Auth_Code) == 0) {
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.REGISTER)));
						System.out.println("Registration succeeded.\n");
						SendMail(Info[0], Info[2], Auth_Code);
						s.close();
					} else {
						System.out.println("Registration failed.\n");
						loos.writeObject(new Update(
								UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(false, RequestType.REGISTER)));
						s.close();
					}
					break;

				case CONFIRM:
					System.out
							.println("Confirmation attempt acquired. Checking...\n");
					if (server.Access.confirm(Info[0], Info[1])) {
						System.out.println("Validated " + Info[0] + "...\n");
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(true, RequestType.CONFIRM)));
						s.close(); // need return message !!!
					} else {
						System.out.println("Failed to validate " + Info[0]
								+ "...\n");
						loos.writeObject(new Update(UpdateType.ACKNOWLEDGMENT,
								new Acknowledgement(false, RequestType.CONFIRM)));
						s.close(); // need return message !!!
					}
					break;

				default:
					System.out
							.println("Received invalid request, ignoring...\n");
					s.close();
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Init error: " + e);
		} // printing errors

	}

	/**
	 * Starting a new Thread , given him a ID name of client.
	 * 
	 * @param num
	 * @param s
	 */
	public Server(int num, Socket s, ObjectOutputStream loos1, ObjectInputStream lois1) {
		// copy parameters
		this.num = num;
		this.s = s;
		this.lois = lois1;
		this.loos = loos1;
		System.out.println("Starting a new player thread for player ID "
				+ Access.id);
		// starting new thread
		String name = "" + Access.id;
		setDaemon(true);
		setName(name);
		setPriority(NORM_PRIORITY);
		start();
	}

	/**
	 * kind main function of the game All events happened here , receive and
	 * switch by request type of event. Ignore unknown requests.
	 */
	public void run() {
		try {

			Coordinate co;
			Player pl = server.DataBase.GetPlayerByID(Integer.parseInt(this
					.getName()));

			ObjectOutputStream oos = loos;// getting data from server
											// to client
			ObjectInputStream ois = lois;

			oos.writeObject(new Update(UpdateType.COORDINATE, pl.Coordinates()));
			System.out.println("Sent coordinates to " + this.getName() + "\n");
			oos.writeObject(new Update(UpdateType.INVENTORY, pl.Inventory));
			System.out.println("Sent inventory to " + this.getName() + "\n");
			oos.writeObject(new Update(UpdateType.HIT_POINTS, pl.Health));
			System.out.println("Sent Health to " + this.getName() + "\n");

			while (s.isConnected()) {

				int check = s.getInputStream().available();

				if (check > 0) {

					Request re = null;
					Update up = null;
					Resource resource = null;
					re = (Request) ois.readObject();
					//resource = (Resource) re.getData(); //Why is this here, Ed?
					System.out.println("Got request from " + this.getName() + " : " + re.getType() + "\n");

					// Update up = (Update)oos.writeObject(obj);
					try {
						switch (re.getType()) {
						case ATTACK:
							co = (Coordinate) re.getData();
							if (pl.attack(co)) {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(true,
												RequestType.ATTACK)));
								up = pl.getEvents();
								oos.writeObject(up);

							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.ATTACK)));

							}

							break;
						case CRAFT:
							if (pl.craft(resource)) {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(true,
												RequestType.CRAFT)));
								up = pl.getEvents();
								oos.writeObject(up);

							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.CRAFT)));
							}

							break;
						case HARVEST:
							co = (Coordinate) re.getData();
							if (pl.object_in_tile(co)) {
								if (pl.attack(co)) {
									oos.writeObject(new Update(
											UpdateType.ACKNOWLEDGMENT,
											new Acknowledgement(true,
													RequestType.HARVEST)));
									up = pl.getEvents();
									oos.writeObject(up);

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
									up = pl.getEvents();
									oos.writeObject(up);

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
							System.out.println("Request of " + this.getName() + " is Move request.\n");
							co = (Coordinate) re.getData();
							if (pl.Move(co)) {
								up = new Update(UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(true,
												RequestType.MOVE));
								oos.writeObject(up);
								System.out.println("Sent move ack to " + this.getName() + "\n");
							} else {
								oos.writeObject(new Update(
										UpdateType.ACKNOWLEDGMENT,
										new Acknowledgement(false,
												RequestType.MOVE)));
							}
							break;

						case TILE:
							System.out.println("Request of " + this.getName() + " is Tile.\n");
							co = (Coordinate) re.getData();
							if (pl.see_Tile(co)) {

								Tile toClient = WorldMap.getInstance()
										.get_tile_at(co, true);
								up = new Update(UpdateType.TILE, toClient);
								oos.writeObject(up);
								System.out.println("Sent Tile to " + this.getName() + "\n");
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
							System.out.println("Request by " + this.getName() + " not understood.\n");
							break;
						}

					} catch (Exception e) {
						System.out.print(e.getMessage());
					}

				}// end if
					// if user Deactivation , we will check events
//				else {
//					Update newUP = pl.getEvents();
//					if (pl.getEvents() == null)
//						continue;
//					else {
//						oos.writeObject(newUP);
//					}
				Update newUP = pl.getEvents();
				while (newUP != null){
					oos.writeObject(newUP);
					System.out.print("Sent " +newUP.getType()+ "to Player " + this.getName() + "\n");
					if(newUP.getData() instanceof Tile) {
						Tile tile = (Tile)newUP.getData();
						System.out.println(tile.getCoordinate().X()+","+tile.getCoordinate().Y() + ":" +tile.getFloorType() + "," + tile.getMapObjectType());
					}
					newUP = pl.getEvents();
				}
			}

		} catch (Exception e) {
			System.out.println("Close connection for ID : " + this.getName()
					+ "\n");
		}
	}

	/**
 * 
 */
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
					+ ",\n\n Your authentication code is: "
					+ Auth_Code
					+ " Please copy and paste this into the client to complete your registration!");

			Transport.send(message);

			System.out.println("Sent mail to " + email + "...\n");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
