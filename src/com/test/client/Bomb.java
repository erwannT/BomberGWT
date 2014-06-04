package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Bomb {

	private ImageElement image;

	private final Context2d context;

	public Bomb(Context2d context) {
		this.context = context;
		image = ImageElement.as(new Image(SpritesRessource.INSTANCE.mapBomberStage1()).getElement());
	}

	//

	public void waitExplosion() {

	}

	public void explosion() {

	}

}
