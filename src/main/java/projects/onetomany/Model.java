package projects.onetomany;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_id")
    private Mark mark;

    public static Model of(String name, Mark mark) {
        Model model = new Model();
        model.setName(name);
        model.setMark(mark);
        return model;
    }
}
