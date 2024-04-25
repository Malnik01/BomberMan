public class Player {
    private int player;
    private int locationX;
    private int locationY;

    public Player(int player, int locationY, int locationX) {
        //defines player object values
        this.player = player;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    //get functions
    public int getPlayer(){
        return this.player;
    }
    public int getLocationX(){
        return this.locationX;
    }
    public int getLocationY(){
        return this.locationY;
    }

    //set functions
    public void setLocationX(int input){
        this.locationX = input;
    }
    public void setLocationY(int input){
        this.locationY = input;
    }

}
