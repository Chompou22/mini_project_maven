package org.example.modal.dao;

import org.example.modal.entity.Product;
import org.example.utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImp implements ProductDao {

    @Override
    public void addProduct(Product product, List<Product> pendingProducts) {
        pendingProducts.add(product);
        System.out.println("Product added to pending list.");
    }

    @Override
    public void save(List<Product> pendingProducts) {
        if (pendingProducts.isEmpty()) return;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC.getConnection();
            connection.setAutoCommit(false); // Start transaction

            String sql = "INSERT INTO products (name, unit_price, quantity) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            for (Product product : pendingProducts) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getUnitPrice());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.executeUpdate();
            }

            connection.commit(); // Commit transaction
            pendingProducts.clear(); // Clear the list after successful insertion

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBC.close(preparedStatement);
            JDBC.close(connection);
        }
    }
}
