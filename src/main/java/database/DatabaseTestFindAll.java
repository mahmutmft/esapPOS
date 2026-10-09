package database;

import com.mahmutmft.pos.item.Item;
import com.mahmutmft.pos.repository.ItemRepository;

import java.sql.SQLException;
import java.util.List;

public class DatabaseTestFindAll {

    public static void main(String[] args) throws SQLException {

        ItemRepository repository = new ItemRepository();

        List<Item> items = repository.findAll();

        for (Item item : items) {
            System.out.println(
                    item.getId() + " | " +
                            item.getName() + " | " +
                            item.getPrice() + " | " +
                            item.getDescription()
            );
        }
    }
}