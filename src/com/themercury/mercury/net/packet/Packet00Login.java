package com.themercury.mercury.net.packet;

import com.themercury.mercury.net.GameClient;
import com.themercury.mercury.net.GameServer;

public class Packet00Login extends Packet {

	private String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		this.username = readData(data);
	}
	
	public Packet00Login(String userName) {
		super(00);
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
		return ("00" + this.username).getBytes();
	}
 
	public String getUsername() {
		return username;
	}
}
