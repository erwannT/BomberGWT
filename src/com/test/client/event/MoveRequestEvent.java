package com.test.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.test.client.Movement;

public class MoveRequestEvent extends GwtEvent<MoveRequestHandler> {

	private final static Type<MoveRequestHandler> TYPE = new Type<>();

	private int position;
	private Movement move;

	public MoveRequestEvent(int position, Movement move) {
		this.position = position;
		this.move = move;
	}

	public static Type<MoveRequestHandler> getType() {
		return TYPE;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MoveRequestHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(MoveRequestHandler handler) {
		handler.onRequest(position, move);

	}

}
