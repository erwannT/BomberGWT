package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.test.client.resources.SpritesRessource;

public class Bomb {

	private final static int CONSTANTES_TIMEOUT = 300;

	private enum State {
		WAIT_FOR_EXPLOSION, EXPLOSION
	}

	private State state;

	int pixelXImg = 0;

	int pixelYImg = 34;

	private ImageElement image;

	private Timer timer;

	public Bomb() {
		image = ImageElement.as(new Image(SpritesRessource.INSTANCE.mapBomberStage1()).getElement());

		timer = new Timer() {

			@Override
			public void run() {

				switch (state) {
				case WAIT_FOR_EXPLOSION:
					waitExplosion();
					break;

				default:
					break;
				}
			}
		};
	}

	public void startWaitingExplosion() {

		if (state != State.WAIT_FOR_EXPLOSION) {
			state = State.WAIT_FOR_EXPLOSION;

			waitExplosion();
		}

	}

	public void waitExplosion() {

		if (State.WAIT_FOR_EXPLOSION != state) {
			stopCurrentAnimation();
		}

		pixelXImg += 17;

		if (pixelXImg > 51) {
			pixelXImg = 0;
		}

		timer.schedule(CONSTANTES_TIMEOUT);
	}

	public void startExplosion() {

	}

	public void draw(Context2d context) {
		context.drawImage(image, pixelXImg, pixelYImg, 16, 16, 200, 200, 16, 16);
	}

	private void stopCurrentAnimation() {
		timer.cancel();
	}

}
