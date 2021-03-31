package projects.hql;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String experience;
    private double salary;

    public static Candidate of(String name, String experience, double salary) {
        final var candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
    }

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            final var ivan = Candidate.of("ivan", "2 years", 3000);
            final var alex = Candidate.of("Alex", "1 years", 2000);
            final var bob = Candidate.of("Bob", "6 month", 1000);

            session.save(ivan);
            session.save(alex);
            session.save(bob);

            // SELECT
            session.createQuery("from Candidate ").list().forEach(System.out::println);

            session.createQuery("from Candidate where id = : fId")
                    .setParameter("fId", 1)
                    .list().forEach(System.out::println);

            session.createQuery("from Candidate where name = : fName")
                    .setParameter("fName", "Bob")
                    .list().forEach(System.out::println);

            // UPDATE
            session.createQuery(
                    "update Candidate set name = : nName, experience = : nExperience, salary = :newSalary where id = :fId")
                    .setParameter("nName", "Bob2")
                    .setParameter("nExperience", "5 years")
                    .setParameter("newSalary", 1500d)
                    .setParameter("fId", 3)
                    .executeUpdate();
            session.createQuery("from Candidate ").list().forEach(System.out::println);

            // DELETE
            session.createQuery("delete from Candidate where id = :id")
                    .setParameter("id", 1)
                    .executeUpdate();
            session.createQuery("from Candidate ").list().forEach(System.out::println);

            // INSERT
            session.createQuery("insert into Candidate (name, experience, salary) "
                    + "select concat(s.name, 'NEW'), concat(s.experience, 'NEW'), s.salary + 400  "
                    + "from Candidate s where s.id = :fId")
                    .setParameter("fId", 2)
                    .executeUpdate();
            session.createQuery("from Candidate ").list().forEach(System.out::println);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
