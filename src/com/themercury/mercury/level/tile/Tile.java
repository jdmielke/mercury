package com.themercury.mercury.level.tile;

import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.Graphics.Sprite;

public class Tile {
	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile dirtyGrass = new RockTile(Sprite.dirtyGrass);
	public static Tile sand = new SandTile(Sprite.sand);
	public static Tile redBrick = new RedBrickTile(Sprite.redBrick);
	public static Tile greyBrick = new GreyBrickTile(Sprite.greyBrick);
	public static Tile marble = new MarbleTile(Sprite.marble);
	public static Tile redCrapet = new RedCarpetTile(Sprite.redCarpet);
	public static Tile woodFloor = new WoodFloorTile(Sprite.woodFloor);
	public static Tile erodingStone = new ErodingStoneTile(Sprite.erodingStone);
	public static Tile erodingStoneJungle = new ErodingStoneJungleTile(Sprite.erodingStoneJungle);


	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid() {
		return false;
	}
}
