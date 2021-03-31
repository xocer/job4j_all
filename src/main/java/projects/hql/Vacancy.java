package projects.hql;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    public static Vacancy of(String name, String description) {
        final var vacancy = new Vacancy();
        vacancy.name = name;
        vacancy.description = description;
        return vacancy;
    }
}
