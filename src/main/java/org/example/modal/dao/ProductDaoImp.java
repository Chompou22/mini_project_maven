package org.example.modal.dao;

import org.example.modal.entity.Product;
import org.example.utils.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImp implements ProductDao {

    private List<Product> pendingProductsToUpdate = new ArrayList<>(); // Store pending updates

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
        ResultSet generatedKeys = null;

        try {
            connection = JDBC.getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO products (name, unit_price, quantity, imported_date) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            for (Product product : pendingProducts) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getUnitPrice());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.setDate(4, java.sql.Date.valueOf(product.getImportedDate()));
                preparedStatement.executeUpdate();

                // Retrieve generated keys
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setId(generatedId); // Assign the correct ID to the product
                    System.out.println("Saved product with ID: " + generatedId);
                }
            }

            connection.commit();
            pendingProducts.clear();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBC.close(generatedKeys);
            JDBC.close(preparedStatement);
            JDBC.close(connection);
        }
    }

    @Override
    public void updateProduct(Product product, List<Product> pendingProductsToUpdate) {
        pendingProductsToUpdate.add(product); // Add the product to the pending list
        System.out.println("Product added to pending updates list.");
    }

    @Override
    public void saveUpdates(List<Product> pendingProductsToUpdate) {
        if (pendingProductsToUpdate.isEmpty()) return;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC.getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE products SET name = ?, unit_price = ?, quantity = ? WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);

            for (Product product : pendingProductsToUpdate) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getUnitPrice());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.setInt(4, product.getId());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Product updated with ID: " + product.getId());
                } else {
                    System.out.println("Product update failed. No product found with ID: " + product.getId());
                }
            }

            connection.commit();
            pendingProductsToUpdate.clear(); // Clear pending updates

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
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

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"), // Retrieve ID
                        resultSet.getString("name"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("imported_date").toLocalDate()
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product(
                            resultSet.getInt("id"), // Retrieve correct ID
                            resultSet.getString("name"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getInt("quantity"),
                            resultSet.getDate("imported_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Use the "%" symbol to match any substring in the name
            preparedStatement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getInt("quantity"),
                            resultSet.getDate("imported_date").toLocalDate()
                    );
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
