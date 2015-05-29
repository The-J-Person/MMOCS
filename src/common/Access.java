package common;

import java.security.SecureRandom;

import server.DataBase;
import server.SQLOutput;

public class Access {

	String action, User, Pass, Email, code;

	public Access(String a, String u, String p, String e, String c) {
		this.action = a;
		this.User = u;
		this.Pass = p;
		this.Email = e;
		this.code = c;
	}

	public String getAction() {
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
		if (DataBase.LoginFun(u, p) == SQLOutput.OK)
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
		if (DataBase.ChangeActivity(u , con) == SQLOutput.OK) {
			return true;
		} else {
			return false;
		}
	}
}
