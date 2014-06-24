package com.test.client.physics;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.test.client.level.Level;

public class BombPhysics implements IBombPhysics {

	private final static int STRENGTH_MAX = 15;

	private final static int WAIT_FOR_EXPLOSION = 1000;

	private final static int WAIT_FOR_END_EXPLOSION = 500;

	private final static int INTERVAL_BETWEEN_INC_STRENGTH = 50;

	private boolean explosionStarted = false;

	private boolean explosionFinished = false;

	public enum Fire {

		// center
		CENTER(102, 51),
		// UP
		UP(68, 51), END_UP(0, 51),
		// DOWN
		DOWN(68, 51), END_DOWN(17, 51),
		// LEFT
		LEFT(85, 51), END_LEFT(34, 51),
		// RIGHT
		RIGHT(85, 51), END_RIGHT(51, 51);

		private int pixelXPosition;
		private int pixelYPosition;

		Fire(int pixelXPosition, int pixelYPosition) {
			this.pixelXPosition = pixelXPosition;
			this.pixelYPosition = pixelYPosition;
		}

		public int getPixelXPosition() {
			return pixelXPosition;
		}

		public int getPixelYPosition() {
			return pixelYPosition;
		}

	}

	private final static int PIXEL_TILE_WIDTH = 16;
	private final static int PIXEL_TILE_HEIGHT = 16;

	private final static int LAYER_WIDTH = 16;

	private IPhysics physics;

	private int maxStrengthUp = STRENGTH_MAX;
	private int maxStrengthDown = STRENGTH_MAX;
	private int maxStrengthRight = STRENGTH_MAX;
	private int maxStrengthLeft = STRENGTH_MAX;

	private int strengthUp = 0;
	private int strengthDown = 0;
	private int strengthLeft = 0;
	private int strengthRight = 0;

	private boolean explosionUpFinished = false;
	private boolean explosionDownFinished = false;
	private boolean explosionLeftFinished = false;
	private boolean explosionRightFinished = false;

	private Timer timerExplosion;

	private Timer timerStrengthExplosion;

	private Timer timerStrengthWaitForEndExplosion;

	private Map<Integer, Fire> collisionData = new HashMap<>(256);

	public BombPhysics(Level level) {

		physics = new Physics(level);

		timerExplosion = new Timer() {

			@Override
			public void run() {
				startExplostion();
			}
		};

		timerExplosion.schedule(WAIT_FOR_EXPLOSION);

		timerStrengthExplosion = new Timer() {

			@Override
			public void run() {
				if (maxStrengthUp != strengthUp) {
					strengthUp++;
				}
				if (maxStrengthDown != strengthDown) {
					strengthDown++;
				}
				if (maxStrengthRight != strengthRight) {
					strengthRight++;
				}
				if (maxStrengthLeft != strengthLeft) {
					strengthLeft++;
				}

				if (!isExplosionMax()) {
					timerStrengthExplosion.schedule(INTERVAL_BETWEEN_INC_STRENGTH);
				} else {
					timerStrengthWaitForEndExplosion.schedule(WAIT_FOR_END_EXPLOSION);
				}
			}
		};

		timerStrengthWaitForEndExplosion = new Timer() {

			@Override
			public void run() {
				explosionFinished = true;
			}
		};
	}

	private void startExplostion() {
		explosionStarted = true;
		timerStrengthExplosion.schedule(INTERVAL_BETWEEN_INC_STRENGTH);
	}

	@Override
	public void moveRight() {
		physics.moveRight();
	}

	@Override
	public void moveLeft() {
		physics.moveLeft();

	}

	@Override
	public void moveUp() {
		physics.moveUp();

	}

	@Override
	public void moveDown() {
		physics.moveDown();

	}

	@Override
	public boolean canMove() {
		return physics.canMove();
	}

