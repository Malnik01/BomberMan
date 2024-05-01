import javax.swing.*;
import java.awt.event.ActionListener;

public class Bomb {


    private int owner;
    private int locationX;
    private int locationY;
    private String spreadString;
    private Boolean bool;
    private int spread;

    public Bomb(int owner, int locationY, int locationX, String spreadString, int spread) {
        //definerer værdier i bomb objektet
        this.owner = owner;
        this.locationX = locationX;
        this.locationY = locationY;
        this.spreadString = spreadString;
        this.bool = true;
        this.spread = spread;

        //exploderer bombe efter 2000 ms
        ActionListener taskPerformer = e -> explode();
        new Timer(2000, taskPerformer).start();
    }
// Gets value
    public int getOwner(){return this.owner;}
    public int getLocationX() {
        return this.locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }
    public int getSpread(){return this.spread;}

    public void explode() {
        //kører explodebomb bombe en gang
        if (bool) {
            BMManager.explodeBomb(this, spreadString);
            bool = false;
        }
    }
}
