package com.test.client.physics;

import java.util.Map;

import com.test.client.physics.BombPhysics.Fire;

public interface IBombPhysics extends IPhysics {

	Map<Integer, Fire> getCollisionData();

	boolean isExplosionStarted();

	boolean isExplosionMax();

	boolean isExplosionFinished();

}
