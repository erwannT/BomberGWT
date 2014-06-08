package com.test.client.physics;

public interface IPhysics {

	void moveRight();

	void moveLeft();

	void moveUp();

	void moveDown();

	boolean canMove();

	void apply();

	void stop();

	boolean isMovingRight();

	boolean isMovingLeft();

	boolean isMovingUp();

	boolean isMovingDown();

	int getPixelXPosition();

	int getPixelYPosition();

}
