package com.masterchef;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game implements ApplicationListener, InputProcessor {
	
	Floor floor;
	
	Chef chef;
	Texture chefSheet;
	TextureRegion[] chefWalkFrames;
	TextureRegion[] chefIdleFrames;
	Animation chefWalk;
	Animation chefIdle;
	
	float stateTime;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	List<Food> food = new ArrayList<Food>();
	ArrayList<String> possibleFoodPics = new ArrayList<String>();
	
	
	Texture cleese_image;
	Sprite box;
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	@Override
	public void create() {
		possibleFoodPics.add("assets/chicken.png");
		possibleFoodPics.add("assets/duck.jpg");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		
		batch = new SpriteBatch();
		Registry.world = new World(Registry.gravity, true);
		debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(this);
		
		cleese_image = new Texture(Gdx.files.internal("assets/cleese.png"));
		box = new Sprite(cleese_image, 0, 0, 128, 128);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100, 100);
		
		body = Registry.world.createBody(bodyDef);
		
		ps = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(128f, 0);
		vertices[2] = new Vector2(128f, 128f);
		vertices[3] = new Vector2(0, 128f);
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
		
		floor = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 800, 16);
		floor.setPosition(0, 0);
		/*for (int i = 0; i < 25d; i++) {
			int index = (int) (Math.random() * possibleFoodPics.size()); 
			String foodFile = possibleFoodPics.get(index);
			String foodName =  foodFile.substring(7,foodFile.indexOf("."));
			Food thisFood = new Food(foodName, new Texture(Gdx.files.internal(possibleFoodPics.get(index))), 0, 0, 12 ,12 );
			thisFood.setPosition(0,5);
			food.add(thisFood);
		} 
		chef = new Chef(new Texture(Gdx.files.internal("assets/cleese.png")), 0, 0, 32, 32);
		chef.setPosition(0, 5);
		
		chefSheet = new Texture(Gdx.files.internal("assets/chef.png"));
		TextureRegion[][] tmp = TextureRegion.split(chefSheet, 32, 32);
		chefIdleFrames = new TextureRegion[4];
		chefWalkFrames = new TextureRegion[8];
		for(int i = 0; i < 8; i++) {
			chefWalkFrames[i] = tmp[1][i+4];
		}
		for(int i = 0; i < 4; i++) {
			chefIdleFrames[i] = tmp[1][i];
		}
		chefWalk = new Animation(0.025f, chefWalkFrames);
		chefIdle = new Animation(0.025f, chefIdleFrames);*/
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		//stateTime += Gdx.graphics.getDeltaTime();
		Registry.world.step(1/60f, 6, 2);
		
		box.setPosition(body.getPosition().x, body.getPosition().y);
		/*for (Food foods : food)d {
			foods.setPosition(Registry.b2dScale.x * foods.body.getPosition().x, Registry.b2dScale.y * foods.body.getPosition().y);
			foods.setRotation((float)(foods.body.getAngle() * 180 / Math.PI));
		}
		chef.setPosition(Registry.b2dScale.x * chef.body.getPosition().x, Registry.b2dScale.y * chef.body.getPosition().y);
		chef.setRotation((float) (chef.body.getAngle() * 180/Math.PI));*/
		
		// horizontal movement
		/*if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			//cleeseHead.x--;
			//chef.body.applyForceToCenter(new Vector2(-50.0f, 0), true);
			chef.body.setLinearVelocity(-5.0f, chef.body.getLinearVelocity().y);
			//chef.body.applyLinearImpulse(new Vector2(-5.0f, 0), chef.body.getWorldCenter(), true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			//cleeseHead.x++;
			//chef.body.applyForceToCenter(new Vector2(50.0f, 0), true);
			chef.body.setLinearVelocity(5.0f, chef.body.getLinearVelocity().y);
			//chef.body.applyLinearImpulse(new Vector2(5.0f, 0), chef.body.getWorldCenter(), true);
		}*/
		/*if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			chef.body.applyLinearImpulse(new Vector2(0, 10.0f), new Vector2(0, 0), true);
		}*/
		
		// punches
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			body.setTransform(Gdx.input.getX(), 800-Gdx.input.getY(), 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			
		}
		
	}
	@Override
	public void render() {
		// run update
		//update();
		
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// update camera
		camera.update();
		
		//debugRenderer.render(Registry.world, camera.combined);
		// draw sprites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		/*chef.draw(batch);
		for  (Food foods : food) {
			foods.draw(batch);
		}*/
		box.draw(batch);
		batch.end();
		debugRenderer.render(Registry.world, camera.combined);
		
		
		update();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.A) {
			
		} else if (keycode == Input.Keys.D) {
			
		}
		if(keycode == Input.Keys.W) {
			chef.body.applyLinearImpulse(new Vector2(0, 40.0f), chef.body.getWorldCenter(), true);
		}
		if(keycode == Input.Keys.R) {
			
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.A) {
			chef.body.setLinearVelocity(new Vector2(0, chef.body.getLinearVelocity().y));
		} else if (keycode == Input.Keys.D) {
			chef.body.setLinearVelocity(new Vector2(0, chef.body.getLinearVelocity().y));
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//body.setTransform(screenX, 800-screenY, 0);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
