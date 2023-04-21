import java.util.ArrayList;
import java.util.Arrays;

public class Part {
    private String name;
    private int items;
    public ArrayList<PartOperation> operations = new ArrayList<>();
    private int contor = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public ArrayList<PartOperation> getOperations() {
        return operations;
    }
    public void addOperations(PartOperation newObject) {
        operations.add(newObject);
        contor++;
    }

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", operations=" + operations +
                '}';
    }
}
