package com.themercury.mercury.level.tile;

import com.themercury.mercury.Graphics.Sprite;

public class GreyBrickTile extends Tile {

	public GreyBrickTile(Sprite sprite) {
		super(sprite);
	}

	
	public boolean solid() {
		return true;
	}
}
