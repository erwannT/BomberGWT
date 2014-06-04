package com.test.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.Image;
import com.test.client.event.MoveRequestEvent;
import com.test.client.event.MoveRequestHandler;

public class Tiles implements MoveRequestHandler {

	private final int height;
	private final int width;
	private final int spacing;
	private final Context2d context;

	private JSONObject tileContext;
	private ImageElement image;

	public Tiles(EventBus eventBus, TextResource resource, Context2d context) {

		eventBus.addHandler(MoveRequestEvent.getType(), this);

		image = ImageElement.as(new Image(SpritesRessource.INSTANCE.mapBomberStage1()).getElement());

		JSONValue parseStrict = JSONParser.parseStrict(resource.getText());
		tileContext = parseStrict.isObject();

		/*
		 * tilesets
		 */

		JSONObject tileset0 = (JSONObject) ((JSONArray) tileContext.get("tilesets")).get(0);

		height = Integer.parseInt(tileset0.get("tileheight").toString());
		width = Integer.parseInt(tileset0.get("tilewidth").toString());
		spacing = Integer.parseInt(tileset0.get("spacing").toString());

		// utiliser pour determiner combien il y a de tile par coté
		int widthImage = Integer.parseInt(tileset0.get("imageheight").toString());
		int heightImage = Integer.parseInt(tileset0.get("imagewidth").toString());
		// definir le ressource a charger
		String imageName = tileset0.get("image").toString();

		this.context = context;
	}

	public int getElement(int xPos, int yPos) {

		JSONObject layers0 = (JSONObject) ((JSONArray) tileContext.get("layers")).get(0);
		JSONArray data = (JSONArray) layers0.get("data");
		return Integer.parseInt(data.get((xPos) + 16 * yPos).toString());

	}

	public void refresh() {
		JSONObject layers0 = (JSONObject) ((JSONArray) tileContext.get("layers")).get(0);

		JSONArray data = (JSONArray) layers0.get("data");

		for (int i = 0; i < data.size(); i++) {

			// image index in tiles
			int tilesIndex = Integer.parseInt(data.get(i).toString());

			if (tilesIndex != 0) {

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
					xImgPos = (xPosTiles * width + (xPosTiles * spacing) - spacing) - width;

				}

				int yImgPos = 0;
				if (yPosTiles != 0) {
					yImgPos = (yPosTiles * height + (yPosTiles * spacing) - spacing) - height;

				}

				// determine le coordonnée d'affichage du tile sur le canvas
				int displayY = i / 16;
				int displayX = i % 16;

				context.drawImage(image, xImgPos, yImgPos, width, height, displayX * width, displayY * height, width, height);
			}

		}
	}

	@Override
	public void onRequest(int position, Movement move) {
		// TODO Auto-generated method stub

	}

}
