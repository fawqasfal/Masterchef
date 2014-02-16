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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	String mostRecent = new String();
	int facing1 = RIGHT;
	int facing2 = RIGHT;

	Floor floor;
	double sinceLastPressed1 = System.nanoTime();
	double sinceLastPressed2 = System.nanoTime();
	Chef chef;
	Chef chef2;
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
	Animation currentAnimation1;
	Animation currentAnimation2;
	TextureRegion currentFrame1;
	TextureRegion currentFrame2;
	
	float stateTime;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	List<Food> food = new ArrayList<Food>();
	ArrayList<String> possibleFoodPics = new ArrayList<String>();
	
	
	Texture cleese_image;
	Wall wallLeft;
	Wall wallRight;
	//Box box;
	BodyDef bodyDef;
	Body body;
	PolygonShape ps;
	FixtureDef fd;
	Fixture f;
	Floor ceilingLeft;
	Floor ceilingRight;
	
	BitmapFont font;
	
	
	@Override
	public void create() {
		possibleFoodPics.add("assets/chicken.png");
		possibleFoodPics.add("assets/duck.png");
		possibleFoodPics.add("assets/mutton.png");
		possibleFoodPics.add("assets/peas.png");
		possibleFoodPics.add("assets/chef.png");
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
		
		font = new BitmapFont();
		
		floor = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 800, 16);
		floor.setPosition(0, 0);
		
		ceilingLeft = new Floor(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 800, 8);
		ceilingLeft.body.setTransform(0,19.5f,0);
		ceilingLeft.setPosition(0, 195);

		wallLeft = new Wall(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 8, 200);
		wallLeft.body.setTransform(0,0,0);
		wallLeft.setPosition(0, 0);
		
		wallRight = new Wall(new Texture(Gdx.files.internal("assets/floor.png")), 0, 0, 8, 200);
		wallRight.body.setTransform(19.3f, 0, 0);
		wallRight.setPosition(193, 0);
		
		chef = new Chef(new Texture(Gdx.files.internal("assets/chef.png")), 0, 0, 512, 512);
		chef.body.setTransform(5, 10, 0);
		
		chef2 = new Chef(new Texture(Gdx.files.internal("assets/chef.png")), 0, 0, 512, 512);
		chef2.body.setTransform(10, 10, 0);

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
		if (chef.body.getLinearVelocity().y < 0)
			chef.body.setLinearVelocity(chef.body.getLinearVelocity().x, chef.body.getLinearVelocity().y * (float) 1.1);
		if (chef2.body.getLinearVelocity().y < 0)
		chef2.body.setLinearVelocity(chef2.body.getLinearVelocity().x, chef2.body.getLinearVelocity().y * (float) 1.1);

		stateTime += Gdx.graphics.getDeltaTime();
		Registry.world.step(1/60f, 6, 2);
		
		//box.setPosition(box.getOriginX()-(box.getWidth()/2), box.getOriginY() - (box.getHeight()/2));
		//box.setRotation((float)(body.getAngle() * 180 / Math.PI));
		//box.setPosition(body.getPosition().x * 10, body.getPosition().y * 10);
		//box.setRotation(body.getAngle());
		//box.setScaledPosition(body.getPosition().x, body.getPosition().y);
		
		//box.setOrigin(box.getX()+64, box.getY()+64);
		
		
		chef.body.setFixedRotation(true);
		chef.setRotation(chef.body.getAngle());
		chef.setScaledPosition(chef.body.getPosition().x, chef.body.getPosition().y);
		
		chef2.body.setFixedRotation(true);
		chef2.setRotation(chef2.body.getAngle());
		chef2.setScaledPosition(chef2.body.getPosition().x, chef2.body.getPosition().y);
		
		// horizontal movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			chef.body.setLinearVelocity(-10.0f, chef.body.getLinearVelocity().y);
			//currentAnimation = chefWalk;
			facing1 = LEFT;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			chef.body.setLinearVelocity(10.0f, chef.body.getLinearVelocity().y);
			//currentAnimation = chefWalk;
			facing1 = RIGHT;
		} else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (Math.abs(chef.getY() - floor.getHeight()) <= 49) {
				//if (Math.abs(System.nanoTime() - sinceLastPressed) > 1E9) {
				//sinceLastPressed = System.nanoTime();
				//chef.body.applyLinearImpulse(new Vector2(0, 40.0f), chef.body.getWorldCenter(), true);
				//}java
		
			}
			//chef.body.setLinearVelocity(chef.body.getLinearVelocity().x, 5.0f);
		} 
		//animation testing
		if(Math.abs(chef.body.getLinearVelocity().y) > 0.2f) {
			currentAnimation1 = chefJump;
		}
		else if (Math.abs(chef.body.getLinearVelocity().x) > 0.2f) {
			currentAnimation1 = chefWalk;
		}
		else {
			currentAnimation1 = chefIdle;
		}
		if(Math.abs(chef2.body.getLinearVelocity().y) > 0.2f) {
			currentAnimation2 = chefJump;
		}
		else if (Math.abs(chef2.body.getLinearVelocity().x) > 0.2f) {
			currentAnimation2 = chefWalk;
		}
		else {
			currentAnimation2 = chefIdle;
		}
		
		//if (chef.body.getLinearVelocity().x > 1.0f) facing = RIGHT;
		//else if (chef.body.getLinearVelocity().x < 1.0f) facing = LEFT;
		
		
		
		// punches
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			//body.setTransform(Gdx.input.getX(), 800-Gdx.input.getY(), 0);
			//body.setAwake(true);
			
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			//body.setAngularVelocity(-1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			//body.setAngularVelocity(1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			
			
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			facing2 = LEFT;
			chef2.body.setLinearVelocity(-10.0f, chef2.body.getLinearVelocity().y);
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			facing2 = RIGHT;
			chef2.body.setLinearVelocity(10.0f, chef2.body.getLinearVelocity().y);
			
		}
		for (Food foods : food) {
			foods.setRotation(foods.body.getAngle());
			foods.setScaledPosition(foods.body.getPosition().x, foods.body.getPosition().y);
			float neccX = Math.abs(foods.body.getPosition().x - chef.body.getPosition().x);
			float neccY = foods.body.getPosition().y - chef.body.getPosition().x;
			if(Math.abs(chef.body.getLinearVelocity().y) > 0.05f && neccX < 10 && neccY < 65)  {
				mostRecent = "chef";
			}
			if (Math.abs(chef.body.getLinearVelocity().y) > 0.05f && neccX < 10 && neccY < 65) {
				mostRecent = "chef2";
			}
		}
		//for some reason this has to be in it's own loop
		for (Food foods : food ) {
			if ( Math.abs(foods.getY() - floor.getY()) < 25 && !Gdx.input.isKeyPressed(Input.Keys.L)) {
				foods.body.setActive(false);
				foods.isOn = false;
				}
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
		currentFrame1 = currentAnimation1.getKeyFrame(stateTime, true);
		currentFrame2 = currentAnimation2.getKeyFrame(stateTime, true);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		floor.draw(batch);
		wallLeft.draw(batch);
		wallRight.draw(batch);
		ceilingLeft.draw(batch);
		//ceilingRight.draw(batch);
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
		
		
		//batch.draw(currentFrame, chef.getX(), chef.getY());
		//batch.draw(, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		if(facing1 == RIGHT) {
			batch.draw(currentFrame1, chef.getX(), chef.getY(), 0, 0, 
					chef.getWidth()/10, chef.getHeight()/10, 1.0f, 1.0f, 0.0f);
		} else {
			batch.draw(currentFrame1, chef.getX()+15, chef.getY(), 0, 0, 
					-chef.getWidth()/10, chef.getHeight()/10, 1.0f, 1.0f, 0.0f);
		}
		if(facing2 == RIGHT) {
			batch.draw(currentFrame2, chef2.getX(), chef2.getY(), 0, 0, 
					chef2.getWidth()/10, chef2.getHeight()/10, 1.0f, 1.0f, 0.0f);
		} else {
			batch.draw(currentFrame2, chef2.getX()+15, chef2.getY(), 0, 0, 
					-chef2.getWidth()/10, chef2.getHeight()/10, 1.0f, 1.0f, 0.0f);
		}
		
		for  (Food foods : food) {
			if (foods.isOn)
				foods.draw(batch);
		}
		//box.draw(batch);
		
		batch.end();
		//debugRenderer.render(Registry.world, camera.combined);
		
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
			if (Math.abs(chef.getY() - floor.getHeight()) <= 50 && chef.body.getLinearVelocity().y >= 0) {
				if (Math.abs(System.nanoTime() - sinceLastPressed1) > 0.5E9) {
				sinceLastPressed1 = System.nanoTime();
				chef.body.applyLinearImpulse(new Vector2(0, 35.0f), chef.body.getWorldCenter(), true);

				}
			}
		}
		if(keycode == Input.Keys.UP) {
			if (Math.abs(chef2.getY() - floor.getHeight()) <= 50 && chef2.body.getLinearVelocity().y >= 0) {
				if (Math.abs(System.nanoTime() - sinceLastPressed2) > 0.5E9) {
				sinceLastPressed2 = System.nanoTime();
				chef2.body.applyLinearImpulse(new Vector2(0, 25.0f), chef2.body.getWorldCenter(), true);
				}
			}
		}
		if (keycode == Input.Keys.Q) {
			for (int i = 0; i < 7; i++) {
				int index = (int) (Math.random() * possibleFoodPics.size()); 
				String foodFile = possibleFoodPics.get(index);
				int random = (int) (Math.random() * 18);
				String foodName =  "asdf";
				Food thisFood = new Food(foodName, new Texture(Gdx.files.internal(possibleFoodPics.get(index))), 0, 0, 16 ,16);
				thisFood.body.setTransform(random,18, 0);
				food.add(thisFood);
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
		if(keycode == Input.Keys.LEFT) {
			chef2.body.setLinearVelocity(new Vector2(0, chef2.body.getLinearVelocity().y));
		} else if (keycode == Input.Keys.RIGHT) {
			chef2.body.setLinearVelocity(new Vector2(0, chef2.body.getLinearVelocity().y));
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
