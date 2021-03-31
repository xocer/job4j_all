package projects.integration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    private int id;

    private String name;

    private String description;

    private Timestamp created;

    public Order() {
    }

    public Order(int id, String name, String description, Timestamp created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public static Order of(String name, String description) {
        Order o = new Order();
        o.name = name;
        o.description = description;
        o.created = new Timestamp(System.currentTimeMillis());
        return o;
    }
}