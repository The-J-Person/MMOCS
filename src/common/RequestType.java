package common;

public enum RequestType {
	MOVE,
	ATTACK,
	HARVEST,
	LOG_IN,
	LOG_OUT,
	REGISTER,
	CRAFT,
	TILE,
	UPDATE_TILE,
	CONFIRM;
	
	static final long serialVersionUID = 12142322;
	
	public boolean requireAck(){
		return (this == MOVE || this == ATTACK || this == HARVEST || this == LOG_IN || this == REGISTER || this == CRAFT
				|| this == CONFIRM); 
	}
}