package com.masterchef;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
		
		floor = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 256, 256);
		floor.setPosition(0, -240);
		for (int i = 0; i < 25; i++) {
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
		chefIdle = new Animation(0.025f, chefIdleFrames);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		stateTime += Gdx.graphics.getDeltaTime();
		Registry.world.step(1/60f, 6, 2);
		for (Food foods : food) {
			foods.setPosition(Registry.b2dScale.x * foods.body.getPosition().x, Registry.b2dScale.y * foods.body.getPosition().y);
			foods.setRotation((float)(foods.body.getAngle() * 180 / Math.PI));
		}
		chef.setPosition(Registry.b2dScale.x * chef.body.getPosition().x, Registry.b2dScale.y * chef.body.getPosition().y);
		chef.setRotation((float) (chef.body.getAngle() * 180/Math.PI));
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			//cleeseHead.x--;
			//chef.body.applyForceToCenter(new Vector2(-50.0f, 0), true);
			chef.body.setLinearVelocity(-5.0f, chef.body.getLinearVelocity().y);
			//chef.body.applyLinearImpulse(new Vector2(-5.0f, 0), chef.body.getWorldCenter(), true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			//cleeseHead.x++;
			//chef.body.applyForceToCenter(new Vector2(50.0f, 0), true);
			chef.body.setLinearVelocity(5.0f, chef.body.getLinearVelocity().y);
			//chef.body.applyLinearImpulse(new Vector2(5.0f, 0), chef.body.getWorldCenter(), true);
		}
		/*if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			chef.body.applyLinearImpulse(new Vector2(0, 10.0f), new Vector2(0, 0), true);
		}*/
		
		// punches
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			
		}
		
	}
	@Override
	public void render() {
		// run update
		update();
		
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// update camera
		camera.update();
		
		// draw sprites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		chef.draw(batch);
		for  (Food foods : food) {
			foods.draw(batch);
		}
		batch.end();
		
		debugRenderer.render(Registry.world, camera.combined);
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
