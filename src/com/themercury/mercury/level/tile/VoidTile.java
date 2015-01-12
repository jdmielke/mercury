package com.themercury.mercury.level.tile;

import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.Graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}
