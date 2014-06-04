package com.test.client;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTML5 implements EntryPoint, KeyDownHandler, KeyUpHandler, AnimationCallback {

	private Movement movBomber = Movement.STOP;

	private AnimationScheduler create = AnimationScheduler.get();

	private HTML html = new HTML();
	private HTML htmlNextPos = new HTML();

	private Bomberman bomberman;

	private EventBus eventBus = new SimpleEventBus();

	private Turtle turtle;

	private Tiles tiles;

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

		final Context2d context = canvas.getContext2d();

		// display a turle
		turtle = new Turtle(context);
		turtle.walk();

		bomberman = new Bomberman(context);

		// create tiles
		tiles = new Tiles(eventBus, Resources.INSTANCE.synchronous(), context);

		// init keyboard handler
		canvas.addKeyUpHandler(this);
		canvas.addKeyDownHandler(this);

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

			int xBomberMan = Math.round((float) bomberman.getXPos() / 16);

			int yBomberMan = Math.round((float) (bomberman.getYPos() + 16) / 16);

			int indexData = xBomberMan + yBomberMan * 16;

			int currentTiles = tiles.getElement(xBomberMan, yBomberMan);
			bomberman.clear();
			html.setText("X : " + Math.round(xBomberMan) + " y : " + Math.round(yBomberMan) + ", indexData : " + indexData + "  coord tiltes : " + currentTiles);
			int nextElt = 0;
			switch (movBomber) {
			case DOWN:
				nextElt = tiles.getElement(xBomberMan, yBomberMan + 1);
				if (nextElt == 0) {
					bomberman.walk(movBomber);
				} else {
					bomberman.walk(Movement.STOP);
				}
				break;
			case LEFT:
				nextElt = tiles.getElement(xBomberMan - 1, yBomberMan);
				if (nextElt == 0) {
					bomberman.walk(movBomber);
				} else {
					bomberman.walk(Movement.STOP);
				}
				break;

			case UP:
				nextElt = tiles.getElement(xBomberMan, yBomberMan - 1);
				if (nextElt == 0) {
					bomberman.walk(movBomber);
				} else {
					bomberman.walk(Movement.STOP);
				}
				break;
			case RIGHT:
				nextElt = tiles.getElement(xBomberMan + 1, yBomberMan);
				if (nextElt == 0) {
					bomberman.walk(movBomber);
				} else {
					bomberman.walk(Movement.STOP);
				}
				break;
			default:
				bomberman.walk(Movement.STOP);
				break;
			}

			tiles.refresh();
			bomberman.refresh();

			htmlNextPos.setText(" futur pos : " + nextElt);
			this.timestamp = timestamp;

			// movBomber = Movement.STOP;

		}
		create.requestAnimationFrame(this);

	}
}
