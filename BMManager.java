import java.util.Objects;
import java.util.Random;

public class BMManager {
    public static Object[][][] gameTable;
    static int player1LocationX = 0;
    static int player1LocationY = 0;
    static int player2LocationX = 8;
    static int player2LocationY = 10;
    static int fireLocationY;
    static int fireLocationX;
    static int spread;


    static Player player1 = new Player(1, player1LocationY, player1LocationX, 2);
    static Player player2 = new Player(2, player2LocationY, player2LocationX, 2);

    public BMManager() {
        //intializes gametable, and its contents
        gameTable = new Object[11][9][2];
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            gameTable[random.nextInt(1, 11)][random.nextInt(1, 9)][0] = "crate";
        }
        for (int row = 0; row < gameTable.length; row++) {

            for (int col = 0; col < gameTable[row].length; col++) {
                // covers the matrix in breakable walls
                 if (gameTable[row][col][0] != "crate"){
                    gameTable[row][col][0] = "breakablewall";
                }
                //creates the unbreakable walls
                if ((row % 2) == 1 && (col % 2) == 1) {
                    gameTable[row][col][0] = "wall";
                }
                // removes blocks in the spawn area
                if ((row == 0 && col == 1) || (row == 1 && col == 0) || (row == 9 && col == 8) ||
                        (row == 10 && col == 7)|| (row == 0 && col == 0) || (row == 10 && col == 8) ) {
                    gameTable[row][col][0] = "N/A";
                }
            }
        }
        //places the players
        gameTable[player1.getLocationY()][player1.getLocationX()][1] = player1;
        gameTable[player2.getLocationY()][player2.getLocationX()][1] = player2;
    }

    public static void movePlayer(Player player, String direction) {
        //moves player in desired direction. cannot move if location + direction is a wall or outside gametable[][]
        //int[] used for movement. uses key-input to return a [x, y] direction
        int[] Direction = getInts(direction);

        int directionY = Direction[1];
        int directionX = Direction[0];
        int newY = player.getLocationY() + directionY;
        int newX = player.getLocationX() + directionX;
        //checks if the player can move
        if (newY >= 0 && newY <= 10 && newX >= 0 && newX <= 8 && gameTable[newY][newX][0] != "wall" && gameTable[newY][newX][0]
        != "breakablewall" && gameTable[newY][newX][1] != "player") {
            player.setLocationY(newY);
            player.setLocationX(newX);
        }
        //checks if the player moved into fire
        checkWin();
    }

    private static int[] getInts(String direction) {
        //gets direction on matrix. uses key input in switch
        int[][] DirectionList = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}, {0, 0}};
        int number = switch (direction) {
            case "W", "Up", "0" -> 0;
            case "A", "Left", "1" -> 1;
            case "S", "Down", "2" -> 2;
            case "D", "Right", "3" -> 3;
            default -> 4;
        };

        return DirectionList[number];
    }


    public static void placeBomb(Player player) throws InterruptedException {
        //places a fire-bomb on players feet.
        Bomb bomb = new Bomb(player.getPlayer(), player.getLocationY(), player.getLocationX(), "fire", player.getSpreadLenght());
        gameTable[player.getLocationY()][player.getLocationX()][0] = bomb;
    }


    public static void explodeBomb(Bomb bomb, String SpreadString){
        //initialises values used in logic. coordinates, spreadlenght and bomb-type
        fireLocationY = bomb.getLocationY();
        fireLocationX = bomb.getLocationX();
        spread = bomb.getSpread();
        gameTable[fireLocationY][fireLocationX][0] = SpreadString;
        int[][] DirectionList = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}, {0, 0}};
        //looper for alle directions i Directionlist (minus 0, 0 bevægelsen)
        for (int i = 0; i <= DirectionList.length-1; i++) {
            int[] fireDirection = getInts(String.valueOf(i));
            int fireDirectionY = fireDirection[1];
            int fireDirectionX = fireDirection[0];
            for (int j = 0; j <= spread && fireLocationY + fireDirectionY * j <= 10 && fireLocationX + fireDirectionX * j <= 8
                && fireLocationY + fireDirectionY * j >= 0 && fireLocationX + fireDirectionX * j >= 0; j++){
                if (gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] != "wall") {
                    if (SpreadString == "fire" && (gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] == "crate")) {
                        if (bomb.getOwner() == 1){
                            player1.setSpreadLenght(player1.getSpreadLenght()+1);
                            gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX][0] = SpreadString;
                        } else {
                            player2.setSpreadLenght(player2.getSpreadLenght()+1);
                            gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX][0] = SpreadString;
                        }
                        break;
                    } else if (SpreadString == "fire" && (gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] == "breakablewall")){
                        gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX][0] = SpreadString;
                        break;
                    } else if (!(gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] == "breakablewall") &&
                            !(gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] == "crate")) {
                        gameTable[fireLocationY + fireDirectionY * j][fireLocationX + fireDirectionX * j][0] = SpreadString;
                    }
                }
                else {
                    break;
                }
            }
        }
        //checks if one of the players got hit by fire
        checkWin();
        //explodes an N/A bomb, if current bomb is a firebomb. Used to remove the fire
        if (Objects.equals(SpreadString, "fire")){
            Bomb bomb2 = new Bomb(0, fireLocationY, fireLocationX, "N/A", spread);
            gameTable[fireLocationY][fireLocationX][0] = bomb2;
        }

    }
    public static void checkWin(){
        // Which player wins
        if (BMManager.player1 != null && BMManager.player2 != null) {
            // player 1 dies == player 2 win
            if (gameTable[player1.getLocationY()][player1.getLocationX()][0] == "fire") {
                System.out.println("Player 2 Wins!");
                gameTable[player1.getLocationY()][player1.getLocationX()][1] = "N/A";
                player1 = null;
            }
            // player 2 dies == player 1 wins
            if (gameTable[player2.getLocationY()][player2.getLocationX()][0] == "fire") {
                System.out.println("Player 1 Wins!");
                gameTable[player2.getLocationY()][player2.getLocationX()][1] = "N/A";
                player2 = null;

            }
        }
    }

}
