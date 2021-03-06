/*
 * This file is part of FYoung4j.
 *
 * FYoung4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FYoung4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FYoung4j.  If not, see <http://www.gnu.org/licenses/>.
 */

package love.sola.fyoung.gui.util;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeListener implements EventHandler<MouseEvent> {

	public static final int BORDER = 4;
	private Stage stage;
	private Cursor cursorEvent = Cursor.DEFAULT;
	private double startX = 0;
	private double startY = 0;

	public ResizeListener(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
		Scene scene = stage.getScene();

		double mouseEventX = mouseEvent.getSceneX(),
				mouseEventY = mouseEvent.getSceneY(),
				sceneWidth = scene.getWidth(),
				sceneHeight = scene.getHeight();

		if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
			if (mouseEventX < BORDER && mouseEventY < BORDER) {
				cursorEvent = Cursor.NW_RESIZE;
			} else if (mouseEventX < BORDER && mouseEventY > sceneHeight - BORDER) {
				cursorEvent = Cursor.SW_RESIZE;
			} else if (mouseEventX > sceneWidth - BORDER && mouseEventY < BORDER) {
				cursorEvent = Cursor.NE_RESIZE;
			} else if (mouseEventX > sceneWidth - BORDER && mouseEventY > sceneHeight - BORDER) {
				cursorEvent = Cursor.SE_RESIZE;
			} else if (mouseEventX < BORDER) {
				cursorEvent = Cursor.W_RESIZE;
			} else if (mouseEventX > sceneWidth - BORDER) {
				cursorEvent = Cursor.E_RESIZE;
			} else if (mouseEventY < BORDER) {
				cursorEvent = Cursor.N_RESIZE;
			} else if (mouseEventY > sceneHeight - BORDER) {
				cursorEvent = Cursor.S_RESIZE;
			} else {
				cursorEvent = Cursor.DEFAULT;
			}
			scene.setCursor(cursorEvent);
		} else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
			startX = stage.getWidth() - mouseEventX;
			startY = stage.getHeight() - mouseEventY;
		} else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
			if (!Cursor.DEFAULT.equals(cursorEvent)) {
				if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
					double minHeight = stage.getMinHeight() > (BORDER * 2) ? stage.getMinHeight() : (BORDER * 2);
					if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) || Cursor.NE_RESIZE.equals(cursorEvent)) {
						if (stage.getHeight() > minHeight || mouseEventY < 0) {
							stage.setHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
							stage.setY(mouseEvent.getScreenY());
						}
					} else {
						if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
							stage.setHeight(mouseEventY + startY);
						}
					}
				}
				if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
					double minWidth = stage.getMinWidth() > (BORDER * 2) ? stage.getMinWidth() : (BORDER * 2);
					if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) || Cursor.SW_RESIZE.equals(cursorEvent)) {
						if (stage.getWidth() > minWidth || mouseEventX < 0) {
							stage.setWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
							stage.setX(mouseEvent.getScreenX());
						}
					} else {
						if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
							stage.setWidth(mouseEventX + startX);
						}
					}
				}
				mouseEvent.consume();
			}
		}
	}

	public static void addResizeListener(Stage stage) {
		ResizeListener resizeListener = new ResizeListener(stage);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
		stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
		ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
		for (Node child : children) {
			addListenerDeeply(child, resizeListener);
		}
	}

	public static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
		node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
		if (node instanceof Parent) {
			Parent parent = (Parent) node;
			ObservableList<Node> children = parent.getChildrenUnmodifiable();
			for (Node child : children) {
				addListenerDeeply(child, listener);
			}
		}
	}
}