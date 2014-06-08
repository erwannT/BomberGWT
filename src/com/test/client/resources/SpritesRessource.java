package com.test.client.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface SpritesRessource extends ClientBundle {

	SpritesRessource INSTANCE = GWT.create(SpritesRessource.class);

	@Source("smb3_enemies_sheet.png")
	ImageResource turtle();

	ImageResource bomberman();

	ImageResource mapBomberStage1();

}
