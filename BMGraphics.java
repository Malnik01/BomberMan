import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BMGraphics extends JPanel implements KeyListener {
    int boxSize = 50;
    Object tile;
    static String player1Direction;
    static String player2Direction;


    BMGraphics() { //kontructor for klassen referer til UIpanel
        BMManager bmManager = new BMManager();
        //sætter panelets egenskaber
        setSize(300, 410);
        setVisible(true);
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.white);
        //sætter boxsize til at være en 7'ende del af panelets bredde
        boxSize = getWidth() / 7;
    }

    public void paintComponent(Graphics g) { //funktion som tegner grafikken g
        super.paintComponent(g);

        //instantierer variabler for brikernes lokation
        int posX = 0;
        int posY = 0;
        int startPos = 10;

        //repeats for rows (gametable[row][col])
        for (int row = 0; row < BMManager.gameTable.length; row++) {
            //changes location variables
            if (posX >= 9) {
                posX = 0;
                posY++;
            }
            //repeats for rows (gametable[row][col])
            for (int col = 0; col < BMManager.gameTable[row].length; col++) {
                //repeats for third axis
                for (int axis3 = 1; axis3 >= 0; axis3--) {
                    //definerer tile som locationen i loopet
                    tile = BMManager.gameTable[row][col][axis3];
                    //tegner tile hvis den er enten player, bomb, wall, breakablewall eller fire
                    if (tile instanceof Player) {
                        if (((Player) tile).getPlayer() == 1) {
                            g.setColor(Color.green);
                            g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                        } else {
                            g.setColor(Color.yellow);
                            g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                        }
                    }
                    if (tile instanceof Bomb) {
                        g.setColor(Color.blue);
                        g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                    }
                    if (tile == "wall") {
                        g.setColor(Color.gray);
                        g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                    } else if (tile == "breakablewall") {
                        g.setColor(Color.lightGray);
                        g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                    } else if (tile == "fire") {
                        g.setColor(Color.red);
                        g.fillRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(boxSize * posX + startPos, boxSize * posY + startPos, boxSize, boxSize);
                }
                posX++;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //definerer taskPerfomer som kører reoaint efter noget tid (se case t og m)
        ActionListener taskPerformer = evt -> repaint();
        //får keycode og key fra keyboard input
        int keyCode = e.getKeyCode();
        String key = KeyEvent.getKeyText(keyCode);
        switch (key) {
            case "W", "A", "S", "D":
                //bevæger player1
                if (BMManager.player1 != null) {
                    player1Direction = key;
                    BMManager.gameTable[BMManager.player1.getLocationY()][BMManager.player1.getLocationX()][1] = "N/A";
                    BMManager.movePlayer(BMManager.player1, key);
                    if (BMManager.player1 != null) {
                        BMManager.gameTable[BMManager.player1.getLocationY()][BMManager.player1.getLocationX()][1] = BMManager.player1;
                    }
                    revalidate();
                    repaint();
                }
                break;
            case "T":
                //placerer bombe hvis player1 ikke er null. har et delay på 2000 ms
                if (BMManager.player1 != null) {
                    try {
                        BMManager.placeBomb(BMManager.player1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    new Timer(2000, taskPerformer).start();
                }
                break;
            case "Right", "Left", "Up", "Down":
                //bevæger player2
                if (BMManager.player2 != null) {
                    player2Direction = key;
                    BMManager.gameTable[BMManager.player2.getLocationY()][BMManager.player2.getLocationX()][1] = "N/A";
                    BMManager.movePlayer(BMManager.player2, key);
                    if (BMManager.player2 != null) {
                        BMManager.gameTable[BMManager.player2.getLocationY()][BMManager.player2.getLocationX()][1] = BMManager.player2;
                    }
                    revalidate();
                    repaint();
                }
                break;
            case "M":
                //placerer bombe hvis player2 ikke er null. har et delay på 2000 ms
                if (BMManager.player2 != null) {
                    try {
                        BMManager.placeBomb(BMManager.player2);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    new Timer(2000, taskPerformer).start();
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //når man løfter knappen bliver værdien til "N/A"
        int keyCode = e.getKeyCode();
        String key = KeyEvent.getKeyText(keyCode);
        if (key.equals("W") || key.equals("A") || key.equals("S") || key.equals("D") || key.equals("T")) {
            player1Direction = "N/A";
        }
        if (key.equals("Up") || key.equals("Left") || key.equals("Down") || key.equals("Right") || key.equals("M")) {
            player2Direction = "N/A";
        }
    }
}
