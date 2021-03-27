package projects.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            final var sedan = Model.of("Sedan");
            final var cabriolet = Model.of("Cabriolet");
            final var truck = Model.of("Truck");
            final var hatchback = Model.of("hatchback");
            final var bus = Model.of("Bus");

            session.save(sedan);
            session.save(cabriolet);
            session.save(truck);
            session.save(hatchback);
            session.save(bus);

            final var ford = Mark.of("Ford"); // одна роль

            ford.addModel(session.load(Model.class, 1));
            ford.addModel(session.load(Model.class, 2));
            ford.addModel(session.load(Model.class, 3));
            ford.addModel(session.load(Model.class, 4));
            ford.addModel(session.load(Model.class, 5));

            session.save(ford);

            session.getTransaction().commit();
        }
    }
}
