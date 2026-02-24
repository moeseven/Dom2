package moesgames.dom2.controller;

public class ControlMessage extends Message{
	
	
	
	public int getSender_id() {
		return sender_id;
	}

	public ControlmessageType getType() {
		return type;
	}

	public int getIndex_1() {
		return index_1;
	}

	
	public int getIndex_2() {
		return index_2;
	}


	private ControlmessageType type;
	
	private int index_1,index_2,sender_id;

	public ControlMessage(ControlmessageType type, int index_1,int sender_id) {
		this(type,index_1,index_1,sender_id);
	}

	public ControlMessage(ControlmessageType type, int index_1, int index_2, int sender_id) {
		super();
		this.type = type;
		this.index_1 = index_1;
		this.index_2 = index_2;
		this.sender_id = sender_id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(type.toString());
		sb.append("from: player");
		sb.append(sender_id);
		sb.append(" index_1:");
		sb.append(index_1);
		sb.append(" index_2:");
		sb.append(index_2);
		return sb.toString();
	}
	
	
	

}
