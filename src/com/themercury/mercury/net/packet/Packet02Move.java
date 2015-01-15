package com.themercury.mercury.net.packet;

import com.themercury.mercury.net.GameClient;
import com.themercury.mercury.net.GameServer;

public class Packet02Move extends Packet {

	public Packet02Move(int packetId) {
		super(packetId);
	}

	@Override
	public void writeData(GameClient client) {
		
	}

	@Override
	public void writeData(GameServer server) {
		
	}

	@Override
	public byte[] getData() {
		return null;
	}

}
