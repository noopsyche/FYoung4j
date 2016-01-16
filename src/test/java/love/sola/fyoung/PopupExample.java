package love.sola.fyoung;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PopupExample extends Application {
  public static void main(String[] args) { launch(args); }

  @Override public void start(final Stage primaryStage) {
    primaryStage.setTitle("Popup Example");

    final ContextMenu contextMenu = new ContextMenu();
    contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent e) {
        System.out.println("showing");
      }
    });
    contextMenu.setOnShown(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent e) {
        System.out.println("shown");
      }
    });

    MenuItem item1 = new MenuItem("About");
    item1.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.out.println("About");
      }
    });
    MenuItem item2 = new MenuItem("Preferences");
    item2.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        System.out.println("Preferences");
      }
    });
    contextMenu.getItems().addAll(item1, item2);

    final Popup popup = new Popup(); popup.setX(300); popup.setY(200);

    popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));
    Stage stage = new Stage();
    Button show = new Button("Show");
    show.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        stage.show();
        contextMenu.show(stage);
//        popup.show(logViewStage);
//        contextMenu.show(popup);

      }
    });

    Button hide = new Button("Hide");
    hide.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        popup.hide();
        stage.hide();
//        contextMenu.hide();
      }
    });

    HBox layout = new HBox(10);
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
    layout.getChildren().addAll(show, hide);
    primaryStage.setScene(new Scene(layout));
    primaryStage.show();
  }
}