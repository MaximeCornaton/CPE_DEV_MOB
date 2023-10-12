package fr.pokemongeo.gr1;

public class Potion extends Item {
    private int pv;

    public Potion(String name, String image, String description, int quantity, int pv) {
        super(name, image, description, quantity);
        this.pv = pv;
    }

    public Potion(String name, String image, String description, int quantity) {
        super(name, image, description, quantity);
        this.pv = 0;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv= pv;
    }
}
