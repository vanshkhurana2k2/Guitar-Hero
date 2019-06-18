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

public class Example extends Application{

	GraphicsContext gc;
	Image trooper;
	int x = 0;
	int y = 0;
	AnimateObjects animate;
	ArrayList <Image> images;

	public static void main(String[] args){

		launch();

	}//main

	public void start(Stage stage){

		stage.setTitle("Final Project Title");
		Group root = new Group();
		Canvas canvas = new Canvas(1000, 1000);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		gc = canvas.getGraphicsContext2D();
		trooper = new Image("trooper.jpg");
		gc.drawImage(trooper, 180, 100);
		animate = new AnimateObjects();
		animate.start();

		stage.show();

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now){

			x+=1;
			gc.drawImage(trooper, 180+x, 100);

			Rectangle2D rect1 = new Rectangle2D( 100,100,100,100 );
			gc.fillRect(100,100,100,100);

			Rectangle2D rect2 = new Rectangle2D( 180+x,100,trooper.getWidth(),trooper.getHeight() );

			if (rect2.intersects(rect1))
				System.out.println("HIT");
		}

	}

}