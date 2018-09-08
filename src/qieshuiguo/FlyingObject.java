package qieshuiguo;

import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Graphics;

/** 飞行物 */
public abstract class FlyingObject {
	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2; 
	protected int state = LIFE; 
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int  speed;

	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random(); 
		x = rand.nextInt(World.WIDTH-this.width); 
		y = World.HEIGHT-this.height;
		speed=5;
	}
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x=x;
		this.y=y;
	}

	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));  
			return img;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public  void step() {
		if(isLife()) {
		y-=speed; 
		}
		if(isDead()) {
			y+=speed;
		}
		if(y<=150) { 
			speed*=-1;
		}
	}
	
	public abstract BufferedImage getImage();
	
	public boolean isLife() {
		return state==LIFE; 
	}
	public boolean isDead() {
		return state==DEAD; 
	}
	public boolean isRemove() {
		return state==REMOVE; 
	}
	public void paintObject(Graphics g) {
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	public boolean hit(FlyingObject other) {
		int x1 = this.x-other.width;  //x1:敌人的x-子弹的宽
		int x2 = this.x+this.width;   //x2:敌人的x+敌人的宽
		int y1 = this.y-other.height; //y1:敌人的y-子弹的高
		int y2 = this.y+this.height;  //y2:敌人的y+敌人的高
		int x = other.x;              //x:子弹的x
		int y = other.y;              //y:子弹的y
		
		return x>=x1 && x<=x2 
			   && 
			   y>=y1 && y<=y2; //x在x1与x2之间，并且，y在y1与y2之间，即为撞上了
	}
	public void goDead() {
		state = DEAD; //将当前状态修改为DEAD(死了的)
	}
	
}

