package At_Java;

public class Toy {    
    int id;
    String name;
    int stock;
    double weight;

    public Toy(int id, String name, int stock, double weight) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void decriseStock() {
        this.stock--;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Toy [id=" + id + ", name=" + name + ", stock=" + stock + ", weight=" + weight + "]";
    }
}