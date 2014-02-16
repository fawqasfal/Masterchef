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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game implements ApplicationListener, InputProcessor {
	
	public final int LEFT = 1;
	public final int RIGHT = 2;
	int facing = RIGHT;
	
	Floor floor;
	double sinceLastPressed = System.nanoTime();
	Chef chef;
	Texture chefSheet;
	TextureRegion[] chefWalkFrames;
	TextureRegion[] chefIdleFrames;
	TextureRegion[] chefJumpFrames;
	TextureRegion[] chefLWalkFrames;
	TextureRegion[] chefLIdleFrames;
	TextureRegion[] chefLJumpFrames;
	Animation chefWalk;
	Animation chefIdle;
	Animation chefJump;
	Animation chefLWalk;
	Animation chefLIdle;
	Animation chefLJump;
	Animation currentAnimation;
	TextureRegion currentFrame;
	
	float stateTime;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	List<Food> food = new ArrayList<Food>();
	ArrayList<String> possibleFoodPics = new ArrayList<String>();
	
	
	Texture cleese_image;
	//Box box;
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	
	@Override
	public void create() {
		possibleFoodPics.add("assets/chicken.png");
		possibleFoodPics.add("assets/duck.png");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 200, 200);
		
		batch = new SpriteBatch();
		Registry.world = new World(Registry.gravity, true);
		debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(this);
		
		/*cleese_image = new Texture(Gdx.files.internal("assets/cleese.png"));
		box = new Box(cleese_image, 0, 0, 128, 128);
		box.setOrigin(0, 0);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100, 100);
		
		body = Registry.world.createBody(bodyDef);
		
		ps = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(12.8f, 0);
		vertices[2] = new Vector2(12.8f, 12.8f);
		vertices[3] = new Vector2(0, 12.8f);
		ps.set(vertices);
		//ps.setAsBox(1.6f, 1.6f);
		ps.setRadius(0.1f);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.96f;
		fd.restitution = 0.2f;
		
		f = body.createFixture(fd);
		
		ps.dispose();*/
		
		floor = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 800, 16);
		floor.setPosition(0, 0);
		for (int i = 0; i < 25; i++) {
			int index = (int) (Math.random() * possibleFoodPics.size()); 
			String foodFile = possibleFoodPics.get(index);
			String foodName =  foodFile.substring(7,foodFile.indexOf("."));
			Food thisFood = new Food(foodName, new Texture(Gdx.files.internal(possibleFoodPics.get(index))), 0, 0, 12 ,12 );
			thisFood.body.setTransform(10,5, 0);
			food.add(thisFood);
		}
		chef = new Chef(new Texture(Gdx.files.internal("assets/chef.png")), 0, 0, 512, 512);
		chef.setOrigin(16, 16);
		chef.body.setTransform(5, 10, 0);
		
		chefSheet = new Texture(Gdx.files.internal("assets/chef.png"));
		TextureRegion[][] tmp = TextureRegion.split(chefSheet, 32, 32);
		chefIdleFrames = new TextureRegion[2];
		chefLIdleFrames = new TextureRegion[2];
		chefWalkFrames = new TextureRegion[8];
		chefLWalkFrames = new TextureRegion[8];
		chefJumpFrames = new TextureRegion[1];
		chefLJumpFrames = new TextureRegion[1];
		for(int i = 0; i < 8; i++) {
			chefWalkFrames[i] = tmp[0][i+3];
			chefLWalkFrames[i] = tmp[0][i+3];
			//chefLWalkFrames[i].flip(true, false);
		}
		for(int i = 0; i < 2; i++) {
			chefIdleFrames[i] = tmp[0][i+1];
			chefLIdleFrames[i] = tmp[0][i+1];
			//chefLIdleFrames[i].flip(true, false);
		}
		chefJumpFrames[0] = tmp[0][0];
		chefLJumpFrames[0] = tmp[0][0];
		//chefLJumpFrames[0].flip(true, false);
		
		chefWalk = new Animation(0.075f, chefWalkFrames);
		chefLWalk = new Animation(0.075f, chefLWalkFrames);
		chefIdle = new Animation(0.2f, chefIdleFrames);
		chefLIdle = new Animation(0.2f, chefLIdleFrames);
		chefJump = new Animation(0.0f, chefJumpFrames);
		chefLJump = new Animation(0.0f, chefLJumpFrames);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
	public void update() {
		
		
		
		stateTime += Gdx.graphics.getDeltaTime();
		Registry.world.step(1/60f, 6, 2);
		
		//box.setPosition(box.getOriginX()-(box.getWidth()/2), box.getOriginY() - (box.getHeight()/2));
		//box.setRotation((float)(body.getAngle() * 180 / Math.PI));
		//box.setPosition(body.getPosition().x * 10, body.getPosition().y * 10);
		//box.setRotation(body.getAngle());
		//box.setScaledPosition(body.getPosition().x, body.getPosition().y);
		
		//box.setOrigin(box.getX()+64, box.getY()+64);
		
		for (Food foods : food) {
			foods.setRotation(foods.body.getAngle());
			foods.setScaledPosition(foods.body.getPosition().x, foods.body.getPosition().y);
		}
		chef.body.setFixedRotation(true);
		chef.setRotation(chef.body.getAngle());
		chef.setScaledPosition(chef.body.getPosition().x, chef.body.getPosition().y);
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			chef.body.setLinearVelocity(-5.0f, chef.body.getLinearVelocity().y);
			//currentAnimation = chefWalk;
			facing = LEFT;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			chef.body.setLinearVelocity(5.0f, chef.body.getLinearVelocity().y);
			//currentAnimation = chefWalk;
			facing = RIGHT;
		} else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (Math.abs(chef.getY() - floor.getHeight()) <= 49) {
				//if (Math.abs(System.nanoTime() - sinceLastPressed) > 1E9) {
				//sinceLastPressed = System.nanoTime();
				//chef.body.applyLinearImpulse(new Vector2(0, 40.0f), chef.body.getWorldCenter(), true);
				//}
		
			}
			//chef.body.setLinearVelocity(chef.body.getLinearVelocity().x, 5.0f);
		} 
		//animation testing
		if(Math.abs(chef.body.getLinearVelocity().y) > 0.05f) {
			if(facing == RIGHT)
				currentAnimation = chefJump;
			else if(facing == LEFT){
				currentAnimation = chefLJump;
			}
		}
		else if (Math.abs(chef.body.getLinearVelocity().x) > 0.2f) {
			if(facing == RIGHT)
				currentAnimation = chefWalk;
			else if(facing == LEFT) {
				currentAnimation = chefLWalk;
			}
		}
		else {
			if(facing == RIGHT)
				currentAnimation = chefIdle;
			else if (facing == LEFT) {
				currentAnimation = chefLIdle;
			}
		}
		
		//if (chef.body.getLinearVelocity().x > 1.0f) facing = RIGHT;
		//else if (chef.body.getLinearVelocity().x < 1.0f) facing = LEFT;
		
		
		
		System.out.println(facing);
		// punches
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			//body.setTransform(Gdx.input.getX(), 800-Gdx.input.getY(), 0);
			//body.setAwake(true);
			
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			body.setAngularVelocity(-1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			//body.setAngularVelocity(1);
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
		
		Gdx.gl.glClearColor(1.0f, 1.0f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// update camera
		//camera.update();
		
		
		camera.update();
		update();
		//update();
		
		//debugRenderer.render(Registry.world, camera.combined);
		// draw sprites
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		
		
		//chef.draw(batch);
		
		/*batch.draw(// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			chef.body.setLinearVelocity(-5.0f, chef.body.getLinearVelocity().y);
			currentAnimation = chefWalk;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			chef.body.setLinearVelocity(5.0f, chef.body.getLinearVdelocity().y);
			currentAnimation = chefWalk;
		} else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			chef.body.setLinearVelocity(chef.body.getLinearVelocity().x, 5.0f);
		} else {
			currentAnimation = chefIdle;
		}*/
		/*batch.draw(currentFrame, chef.getX(), chef.getY(), chef.getOriginX(), chef.getOriginY(),
				chef.getWidth(), chef.getHeight(), chef.getScaleX(), chef.getScaleY(), chef.getRotation());*/
		
		
		batch.draw(currentFrame, chef.getX(), chef.getY());
		//batch.draw(, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		//batch.draw(currentFrame, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		
		for  (Food foods : food) {
			foods.draw(batch);
		}
		//box.draw(batch);m
		
		batch.end();
		debugRenderer.render(Registry.world, camera.combined);
		
		//camera.update();
		//update();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		update();
		
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
			if (Math.abs(chef.getY() - floor.getHeight()) <= 49) {
				if (Math.abs(System.nanoTime() - sinceLastPressed) > 1.5E9) {
				sinceLastPressed = System.nanoTime();
				chef.body.applyLinearImpulse(new Vector2(0, 20.0f), chef.body.getWorldCenter(), true);
				}
			}
		}
		if(keycode == Input.Keys.R) {
			//System.out.println("Box X: " + box.getX() + " Box Y: " + box.getY());
			//System.out.println("Org X: " + box.getOriginX() + " Org Y: " + box.getOriginY());
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
