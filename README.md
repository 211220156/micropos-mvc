# Micro WebPos - Spring MVC Version

本项目采用**前后端分离**的方式实现了一个**微服务版本的在线购物平台**，用户可以登录，并查看商品列表，点击商品可以查看商品详情，并下单购物。购物后自动生成订单、物流信息。用户可以查看自己的订单、物流。前后端通过RestFul风格的api来交互。

**技术栈：Spring Boot、Spring MVC、Spring Cloud Gateway、Redis、MySQL82、RabbitMQ-3.8、MyBatis-plus。**

效果展示：[微服务版本的在线购物平台_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV16rgQeAEb8/?vd_source=4729de3939b68fbfb11b838e515c1fe1)



## 项目介绍

在client目录下存放的是前端相关文件。初始入口页面为`client/dist/login.html`.

到各个`webpos-xxx`目录下运行：

```
mvn spring-boot:run
```

即可启动后端各个服务。

后端各个服务简介如下：

- **webpos-api**：通用模块，各个服务都对其有依赖，定义了一些通用的配置和数据DTO，如`MvcConfig`、`UserInfoInterceptor`以及一些自定义的异常`UnauthorizedException`等。
- **webpos-batch**：用于从Amazon爬取商品数据到本地。这是一个额外的功能，并没有作为一个服务整合项目中，不必启动。
- **webpos-discovery**：`Eureka`服务发现、注册中心，运行在本地8761号端口.应该最先启动这一模块，便于其他模块进行服务注册。
- **webpos-gateway**：采用Spring Cloud Gateway实现的网关，对未登录的用户请求进行拦截，将携带的token解析得到登录用户的id，附加到http header中，便于后续服务获取。运行在本地8081端口。
- **webpos-user**：用户登录的模块，采用JWT实现。登陆成功返回一个利用rs256算法加密的用户id的token给前端。后续请求必须携带此token才能通过网关。运行在本地8086号端口。
- **webpos-products**：商品展示、购物模块。运行在本地8082号端口。下单后通过`restTemplate`对orders模块进行调用、创建订单。
- **webpos-orders**：订单模块，运行在本地8083号端口。订单创建后会通过rabbitmq通知物流delivery模块进行创建物流。
- **webpos-delivery**：物流模块。运行在本地8085号端口。