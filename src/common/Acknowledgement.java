package common;

import java.io.Serializable;

public class Acknowledgement implements Serializable{
	static final long serialVersionUID = 59934332;
	
	private boolean ack;
	private RequestType req;
	
	public Acknowledgement(){
		this.ack = false;
		this.req = null;
	}
	
	public Acknowledgement(boolean ack , RequestType req){
		this.ack = ack;
		this.req = req;
	}
	
	public RequestType getRequestType(){ return req; }
	public boolean getAck(){ return ack; }
}
