package projects.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        List<Mark> list;

        try (Session session = sf.openSession()) {
            session.beginTransaction();
            final var ford = Mark.of("Ford"); // одна роль

            final var sedan = Model.of("Sedan",ford);
            final var cabriolet = Model.of("Cabriolet", ford);

            ford.addModel(sedan);
            ford.addModel(cabriolet);

            session.save(ford);

            list = session.createQuery("select distinct m from Mark m join fetch m.models mod join fetch mod.mark").list();

            session.getTransaction().commit();
        }

        for (Mark category : list) {
            for (Model task : category.getModels()) {
                System.out.println(task);
            }
        }
    }
}
