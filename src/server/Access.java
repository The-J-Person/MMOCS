package server;

import java.security.SecureRandom;

import common.RequestType;

public class Access {

	String User, Pass, Email, code;
	RequestType action;
	public static int id;

	public Access(RequestType a, String u, String p, String e, String c) {
		this.action = a;
		this.User = u;
		this.Pass = p;
		this.Email = e;
		this.code = c;
	}

	public RequestType getAction() {
		return this.action;
	}

	public String getUser() {
		return this.User;
	}

	public String getPass() {
		return this.Pass;
	}

	public String getEmail() {
		return this.Email;
	}
	
	public String getCode() {
		return this.code;
	}

	// function will check if client can connect to server.
	public static boolean login(String u, String p) {
		id =  DataBase.LoginFun(u, p);
		if (id > 0 )
			return true;
		else
			return false;
	}

	public static int newUser(String u, String p, String e) {	//function checking if user exists , if not adding them.

		SecureRandom random = new SecureRandom();
		String con = random.generateSeed(10).toString();

		switch (DataBase.AddUser(u, p, null, e, null, con)) {
		case OK:
			return 0; // User create successfully and added to DB.
		case EXISTS:
			return 1; // User Name exist in DB.
		case SQL_ERROR:
			return -1; // SQL_ERRORE
		default:
			break;
		}
		return -1;
	}

	public static Boolean confirm(String u , String con) {	// function checking if confirmation code same to in DB.
		if (DataBase.ConfirmFun(u , con) == SQLOutput.OK) {
			return true;
		} else {
			return false;
		}
	}
}
