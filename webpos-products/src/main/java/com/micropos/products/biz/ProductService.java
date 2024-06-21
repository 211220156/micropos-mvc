package com.micropos.products.biz;

import com.micropos.products.model.Product;
import com.micropos.products.model.ProductDetail;

import java.util.List;

public interface ProductService {


    public List<Product> products();
    public Product getProductById(String productId);

    public List<Product> search(String keyword);
    public void updateProduct(String productId, int quantity);
    public ProductDetail showProductDetail(String productId);
}
