import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.*;
public class Note{
	private int y;
	private int x;
	private int duration;
	private boolean pressed = false;
	private int entryTime;
	int speed = 17;
	//int speed = 2;
	public Note(int entryTime, int lane)
	{
		x = lane;
		this.entryTime = entryTime;
	}
	public Note(int lane, int entryTime, int duration)
	{
		x = lane;
		this.entryTime = entryTime;
		this.duration = duration;
	}
	public int getY() { return y; }
	public int getLane() { return x; }
	public int getX() { return getLane(); }
	public int getDuration() { return duration; }
	public boolean isPressed() { return pressed; }
	public void moveDown() { y+= speed;
		if(y > 768)
			VanshKhurana.number--;}
	public void press() { pressed = true; }
	public void reduceDuration() { duration--; }
	public int getEntryTime() { return entryTime; }
	//public int setSpeed(int speed){
	//	return speed;
	//}
}