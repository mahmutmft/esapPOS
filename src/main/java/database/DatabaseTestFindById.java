package database;

import com.mahmutmft.pos.item.Item;
import com.mahmutmft.pos.repository.ItemRepository;

import java.sql.SQLException;

public class DatabaseTestFindById {

    public static void main(String[] args) throws SQLException {

        ItemRepository repository = new ItemRepository();

        Item item1 = repository.findById(1);
        printItem(item1);

        Item item2 = repository.findById(2);
        printItem(item2);

        Item item3 = repository.findById(999);
        printItem(item3);
    }

    private static void printItem(Item item) {
        if (item == null) {
            System.out.println("Item not found");
            return;
        }

        System.out.println(
                item.getId() + " | " +
                        item.getName() + " | " +
                        item.getPrice() + " | " +
                        item.getDescription() + " | " +
                        item.getImagePath()
        );
    }
}
