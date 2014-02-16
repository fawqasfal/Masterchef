package com.masterchef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
public class Food extends Box {
	public final String name;
	public final int RAW = 0;
	public final int COOKED = 1;
	public final int BURNT = 2;
	public final int ROTTEN = 3;
	BodyDef bodyDef;
	Body body;
	boolean isOn = true;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	public Food(String name, Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		this.name = name;
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 10);
		body = Registry.world.createBody(bodyDef);
		ps = new PolygonShape();
		//ps.setAsBox(0.6f, 0.6f);
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(1.6f, 0);
		vertices[2] = new Vector2(1.6f, 1.6f);
		vertices[3] = new Vector2(0, 1.6f);
		ps.set(vertices);
		ps.setRadius(0.0f);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.4f;
		fd.restitution = .6f;
		
		f = body.createFixture(fd);
		
		ps.dispose();
	}

	public String getName() {
		return this.name;
	}
	


}
