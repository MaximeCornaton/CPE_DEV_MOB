package fr.pokemongeo.gr1;


public abstract class Item {
    private String name;
    private String image;
    private String description;
    private int quantity;

    public Item(String name, String image, String description, int quantity) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getFront() {
        return image;
    }
    public void setFront(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }

    public void resetQuantity() {
        this.quantity = 0;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
