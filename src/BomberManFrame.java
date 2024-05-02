import javax.swing.*;
import java.awt.*;

public class BomberManFrame extends JFrame {

    BomberManFrame(){
        // Initializing frame
        setTitle("Bomber-man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(510, 610);
        setBackground(Color.darkGray);
        setResizable(false);
        setVisible(true);
        //tilføjer nyt BMGraphics til BomberManFrame
        BMGraphics bMGraphics = new BMGraphics();
        add(bMGraphics, BorderLayout.CENTER);
    }



}
