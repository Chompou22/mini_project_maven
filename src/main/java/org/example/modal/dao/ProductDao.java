package org.example.modal.dao;

import org.example.modal.entity.Product;

import java.util.List;

public interface ProductDao {
    void addProduct(Product product, List<Product> pendingProducts);
    List<Product> getAllProducts();
    Product getProductById(int id);
    void updateProduct(Product product, List<Product> pendingProductsToUpdate);
    //    void deleteProductById();
//    void searchName();
//    void setRows();
    void save(List<Product> pendingProducts);
    void saveUpdates(List<Product> pendingProductsToUpdate);
    List<Product> searchProductByName(String name);
//    void unsaved();
//    void backup();
//    void restore();
//    void exit();
}
