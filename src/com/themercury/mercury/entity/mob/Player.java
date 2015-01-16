package com.themercury.mercury.entity.mob;

import com.themercury.mercury.Game;
import com.themercury.mercury.Graphics.Font;
import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.Graphics.Sprite;
import com.themercury.mercury.input.Keyboard;
import com.themercury.mercury.net.packet.Packet02Move;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int animate = 0;
	public boolean walking = false;
	protected String username;
	
	public Player(Keyboard input) {
		this.input = input;
		this.sprite = Sprite.playerD;
	}
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
	}
	
	public void update() {
		int xa = 0;
		int ya = 0;
		if(animate < 10000) animate++;
		else animate = 0;
		if(input != null){
			if(input.up) ya--;
			if(input.down) ya++;
			if(input.left) xa--;
			if(input.right) xa++;
		}
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
			
			Packet02Move packet = new Packet02Move(this.getUsername(), this.x, this.y);
			packet.writeData(Game.game.socketClient);
		}
		else walking = false;
	}
	
	public void render(Screen screen) {
		if(dir == 0) {
			sprite = Sprite.playerU;
			if(walking || moving) {
				if(animate % 20 > 10) {
					sprite = Sprite.playerU_1;
				}else {
					sprite = Sprite.playerU_2;
				}
			}
		}
		if(dir == 1) {
			sprite = Sprite.playerR;
			if(walking || moving) {
				if(animate % 20 > 10) {
					sprite = Sprite.playerR_1;
				}else {
					sprite = Sprite.playerR_2;
				}
			}
		}
		if(dir == 2) {
			sprite = Sprite.playerD;
			if(walking || moving) {
				if(animate % 20 > 10) {
					sprite = Sprite.playerD_1;
				}else {
					sprite = Sprite.playerD_2;
				}
			}
		}
		if(dir == 3) {
			sprite = Sprite.playerL;
			if(walking || moving) {
				if(animate % 20 > 10) {
					sprite = Sprite.playerL_1;
				}else {
					sprite = Sprite.playerL_2;
				}
			}
		}
		screen.render32Player(x - 16, y - 16, sprite);
		Font.render(username, screen, x - (username.length() * 8) / 2, y - 30, 0x00);
		
	}
	
	public String getUsername() {
		return username;
	}
	
}
