import controller.Controller;
import view.View;
import simu.model.MyEngine;

import javax.swing.*;


/**
 * Generate new instance for Model (MyEngine), View and Controller and open the application.
 */
public class Main {
    public static void main(String[] args) {
        MyEngine engine = new MyEngine(null);

        View view = new View(null);


        Controller controller = new Controller(engine, view);

        view.setController(controller);
        controller.setView(view);
        engine.setController(controller);


        SwingUtilities.invokeLater(() -> {
            controller.setView(view);
        });
    }
}