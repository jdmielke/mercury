package com.themercury.mercury.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.themercury.mercury.Game;
import com.themercury.mercury.entity.mob.Player;
import com.themercury.mercury.entity.mob.PlayerMP;
import com.themercury.mercury.net.packet.Packet;
import com.themercury.mercury.net.packet.Packet.PacketTypes;
import com.themercury.mercury.net.packet.Packet00Login;
import com.themercury.mercury.net.packet.Packet01Disconnect;
import com.themercury.mercury.net.packet.Packet02Move;

public class GameServer extends Thread {
	private DatagramSocket socket;
	private Game game;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	
	public GameServer(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
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
			System.out.println("["+address.getHostAddress()+ ":"+port+"] " + ((Packet00Login)packet).getUsername() + " HAS CONNECTED...");
			PlayerMP player = new PlayerMP(game.getLevel(), game.getLevel().getWidth()<<3, game.getLevel().getHeight()<<3, ((Packet00Login)packet).getUsername(), address, port);
			this.addConnection(player, (Packet00Login)packet);
		} else if(type == PacketTypes.DISCONNECT) {
			packet = new Packet01Disconnect(data);
			System.out.println("["+address.getHostAddress()+ ":"+port+"] " + ((Packet01Disconnect)packet).getUsername() + " HAS LEFT...");
			this.removeConnection((Packet01Disconnect)packet);
		} else if(type == PacketTypes.MOVE) {
			packet = new Packet02Move(data);
			movePlayer((Packet02Move)packet);
		}
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for(PlayerMP p : this.connectedPlayers) {
			if(player.getUsername().equalsIgnoreCase(p.getUsername())) {
				if(p.ipAddress == null) {
					p.ipAddress = player.ipAddress;
				}
				if(p.port == -1) {
					p.port = player.port;
				}
				alreadyConnected = true;
			}else {
				sendData(packet.getData(),p.ipAddress,p.port);
				
				packet = new Packet00Login(p.getUsername());
				sendData(packet.getData(), player.ipAddress, player.port);
			}
		}
		if(!alreadyConnected) {
			this.connectedPlayers.add(player);
		}
	}
	
	private void removeConnection(Packet01Disconnect packet) {
		this.connectedPlayers.remove(getPlayerIndex(packet.getUsername()));
		packet.writeData(this);
	}
	
	private void movePlayer(Packet02Move packet) {
		if(getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerIndex(packet.getUsername());
			this.connectedPlayers.get(index).x = packet.getX();
			this.connectedPlayers.get(index).y = packet.getY();
			packet.writeData(this);
		}
	}
	
	public PlayerMP getPlayerMP(String username) {
		for(PlayerMP player : this.connectedPlayers) {
			if(player.getUsername().equalsIgnoreCase(username)) {
				return player;
			}
		}
		return null;
	}
	
	public int getPlayerIndex(String username) {
		int index = 0;
		for(PlayerMP player : this.connectedPlayers) {
			if(player.getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for(PlayerMP p : connectedPlayers) {
			sendData(data,p.ipAddress, p.port);
		}
	}

}
