//package com.micropos.products.db;
//
//import com.micropos.products.jpa.ReviewRepository;
//import com.micropos.products.jpa.AmazonRepository;
//import com.micropos.products.model.Amazon.AmazonProduct;
//import com.micropos.products.model.Amazon.Review;
//import com.micropos.products.model.Product;
//import com.micropos.products.model.ProductDetail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service("Amazon")
//public class Amazon implements ProductDB {
//    @Autowired
//    private AmazonRepository amazonRepository;
//    @Autowired
//    private ReviewRepository reviewRepository;
//    @Override
//    public List<Product> getProducts() {
//        List<AmazonProduct> amazonProductList = amazonRepository.findAll();
//        List<Product> productList = new ArrayList<>();
//        for (AmazonProduct amazonProduct : amazonProductList) {
//            productList.add(new Product(amazonProduct.getParent_asin(), amazonProduct.getParent_asin(), amazonProduct.getPrice(),
//                    amazonProduct.getMain_category(), amazonProduct.getQuantity(), amazonProduct.getTitle(), 1, amazonProduct.getImages(),
//                    amazonProduct.getAverage_rating(), amazonProduct.getRating_number()));
//        }
//        return productList;
//    }
//    @Override
//    public Product getProduct(String productId) {
//        for (AmazonProduct amazonProduct : amazonRepository.findAll()) {
//            if (productId.equals(amazonProduct.getParent_asin())) {
//                return new Product(amazonProduct.getParent_asin(), amazonProduct.getParent_asin(), amazonProduct.getPrice(),
//                        amazonProduct.getMain_category(), amazonProduct.getQuantity(), amazonProduct.getTitle(), 1, amazonProduct.getImages(),
//                        amazonProduct.getAverage_rating(), amazonProduct.getRating_number());
//            }
//        }
//        return null;
//    }
//    @Override
//    public List<Product> getProductsByKeyword(String keyword) {
//        return new ArrayList<>();
//    }
//    @Override
//    public void updateProduct(String productId, int quantity) {
//        amazonRepository.updateProductQuantity(productId, quantity);
//    }
//    @Override
//    public ProductDetail showProductDetail(String productId) {
//        ProductDetail productDetail = new ProductDetail();
//        Product product = null;
//        for (AmazonProduct amazonProduct : amazonRepository.findAll()) {
//            if (amazonProduct.getParent_asin().equals(productId)) {
//                product = new Product(amazonProduct.getParent_asin(), amazonProduct.getParent_asin(), amazonProduct.getPrice(),
//                        amazonProduct.getMain_category(), amazonProduct.getQuantity(), amazonProduct.getTitle(), 1, amazonProduct.getImages(),
//                        amazonProduct.getAverage_rating(), amazonProduct.getRating_number());
//                break;
//            }
//        }
//        if (product == null) return null;
//        productDetail.setProduct(product);
//        //找出相关的review
//        List<Review> reviewList = new ArrayList<>();
//        for (Review review : reviewRepository.findAll()) {
//            if (review.getParent_asin().equals(productId)) {
//                reviewList.add(review);
//            }
//        }
//        productDetail.setReviewList(reviewList);
//        return productDetail;
//    }
//}
