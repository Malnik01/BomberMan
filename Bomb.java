import javax.swing.*;
import java.awt.event.ActionListener;

public class Bomb {

    private int locationX;
    private int locationY;
    private String spreadString;
    private Boolean bool;

    public Bomb(int locationY, int locationX, String spreadString) {
        //definerer værdier i bomb objektet
        this.locationX = locationX;
        this.locationY = locationY;
        this.spreadString = spreadString;
        this.bool = true;

        //exploderer bombe efter 2000 ms
        ActionListener taskPerformer = e -> explode();
        new Timer(2000, taskPerformer).start();
    }
// Gets value
    public int getLocationX() {
        return this.locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }

    public void explode() {
        //kører explodebomb bombe en gang
        if (bool) {
            BMManager.explodeBomb(this, spreadString);
            bool = false;
        }
    }
}
