package com.micropos.products.db;
import com.micropos.products.jpa.ProductRepository;
import com.micropos.products.model.Product;
import com.micropos.products.model.ProductDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service("JD")
public class JD implements ProductDB {
    @Autowired
    private ProductRepository productRepository;
    private boolean init = false;

    @Override
    public List<Product> getProducts() {
//        不需要爬取数据了，利用spring batch直接读取数据到数据库
        try {
            if (!init) {
                parseJD("Java");
                init = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        throw new RuntimeException("故意的");
//        if (!init) {
//            Product p = new Product("13284888", "Java从入门到精通（第6版）（软件开发视频大讲堂） Java入门经典 Java从入门到精通（第6版）（软件开发视频大讲堂） Java入门经典", 75.8, "https://img13.360buyimg.com/n1/s200x200_jfs/t1/186038/9/7947/120952/60bdd993E41eea7e2/48ab930455d7381b.jpg");
//            p.setQuantity(10);
//            productRepository.save(p);
//            init = true;
//        }
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    public void parseJD(String keyword) throws IOException {
        //获取请求https://search.jd.com/Search?keyword=java
        System.out.println("hello");
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        //解析网页
//        Document document = Jsoup.parse(new URL(url), 10000);
        Document document = Jsoup.connect(url).cookie("thor", "EFAD41B91D876362F624366C2ACCEE3F2F0978E0AEA723AA346D57A884E677A202F82FF76FF39264A4E6D1B09A5154E962E401BC0C382F101BBA8A365D6180591B4F98C0AA2885AA548BB79A52C538BF839E6AE77D2426E668296C09B8B248383BD9D52466E04E8A505FD73246E5BF587C0AC6E1C98C5BA3AF60949DBB772A2523DF17303C28C610D52E7823FAAEA1D3").get();
        //所有js的方法都能用
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements elements = element.getElementsByTag("li");
//        List<Product> list = new ArrayList<>();

        Random random = new Random();

        //获取元素的内容
        for (Element el : elements
        ) {
            try {
                //关于图片特别多的网站，所有图片都是延迟加载的
                String id = el.attr("data-spu");
                String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
                String price = el.getElementsByAttribute("data-price").text();
                String title = el.getElementsByClass("p-name").eq(0).text();
                if (title.indexOf("，") >= 0)
                    title = title.substring(0, title.indexOf("，"));
                if (id == "") continue;

                Product product = new Product(id, title, Double.parseDouble(price), img);
                product.setAverage_rating(random.nextDouble() + random.nextInt(3) + 1);
                product.setRating_number(random.nextInt(100) + 10);
                System.out.println(product.getRating_number());
                System.out.println(product.getAverage_rating());
                assert(product.getRating_number() != 0);

                productRepository.save(product);
            } catch (NumberFormatException ignored) {
            }
        }
    }
    @Override
    public List<Product> getProductsByKeyword(String keyword) {
//        return null;
        return productRepository.searchByName(keyword);
    }

    @Override
    public void updateProduct(String productId, int quantity) {
        productRepository.updateProductQuantity(productId, quantity);
    }

    @Override
    public ProductDetail showProductDetail(String productId) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(getProduct(productId));
        productDetail.setReviewList(null);
        return productDetail;
    }

}
