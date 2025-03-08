package org.example.modal.dao;

import org.example.modal.entity.Product;
import org.example.utils.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaginationDaoImp implements PaginationDao{

    // Get products by page number (Assuming pagination logic)
    public List<Product> getProductsByPage(int pageNumber) {
        List<Product> products = new ArrayList<>();
        int itemsPerPage = 10; // Number of items per page

        // Calculate the offset for SQL query
        int offset = (pageNumber - 1) * itemsPerPage;

        String sql = "SELECT * FROM products LIMIT ? OFFSET ?";

        // Use JDBC connection utility
        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemsPerPage);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getString("name"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getInt("quantity")
                    );
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Get the total number of products for pagination
    public int getTotalProducts() {
        int totalProducts = 0;
        String sql = "SELECT COUNT(*) FROM products";

        // Use JDBC connection utility
        try (Connection connection = JDBC.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                totalProducts = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProducts;
    }
}
