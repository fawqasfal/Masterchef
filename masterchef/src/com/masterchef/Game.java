package com.masterchef;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.masterchef.util.KeyProcessor;

public class Game implements ApplicationListener, InputProcessor {
	
	Floor floor;
	
	Texture cleese;
	Rectangle cleeseHead;
	
	Chef chef;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	List<Food> food = new ArrayList<Food>();
	
	@Override
	public void create() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 200, 200);
		
		batch = new SpriteBatch();
		
		Registry.world = new World(Registry.gravity, true);
		debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(this);
		
		floor = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 256, 256);
		//floor.setTexture(new Texture(Gdx.files.internal("assets/floor.png")));
		floor.setPosition(0, -240);
		
		chef = new Chef(new Texture(Gdx.files.internal("assets/cleese.png")), 0, 0, 32, 32);
		chef.setPosition(0, 0);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		Registry.world.step(1/60f, 6, 2);
		
		chef.setPosition(Registry.b2dScale.x * chef.body.getPosition().x, Registry.b2dScale.y * chef.body.getPosition().y);
		chef.setRotation(chef.body.getAngle());
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			//cleeseHead.x--;
			//chef.body.applyForceToCenter(new Vector2(-50.0f, 0), true);
			chef.body.setLinearVelocity(-2.0f, chef.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)).y);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			//cleeseHead.x++;
			//chef.body.applyForceToCenter(new Vector2(50.0f, 0), true);
			chef.body.setLinearVelocity(2.0f, chef.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)).y);
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
		// run update code
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
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
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
