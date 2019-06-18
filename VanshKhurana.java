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

public class VanshKhurana extends Application{
	GraphicsContext gc;
	AnimateObjects animate;
	int frame = 270;
	int totalpresses = 0;
	int correctpresses = 0;
	Image background;
	int numLanes = 5;
	static int score = 0;
	int[] screenSize = {1366, 768};
	boolean start = true;
	static int number = 19;
	ArrayList<Note> notes = new ArrayList<Note>();
	ArrayList<Note> visibleNotes = new ArrayList<>();
	ArrayList<Rectangle2D> rectangles = new ArrayList<>();
	ArrayList<Boolean> pressedList = new ArrayList<>();
	AudioClip clip;// = new AudioClip(resource.toString());
	AudioClip clip2;
	public static void main(String[]args){
		launch();
	}
	public void start(Stage stage){
		URL resource = getClass().getResource("finesse.mp3");
		clip = new AudioClip(resource.toString());
		URL resource2 = getClass().getResource("lightning.mp3");
		clip2 = new AudioClip(resource2.toString());
		clip2.play();
		stage.setTitle("Guitar Hero");
		Group root = new Group();
		Canvas canvas = new Canvas(1366, 768);
		background = new Image("bg.jpg");
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		gc = canvas.getGraphicsContext2D();
		animate = new AnimateObjects();
		animate.start();
		stage.show();
		//          lane (0, 1, or 2) || entryTime (time * 60)
		addNotes();
		for(int i = 0; i < 5; i++)
			pressedList.add(false);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event) {

				if(start)
				{
					if(event.getCode() == KeyCode.ENTER)
					{
						start = false;
						frame = 270;
						clip.play();
						number = 0;
						score = 0;
					}
					if(event.getCode() == KeyCode.P)
					{
						start = false;
						frame = 270;
						clip.play();
						number = 0;
						score = 0;
					}
				}
				else
				{
					scene.addEventHandler(KeyEvent.KEY_RELEASED,this);
					String text = event.getText();
					if(event.getEventType().toString().equals("KEY_PRESSED"))
					{
						if(text.equals("1")){
							check(1);
							totalpresses++;
							pressedList.set(0, true);
						}
						else if(text.equals("2")){
							check(2);
							totalpresses++;
							pressedList.set(1, true);
						}
						else if(text.equals("3")){
							check(3);
							totalpresses++;
							pressedList.set(2, true);
						}
						else if(text.equals("4")){
							check(4);
							totalpresses++;
							pressedList.set(3, true);
						}
						else if(text.equals("5")){
							check(5);
							totalpresses++;
							pressedList.set(4, true);
						}
					}
					else
					{
						if(text.equals("1")){
							check(1);
							totalpresses++;
							pressedList.set(0, false);
						}
						else if(text.equals("2")){
							check(2);
							totalpresses++;
							pressedList.set(1, false);
						}
						else if(text.equals("3")){
							check(3);
							totalpresses++;
							pressedList.set(2, false);
						}
						else if(text.equals("4")){
							check(4);
							totalpresses++;
							pressedList.set(3, false);
						}
						else if(text.equals("5")){
							check(5);
							totalpresses++;
							pressedList.set(4, false);
						}
					}
				}
            }
		});


		for(int i = 0; i < numLanes; i++)
		{
			rectangles.add(new Rectangle2D(200 + i * 81, 570, 45, 60));
		}

	}
	public class AnimateObjects extends AnimationTimer{
		public void handle(long now){
			if(start) //Start Screen GRAPHICS
			{
				gc.drawImage(new Image("bg.jpg"), 0, 0);
				//gc.setFill(Color.WHITE);
				//gc.fillRect(0, 0, 1336, 768);
				gc.setFill(Color.YELLOW);
				gc.fillText("Ghetto Guitar Hero", 470, 300);
				Font font = Font.font("Sans Serif", FontWeight.NORMAL, 50);
				Font font2 = Font.font("Times New Roman", FontWeight.NORMAL, 600);
				gc.setFont(font);
				gc.fillText("Press Enter to Begin", 470, 500);
				gc.setFill(Color.BLACK);
				if(number != 19)
				{
					gc.setFill(Color.RED);
					gc.setFont(font2);
					if((int)(score / 514.0 * 100) <= 64)
						gc.fillText("F", 50,500);
					else if((int)(score / 514.0 * 100) < 70)
						gc.fillText("D", 50, 500);
					else if((int)(score / 514.0 * 100) < 80)
						gc.fillText("C", 50, 500);
					else if((int)(score / 514.0 * 100) < 90)
						gc.fillText("B", 50, 500);
					else
						gc.fillText("A", 50,500);
					gc.setFont(font);
					gc.fillText("Your Score: " + score + " / 534 (" + ((int)(score / 514.0 * 100) + "%)"), 470, 400);
				}

			}
			else//EVERYWHERE ELSE
			{
				//gc.setFill(Color.WHITE);
				//gc.fillRect(0, 0, screenSize[0], screenSize[1]);
				gc.drawImage(background, 0, 0);
				gc.setFill(Color.BLACK);

				gc.fillText("Notes Hit: " + score, 1000, 350);

				for(int i = 0; i < numLanes; i++)
					gc.fillRect(200 + i * 81, 535
					, 45, 105);
				gc.setFill(Color.BLUE);
				for(int i = 0; i < numLanes; i++)
				{
					if(pressedList.get(i))
						gc.setFill(Color.WHITE);
					else
						gc.setFill(Color.BLUE);
					gc.fillRect(200 + i * 81, 560, 45, 50);
				}


				gc.setFill(Color.RED);
				//System.out.println(frame);
				for(Note note : notes)
					if(frame == note.getEntryTime())
						visibleNotes.add(note);

				for(Note note : visibleNotes)
				{
					gc.fillRect(119 + (note.getX() * 81), note.getY(), 45, 35);
					note.moveDown();
				}

				frame++;

				if(frame > notes.get(notes.size() - 1).getEntryTime() + 250)
				{
					clip.stop();
					start = true;
					visibleNotes.clear();
					notes.clear();
					addNotes();
				}
			}
		}
	}
	public void check(int lane)
	{
		ArrayList<Note> removeNotes = new ArrayList<>();
		for(Note note : visibleNotes)
		{
			if(note.getLane() == lane)
			{
				Rectangle2D rect = new Rectangle2D(119 + (note.getX() * 81), note.getY(), 45, 35);
				if(rect.intersects(rectangles.get(lane - 1)))
				{
					note.press();
					removeNotes.add(note);
					score++;

					System.out.println("pressed note " + lane);
				}
			}
		}

		for(Note note : removeNotes)
			visibleNotes.remove(note);
	}
	public void addNotes()
	{
		notes.add(new Note(524, 3));
		notes.add(new Note(524, 4));
		notes.add(new Note(558, 3));
		notes.add(new Note(558, 4));
		notes.add(new Note(593, 3));
		notes.add(new Note(593, 4));
		notes.add(new Note(603, 3));
		notes.add(new Note(603, 4));
		notes.add(new Note(638, 3));
		notes.add(new Note(638, 4));
		notes.add(new Note(661, 1));
		notes.add(new Note(661, 2));
		notes.add(new Note(695, 1));
		notes.add(new Note(695, 2));
		notes.add(new Note(730, 1));
		notes.add(new Note(730, 2));
		notes.add(new Note(740, 1));
		notes.add(new Note(740, 2));
		notes.add(new Note(775, 1));
		notes.add(new Note(775, 2));
		notes.add(new Note(798, 3));
		notes.add(new Note(798, 4));
		notes.add(new Note(831, 3));
		notes.add(new Note(831, 4));
		notes.add(new Note(866, 3));
		notes.add(new Note(866, 4));
		notes.add(new Note(878, 3));
		notes.add(new Note(878, 4));
		notes.add(new Note(913, 3));
		notes.add(new Note(913, 4));
		notes.add(new Note(953, 1));
		notes.add(new Note(963, 1));
		notes.add(new Note(969, 2));
		notes.add(new Note(981, 2));
		notes.add(new Note(986, 3));
		notes.add(new Note(998, 3));
		notes.add(new Note(1004, 2));
		notes.add(new Note(1016, 2));
		notes.add(new Note(1020, 3));
		notes.add(new Note(1031, 3));
		notes.add(new Note(1038, 4));
		notes.add(new Note(1049, 4));
		notes.add(new Note(1055, 5));
		notes.add(new Note(1070, 3));
		notes.add(new Note(1070, 4));
		notes.add(new Note(1106, 3));
		notes.add(new Note(1106, 4));
		notes.add(new Note(1140, 3));
		notes.add(new Note(1140, 4));
		notes.add(new Note(1154, 3));
		notes.add(new Note(1154, 4));
		notes.add(new Note(1186, 3));
		notes.add(new Note(1186, 4));
		notes.add(new Note(1210, 1));
		notes.add(new Note(1210, 2));
		notes.add(new Note(1244, 1));
		notes.add(new Note(1244, 2));
		notes.add(new Note(1278, 1));
		notes.add(new Note(1278, 2));
		notes.add(new Note(1289, 1));
		notes.add(new Note(1289, 2));
		notes.add(new Note(1324, 1));
		notes.add(new Note(1324, 2));
		notes.add(new Note(1346, 3));
		notes.add(new Note(1346, 4));
		notes.add(new Note(1380, 3));
		notes.add(new Note(1380, 4));
		notes.add(new Note(1415, 3));
		notes.add(new Note(1415, 4));
		notes.add(new Note(1427, 3));
		notes.add(new Note(1427, 4));
		notes.add(new Note(1461, 3));
		notes.add(new Note(1461, 4));
		notes.add(new Note(1621, 1));
		//notes.add(new Note(1621, 2));
		//notes.add(new Note(1621, 3));
		//notes.add(new Note(1621, 4));
		notes.add(new Note(1621, 5));
		notes.add(new Note(1655, 2));
		notes.add(new Note(1655, 3));
		//notes.add(new Note(1655, 5));
		notes.add(new Note(1690, 2));
		notes.add(new Note(1690, 3));
		notes.add(new Note(1690, 4));
		notes.add(new Note(1700, 2));
		notes.add(new Note(1700, 3));
		notes.add(new Note(1700, 5));
		notes.add(new Note(1735, 3));
		notes.add(new Note(1735, 4));
		notes.add(new Note(1735, 5));
		notes.add(new Note(1759, 1));
		notes.add(new Note(1759, 2));
		notes.add(new Note(1759, 3));
		notes.add(new Note(1793, 1));
		notes.add(new Note(1793, 2));
		notes.add(new Note(1793, 4));
		notes.add(new Note(1826, 1));
		notes.add(new Note(1826, 2));
		notes.add(new Note(1826, 3));
		notes.add(new Note(1838, 1));
		notes.add(new Note(1838, 2));
		notes.add(new Note(1838, 3));
		notes.add(new Note(1873, 1));
		notes.add(new Note(1873, 3));
		notes.add(new Note(1873, 4));
		notes.add(new Note(1895, 2));
		notes.add(new Note(1895, 3));
		notes.add(new Note(1895, 4));
		notes.add(new Note(1929, 1));
		notes.add(new Note(1929, 2));
		notes.add(new Note(1929, 4));
		notes.add(new Note(1964, 2));
		notes.add(new Note(1964, 3));
		notes.add(new Note(1964, 4));
		notes.add(new Note(1975, 3));
		notes.add(new Note(1975, 4));
		notes.add(new Note(1975, 5));
		notes.add(new Note(2010, 2));
		notes.add(new Note(2015, 3));
		notes.add(new Note(2020, 4));
		notes.add(new Note(2031, 1));
		//notes.add(new Note(2031, 2));
		//notes.add(new Note(2031, 3));
		//notes.add(new Note(2031, 4));
		notes.add(new Note(2031, 5));
		notes.add(new Note(2170, 1));
		//notes.add(new Note(2170, 2));
		//notes.add(new Note(2170, 3));
		//notes.add(new Note(2170, 4));
		notes.add(new Note(2170, 5));
		notes.add(new Note(2204, 3));
		notes.add(new Note(2204, 4));
		notes.add(new Note(2239, 3));
		notes.add(new Note(2239, 4));
		notes.add(new Note(2249, 3));
		notes.add(new Note(2249, 4));
		notes.add(new Note(2284, 3));
		notes.add(new Note(2284, 4));
		notes.add(new Note(2307, 1));
		notes.add(new Note(2307, 2));
		notes.add(new Note(2341, 1));
		notes.add(new Note(2341, 2));
		notes.add(new Note(2375, 1));
		notes.add(new Note(2375, 2));
		notes.add(new Note(2385, 1));
		notes.add(new Note(2385, 2));
		notes.add(new Note(2420, 1));
		notes.add(new Note(2420, 2));
		notes.add(new Note(2444, 3));
		notes.add(new Note(2444, 4));
		notes.add(new Note(2478, 3));
		notes.add(new Note(2478, 4));
		notes.add(new Note(2512, 3));
		notes.add(new Note(2512, 4));
		notes.add(new Note(2524, 3));
		notes.add(new Note(2524, 4));
		notes.add(new Note(2558, 3));
		notes.add(new Note(2558, 4));
		notes.add(new Note(2621, 2));
		notes.add(new Note(2660, 2));
		notes.add(new Note(2666, 3));
		notes.add(new Note(2684, 3));
		notes.add(new Note(2689, 4));
		notes.add(new Note(2700, 4));
		notes.add(new Note(2706, 5));
		notes.add(new Note(2718, 3));
		notes.add(new Note(2718, 4));
		notes.add(new Note(2753, 3));
		notes.add(new Note(2753, 4));
		notes.add(new Note(2786, 3));
		notes.add(new Note(2786, 4));
		notes.add(new Note(2798, 3));
		notes.add(new Note(2798, 4));
		notes.add(new Note(2833, 3));
		notes.add(new Note(2833, 4));
		notes.add(new Note(2855, 1));
		notes.add(new Note(2855, 2));
		notes.add(new Note(2890, 1));
		notes.add(new Note(2890, 2));
		notes.add(new Note(2924, 1));
		notes.add(new Note(2924, 2));
		notes.add(new Note(2935, 1));
		notes.add(new Note(2935, 2));
		notes.add(new Note(2970, 1));
		notes.add(new Note(2970, 2));
		notes.add(new Note(2991, 3));
		notes.add(new Note(2991, 4));
		notes.add(new Note(3025, 3));
		notes.add(new Note(3025, 4));
		notes.add(new Note(3060, 3));
		notes.add(new Note(3060, 4));
		notes.add(new Note(3073, 3));
		notes.add(new Note(3073, 4));
		notes.add(new Note(3107, 3));
		notes.add(new Note(3107, 4));
		notes.add(new Note(3267, 2));
		notes.add(new Note(3267, 3));
		notes.add(new Note(3267, 4));
		notes.add(new Note(3301, 2));
		notes.add(new Note(3301, 3));
		notes.add(new Note(3301, 5));
		notes.add(new Note(3334, 2));
		notes.add(new Note(3334, 3));
		notes.add(new Note(3334, 4));
		notes.add(new Note(3346, 2));
		notes.add(new Note(3346, 3));
		notes.add(new Note(3346, 5));
		notes.add(new Note(3381, 3));
		notes.add(new Note(3381, 4));
		notes.add(new Note(3381, 5));
		notes.add(new Note(3404, 1));
		notes.add(new Note(3404, 2));
		notes.add(new Note(3404, 3));
		notes.add(new Note(3438, 1));
		notes.add(new Note(3438, 2));
		notes.add(new Note(3438, 4));
		notes.add(new Note(3471, 1));
		notes.add(new Note(3471, 2));
		notes.add(new Note(3471, 3));
		notes.add(new Note(3484, 1));
		notes.add(new Note(3484, 2));
		notes.add(new Note(3484, 3));
		notes.add(new Note(3518, 1));
		notes.add(new Note(3518, 3));
		notes.add(new Note(3518, 4));
		notes.add(new Note(3540, 2));
		notes.add(new Note(3540, 3));
		notes.add(new Note(3540, 4));
		notes.add(new Note(3575, 1));
		notes.add(new Note(3575, 2));
		notes.add(new Note(3575, 4));
		notes.add(new Note(3609, 2));
		notes.add(new Note(3609, 3));
		notes.add(new Note(3609, 4));
		notes.add(new Note(3621, 3));
		notes.add(new Note(3621, 4));
		notes.add(new Note(3621, 5));
		notes.add(new Note(3695, 1));
		notes.add(new Note(3706, 1));
		notes.add(new Note(3712, 2));
		notes.add(new Note(3723, 2));
		notes.add(new Note(3728, 3));
		notes.add(new Note(3741, 3));
		notes.add(new Note(3746, 2));
		notes.add(new Note(3757, 2));
		notes.add(new Note(3763, 3));
		notes.add(new Note(3775, 3));
		notes.add(new Note(3781, 4));
		notes.add(new Note(3792, 4));
		notes.add(new Note(3797, 5));
		notes.add(new Note(3815, 1));
		//notes.add(new Note(3815, 2));
		//notes.add(new Note(3815, 3));
		//notes.add(new Note(3815, 4));
		notes.add(new Note(3815, 5));
		notes.add(new Note(3848, 2));
		notes.add(new Note(3848, 3));
		notes.add(new Note(3848, 4));
		notes.add(new Note(3881, 2));
		notes.add(new Note(3881, 3));
		notes.add(new Note(3881, 4));
		notes.add(new Note(3893, 2));
		notes.add(new Note(3893, 3));
		notes.add(new Note(3893, 5));
		notes.add(new Note(3929, 3));
		notes.add(new Note(3929, 4));
		notes.add(new Note(3929, 5));
		notes.add(new Note(3952, 1));
		notes.add(new Note(3952, 2));
		notes.add(new Note(3952, 3));
		notes.add(new Note(3986, 1));
		notes.add(new Note(3986, 2));
		notes.add(new Note(3986, 4));
		notes.add(new Note(4020, 1));
		notes.add(new Note(4020, 2));
		notes.add(new Note(4020, 3));
		notes.add(new Note(4032, 1));
		notes.add(new Note(4032, 2));
		notes.add(new Note(4032, 3));
		notes.add(new Note(4066, 1));
		notes.add(new Note(4066, 3));
		notes.add(new Note(4066, 4));
		notes.add(new Note(4089, 2));
		notes.add(new Note(4089, 3));
		notes.add(new Note(4089, 4));
		notes.add(new Note(4122, 1));
		notes.add(new Note(4122, 2));
		notes.add(new Note(4122, 4));
		notes.add(new Note(4157, 2));
		notes.add(new Note(4157, 3));
		notes.add(new Note(4157, 4));
		notes.add(new Note(4170, 3));
		notes.add(new Note(4170, 4));
		notes.add(new Note(4170, 5));
		notes.add(new Note(4203, 2));
		notes.add(new Note(4208, 3));
		notes.add(new Note(4214, 4));
		notes.add(new Note(4226, 1));
		//notes.add(new Note(4226, 2));
		//notes.add(new Note(4226, 3));
		//notes.add(new Note(4226, 4));
		notes.add(new Note(4226, 5));
		notes.add(new Note(4363, 3));
		notes.add(new Note(4363, 4));
		notes.add(new Note(4398, 3));
		notes.add(new Note(4398, 4));
		notes.add(new Note(4432, 3));
		notes.add(new Note(4432, 4));
		notes.add(new Note(4443, 3));
		notes.add(new Note(4443, 4));
		notes.add(new Note(4477, 3));
		notes.add(new Note(4477, 4));
		notes.add(new Note(4493, 1));
		notes.add(new Note(4493, 2));
		notes.add(new Note(4535, 1));
		notes.add(new Note(4535, 2));
		notes.add(new Note(4568, 1));
		notes.add(new Note(4568, 2));
		notes.add(new Note(4581, 1));
		notes.add(new Note(4581, 2));
		notes.add(new Note(4615, 1));
		notes.add(new Note(4615, 2));
		notes.add(new Note(4637, 3));
		notes.add(new Note(4637, 4));
		notes.add(new Note(4671, 3));
		notes.add(new Note(4671, 4));
		notes.add(new Note(4706, 3));
		notes.add(new Note(4706, 4));
		notes.add(new Note(4718, 3));
		notes.add(new Note(4718, 4));
		notes.add(new Note(4752, 3));
		notes.add(new Note(4752, 4));
		notes.add(new Note(4792, 1));
		notes.add(new Note(4803, 1));
		notes.add(new Note(4809, 2));
		notes.add(new Note(4820, 2));
		notes.add(new Note(4826, 3));
		notes.add(new Note(4837, 3));
		notes.add(new Note(4843, 2));
		notes.add(new Note(4855, 2));
		notes.add(new Note(4860, 3));
		notes.add(new Note(4872, 3));
		notes.add(new Note(4878, 4));
		notes.add(new Note(4888, 4));
		notes.add(new Note(4895, 5));
		notes.add(new Note(4912, 3));
		notes.add(new Note(4912, 4));
		notes.add(new Note(4945, 3));
		notes.add(new Note(4945, 4));
		notes.add(new Note(4981, 3));
		notes.add(new Note(4981, 4));
		notes.add(new Note(4991, 3));
		notes.add(new Note(4991, 4));
		notes.add(new Note(5026, 3));
		notes.add(new Note(5026, 4));
		notes.add(new Note(5048, 1));
		notes.add(new Note(5048, 2));
		notes.add(new Note(5083, 1));
		notes.add(new Note(5083, 2));
		notes.add(new Note(5117, 1));
		notes.add(new Note(5117, 2));
		notes.add(new Note(5130, 1));
		notes.add(new Note(5130, 2));
		notes.add(new Note(5163, 1));
		notes.add(new Note(5163, 2));
		notes.add(new Note(5186, 3));
		notes.add(new Note(5186, 4));
		notes.add(new Note(5220, 3));
		notes.add(new Note(5220, 4));
		notes.add(new Note(5255, 3));
		notes.add(new Note(5255, 4));
		notes.add(new Note(5266, 3));
		notes.add(new Note(5266, 4));
		notes.add(new Note(5301, 3));
		notes.add(new Note(5301, 4));
		notes.add(new Note(5323, 1));
		//notes.add(new Note(5323, 2));
		//notes.add(new Note(5323, 3));
		//notes.add(new Note(5323, 4));
		notes.add(new Note(5323, 5));
		notes.add(new Note(5461, 3));
		notes.add(new Note(5461, 4));
		notes.add(new Note(5495, 3));
		notes.add(new Note(5495, 4));
		notes.add(new Note(5530, 3));
		notes.add(new Note(5530, 4));
		notes.add(new Note(5540, 3));
		notes.add(new Note(5540, 4));
		notes.add(new Note(5575, 3));
		notes.add(new Note(5575, 4));
		notes.add(new Note(5597, 1));
		notes.add(new Note(5597, 2));
		notes.add(new Note(5632, 1));
		notes.add(new Note(5632, 2));
		notes.add(new Note(5666, 1));
		notes.add(new Note(5666, 2));
		notes.add(new Note(5678, 1));
		notes.add(new Note(5678, 2));
		notes.add(new Note(5712, 1));
		notes.add(new Note(5712, 2));
		notes.add(new Note(5735, 3));
		notes.add(new Note(5735, 4));
		notes.add(new Note(5770, 3));
		notes.add(new Note(5770, 4));
		notes.add(new Note(5803, 3));
		notes.add(new Note(5803, 4));
		notes.add(new Note(5815, 3));
		notes.add(new Note(5815, 4));
		notes.add(new Note(5850, 3));
		notes.add(new Note(5850, 4));
		notes.add(new Note(5890, 1));
		notes.add(new Note(5900, 1));
		notes.add(new Note(5906, 2));
		notes.add(new Note(5918, 2));
		notes.add(new Note(5922, 3));
		notes.add(new Note(5935, 3));
		notes.add(new Note(5941, 2));
		notes.add(new Note(5952, 2));
		notes.add(new Note(5957, 3));
		notes.add(new Note(5968, 3));
		notes.add(new Note(5975, 4));
		notes.add(new Note(5986, 4));
		notes.add(new Note(5991, 5));
		notes.add(new Note(6008, 3));
		notes.add(new Note(6008, 4));
		notes.add(new Note(6041, 3));
		notes.add(new Note(6041, 4));
		notes.add(new Note(6077, 3));
		notes.add(new Note(6077, 4));
		notes.add(new Note(6088, 3));
		notes.add(new Note(6088, 4));
		notes.add(new Note(6123, 3));
		notes.add(new Note(6123, 4));
		notes.add(new Note(6145, 1));
		notes.add(new Note(6145, 2));
		notes.add(new Note(6180, 1));
		notes.add(new Note(6180, 2));
		notes.add(new Note(6215, 1));
		notes.add(new Note(6215, 2));
		notes.add(new Note(6226, 1));
		notes.add(new Note(6226, 2));
		notes.add(new Note(6261, 1));
		notes.add(new Note(6261, 2));
		notes.add(new Note(6283, 3));
		notes.add(new Note(6283, 4));
		notes.add(new Note(6317, 3));
		notes.add(new Note(6317, 4));
		notes.add(new Note(6351, 3));
		notes.add(new Note(6351, 4));
		notes.add(new Note(6363, 3));
		notes.add(new Note(6363, 4));
		notes.add(new Note(6397, 3));
		notes.add(new Note(6397, 4));
		notes.add(new Note(6557, 1));
		notes.add(new Note(6557, 2));
		notes.add(new Note(6557, 3));
		notes.add(new Note(6557, 4));
		notes.add(new Note(6557, 5));
		notes.add(new Note(6592, 2));
		notes.add(new Note(6592, 3));
		notes.add(new Note(6592, 5));
		notes.add(new Note(6625, 2));
		notes.add(new Note(6625, 3));
		notes.add(new Note(6625, 4));
		notes.add(new Note(6637, 2));
		notes.add(new Note(6637, 3));
		notes.add(new Note(6637, 5));
		notes.add(new Note(6672, 3));
		notes.add(new Note(6672, 4));
		notes.add(new Note(6672, 5));
		notes.add(new Note(6695, 1));
		notes.add(new Note(6695, 2));
		notes.add(new Note(6695, 3));
		notes.add(new Note(6728, 1));
		notes.add(new Note(6728, 2));
		notes.add(new Note(6728, 4));
		notes.add(new Note(6763, 1));
		notes.add(new Note(6763, 2));
		notes.add(new Note(6763, 3));
		notes.add(new Note(6775, 1));
		notes.add(new Note(6775, 2));
		notes.add(new Note(6775, 3));
		notes.add(new Note(6810, 1));
		notes.add(new Note(6810, 3));
		notes.add(new Note(6810, 4));
		notes.add(new Note(6831, 2));
		notes.add(new Note(6831, 3));
		notes.add(new Note(6831, 4));
		notes.add(new Note(6866, 1));
		notes.add(new Note(6866, 2));
		notes.add(new Note(6866, 4));
		notes.add(new Note(6900, 2));
		notes.add(new Note(6900, 3));
		notes.add(new Note(6900, 4));
		notes.add(new Note(6912, 3));
		notes.add(new Note(6912, 4));
		notes.add(new Note(6912, 5));
		notes.add(new Note(6947, 2));
		notes.add(new Note(6953, 3));
		notes.add(new Note(6957, 4));
		notes.add(new Note(6968, 1));
		notes.add(new Note(6981, 1));
		notes.add(new Note(6986, 2));
		notes.add(new Note(6997, 2));
		notes.add(new Note(7003, 3));
		notes.add(new Note(7015, 3));
		notes.add(new Note(7020, 4));
		notes.add(new Note(7031, 4));
		notes.add(new Note(7038, 2));
		notes.add(new Note(7048, 2));
		notes.add(new Note(7055, 3));
		notes.add(new Note(7067, 3));
		notes.add(new Note(7072, 4));
		notes.add(new Note(7083, 4));
		notes.add(new Note(7088, 5));
		notes.add(new Note(7105, 1));
		//notes.add(new Note(7105, 2));
		//notes.add(new Note(7105, 3));
		//notes.add(new Note(7105, 4));
		notes.add(new Note(7105, 5));
		notes.add(new Note(7141, 2));
		//notes.add(new Note(7141, 3));
		notes.add(new Note(7141, 5));
		notes.add(new Note(7175, 2));
		notes.add(new Note(7175, 3));
		notes.add(new Note(7175, 4));
		notes.add(new Note(7185, 2));
		notes.add(new Note(7185, 3));
		notes.add(new Note(7185, 5));
		notes.add(new Note(7221, 3));
		//notes.add(new Note(7221, 4));
		notes.add(new Note(7221, 5));
	}
}