import org.mongodb.morphia.annotations.Id;

public class SomePojo {
    @Id
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
