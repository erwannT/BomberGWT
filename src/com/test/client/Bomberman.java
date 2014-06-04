package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Bomberman {

	private int[] position;

	private final static int MOV = 16;

	private final SpritesRessource res = GWT.create(SpritesRessource.class);

	private Image perso = new Image(res.bomberman());
	ImageElement image = ImageElement.as(perso.getElement());

	private final Context2d context;

	private int xPos = 16;
	private int yPos = 32;

	/*
	 * Default display : stop position
	 */
	private int xImg = 0;
	private int yImg = 64;

	public Bomberman(Context2d context) {
		this.context = context;
		position = new int[256];

		// default position
		position[18] = 1;
	}

	/**
	 * TODO : gerer le rafraichissement du personnage
	 */
	public void clear() {
		context.clearRect(0, 0, 250, 250);
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		// ypos + 1 parce que l'ordonne de bomberman decale de 1 car la partie tete ne compte pas pour les point des blocage
		return yPos;
	}

	public void walk(Movement mov) {

		if (mov != Movement.STOP) {
			if (xImg == 16) {
				xImg = 32;
			} else {
				xImg = 16;
			}
		}
		/*
		 * Met a jour les coordonnée PX du sprite en fonction du mouvement du personnage
		 * 
		 * Met a jour les coordonnés du personnage
		 */
		switch (mov) {
		case STOP:
			xImg = 0;
			break;
		case RIGHT:
			yImg = 32;
			xPos += MOV;
			break;
		case LEFT:
			yImg = 96;
			xPos -= MOV;
			break;
		case UP:
			yImg = 0;
			yPos -= MOV;
			break;
		case DOWN:
			yImg = 64;
			yPos += MOV;
			break;
		}
	}

	/**
	 * Raffraichit le personnage sur le canvas
	 */
	public void refresh() {
		context.drawImage(image, xImg, yImg, 16, 32, xPos, yPos, 16, 32);
	}
}
