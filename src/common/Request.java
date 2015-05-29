package common;

import java.io.Serializable;

public class Request implements Serializable{
	static final long serialVersionUID = 74045322;
	
	private RequestType type;
	private Serializable data;
	
	public Request(){
		this.type = null;
		this.data = null;
	}
	
	public Request(RequestType type, Serializable data){
		this.type = type;
		this.data = data;
	}
	
	public RequestType getType(){ return type; }
	public Serializable getData(){ return data; }
}
