import Controller.Control_Menu;
import View.Menu_View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Appli extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Menu_View menu = new Menu_View(stage);
        Control_Menu control_menu = new Control_Menu(menu);

    }
}
