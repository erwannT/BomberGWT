package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Turtle {

	private Context2d context;

	private boolean step;

	private int positionX = 450;

	private final SpritesRessource res = GWT.create(SpritesRessource.class);

	public Turtle(Context2d context) {
		this.context = context;

	}

	// private ImageElement image = ImageElement.as(new
	// Image(res.test()).getElement());

	void walk() {

		final Image img = new Image(res.turtle());
		// final Image img = new Image("/smb3_enemies_sheet.png");

		ImageElement image = ImageElement.as(img.getElement());

		if (step) {

			context.clearRect(positionX + 1, 45, 32, 32);
			// context.translate(32, 0);
			// context.scale(-1, 1);

			context.drawImage(image, 0, 0, 32, 32, positionX, 45, 32, 32);
			positionX--;
		} else {

			context.clearRect(positionX + 1, 45, 32, 32);
			// context.translate(32, 0);
			// context.scale(-1, 1);
			context.drawImage(image, 50, 0, 32, 32, positionX, 45, 32, 32);
			positionX--;
		}
		step = !step;

	}
}
