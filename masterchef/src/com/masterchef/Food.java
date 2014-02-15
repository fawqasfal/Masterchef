package com.masterchef;

import com.badlogic.gdx.math.Rectangle;



public class Food extends Rectangle {
	public final String name;
	public final String picFile;
	public final int RAW = 0;
	public final int COOKED = 1;
	public final int BURNT = 2;
	public final int ROTTEN = 3;
	
	public Food(String name, String picFile) {
		super();
		this.name = name;
		this.picFile = picFile;
	}

	public String getName() {
		return this.name;
	}
	
	public String getPic() {
		return this.picFile;
	}

}
