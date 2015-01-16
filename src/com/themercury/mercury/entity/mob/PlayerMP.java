package com.themercury.mercury.entity.mob;

import java.net.InetAddress;

import com.themercury.mercury.input.Keyboard;
import com.themercury.mercury.level.Level;

public class PlayerMP extends Player {
	
	public InetAddress ipAddress;
	public int port;
	
	public PlayerMP(Level level, int x, int y, Keyboard input, String username, InetAddress ipAddress, int port) {
		super(x, y, input);
		this.level = level;
		this.username = username;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public PlayerMP(Level level, int x, int y,String username, InetAddress ipAddress, int port) {
		super(x, y, null);
		this.level = level;
		this.username = username;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public void update() {
		super.update();
	}
	
	public void render() {
		super.render();
	}
	
}
