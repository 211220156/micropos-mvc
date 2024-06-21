import $ from 'jquery';

window.$ = $;
window.jQuery = $;

let index = 0;
let cart = [];
let user = {};
let receipt = '';
let item;
let categories = [];
let allProducts = [];
let allCategories = [];
let subTotal = 0;
let orderTotal = 0;
let settings;
let holdOrder = 0;
let paymentType = 0;
let method = '';
let totalVat = 0;
let moment = require('moment');
let Swal = require('sweetalert2');
let json_api = 'http://localhost:8081/';
let json_img = '';//添加当前文件夹路径

$.ajaxSetup({
    headers: {
        'Authorization': localStorage.getItem("token") // 添加需要的请求头字段
    }
});

$.get(json_api + 'settings', function (data) {
    settings = data[0].settings;
    console.log(settings);
});
$(document).ready(function () {
    $.fn.addToCart = function (id, count, stock) {
        if (stock == 1) {
            if (count > 0) {
                console.log(id);
                console.log(json_api + 'products/' + id);
                $.get(json_api + 'products/' + id, function (data) {
                    $(this).addProductToCart(data);
                });
            }
            else {
                Swal.fire(
                    'Out of stock!',
                    'This item is currently unavailable',
                    'info'
                );
            }
        }
        else {
            $.get(json_api + 'products/' + id, function (data) {
                $(this).addProductToCart(data);
            });
        }

    };

    $(".loading").hide();
    loadCategories();
    loadProducts("");
    $.fn.renderTable = function (cartList) {
        $('#cartTable > tbody').empty();
        $(this).calculateCart();
        $.each(cartList, function (index, data) {
            $('#cartTable > tbody').append(
                $('<tr>').append(
                    $('<td>', { text: index + 1 }),
                    $('<td>', { text: data.product_name }),
                    $('<td>').append(
                        $('<div>', { class: 'input-group' }).append(
                            $('<div>', { class: 'input-group-btn btn-xs' }).append(
                                $('<button>', {
                                    class: 'btn btn-default btn-xs',
                                    onclick: '$(this).qtDecrement(' + index + ')'
                                }).append(
                                    $('<i>', { class: 'fa fa-minus' })
                                )
                            ),
                            $('<input>', {
                                class: 'form-control',
                                type: 'number',
                                value: data.quantity,
                                onInput: '$(this).qtInput(' + index + ')'
                            }),
                            $('<div>', { class: 'input-group-btn btn-xs' }).append(
                                $('<button>', {
                                    class: 'btn btn-default btn-xs',
                                    onclick: '$(this).qtIncrement(' + index + ')'
                                }).append(
                                    $('<i>', { class: 'fa fa-plus' })
                                )
                            )
                        )
                    ),
                    $('<td>', { text: settings.symbol + (data.price * data.quantity).toFixed(2) }),
                    $('<td>').append(
                        $('<button>', {
                            class: 'btn btn-danger btn-xs',
                            onclick: '$(this).deleteFromCart(' + index + ')'
                        }).append(
                            $('<i>', { class: 'fa fa-times' })
                        )
                    )
                )
            )
        })
    };

    if (settings && settings.symbol) {
        $("#price_curr, #payment_curr, #change_curr").text(settings.symbol);
    }

    $.fn.search = function () {
        console.log(document.getElementById("search").value.trim())
        loadProducts(document.getElementById("search").value.trim());
    };
    function loadProducts(keyword) {
        var url = json_api + 'products';
        // 判断 keyword 是否为空
        if (keyword !== "") {
            // 如果不为空，则将 keyword 作为参数添加到 URL 中
            url += '/search?keyword=' + keyword;
            console.log("keyword != null");
            console.log(url)
        }

        $.get(url, function (data) {
            // console.log(data);
            data.forEach(item => {
                item.price = parseFloat(item.price).toFixed(2);
            });

            allProducts = [...data];

            loadProductList();

            $('#parent').text('');
            $('#categories').html(`<button type="button" id="all" class="btn btn-categories btn-white waves-effect waves-light" onclick="$(this).search()">search</button> `);

            data.forEach(item => {
                // console.log(item);
                // console.log(item._id);
                if (!categories.includes(item.category)) {
                    categories.push(item.category);
                }
                let item_info = `<div class="col-lg-2 box ${item.category}"
                        >
                      <div class="widget-panel widget-style-2 ">
                      <div id="image" ><img src="${item.img == "" ? "./public/images/default.jpg" : json_img/*img_path*/ + item.img}" id="product_img" alt="" onclick="$(this).openNewWindow('${item.id}')"></div>
                                  <div class="text-muted m-t-5 text-center">
                                  <div class="name" id="product_name">${item.name}</div>
                                  <span class="sku">${item.sku}</span>
                                  <span class="stock">STOCK </span><span class="count">${item.stock == 1 ? item.quantity : 'N/A'}</span></div>
                                  <!-- <sp class="text-success text-center"><b data-plugin="counterup">${settings.symbol + item.price}</b> </sp>
                                   <div class="col-md-2"><button data-toggle="modal" data-target="" class="btn btn-success"><i class="fa fa-plus"></i></button></div> -->
                                  <div class="container" >
                                      <div class="row">
                                        <div class="col-md-12 text-center">
                                          <div class="d-inline-block">
                                            <sp class="text-success">
                                              <b data-plugin="counterup">${settings.symbol + item.price}</b>
                                            </sp>
                                            <div class="d-inline-block">
                                              <button data-toggle="modal" data-target="" class="btn btn-success" onclick="$(this).addToCart('${item._id}', ${item.quantity}, ${item.stock})">
                                                <i class="fa fa-plus"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                  </div>
                      </div>
                  </div>`;
                $('#parent').append(item_info);
            });

            categories.forEach(category => {

                let c = allCategories.filter(function (ctg) {
                    return ctg._id == category;
                })

                $('#categories').append(`<button type="button" id="${category}" class="btn btn-categories btn-white waves-effect waves-light">${c.length > 0 ? c[0].name : ''}</button> `);
            });

        });

    }

    function loadCategories() {
        $.get(json_api + 'categories', function (data) {
            allCategories = data;
            loadCategoryList();
            $('#category').html(`<option value="0">Select</option>`);
            allCategories.forEach(category => {
                $('#category').append(`<option value="${category._id}">${category.name}</option>`);
            });
        });
    }


    $.fn.calculateCart = function () {
        let total = 0;
        let grossTotal;
        $('#total').text(cart.length);
        $.each(cart, function (index, data) {
            total += data.quantity * data.price;
        });
        total = total - $("#inputDiscount").val();
        $('#price').text(settings.symbol + total.toFixed(2));

        subTotal = total;

        if ($("#inputDiscount").val() >= total) {
            $("#inputDiscount").val(0);
        }

        if (settings.charge_tax) {
            totalVat = ((total * vat) / 100);
            grossTotal = total + totalVat
        }

        else {
            grossTotal = total;
        }

        orderTotal = grossTotal.toFixed(2);

        $("#gross_price").text(settings.symbol + grossTotal.toFixed(2));
        $("#payablePrice").val(grossTotal);
    };



    $.fn.deleteFromCart = function (index) {
        cart.splice(index, 1);
        $(this).renderTable(cart);

    }


    $.fn.qtIncrement = function (i) {

        item = cart[i];
        // console.log(item);
        let product = allProducts.filter(function (selected) {
            // return selected._id == parseInt(item.id);
            return selected._id == item.id;
        });
        // console.log(product);
        if (product[0].stock == 1) {
            if (item.quantity < product[0].quantity) {
                item.quantity += 1;
                $(this).renderTable(cart);
            }

            else {
                Swal.fire(
                    'No more stock!',
                    'You have already added all the available stock.',
                    'info'
                );
            }
        }
        else {
            item.quantity += 1;
            $(this).renderTable(cart);
        }

    }


    $.fn.qtDecrement = function (i) {
        if (item.quantity > 1) {
            item = cart[i];
            item.quantity -= 1;
            $(this).renderTable(cart);
        }
    }


    $.fn.qtInput = function (i) {
        item = cart[i];
        item.quantity = $(this).val();
        $(this).renderTable(cart);
    }


    $.fn.cancelOrder = function () {

        if (cart.length > 0) {
            Swal.fire({
                title: 'Are you sure?',
                text: "You are about to remove all items from the cart.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, clear it!'
            }).then((result) => {

                if (result.value) {

                    cart = [];
                    $(this).renderTable(cart);
                    holdOrder = 0;

                    Swal.fire(
                        'Cleared!',
                        'All items have been removed.',
                        'success'
                    )
                }
            });
        }

    }


    $("#payButton").on('click', function () {
        if (cart.length != 0) {
            $("#paymentModel").modal('toggle');
        } else {
            Swal.fire(
                'Oops!',
                'There is nothing to pay!',
                'warning'
            );
        }

    });


    $("#hold").on('click', function () {

        if (cart.length != 0) {

            $("#dueModal").modal('toggle');
        } else {
            Swal.fire(
                'Oops!',
                'There is nothing to hold!',
                'warning'
            );
        }
    });



    $.fn.submitDueOrder = function (status) {

        let items = "";
        let payment = 0;

        cart.forEach(item => {

            items += "<tr><td>" + item.product_name + "</td><td>" + item.quantity + "</td><td>" + settings.symbol + parseFloat(item.price).toFixed(2) + "</td></tr>";

        });

        let currentTime = new Date(moment());

        let discount = $("#inputDiscount").val();
        let customer = localStorage.getItem("username");
        let date = moment(currentTime).format("YYYY-MM-DD HH:mm:ss");
        let paid = $("#payment").val() == "" ? "" : parseFloat($("#payment").val()).toFixed(2);
        let change = $("#change").text() == "" ? "" : parseFloat($("#change").text()).toFixed(2);
        let refNumber = $("#refNumber").val();
        let orderNumber = holdOrder;
        let type = "";
        let tax_row = "";


        switch (paymentType) {

            case 1: type = "Cheque";
                break;

            case 2: type = "Card";
                break;

            default: type = "Cash";

        }


        if (paid != "") {
            payment = `<tr>
                  <td>Paid</td>
                  <td>:</td>
                  <td>${settings.symbol + paid}</td>
              </tr>
              <tr>
                  <td>Change</td>
                  <td>:</td>
                  <td>${settings.symbol + Math.abs(change).toFixed(2)}</td>
              </tr>
              <tr>
                  <td>Method</td>
                  <td>:</td>
                  <td>${type}</td>
              </tr>`
        }



        if (settings.charge_tax) {
            tax_row = `<tr>
              <td>Vat(${settings.percentage})% </td>
              <td>:</td>
              <td>${settings.symbol}${parseFloat(totalVat).toFixed(2)}</td>
          </tr>`;
        }



        if (status == 0) {

            // if ($("#customer").val() == 0 && $("#refNumber").val() == "") {
                Swal.fire(
                    'Reference Required!',
                    'You either need to select a customer <br> or enter a reference!',
                    'warning'
                )
                return;
            // }
        }

        $(".loading").show();

        if (holdOrder != 0) {
            orderNumber = holdOrder;
            method = 'PUT'
        }
        else {
            orderNumber = Math.floor(Date.now() / 1000);
            console.log(orderNumber);
            method = 'POST'
        }

        receipt = `<div style="font-size: 10px;">                            
  <p style="text-align: center;">
  ${settings.img == "" ? settings.img : '<img style="max-width: 50px;max-width: 100px;" src ="' + img_path + settings.img + '" /><br>'}
      <span style="font-size: 22px;">${settings.store}</span> <br>
      ${settings.address_one} <br>
      ${settings.address_two} <br>
      ${settings.contact != '' ? 'Tel: ' + settings.contact + '<br>' : ''} 
      ${settings.tax != '' ? 'Vat No: ' + settings.tax + '<br>' : ''} 
  </p>
  <hr>
  <left>
      <p>
      Order No : ${orderNumber} <br>
      Ref No : ${refNumber == "" ? orderNumber : refNumber} <br>
    Customer : ${customer} <br>
      Cashier : ${user.fullname} <br>
      Date : ${date}<br>
      </p>

  </left>
  <hr>
  <table width="100%">
      <thead style="text-align: left;">
      <tr>
          <th>Item</th>
          <th>Qty</th>
          <th>Price</th>
      </tr>
      </thead>
      <tbody>
      ${items}                

      <tr>                        
          <td><b>Subtotal</b></td>
          <td>:</td>
          <td><b>${settings.symbol}${subTotal.toFixed(2)}</b></td>
      </tr>
      <tr>
          <td>Discount</td>
          <td>:</td>
          <td>${discount > 0 ? settings.symbol + parseFloat(discount).toFixed(2) : ''}</td>
      </tr>
      
      ${tax_row}
  
      <tr>
          <td><h3>Total</h3></td>
          <td><h3>:</h3></td>
          <td>
              <h3>${settings.symbol}${parseFloat(orderTotal).toFixed(2)}</h3>
          </td>
      </tr>
      ${payment == 0 ? '' : payment}
      </tbody>
      </table>
      <br>
      <hr>
      <br>
      <p style="text-align: center;">
       ${settings.footer}
       </p>
      </div>`;


        if (status == 3) {
            if (cart.length > 0) {

                printJS({ printable: receipt, type: 'raw-html' });

                $(".loading").hide();
                return;

            }
            else {

                $(".loading").hide();
                return;
            }
        }


        let data = {
            order: orderNumber,
            ref_number: refNumber,
            discount: discount,
            customer: customer,
            status: status,
            subtotal: parseFloat(subTotal).toFixed(2),
            tax: totalVat,
            order_type: 1,
            items: cart,
            date: currentTime,
            payment_type: type,
            payment_info: $("#paymentInfo").val(),
            total: orderTotal,
            paid: paid,
            change: change,
            _id: orderNumber,
            till: 'till',
            mac: 'mac',
            user: 'admin',
            user_id: '001'
        }
        var requestData = {pur:[]};
        for (let i = 0; i < cart.length; i++) {
            requestData.pur.push({ productId: cart[i].id, quantity: cart[i].quantity });
        }
        $.ajax({
            url: json_api + 'products/charge',
            contentType: "application/json",
            data: JSON.stringify(requestData),
            type: "PATCH",
            success: function (data) {
                console.log("Data updated!");
            },
            error: function (data) {
                console.log("failed");
            }
        });
        // for (let i = 0; i < cart.length; i++) {
        //     console.log(cart[i]);
        //     $.ajax({
        //         url: json_api + 'products/' + cart[i].id,
        //         contentType: "application/json",
        //         data: JSON.stringify({ quantity: cart[i].max_quantity - cart[i].quantity }),
        //         type: "PATCH",
        //         success: function (data) {
        //             console.log("Data updated!");
        //         },
        //         error: function (data) {
        //             console.log("failed");
        //         }
        //     })
        // }

        cart = [];
        $('#viewTransaction').html('');
        $('#viewTransaction').html(receipt);
        $('#orderModal').modal('show');
        loadProducts("");
        $(".loading").hide();
        $("#dueModal").modal('hide');
        $("#paymentModel").modal('hide');
        $(this).renderTable(cart);


        $("#refNumber").val('');
        $("#change").text('');
        $("#payment").val('');

    }





    $("#payment").on('input', function () {
        $(this).calculateChange();
    });


    $("#confirmPayment").on('click', function () {
        if ($('#payment').val() == "") {
            Swal.fire(
                'Nope!',
                'Please enter the amount that was paid!',
                'warning'
            );
        }
        else {
            $(this).submitDueOrder(1);
        }
    });


    function loadProductList() {
        let products = [...allProducts];
        let product_list = '';
        let counter = 0;
        $('#product_list').empty();
        $('#productList').DataTable().destroy();

        products.forEach((product, index) => {

            counter++;

            let category = allCategories.filter(function (category) {
                return category._id == product.category;
            });

            //console.log(product.img);
            product_list += `<tr>
      <td><img id="`+ product._id + `"></td>
      <td><img style="max-height: 50px; max-width: 50px; border: 1px solid #ddd;" src="${product.img == "" ? "./public/images/default.jpg" : json_img/*img_path*/ + product.img}" id="product_img"></td>
      <td>${product.name}</td>
      <td>${settings.symbol}${product.price}</td>
      <td>${product.stock == 1 ? product.quantity : 'N/A'}</td>
      <td>${category.length > 0 ? category[0].name : ''}</td>
      <td class="nobr"><span class="btn-group"><button onClick="$(this).editProduct(${index})" class="btn btn-warning btn-sm"><i class="fa fa-edit"></i></button><button onClick="$(this).deleteProduct(${product._id})" class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></button></span></td></tr>`;
            if (counter == allProducts.length) {

                $('#product_list').html(product_list);


                $('#productList').DataTable({
                    "order": [[1, "desc"]]
                    , "autoWidth": false
                    , "info": true
                    , "JQueryUI": true
                    , "ordering": true
                    , "paging": false
                });
            }

        });
    }


    function loadCategoryList() {

        let category_list = '';
        let counter = 0;
        $('#category_list').empty();
        $('#categoryList').DataTable().destroy();

        allCategories.forEach((category, index) => {

            counter++;

            category_list += `<tr>

      <td>${category.name}</td>
      <td><span class="btn-group"><button onClick="$(this).editCategory(${index})" class="btn btn-warning"><i class="fa fa-edit"></i></button><button onClick="$(this).deleteCategory(${category._id})" class="btn btn-danger"><i class="fa fa-trash"></i></button></span></td></tr>`;
        });

        if (counter == allCategories.length) {

            $('#category_list').html(category_list);
            $('#categoryList').DataTable({
                "autoWidth": false
                , "info": true
                , "JQueryUI": true
                , "ordering": true
                , "paging": false

            });
        }
    }




});
$.fn.addProductToCart = function (data) {
    item = {
        id: data._id,
        product_name: data.name,
        // sku: data.sku,
        max_quantity: data.quantity,
        price: data.price,
        quantity: 1
    };

    if ($(this).isExist(item)) {
        $(this).qtIncrement(index);
    } else {
        cart.push(item);
        $(this).renderTable(cart)
    }
}

