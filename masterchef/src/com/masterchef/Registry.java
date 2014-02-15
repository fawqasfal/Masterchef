package com.masterchef;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.masterchef.util.Point;

public class Registry {
	
	// used for scaling between box2d world and the game world
	// box2d world is significantly smaller
	public static Point b2dScale = new Point(10.0f, 10.0f);
	
	public static World world;
	public static Vector2 gravity = new Vector2(0, -10.0f);
	
	public Registry() {
		//b2dScale = new Point(10.0f, 10.0f);
		//gravity = new Vector2(0, -10.0f);
	}
}
