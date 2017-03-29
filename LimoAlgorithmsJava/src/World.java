import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class World extends Application {
	public final int WIDTH = 750;
	public final int HEIGHT = 500;
	public final int FPS = 900;

	public GraphicsContext gc;

	int callers;
	double chance;

	ArrayList<Limo> limos = new ArrayList<Limo>();

	void initialize() {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter number of callers");
		callers = sc.nextInt();
		System.out.println("Enter % chance of call per update : DOUBLE");
		chance = sc.nextDouble();
		Limo l = new FCFSLimo(WIDTH / 2, HEIGHT / 2);
		limos.add(l);
		l = new ClosestFirstLimo(WIDTH / 2, HEIGHT / 2);
		limos.add(l);
		l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 0.1, Color.GREEN);
		limos.add(l);
		l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 1, Color.CORNFLOWERBLUE);
		limos.add(l);
		l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 100, Color.HOTPINK);
		limos.add(l);

	}

	void update() {
		for (Limo l : limos) {
			l.update();
			l.updateData();
			// l.draw();
			if (callers <= 0) {
				l.noMoreCallers = true;
			}
		}
		if (Math.random() * 100 < chance && callers > 0) {
			Caller c = new Caller();
			for (Limo l : limos) {
				if (l instanceof FCFSLimo) // need this for all algorithms
					((FCFSLimo) l).addCaller(c);
				if (l instanceof ClosestFirstLimo)
					((ClosestFirstLimo) l).addCaller(c);
				if (l instanceof GreedyCF)
					((GreedyCF) l).addCaller(c);
			}
			callers--;
		}

	}

	public static void start(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("Limo Sevice");

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();

		initialize();

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			update();
			// draw frame
			render(gc);
		});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();

	}

	void render(GraphicsContext gc) {
		// Clear background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		for (Limo l : limos) {
			gc.setFill(l.color);
			gc.fillRect(l.x - (l.width / 2), l.y - (l.height / 2), l.width, l.height);
			for (Caller c : l.callerList) {
				gc.fillOval(c.x - (c.r / 2), c.y - (c.r / 2), c.r, c.r);
				gc.fillText("x", c.destX, c.destY);
			}
			for (Caller c : l.passengerList) {
				// gc.fillOval(c.x - (c.r / 2), c.y - (c.r / 2), c.r, c.r);
				gc.fillText("x", c.destX, c.destY);
			}

		}

	}
}