$.fn.openNewWindow = function (id) {
    window.open("productInfo.html?productId=" + id, '_blank');
    // let newWindow = window.open('', '_blank');
    // //等待动画
    // newWindow.document.write('<html><head><title>Product Detail</title>');
    // newWindow.document.write('<style>');
    // newWindow.document.write('@keyframes spin {');
    // newWindow.document.write('  0% { transform: rotate(0deg); }');
    // newWindow.document.write('  100% { transform: rotate(360deg); }');
    // newWindow.document.write('}');
    // newWindow.document.write('.loading { text-align: center; margin-top: 50px; font-size: 24px; }');
    // newWindow.document.write('.loading::after {');
    // newWindow.document.write('  content: "Loading";');
    // newWindow.document.write('  display: inline-block;');
    // newWindow.document.write('  animation: spin 2s linear infinite;');
    // newWindow.document.write('}');
    // newWindow.document.write('</style>');
    // newWindow.document.write('</head><body>');
    // newWindow.document.write('<div class="loading"></div>');
    // newWindow.document.write('</body></html>');
    // newWindow.document.close();
    // $.get(json_api + 'products/detail/' + id, function (productDetail) {

    //     // newWindow.document.write('<html><head><title>Product Detail</title></head><body>');
    //     // // newWindow.document.write(JSON.stringify(productDetail));

    //     // // 将 Product 的属性写入 newWindow
    //     // newWindow.document.write('<h1>Product</h1>');
    //     // newWindow.document.write('<img src="' + productDetail.product.img + '">');
    //     // // newWindow.document.write('<p>img: ' + productDetail.product.img + '</p>');
    //     // newWindow.document.write('<p>ID: ' + productDetail.product.id + '</p>');
    //     // newWindow.document.write('<p>Name: ' + productDetail.product.name + '</p>');
    //     // newWindow.document.write('<p>Category: ' + productDetail.product.category + '</p>');
    //     // newWindow.document.write('<p>Price: ' + productDetail.product.price + '</p>');
    //     // newWindow.document.write('<p>Average rating: ' + productDetail.product.average_rating + '</p>');
    //     // newWindow.document.write('<p>Rating number: ' + productDetail.product.rating_number + '</p>');

    //     // // 将 Review 的属性写入 newWindow
    //     // newWindow.document.write('<h1>Review</h1>');
    //     // for (var i = 0; i < productDetail.reviewList.length; i++) {
    //     //     var review = productDetail.reviewList[i];
    //     //     newWindow.document.write('<p>Timestamp: ' + review.timestamp + '</p>');
    //     //     newWindow.document.write('<p>Rating: ' + review.rating + '</p>');

    //     //     if (review.images != null) {
    //     //         for (var j = 0; j < productDetail.reviewList.images.length; j++) {
    //     //             newWindow.document.write('<img src="' + review.images[j].large + '">');
    //     //         }
    //     //     }
    //     //     newWindow.document.write('<p>Title: ' + review.title + '</p>');
    //     //     newWindow.document.write('<p>Text: ' + review.text + '</p>');
    //     //     newWindow.document.write('<p>User_id: ' + review.user_id + '</p>');
    //     // }

    //     // newWindow.document.write('</body></html>');
    //     // newWindow.document.close();
    //     // localStorage.setItem("productDetail", productDetail);
    // });
}

$.fn.isExist = function (data) {
    let toReturn = false;
    $.each(cart, function (index, value) {
        if (value.id == data.id) {
            $(this).setIndex(index);
            toReturn = true;
        }
    });
    return toReturn;
}
$.fn.setIndex = function (value) {
    index = value;
}
$.fn.go = function (value, isDueInput) {
    if (isDueInput) {
        $("#refNumber").val($("#refNumber").val() + "" + value)
    } else {
        $("#payment").val($("#payment").val() + "" + value);
        $(this).calculateChange();
    }
}
$.fn.calculateChange = function () {
    var change = $("#payablePrice").val() - $("#payment").val();
    if (change <= 0) {
        $("#change").text(change.toFixed(2));
    } else {
        $("#change").text('0')
    }
    if (change <= 0) {
        $("#confirmPayment").show();
    } else {
        $("#confirmPayment").hide();
    }
}