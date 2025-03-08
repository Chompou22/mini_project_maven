package org.example.modal.dao;

import org.example.modal.entity.Product;

import java.util.List;

public interface PaginationDao {
    List<Product> getProductsByPage(int pageNumber);
    int getTotalProducts();
}
