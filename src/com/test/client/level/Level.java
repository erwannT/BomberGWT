package com.test.client.level;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.Image;
import com.test.client.resources.SpritesRessource;

public class Level {

	private int layerHeight;
	private int layerWidth;

	private final int pixelTileHeight;
	private final int pixelTileWidth;
	private final int pixelSpacing;
	private final Context2d context;

	private JSONObject tileContext;
	private ImageElement image;

	private JSONArray mapData;

	public Level(EventBus eventBus, TextResource resource, Context2d context) {

		image = ImageElement.as(new Image(SpritesRessource.INSTANCE.mapBomberStage1()).getElement());

		JSONValue parseStrict = JSONParser.parseStrict(resource.getText());
		tileContext = parseStrict.isObject();

		/*
		 * layers
		 */
		JSONObject layers0 = (JSONObject) ((JSONArray) tileContext.get("layers")).get(0);
		layerHeight = Integer.parseInt(layers0.get("height").toString());
		layerWidth = Integer.parseInt(layers0.get("width").toString());

		mapData = (JSONArray) layers0.get("data");
		/*
		 * tilesets
		 */

		JSONObject tileset0 = (JSONObject) ((JSONArray) tileContext.get("tilesets")).get(0);

		pixelTileHeight = Integer.parseInt(tileset0.get("tileheight").toString());
		pixelTileWidth = Integer.parseInt(tileset0.get("tilewidth").toString());
		pixelSpacing = Integer.parseInt(tileset0.get("spacing").toString());

		// utiliser pour determiner combien il y a de tile par coté
		int widthImage = Integer.parseInt(tileset0.get("imageheight").toString());
		int heightImage = Integer.parseInt(tileset0.get("imagewidth").toString());

		// definir le ressource a charger
		String imageName = tileset0.get("image").toString();

		this.context = context;
	}

	/**
	 * Retourne l'element pour une position
	 * 
	 * @param pixelXPos
	 * @param pixelYPos
	 * @return
	 */
	public boolean getCollisionData(int pixelXPos, int pixelYPos) {

		int xPos = pixelXPos / pixelTileWidth;
		int yPos = pixelYPos / pixelTileHeight;

		int position = (xPos) + layerWidth * yPos;
		int val = Integer.parseInt(mapData.get(position).toString());

		return val > 0;

	}

	public void draw() {

		for (int i = 0; i < mapData.size(); i++) {

			// image index in tiles
			int tilesIndex = Integer.parseInt(mapData.get(i).toString());

			/*
			 * Converti l'index du tableau de tiles en coordonnée
			 */
			int yPosTiles = tilesIndex / 9;
			int xPosTiles = tilesIndex % 9;

			/*
			 * Converti les coordonnée en PX pour determine le tile a afficher
			 */
			int xImgPos = 0;
			if (xPosTiles != 0) {
				xImgPos = (xPosTiles * pixelTileWidth + (xPosTiles * pixelSpacing) - pixelSpacing) - pixelTileWidth;

			}

			int yImgPos = 0;
			if (yPosTiles != 0) {
				yImgPos = (yPosTiles * pixelTileHeight + (yPosTiles * pixelSpacing) - pixelSpacing) - pixelTileHeight;

			}

			// determine le coordonnée d'affichage du tile sur le canvas
			int displayY = i / layerHeight;
			int displayX = i % layerWidth;

			if (tilesIndex != 0) {
				context.drawImage(image, xImgPos, yImgPos, pixelTileWidth, pixelTileHeight, displayX * pixelTileWidth, displayY * pixelTileHeight,
						pixelTileWidth, pixelTileHeight);
			} else {
				// care vide
				context.clearRect(displayX * pixelTileWidth, displayY * pixelTileHeight, pixelTileWidth, pixelTileHeight);
			}
		}
	}

}
