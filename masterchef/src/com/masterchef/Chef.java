package com.masterchef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.*;

public class Chef extends Box {
	
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	RevoluteJointDef jd;
	Joint j;
	
	BodyDef circleBD;
	Body circle;
	CircleShape cs;
	
	public Chef(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(10.0f, 5.0f);
		
		body = Registry.world.createBody(bodyDef);
		
		ps = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(1.6f, 0);
		vertices[2] = new Vector2(1.6f, 3.2f);
		vertices[3] = new Vector2(0, 3.2f);
		ps.set(vertices);
		//ps.setAsBox(1.6f, 1.6f);
		ps.setRadius(0.0f);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.96f;
		fd.restitution = 0.2f;
		
		f = body.createFixture(fd);
		
		ps.dispose();
		
		circleBD = new BodyDef();
		circleBD.type = BodyType.DynamicBody;
		circleBD.position.set(11.0f, 8.8f);
		
		circle = Registry.world.createBody(circleBD);
		
		cs = new CircleShape();
		cs.setRadius(1.0f);
		
		fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.5f;
		fd.friction = 0.96f;
		fd.restitution = 0.2f;
		
		f = circle.createFixture(fd);
		
		cs.dispose();
		
		
		jd = new RevoluteJointDef();
		jd.initialize(body, circle, new Vector2(body.getWorldCenter().x, body.getWorldCenter().y));
		jd.collideConnected = false;
		jd.enableLimit = true;
		jd.lowerAngle = 0.0f;
		jd.upperAngle = 0.1f;
		j = Registry.world.createJoint(jd);
		
	}
}