package com.masterchef;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Food extends Rectangle {
	public final String name;
	public final String picFile;
	public final int RAW = 0;
	public final int COOKED = 1;
	public final int BURNT = 2;
	public final int ROTTEN = 3;
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	public Food(String name, String picFile) {
		super();
		this.name = name;
		this.picFile = picFile;
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(20, 10);
		
		body = Registry.world.createBody(bodyDef);
		
		ps = new PolygonShape();
		ps.setAsBox(1.6f, 1.6f);
		ps.setRadius(0.0f);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.4f;
		fd.restitution = 0.6f;
		
		f = body.createFixture(fd);
		
		ps.dispose();
	}

	public String getName() {
		return this.name;
	}
	
	public String getPic() {
		return this.picFile;
	}

}
