package com.test.client.physics;

import com.google.gwt.user.client.Timer;
import com.test.client.level.Level;

public class BomberManPhysics implements IPhysics {

	private IPhysics physics;

	private int maxStrength = 3;

	private int strength = 0;

	private Timer timerExplosion;

	private Timer timerStrengthExplosion;

	public BomberManPhysics(Level level) {
		physics = new Physics(level);

		timerExplosion = new Timer() {

			@Override
			public void run() {
				startExplostion();
			}
		};

		timerExplosion.schedule(5000);

		timerStrengthExplosion = new Timer() {

			@Override
			public void run() {
				strength++;

				if (strength > maxStrength) {
					strength = maxStrength;
				}

			}
		};
	}

	public void startExplostion() {
		strength = 1;
		timerStrengthExplosion.schedule(200);
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

	}

	@Override
	public void stop() {
		physics.stop();

	}

	@Override
	public boolean isMovingRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMovingLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMovingUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMovingDown() {
		// TODO Auto-generated method stub
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

}
