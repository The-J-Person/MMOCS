package common;

import java.io.Serializable;

public class Update implements Serializable{
	static final long serialVersionUID = 42023892;
	
	private UpdateType type;
	private Serializable data;
	
	public Update(){
		this.type = null;
		this.data = null;
	}
	
	public Update(UpdateType type, Serializable data){
		this.type = type;
		this.data = data;
	}
	
	public UpdateType getType(){ return type; }
	public Serializable getData(){ return data; }
}
