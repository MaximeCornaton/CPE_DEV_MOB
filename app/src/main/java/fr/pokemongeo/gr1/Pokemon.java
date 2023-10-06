package fr.pokemongeo.gr1;

public class Pokemon {
    private int order;
    private boolean capture;
    private String name;
    private double height;
    private double weight;
    private int frontResource;
    private POKEMON_TYPE type1;
    private POKEMON_TYPE type2;
    public Pokemon() {
        order = 1;
        name = "Unknown";
        frontResource = R.drawable.p1;
        type1 = POKEMON_TYPE.Plante;
    }
    public Pokemon(int order, String name, int frontResource,
                   POKEMON_TYPE type1, POKEMON_TYPE type2, double weight, double height) {
        this.order = order;
        this.capture = false;
        this.name = name;
        this.frontResource = frontResource;
        this.type1 = type1;
        this.type2 = type2;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public int getFrontResource() {
        return frontResource;
    }
    public void setFrontResource(int frontResource) {
        this.frontResource = frontResource;
    }
    public POKEMON_TYPE getType1() {
        return type1;
    }
    public void setType1(POKEMON_TYPE type1) {
        this.type1 = type1;
    }
    public POKEMON_TYPE getType2() {
        return type2;
    }
    public void setType2(POKEMON_TYPE type2) {
        this.type2 = type2;
    }
    public String getType1String() {
        return type1.name();
    }
    public String getType2String() {
        if (type2 == null) {
            return null;
        }
        return type2.name();
    }
    public boolean isCapture() { return capture; }
}
