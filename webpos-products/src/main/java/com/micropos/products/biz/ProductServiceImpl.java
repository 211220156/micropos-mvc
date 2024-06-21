package com.micropos.products.biz;

import com.micropos.products.db.ProductDB;
import com.micropos.products.model.Product;
import com.micropos.products.model.ProductDetail;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Resource(name = "JD")//指定使用哪个productDB
    private ProductDB productDB;

    @Resource(name = "JD")
    public void setPosDB(ProductDB productDB) {
        this.productDB = productDB;
    }


    @Override
    @CircuitBreaker(name = "products-breaker", fallbackMethod = "getDefaultProducts")
    public List<Product> products() {
        System.out.println("in /products");
        return productDB.getProducts();
    }

    @Override
    public List<Product> search(String keyword) { return productDB.getProductsByKeyword(keyword); }



    @Override
//    @Cacheable(value = "products", key = "#productId")
    public Product getProductById(String productId) {
        return productDB.getProduct(productId);
    }

    @Override
//    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(String productId, int quantity) {
        productDB.updateProduct(productId, quantity);
    }

    public List<Product> getDefaultProducts(Throwable throwable) {
        System.out.println("in get default Products!");
        List<Product> list = new ArrayList<>();
        try{
            Product p = new Product("13284888", "Java从入门到精通（第6版）（软件开发视频大讲堂） Java入门经典 Java从入门到精通（第6版）（软件开发视频大讲堂） Java入门经典", 75.8, "https://img13.360buyimg.com/n1/s200x200_jfs/t1/186038/9/7947/120952/60bdd993E41eea7e2/48ab930455d7381b.jpg");
            list.add(p);
            return list;
        } catch (Exception e){}
        return list;
    }

    @Override
    public ProductDetail showProductDetail(String productId) {
        return productDB.showProductDetail(productId);
    }
}
