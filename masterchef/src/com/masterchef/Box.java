package com.masterchef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Box extends Sprite {
	
	public Box(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
	}
	
	public void setRotation(float angle) {
		this.setPosition(-(this.getWidth()/2), -(this.getHeight()/2));
		super.setRotation((float)(angle * 180/Math.PI));
	}
	public void setScaledPosition(float x, float y) {
		this.setPosition(x * Registry.b2dScale.x, y * Registry.b2dScale.y);
	}
}
