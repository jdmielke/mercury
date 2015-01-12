package com.themercury.mercury.entity;

import java.util.Random;

import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.level.Level;

public abstract class Entity {
	
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
}
