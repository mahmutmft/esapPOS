package com.mahmutmft.pos.repository;

import com.mahmutmft.pos.item.Item;
import database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemRepository {

    private final ConnectionProvider connectionProvider;

    public ItemRepository() {
        this(DatabaseConnection::getConnection);
    }

    ItemRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = Objects.requireNonNull(connectionProvider);
    }

    public void save(Item item) throws SQLException {
        String query = "INSERT INTO ITEM (name, price, description, image_path) VALUES (?,?,?,?)";
        try (Connection connection = connectionProvider.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getImagePath());
            statement.executeUpdate();
        }
    }

    public List<Item> findAll() throws SQLException{
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM ITEM";

        try (Connection connection = connectionProvider.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet =  statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                String description = resultSet.getString("description");
                String imagePath = resultSet.getString("image_path");
                Item item = new Item(id, name, price, description, imagePath);
                items.add(item);
            }
        }
        return items;
    }

    public Item findById(int id) throws SQLException{
        String query = "SELECT * FROM ITEM WHERE ID = ?";
        try (Connection connection = connectionProvider.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int itemId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                String description = resultSet.getString("description");
                String imagePath = resultSet.getString("image_path");
                return new Item(itemId,name,price,description,imagePath);
            }
            return null;
        }
    }
}
