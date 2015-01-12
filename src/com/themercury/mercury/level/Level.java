package com.themercury.mercury.level;

import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.level.tile.Tile;

public class Level {
	protected int width;
	protected int height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void update() {
		
	}
	
	private void time() {
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x,y).render(x, y, screen);
			}
		}
		
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.grass;
		if(tiles[x + y * width] == 0xff4cff00) return Tile.grass;
		if(tiles[x + y * width] == 0xffffd800) return Tile.flower;
		if(tiles[x + y * width] == 0xff808080) return Tile.rock;
		if(tiles[x + y * width] == 0xff7f6a00) return Tile.dirtyGrass;
		if(tiles[x + y * width] == 0xffdbc467) return Tile.sand;
		if(tiles[x + y * width] == 0xffb73838) return Tile.redBrick;
		if(tiles[x + y * width] == 0xff404040) return Tile.greyBrick;
		if(tiles[x + y * width] == 0xffbcbcbc) return Tile.marble;
		if(tiles[x + y * width] == 0xffad0002) return Tile.redCrapet;
		if(tiles[x + y * width] == 0xff683913) return Tile.woodFloor;
		if(tiles[x + y * width] == 0xff303030) return Tile.erodingStone;
		if(tiles[x + y * width] == 0xff2a542e) return Tile.erodingStoneJungle;
		
		return Tile.voidTile;
	}
			
}
