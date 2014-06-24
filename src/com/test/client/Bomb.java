package com.test.client;

import java.util.Map.Entry;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.test.client.physics.BombPhysics.Fire;
import com.test.client.physics.IBombPhysics;
import com.test.client.resources.SpritesRessource;

public class Bomb {

	private final static int CONSTANTES_TIMEOUT = 300;

	private enum State {
		WAIT_FOR_EXPLOSION, EXPLOSION
	}

	// nb d'item par hauteur sur le layer
	private final static int LAYER_HEIGHT = 16;
	private final static int LAYER_WIDTH = 16;

	private final static int PIXEL_BOMB_X = 16;
	private final static int PIXEL_BOMB_Y = 16;

	private State state = State.WAIT_FOR_EXPLOSION;

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

		timer.schedule(CONSTANTES_TIMEOUT);
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

		stopCurrentAnimation();
		state = State.EXPLOSION;
	}

	public void draw(Context2d context, IBombPhysics physics) {

		if (state != State.EXPLOSION) {
			context.drawImage(image, pixelXImg, pixelYImg, 16, 16, physics.getPixelXPosition(), physics.getPixelYPosition(), 16, 16);
		} else {

			for (Entry<Integer, Fire> entry : physics.getCollisionData().entrySet()) {

				int i = entry.getKey();
				Fire fire = entry.getValue();

				int displayY = i / LAYER_HEIGHT;
				int displayX = i % LAYER_WIDTH;

				if (fire != null) {
					context.drawImage(image, fire.getPixelXPosition(), fire.getPixelYPosition(), 16, 16, displayX * PIXEL_BOMB_X, displayY * PIXEL_BOMB_Y,
							PIXEL_BOMB_X, PIXEL_BOMB_Y);
				}
				i++;
			}
		}
	}

	private void stopCurrentAnimation() {
		if (timer != null) {
			timer.cancel();
		}
	}

}
