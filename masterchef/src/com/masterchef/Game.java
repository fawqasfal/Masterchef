package com.masterchef;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Game implements ApplicationListener {
	
	Texture floor_image;
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
		
		// just for testing
		//cleese = new Texture(Gdx.files.internal("/root/git/Masterchef/masterchef/src/assets/cleese.png"));
		cleese = new Texture(Gdx.files.internal("assets/cleese.png"));
		cleeseHead = new Rectangle();
		cleeseHead.x = 0;
		cleeseHead.y = 0;
		cleeseHead.width = 128;
		cleeseHead.height = 128;
		
		floor_image = new Texture(Gdx.files.internal("assets/floor.png"));
		floor = new Floor();
		floor.x = 0;
		floor.y = -240;
		floor.width = 256;
		floor.height = 256;
		
		chef = new Chef();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		Registry.world.step(1/60f, 6, 2);
		
		cleeseHead.x = Registry.b2dScale.x * chef.body.getPosition().x;
		cleeseHead.y = Registry.b2dScale.y * chef.body.getPosition().y;
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			//cleeseHead.x--;
			chef.body.applyForceToCenter(new Vector2(-50.0f, 0), true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			//cleeseHead.x++;
			chef.body.applyForceToCenter(new Vector2(50.0f, 0), true);
		}
		//if(Gdx.i)
		
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
		batch.draw(floor_image, floor.x, floor.y);
		batch.draw(cleese, cleeseHead.x, cleeseHead.y);
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

}
