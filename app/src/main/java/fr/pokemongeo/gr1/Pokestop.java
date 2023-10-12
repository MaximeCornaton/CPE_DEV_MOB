package fr.pokemongeo.gr1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pokestop {
    private String name;
    private String description;
    private int imageResource;

    public Pokestop(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
    }

    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();
        Random random = new Random();
        int nbItems = random.nextInt(5) + 1;

        for (int i = 0; i < nbItems; i++) {
            int itemIndex = random.nextInt(3);
            switch (itemIndex) {
                case 0:
                    itemList.add(new Item("Potion", "potion", "Restaure 20 PV", 3));
                    break;
                case 1:
                    itemList.add(new Item("Super Potion", "super_potion", "Restaure 50 PV", 1));
                    break;
                case 2:
                    itemList.add(new Item("Hyper Potion", "hyper_potion", "Restaure 200 PV", 1));
                    break;
            }
        }
        return itemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
