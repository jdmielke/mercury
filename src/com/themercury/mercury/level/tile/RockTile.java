package com.themercury.mercury.level.tile;

import com.themercury.mercury.Graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	
	public boolean solid() {
		return true;
	}

}
