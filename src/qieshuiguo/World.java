package qieshuiguo;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/** 整个游戏世界 */
public class World extends JPanel {
	public static final int WIDTH =640;
	public static final int HEIGHT =480; 

	public static final int START = 0;     
	public static final int RUNNING = 1;   
	public static final int PAUSE = 2;     
	public static final int GAME_OVER = 3; 
	private int state = START; 
	private FlyingObject[] enemies = {};  
	private Dao dao = new Dao();
private Sky sky = new Sky();

	int enterIndex = 0;
	public void enterAction() { 
		enterIndex++;
		if(enterIndex%40==0) { 
			FlyingObject obj = nextOne(); 
			enemies = Arrays.copyOf(enemies,enemies.length+1);
			enemies[enemies.length-1] = obj;
		}
	}
	public FlyingObject nextOne() {
		Random rand = new Random(); //随机数对象
		int type = rand.nextInt(50); //0到19之间
		if(type<10) { 
			return new Apple();
		}else if(type<20) { 
			return new Back();
		}else if(type<30) { 
			return new Banana();
		}else if(type<40) {
			return new Peach();
		}else {
			return new Sandia();
		}

	}
	public void stepAction() { 
		for(int i=0;i<enemies.length;i++) { 
			enemies[i].step(); 
		}
	
	}
	int score = 0;
	public void heroBangAction() { //每10毫秒走一次
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			FlyingObject f = enemies[i]; //获取每一个敌人
			if(dao.isLife() && f.isLife() && f.hit(dao)) { //撞上了
				f.goDead();
				if(f instanceof Enemy) {
					Enemy e = (Enemy)f;
					score+=e.getScore();
				}
			}
		}
	}
	public void action() { 
				MouseAdapter l = new MouseAdapter() {
					public void mouseMoved(MouseEvent e) {
							int x = e.getX(); //获取鼠标的x坐标
							int y = e.getY(); //获取鼠标的y坐标
							dao.moveTo(x, y); //英雄机随着鼠标移动
					}
				};
				this.addMouseListener(l); 
				this.addMouseMotionListener(l);
		Timer timer = new Timer(); 
		int intervel = 10; 
		timer.schedule(new TimerTask() {
			public void run() {
				enterAction(); 
				stepAction();
				heroBangAction();
				System.out.println(score);
				repaint();    
			}
		},intervel,intervel); 
	}
	public void paint(Graphics g) {
		sky.paintObject(g);
		dao.paintObject(g);
		for(int i=0;i<enemies.length;i++) { 
			enemies[i].paintObject(g); 
		}
		dao.paintObject(g);
		g.drawLine(100,100,200,200);
		Font f=new Font(null,Font.PLAIN,30);
		g.setFont(f);
		g.drawString("得分: "+score,10,25); //画分
	}
	//加载背景音乐
	public static void playMusic(){
		try {
			URL cb;
			File f = new File("/home/soft01/eclipse-workspace/Demo/JSD1807_SE/MyProject/src/qieshuiguo/Ariari.wav"); 
			cb = f.toURL();
			AudioClip aau;
			aau = Applet.newAudioClip(cb);
		
			aau.play();	
			aau.loop();//循环播放
			System.out.println("可以播放");
			// 循环播放 aau.play()
			//单曲 aau.stop()停止播放
 
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		playMusic();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		
		world.action();
		
		
	}
}

