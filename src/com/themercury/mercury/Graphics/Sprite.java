package com.themercury.mercury.Graphics;

public class Sprite {
	public final int SIZE;
	private int x;
	private int y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	/*  TILE SPRITES  */
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite dirtyGrass = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite redBrick = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite greyBrick = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite marble = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite redCarpet = new Sprite(16, 1, 2, SpriteSheet.tiles);
	public static Sprite woodFloor = new Sprite(16, 2, 2, SpriteSheet.tiles);
	public static Sprite erodingStone = new Sprite(16, 0, 3, SpriteSheet.tiles);
	public static Sprite erodingStoneJungle = new Sprite(16, 1, 3, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0xffffff);

	
	/*  PLAYER AND ANIMATION SPRITES  */
	public static Sprite playerU = new Sprite(32, 0, 0, SpriteSheet.player);
	public static Sprite playerD = new Sprite(32, 2, 0, SpriteSheet.player);
	public static Sprite playerL = new Sprite(32, 3, 0, SpriteSheet.player);
	public static Sprite playerR = new Sprite(32, 1, 0, SpriteSheet.player);
	
	public static Sprite playerU_1 = new Sprite(32, 0, 1, SpriteSheet.player);
	public static Sprite playerU_2 = new Sprite(32, 0, 2, SpriteSheet.player);
	public static Sprite playerD_1 = new Sprite(32, 2, 1, SpriteSheet.player);
	public static Sprite playerD_2 = new Sprite(32, 2, 2, SpriteSheet.player);
	public static Sprite playerL_1 = new Sprite(32, 3, 1, SpriteSheet.player);
	public static Sprite playerL_2 = new Sprite(32, 3, 2, SpriteSheet.player);
	public static Sprite playerR_1 = new Sprite(32, 1, 1, SpriteSheet.player);
	public static Sprite playerR_2 = new Sprite(32, 1, 2, SpriteSheet.player);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for(int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = color;
		}
	}
	
	private void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}
