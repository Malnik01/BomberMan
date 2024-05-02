public class Player {
    private int player;
    private int locationX;
    private int locationY;
    private int spreadLenght;

    public Player(int player, int locationY, int locationX, int spreadLenght) {
        //defines player object values
        this.player = player;
        this.locationX = locationX;
        this.locationY = locationY;
        this.spreadLenght = spreadLenght;
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
    public int getSpreadLenght(){
        return this.spreadLenght;
    }

    //set functions
    public void setLocationX(int input){
        this.locationX = input;
    }
    public void setLocationY(int input){
        this.locationY = input;
    }
    public void setSpreadLenght(int input){
        this.spreadLenght = input;
    }


}
