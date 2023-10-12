package fr.pokemongeo.gr1;

public class Ball extends Item {
    private int catchRate;

    public Ball(String name, String image, String description, int quantity, int catchRate) {
        super(name, image, description, quantity);
        this.catchRate = catchRate;
    }
    public Ball(String name, String image, String description, int quantity) {
        super(name, image, description, quantity);
        this.catchRate = 0;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(int catchRate) {
        this.catchRate= catchRate;
    }
}