	@Override
	public void apply() {
		physics.apply();

		int xPos = getPixelXPosition() / PIXEL_TILE_WIDTH;
		int yPos = getPixelYPosition() / PIXEL_TILE_HEIGHT;

		int position = (xPos) + LAYER_WIDTH * yPos;

		collisionData = new HashMap<>();

		collisionData.put(position, Fire.CENTER);

		for (int i = 1; i <= strengthUp; i++) {

			// up
			int upPosition = position - (i * 16);
			if (upPosition > 0) {

				if (upPosition / 16 == 0) {
					collisionData.put(upPosition, Fire.END_UP);
					explosionUpFinished = true;
					maxStrengthUp = i;
				} else if (i == maxStrengthUp) {
					collisionData.put(upPosition, Fire.END_UP);
				} else if (i == strengthUp) {
					collisionData.put(upPosition, Fire.END_UP);
				} else {
					collisionData.put(upPosition, Fire.UP);
				}
			}
		}

		for (int i = 1; i <= strengthDown; i++) {
			// down
			int downPosition = position + (i * 16);
			if (downPosition < 256) {

				if (downPosition >= 240) {
					collisionData.put(downPosition, Fire.END_DOWN);
					explosionDownFinished = true;
					maxStrengthDown = i;
				} else if (i == maxStrengthDown) {
					collisionData.put(downPosition, Fire.END_DOWN);
				} else if (i == strengthDown) {
					collisionData.put(downPosition, Fire.END_DOWN);
				} else {
					collisionData.put(downPosition, Fire.DOWN);
				}
			}
		}

		for (int i = 1; i <= strengthLeft; i++) {

			// left
			int leftPosition = position - i;
			if (leftPosition >= 0) {
				if (leftPosition % 16 == 0) {
					collisionData.put(leftPosition, Fire.END_LEFT);
					explosionLeftFinished = true;
					maxStrengthLeft = i;
				} else if (i == maxStrengthLeft) {
					collisionData.put(leftPosition, Fire.END_LEFT);
				} else if (i == strengthLeft) {
					collisionData.put(leftPosition, Fire.END_LEFT);
				} else {
					collisionData.put(leftPosition, Fire.LEFT);
				}
			}
		}

		for (int i = 1; i <= strengthRight; i++) {

			// right
			int rightPosition = position + i;
			if (rightPosition < 256) {
				if (rightPosition % 16 == 15) {
					collisionData.put(rightPosition, Fire.END_RIGHT);
					explosionRightFinished = true;
					maxStrengthRight = i;
				} else if (i == maxStrengthRight) {
					collisionData.put(rightPosition, Fire.END_RIGHT);
				} else if (i == strengthRight) {
					collisionData.put(rightPosition, Fire.END_RIGHT);
				} else {
					collisionData.put(rightPosition, Fire.RIGHT);
				}
			}
		}
	}

	@Override
	public void stop() {
		physics.stop();
	}

	@Override
	public boolean isMovingRight() {
		return false;
	}

	@Override
	public boolean isMovingLeft() {
		return false;
	}

	@Override
	public boolean isMovingUp() {
		return false;
	}

	@Override
	public boolean isMovingDown() {
		return false;
	}

	@Override
	public int getPixelXPosition() {
		return physics.getPixelXPosition();
	}

	@Override
	public int getPixelYPosition() {
		return physics.getPixelYPosition();
	}

	public Map<Integer, Fire> getCollisionData() {
		return collisionData;
	}

	public boolean isExplosionMax() {
		return (explosionDownFinished || strengthDown == maxStrengthDown) && (explosionUpFinished || strengthUp == maxStrengthUp)
				&& (explosionRightFinished || strengthRight == maxStrengthRight) && (explosionLeftFinished || strengthLeft == maxStrengthLeft);
	}

	@Override
	public boolean isExplosionStarted() {
		// TODO Auto-generated method stub
		return explosionStarted;
	}

	public boolean isExplosionFinished() {
		return explosionFinished;
	}

}
