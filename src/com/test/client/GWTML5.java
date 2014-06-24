package com.test.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.animation.client.AnimationScheduler.AnimationHandle;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.test.client.level.Level;
import com.test.client.physics.BombPhysics;
import com.test.client.physics.IBombPhysics;
import com.test.client.physics.IPhysics;
import com.test.client.physics.Physics;
import com.test.client.resources.Resources;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTML5 implements EntryPoint, KeyDownHandler, KeyUpHandler, AnimationCallback {

	private Movement movBomber = Movement.STOP;

	private AnimationScheduler create = AnimationScheduler.get();

	private HTML html = new HTML();
	private HTML htmlNextPos = new HTML();

	private Bomberman bomberman;
	private IPhysics bomberManPhysics;

	private Map<IBombPhysics, Bomb> mapBbomb = new HashMap<IBombPhysics, Bomb>();

	private EventBus eventBus = new SimpleEventBus();

	private Level tiles;

	private Context2d context;

	/**
	 * This is the entry point method.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		Canvas canvas = Canvas.createIfSupported();
		RootPanel.get().add(html);
		RootPanel.get().add(htmlNextPos);
		RootPanel.get().add(canvas);
		canvas.getCanvasElement().setHeight(Window.getClientHeight());
		canvas.getCanvasElement().setWidth(Window.getClientWidth());

		context = canvas.getContext2d();

		// create tiles
		tiles = new Level(eventBus, Resources.INSTANCE.synchronous(), context);
		tiles.draw();

		bomberManPhysics = new Physics(tiles);

		bomberman = new Bomberman();
		bomberman.draw(context, bomberManPhysics.getPixelXPosition(), bomberManPhysics.getPixelYPosition());

		IBombPhysics bombPhysics = new BombPhysics(tiles);

		Bomb bomb = new Bomb();

		mapBbomb.put(bombPhysics, bomb);

		bomb.draw(context, bombPhysics);

		// init keyboard handler
		canvas.addKeyUpHandler(this);
		canvas.addKeyDownHandler(this);

		anime();

		// init animation frame
		AnimationHandle requestAnimationFrame = create.requestAnimationFrame(this, context.getCanvas());
	}

	@Override
	public void onKeyDown(KeyDownEvent e) {
		if (e.getNativeKeyCode() == 37) {
			movBomber = Movement.LEFT;
			e.preventDefault();
			e.stopPropagation();
		}
		if (e.getNativeKeyCode() == 38) {
			movBomber = Movement.UP;
			e.preventDefault();
			e.stopPropagation();
		}
		if (e.getNativeKeyCode() == 39) {
			movBomber = Movement.RIGHT;
			e.preventDefault();
			e.stopPropagation();
		}
		if (e.getNativeKeyCode() == 40) {
			movBomber = Movement.DOWN;
			e.preventDefault();
			e.stopPropagation();
		}
		execute(0);

	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		movBomber = Movement.STOP;
	}

	double timestamp = 0;

	@Override
	public void execute(double timestamp) {

		if (this.timestamp == 0) {
			this.timestamp = timestamp;
		}

		if (timestamp - this.timestamp > 020) {

			anime();
			this.timestamp = timestamp;

		}
		create.requestAnimationFrame(this);

	}

	public void anime() {
		switch (movBomber) {
		case DOWN:
			bomberManPhysics.moveDown();
			bomberman.startMoveDown();
			break;
		case LEFT:
			bomberManPhysics.moveLeft();
			bomberman.startMoveLeft();
			break;
		case UP:
			bomberManPhysics.moveUp();
			bomberman.startMoveUp();
			break;
		case RIGHT:
			bomberManPhysics.moveRight();
			bomberman.startMoveRight();
			break;
		case STOP:
			bomberManPhysics.stop();
			bomberman.stop();

		default:
			bomberman.stop();
			break;
		}

		tiles.draw();
		bomberManPhysics.apply();

		html.setText(" x : " + bomberManPhysics.getPixelXPosition() + "; y : " + bomberManPhysics.getPixelYPosition());
		bomberman.draw(context, bomberManPhysics.getPixelXPosition(), bomberManPhysics.getPixelYPosition());

		for (Entry<IBombPhysics, Bomb> entry : mapBbomb.entrySet()) {
			// TODO n'appeler qu'une seule fois
			if (entry.getKey().isExplosionStarted()) {
				entry.getValue().startExplosion();
			}

			if (entry.getKey().isExplosionFinished()) {
				mapBbomb.remove(entry.getKey());
			}

		}

		for (Entry<IBombPhysics, Bomb> entry : mapBbomb.entrySet()) {
			entry.getKey().apply();
			entry.getValue().draw(context, entry.getKey());
		}

	}
}
