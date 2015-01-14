package com.themercury.mercury.entity.mob;

import com.themercury.mercury.Graphics.Sprite;
import com.themercury.mercury.entity.Entity;

public abstract class Mob extends Entity {
	
	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	
	public void move(int xa, int ya) {
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if(xa > 0) dir = 1;
		if(xa < 0) dir = 3;
		if(ya > 0) dir = 2;
		if(ya < 0) dir = 0;
		
		if(!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}
	
	public void update() {
	}
	
	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for(int i = 0; i < 4; i++) {
			int xt = ((x + xa) + i % 2 * 12 - 6) / 16;
			int yt = ((y + ya) + i / 2 * 12 + 3) / 16;
			if(level.getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	
	public void render() {
	}
	
}
