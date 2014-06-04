package com.test.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.test.client.Movement;

public interface MoveRequestHandler extends EventHandler {

	void onRequest(int position, Movement move);
}
