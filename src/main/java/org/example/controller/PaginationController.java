package org.example.controller;

import org.example.modal.dao.PaginationDaoImp;
import org.example.modal.entity.Product;

import java.util.List;

public class PaginationController {

    private final PaginationDaoImp paginationDao;

    public PaginationController() {
        this.paginationDao = new PaginationDaoImp();
    }

    // Display first page
    public void displayFirstPage() {
        List<Product> products = paginationDao.getProductsByPage(2);
        displayProducts(products);
    }

    // Display next page
    public void displayNextPage(int currentPage) {
        int nextPage = currentPage + 1;
        List<Product> products = paginationDao.getProductsByPage(nextPage);
        displayProducts(products);
    }

    // Display previous page
    public void displayPreviousPage(int currentPage) {
        int previousPage = currentPage > 1 ? currentPage - 1 : 1;
        List<Product> products = paginationDao.getProductsByPage(previousPage);
        displayProducts(products);
    }

    // Display last page
    public void displayLastPage() {
        int totalPages = getTotalPages();
        List<Product> products = paginationDao.getProductsByPage(totalPages);
        displayProducts(products);
    }

    // Display a specific page by number
    public void displayPage(int pageNumber) {
        List<Product> products = paginationDao.getProductsByPage(pageNumber);
        displayProducts(products);
    }

    // Helper method to print products to the console
    private void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    // Get the total number of pages based on total products
    public int getTotalPages() {
        int totalProducts = paginationDao.getTotalProducts();
        return (int) Math.ceil((double) totalProducts / 10);
    }
}
