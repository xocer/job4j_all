package projects.hql;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "bank_of_vacancies")
public class BankOfVacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public static BankOfVacancies of(String name) {
        final var bankOfVacancies = new BankOfVacancies();
        bankOfVacancies.name = name;
        return bankOfVacancies;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }
}
