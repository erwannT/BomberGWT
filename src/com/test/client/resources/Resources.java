package com.test.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundle {
	Resources INSTANCE = GWT.create(Resources.class);

	@Source("mapStage1.json")
	TextResource synchronous();

}