package qieshuiguo;
import java.awt.image.BufferedImage;
public class Dao extends FlyingObject {
	private static BufferedImage image;
	static {
		image = loadImage("123.png");
	}
	public Dao(){
		super(60,116,300,100);
	}
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;  
		this.y = y-this.height/2; 
	}
	public BufferedImage getImage() {
		if(isLife()) { 
			return image;
		}
		return null;
}
	
	
	
}


















