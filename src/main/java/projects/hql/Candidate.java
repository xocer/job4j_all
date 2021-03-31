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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BankOfVacancies bankOfVacancies;

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

            final var sales_managers = BankOfVacancies.of("Sales Managers");
            final var it_managers = BankOfVacancies.of("It Managers");

            final var lentaJob = Vacancy.of("Sales manager in Lenta", "сидеть на кассе");
            final var okeyJob = Vacancy.of("Sales manager in Okey", "сидеть на кассе");

            final var teslaJob = Vacancy.of("Java Developer", "получать деньги");
            final var googleJob = Vacancy.of("Senior Java Developer", "получать много денег");

            sales_managers.addVacancy(lentaJob);
            sales_managers.addVacancy(okeyJob);
            it_managers.addVacancy(teslaJob);
            it_managers.addVacancy(googleJob);

            ivan.setBankOfVacancies(sales_managers);
            alex.setBankOfVacancies(it_managers);

            session.save(ivan);
            session.save(alex);

            session.createQuery(
                    "select distinct can from Candidate can join fetch can.bankOfVacancies bov join fetch bov.vacancies")
                    .list()
                    .forEach(System.out::println);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
