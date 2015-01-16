package com.themercury.mercury.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.themercury.mercury.Game;
import com.themercury.mercury.entity.mob.PlayerMP;
import com.themercury.mercury.level.Level;
import com.themercury.mercury.net.packet.Packet;
import com.themercury.mercury.net.packet.Packet00Login;
import com.themercury.mercury.net.packet.Packet01Disconnect;
import com.themercury.mercury.net.packet.Packet02Move;
import com.themercury.mercury.net.packet.Packet.PacketTypes;

public class GameClient extends Thread {
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet = null;
		if(type == PacketTypes.INVALID) {
			
		} else if(type == PacketTypes.LOGIN) {
			packet = new Packet00Login(data);
			System.out.println("["+address.getHostAddress()+ ":"+port+"] " + ((Packet00Login)packet).getUsername() + " HAS JOINED THE GAME...");
			PlayerMP player = new PlayerMP(game.getLevel(), game.getLevel().getWidth()<<3, game.getLevel().getHeight()<<3, ((Packet00Login)packet).getUsername(), address, port);
			game.level.addEntity(player);
		} else if(type == PacketTypes.DISCONNECT) {
			packet = new Packet01Disconnect(data);
			System.out.println("["+address.getHostAddress()+ ":"+port+"] " + ((Packet01Disconnect)packet).getUsername() + " HAS LEFT THE WORLD...");
			game.level.removePlayerMP(((Packet01Disconnect)packet).getUsername());
		} else if(type == PacketTypes.MOVE) {
			packet = new Packet02Move(data);
			movePlayer((Packet02Move)packet);
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void movePlayer(Packet02Move packet) {
		this.game.level.movePlayer(packet.getUsername(),packet.getX(), packet.getY());
	}
}
