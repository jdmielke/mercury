package com.themercury.mercury;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.themercury.mercury.Graphics.Screen;
import com.themercury.mercury.entity.mob.Player;
import com.themercury.mercury.entity.mob.PlayerMP;
import com.themercury.mercury.input.Keyboard;
import com.themercury.mercury.input.WindowHandler;
import com.themercury.mercury.level.Level;
import com.themercury.mercury.level.SpawnLevel;
import com.themercury.mercury.net.GameClient;
import com.themercury.mercury.net.GameServer;
import com.themercury.mercury.net.packet.Packet00Login;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Mercury";
	public static Game game;

	private Thread gameThread;
	public JFrame frame;
	private Keyboard key;
	public WindowHandler windowHandler;
	public Level level;
	public Player player;
	private boolean running = false;
	

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	
	public GameClient socketClient;
	public GameServer socketServer;
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Mercury");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init() {
		game = this;
		screen = new Screen(width, height);
		key = new Keyboard();
		windowHandler = new WindowHandler(this);
		level = new SpawnLevel("/textures/spawnlevel.png");
		addKeyListener(key);
		player = new PlayerMP(level, level.getWidth()<<3, level.getHeight()<<3, key, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1);
		level.addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername());

		if(socketServer!=null) {
			socketServer.addConnection((PlayerMP)player, loginPacket);
		}
		loginPacket.writeData(socketClient);
		
		
		
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Keyboard getKeyoard() {
		return key;
	}
	
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Display");
		gameThread.start();
		
		if(JOptionPane.showConfirmDialog(this, "START SERVER?") == 0) {
			socketServer = new GameServer(this);
			socketServer.start();
		}
		
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		
	}
	
	public synchronized void stop() {
		running = false;
		try{
			gameThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		init();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(Game.title + "  |  " + updates +" ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update() {
		key.update();
		level.updateEntities();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		level.renderPlayers(screen);
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}
}
