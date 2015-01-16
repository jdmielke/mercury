package com.themercury.mercury.net.packet;

import com.themercury.mercury.net.GameClient;
import com.themercury.mercury.net.GameServer;

public class Packet01Disconnect extends Packet {

	private String username;
	
	public Packet01Disconnect(byte[] data) {
		super(01);
		this.username = readData(data);
	}
	
	public Packet01Disconnect(String userName) {
		super(01);
		this.username = userName;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("01" + this.username).getBytes();
	}
 
	public String getUsername() {
		return username;
	}

}
