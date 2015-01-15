package com.themercury.mercury.Graphics;

public class Font {
	private static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      "+"0123456789.,:;'\"!?$%()-=+/      ";
	
	public static void render(String msg, Screen screen, int x, int y, int color) {
		msg=msg.toUpperCase();
		for(int i = 0; i < msg.length(); i++) {
			int charIndex = characters.indexOf(msg.charAt(i));
			if(charIndex >= 0) screen.renderText(x + (i * 8), y, charIndex);;
		}
	}
}
