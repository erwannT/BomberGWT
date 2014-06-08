package com.test.client.physics;

import com.test.client.level.Level;

public class Physics implements IPhysics {

	private Level level;

	private int xSpeed;
	private int ySpeed;

	private int pixelXPosition = 16;
	private int pixelYPosition = 16;

	public Physics(Level level) {
		this.level = level;
	}

	@Override
	public void moveRight() {
		ySpeed = 0;
		xSpeed = 1;
	}

	@Override
	public void moveLeft() {
		ySpeed = 0;
		xSpeed = -1;
	}

	@Override
	public void moveUp() {
		ySpeed = -1;
		xSpeed = 0;
	}

	@Override
	public void moveDown() {
		ySpeed = 1;
		xSpeed = 0;
	}

	@Override
	public boolean canMove() {

		// move right
		if (xSpeed > 0) {
			return !level.getCollisionData(pixelXPosition + 16 + xSpeed - 1, pixelYPosition + 16 + 1)
					&& !level.getCollisionData(pixelXPosition + 16 + xSpeed - 1, pixelYPosition + 32 - 1);
		}
		// move left
		else if (xSpeed < 0) {
			return !level.getCollisionData(pixelXPosition + xSpeed + 1, pixelYPosition + 16 + 1)
					&& !level.getCollisionData(pixelXPosition + xSpeed + 1, pixelYPosition + 32 - 1);
		}
		// move up
		else if (ySpeed < 0) {
			return !level.getCollisionData(pixelXPosition + 1, pixelYPosition + ySpeed + 16 + 1)
					&& !level.getCollisionData(pixelXPosition + 16 - 1, pixelYPosition + ySpeed + 16 + 1);
		}
		// move down
		else if (ySpeed > 0) {
			return !level.getCollisionData(pixelXPosition + 1, pixelYPosition + 32 + ySpeed - 1)
					&& !level.getCollisionData(pixelXPosition + 16 - 1, pixelYPosition + 32 + ySpeed - 1);
		}
		return false;

	}

	@Override
	public void apply() {

		if (canMove()) {
			pixelXPosition += xSpeed;
			pixelYPosition += ySpeed;
		} else {
			xSpeed = 0;
			ySpeed = 0;
		}
	}

	@Override
	public void stop() {
		xSpeed = 0;
		ySpeed = 0;
	}

	@Override
	public boolean isMovingRight() {
		return xSpeed > 0;
	}

	@Override
	public boolean isMovingLeft() {

		return xSpeed < 0;
	}

	@Override
	public boolean isMovingUp() {
		return ySpeed > 0;
	}

	@Override
	public boolean isMovingDown() {
		return ySpeed < 0;
	}

	public int getPixelXPosition() {
		return pixelXPosition;
	}

	public int getPixelYPosition() {
		return pixelYPosition;
	}
}
