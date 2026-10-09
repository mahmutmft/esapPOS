package com.mahmutmft.pos.repository;

import com.mahmutmft.pos.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockMakers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

class ItemRepositoryTest {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ItemRepository repository;

    @BeforeEach
    void setUp() {
        connection = mock(Connection.class, withSettings().mockMaker(MockMakers.PROXY));
        statement = mock(PreparedStatement.class, withSettings().mockMaker(MockMakers.PROXY));
        resultSet = mock(ResultSet.class, withSettings().mockMaker(MockMakers.PROXY));
        repository = new ItemRepository(() -> connection);
    }

    @Test
    void savesItemUsingPreparedStatement() throws SQLException {
        when(connection.prepareStatement("INSERT INTO ITEM (name, price, description, image_path) VALUES (?,?,?,?)"))
                .thenReturn(statement);
        Item item = new Item(7, "Fanta", new BigDecimal("90.00"), "Cold drink", "fanta.jpg");

        repository.save(item);

        verify(statement).setString(1, "Fanta");
        verify(statement).setBigDecimal(2, new BigDecimal("90.00"));
        verify(statement).setString(3, "Cold drink");
        verify(statement).setString(4, "fanta.jpg");
        verify(statement).executeUpdate();
        verify(connection).close();
    }

    @Test
    void findsAndMapsAllItems() throws SQLException {
        prepareQuery("SELECT * FROM ITEM");
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Fanta", "Water");
        when(resultSet.getBigDecimal("price")).thenReturn(new BigDecimal("90.00"), new BigDecimal("60.00"));
        when(resultSet.getString("description")).thenReturn("Orange drink", "Still water");
        when(resultSet.getString("image_path")).thenReturn("fanta.jpg", "water.jpg");

        List<Item> items = repository.findAll();

        assertEquals(2, items.size());
        assertItem(items.get(0), 1, "Fanta", "90.00", "Orange drink", "fanta.jpg");
        assertItem(items.get(1), 2, "Water", "60.00", "Still water", "water.jpg");
        verify(connection).close();
    }

    @Test
    void returnsEmptyListWhenNoItemsExist() throws SQLException {
        prepareQuery("SELECT * FROM ITEM");
        when(resultSet.next()).thenReturn(false);

        List<Item> items = repository.findAll();

        assertEquals(List.of(), items);
    }

    @Test
    void findsItemById() throws SQLException {
        prepareQuery("SELECT * FROM ITEM WHERE ID = ?");
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(3);
        when(resultSet.getString("name")).thenReturn("Coffee");
        when(resultSet.getBigDecimal("price")).thenReturn(new BigDecimal("80.00"));
        when(resultSet.getString("description")).thenReturn("Espresso");
        when(resultSet.getString("image_path")).thenReturn("coffee.jpg");

        Item item = repository.findById(3);

        verify(statement).setInt(1, 3);
        assertItem(item, 3, "Coffee", "80.00", "Espresso", "coffee.jpg");
    }

    @Test
    void returnsNullWhenItemIdDoesNotExist() throws SQLException {
        prepareQuery("SELECT * FROM ITEM WHERE ID = ?");
        when(resultSet.next()).thenReturn(false);

        Item item = repository.findById(999);

        verify(statement).setInt(1, 999);
        assertNull(item);
    }

    @Test
    void propagatesConnectionFailure() {
        SQLException failure = new SQLException("Database unavailable");
        ItemRepository failingRepository = new ItemRepository(() -> {
            throw failure;
        });

        SQLException thrown = assertThrows(SQLException.class, failingRepository::findAll);

        assertEquals(failure, thrown);
    }

    private void prepareQuery(String query) throws SQLException {
        when(connection.prepareStatement(query)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
    }

    private void assertItem(Item item, int id, String name, String price, String description, String imagePath) {
        assertAll(
                () -> assertEquals(id, item.getId()),
                () -> assertEquals(name, item.getName()),
                () -> assertEquals(new BigDecimal(price), item.getPrice()),
                () -> assertEquals(description, item.getDescription()),
                () -> assertEquals(imagePath, item.getImagePath())
        );
    }
}
