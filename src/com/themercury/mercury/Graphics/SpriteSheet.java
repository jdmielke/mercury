package com.themercury.mercury.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/tiles.png", 256);
	public static SpriteSheet player = new SpriteSheet("/textures/player.png", 256);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getHeight();
			int h = image.getWidth();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
