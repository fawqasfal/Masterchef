package com.masterchef;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Floor extends Rectangle {
	
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	public Floor() {
		super();
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		body = Registry.world.createBody(bodyDef);
		
		ps = new PolygonShape();
		ps.setAsBox(20.0f, 0.2f);
		ps.setRadius(0.0f);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.4f;
		fd.restitution = 0.6f;
		
		f = body.createFixture(fd);
		
		ps.dispose();
	}

}
