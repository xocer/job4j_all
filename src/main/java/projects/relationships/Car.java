package projects.relationships;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

public class Car {
    private int id;
    private String name;

    @ManyToOne
    private Engine engine;

    @ManyToOne
    private HistoryOwner historyOwner;

    @ManyToMany
    private List<Driver> drivers;
}
