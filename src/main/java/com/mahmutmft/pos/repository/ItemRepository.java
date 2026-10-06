package com.mahmutmft.pos.repository;

import com.mahmutmft.pos.item.Item;
import database.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemRepository {

    public void save(Item item) throws SQLException {
        String sql = "INSERT INTO ITEM (name, price, description, image_path) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getImagePath());
            statement.executeUpdate();
        }
    }
}
