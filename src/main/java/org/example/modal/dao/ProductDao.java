package org.example.modal.dao;

import org.example.modal.entity.Product;

import java.util.List;

public interface ProductDao {
    void addProduct(Product product, List<Product> pendingProducts);
//    void findProductById();
//    void updateProduct();
//    void deleteProductById();
//    void searchName();
//    void setRows();
    void save(List<Product> pendingProducts);
//    void unsaved();
//    void backup();
//    void restore();
//    void exit();
}
