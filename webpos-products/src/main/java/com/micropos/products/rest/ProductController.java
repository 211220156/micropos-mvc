package com.micropos.products.rest;


import com.micropos.api.dto.OrderDTO;
import com.micropos.api.dto.OrderItemDTO;
import com.micropos.api.dto.ProductDTO;
import com.micropos.api.utils.UserContext;
import com.micropos.products.model.*;
import com.micropos.products.biz.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ModelMapper modelMapper;
    private ProductService productService;
    private RestTemplate restTemplate;
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/settings")
    public ResponseEntity<List<Settings>> listSettings() {
        List<Settings> settings = new ArrayList<>();
        settings.add(new Settings(1,
                new Settings.Setting("Standalone Point of Sale", "Store-Pos", "10086", "10087", "15968774896", "", "$", "10", "", ""),
                "d36d"));
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("1711853606", "drink", 1711853606));
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> listProducts() {
        System.out.println("user:" + UserContext.getUser());
        return new ResponseEntity<>(
                productService.products().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam("keyword") String keyword) {
        System.out.println("in search");
        return new ResponseEntity<>(
                productService.search(keyword).stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String productId) {
        System.out.println("in get product by id");
        Product product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(product, ProductDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/products/charge")
    @CircuitBreaker(name = "products-breaker", fallbackMethod = "fail")
    public ResponseEntity<String> charge(@RequestBody ChargeRequest request) {
        System.out.println("in charge!");
        double total = 0;
        OrderDTO orderDTO = new OrderDTO(UserContext.getUser(), 0, new ArrayList<>(), "finish");

        for (ProductUpdateRequest p : request.getPur()) {
            System.out.println(p.getProductId() + " " + p.getQuantity());
            Product product = productService.getProductById(p.getProductId());
            if (product == null || product.getQuantity() < p.getQuantity()) {//先判断库存是否充足
                //库存不足返回400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not existed or no enough quantity!");
            }

            total += product.getPrice() * p.getQuantity();
            orderDTO.getItems().add(new OrderItemDTO(p.getProductId(), p.getQuantity(), product.getPrice(), product.getImg()));
        }
        orderDTO.setAmount(total);
        //创建订单
        String ret = restTemplate.postForObject("http://webpos-orders/createOrder", orderDTO, String.class);
        //创建订单成功，且没抛出异常，才会修改库存
        request.getPur().forEach(p -> {
            productService.updateProduct(p.getProductId(), productService.getProductById(p.getProductId()).getQuantity() - p.getQuantity());
        });
        return ResponseEntity.ok("Data updated! orders return : " + ret);
    }
    public ResponseEntity<String> fail(ChargeRequest request, Throwable throwable) {
        System.out.println("Circuit breaker triggered, fallback method called.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update data :" + throwable.getMessage());
    }

    @GetMapping("/products/detail/{productId}")
    public ResponseEntity<ProductDetail> getProductDetail(@PathVariable String productId) {
        System.out.println("get product detail of " + productId);
        ProductDetail productDetail = productService.showProductDetail(productId);
        if (productDetail == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("find product !");
        System.out.println(productDetail.getProduct());
        return new ResponseEntity<>(productDetail, HttpStatus.OK);
    }
}
