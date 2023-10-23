package fr.pokemongeo.gr1;

public class Ball extends Item {
    private int catchRate;

    public Ball(String name, String description, int quantity, int catchRate) {
        super(name, "baseline_catching_pokemon_24", description, quantity);
        this.catchRate = catchRate;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(int catchRate) {
        this.catchRate= catchRate;
    }
}
