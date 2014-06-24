package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.test.client.resources.SpritesRessource;

public class Bomberman {

	private final int CONSTANTES_TIMEOUT = 100;

	public enum Movement {
		MOVE_RIGHT, MOVE_LEFT, MOVE_UP, MOVE_DOWN, STOP
	}

	private Movement mov;
	private final SpritesRessource res = GWT.create(SpritesRessource.class);
	private Image perso = new Image(res.bomberman());
	private ImageElement image = ImageElement.as(perso.getElement());

	/*
	 * Default display : stop position
	 */
	private int xImg = 0;
	private int yImg = 64;

	private Timer timer;

	public Bomberman() {

		timer = new Timer() {

			@Override
			public void run() {
				if (mov != null) {

					switch (mov) {
					case MOVE_RIGHT:
						moveRight();
						break;
					case MOVE_LEFT:
						moveLeft();
						break;
					case MOVE_UP:
						moveUp();
						break;
					case MOVE_DOWN:
						moveDown();
						break;
					}
				}
			}
		};

		timer.schedule(CONSTANTES_TIMEOUT);

	}

	public void startMoveRight() {
		if (mov != Movement.MOVE_RIGHT) {
			mov = Movement.MOVE_RIGHT;
			moveRight();
		}
	}

	public void moveRight() {
		if (mov != Movement.MOVE_RIGHT) {
			stopCurrentAnimation();
		}
		yImg = 32;
		if (xImg == 16) {
			xImg = 32;
		} else {
			xImg = 16;
		}

		timer.schedule(CONSTANTES_TIMEOUT);
	}

	public void startMoveLeft() {
		if (mov != Movement.MOVE_LEFT) {
			mov = Movement.MOVE_LEFT;
			moveLeft();
		}
	}

	public void moveLeft() {
		if (mov != Movement.MOVE_LEFT) {
			stopCurrentAnimation();
		}
		mov = Movement.MOVE_LEFT;
		yImg = 96;
		if (xImg == 16) {
			xImg = 32;
		} else {
			xImg = 16;
		}
		timer.schedule(CONSTANTES_TIMEOUT);
	}

	public void startMoveUp() {
		if (mov != Movement.MOVE_UP) {
			mov = Movement.MOVE_UP;
			moveUp();
		}
	}

	public void moveUp() {
		if (mov != Movement.MOVE_UP) {
			stopCurrentAnimation();
		}
		mov = Movement.MOVE_UP;
		yImg = 0;
		if (xImg == 16) {
			xImg = 32;
		} else {
			xImg = 16;
		}
		timer.schedule(CONSTANTES_TIMEOUT);
	}

	public void startMoveDown() {
		if (mov != Movement.MOVE_DOWN) {
			mov = Movement.MOVE_DOWN;
			moveDown();
		}
	}

	public void moveDown() {

		if (mov != Movement.MOVE_DOWN) {
			stopCurrentAnimation();
		}

		mov = Movement.MOVE_DOWN;
		yImg = 64;
		if (xImg == 16) {
			xImg = 32;
		} else {
			xImg = 16;
		}
		timer.schedule(CONSTANTES_TIMEOUT);
	}

	public void stop() {
		xImg = 0;
		mov = Movement.STOP;
		stopCurrentAnimation();
	}

	private void stopCurrentAnimation() {
		timer.cancel();
	}

	/**
	 * Raffraichit le personnage sur le canvas
	 */
	public void draw(Context2d context, int x, int y) {
		context.drawImage(image, xImg, yImg, 16, 32, x, y, 16, 32);

	}
}
