package database;

import com.mahmutmft.pos.item.Item;
import com.mahmutmft.pos.repository.ItemRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseTestSave {

    public static void main(String[] args) throws SQLException {
        ItemRepository itemRepository = new ItemRepository();

        Item item = new Item(
                999,
                "Fanta",
                new BigDecimal("90"),
                "Cold Drink",
                "fanta.jpg"
        );

        itemRepository.save(item);
    }
}