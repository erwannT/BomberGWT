package com.test.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

interface Resources extends ClientBundle {
	Resources INSTANCE = GWT.create(Resources.class);

	@Source("mapStage1.json")
	TextResource synchronous();

}