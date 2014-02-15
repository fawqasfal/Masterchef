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
	
	World world;
	Box2DDebugRenderer debugRenderer;
	Texture cleese;
	Rectangle cleeseHead;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	List<Rectangle> food = new ArrayList<Rectangle>();
	
	@Override
	public void create() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 200, 200);
		
		batch = new SpriteBatch();
		
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		
		// just for testing
		cleese = new Texture(Gdx.files.internal("/root/git/Masterchef/masterchef/src/assets/cleese.png"));
		cleeseHead = new Rectangle();
		cleeseHead.x = 0;
		cleeseHead.y = 0;
		cleeseHead.width = 128;
		cleeseHead.height = 128;
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		world.step(1/60f, 6, 2);
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cleeseHead.x--;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			cleeseHead.x++;
		}
		
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
		batch.draw(cleese, cleeseHead.x, cleeseHead.y);
		batch.end();
		
		debugRenderer.render(world, camera.combined);
		
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
