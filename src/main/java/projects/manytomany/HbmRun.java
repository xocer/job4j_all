package projects.manytomany;

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

            final var book = Book.of("Война и мир");
            final var book1 = Book.of("Евгений Онегин");
            final var book2 = Book.of("Маскарад");
            final var book3 = Book.of("Борис Годунов");


            session.save(book);
            session.save(book1);
            session.save(book2);
            session.save(book3);

            final var author = Author.of("Пушкин");
            author.addBook(book1);
            author.addBook(book3);
            session.persist(author);

            final var author1 = Author.of("Толстой");
            author1.addBook(book);
            session.persist(author1);

            final var author2 = Author.of("Лермонтов");
            author2.addBook(book2);
            author2.addBook(book3);
            session.persist(author2);


            Author forDelete = session.get(Author.class, 3);
            session.remove(forDelete);

            session.getTransaction().commit();
        }
    }
}
