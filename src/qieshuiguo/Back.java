package qieshuiguo;
import java.awt.image.BufferedImage;
public class Back extends FlyingObject{
	private int y1;
	private static BufferedImage image;
	static {
		image = loadImage("boom.png");
	}
	public Back(){
		super(66,68);

	}
	
	public BufferedImage getImage() {
		if(isLife()) {      
			return image;
		}else if(isDead()) { 
				state = REMOVE;
		}
		return null; 
	}


}
