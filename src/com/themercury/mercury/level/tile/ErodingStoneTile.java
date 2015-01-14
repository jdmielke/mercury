package com.themercury.mercury.level.tile;

import com.themercury.mercury.Graphics.Sprite;

public class ErodingStoneTile extends Tile {

	public ErodingStoneTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean solid() {
		return true;
	}

}
