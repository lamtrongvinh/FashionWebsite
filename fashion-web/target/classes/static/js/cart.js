
$(document).ready(function() {
    $(".increment-btn").click(function() {
        var productId = $(this).attr("data-product-id");
        let cartId = $(this).attr("data-item-id");
        var sizeSpan = document.getElementById(cartId);
        var sizeValue = sizeSpan.textContent.trim();
        $.ajax({
            type: "GET",
            url: "/product/" + productId + "/increment",
            data : {sizeValue : sizeValue},
            success: function(data) {
                $("#quantity-" + productId).text(data.quantity);
                $("#total-" + productId).text(data.total);
                var total = 0;
                $(".total").each(function() {
                    total += parseFloat($(this).text());
                });
                $("#grand-total").text(total.toFixed(2));
                location.reload();
            }
        });
    });
});

$(document).ready(function() {
    $(".decrement-btn").click(function() {
        var productId = $(this).attr("data-product-id");
        let cartId = $(this).attr("data-item-id");
        var sizeSpan = document.getElementById(cartId);
        var sizeValue = sizeSpan.textContent.trim();
        $.ajax({
            type: "GET",
            url: "/product/" + productId + "/decrement",
            data : {sizeValue : sizeValue},
            success: function(data) {
                $("#quantity-" + productId).text(data.quantity);
                $("#total-" + productId).text(data.total);
                var total = 0;
                $(".total").each(function() {
                    total += parseFloat($(this).text());
                });
                $("#grand-total").text(total.toFixed(2));
                location.reload();
            }
        });
    });
});

$(document).ready(function() {
    $(".delete-product").click(function() {
        var productId = $(this).attr("data-product-id");
        let cartId = $(this).attr("data-item-id");
        var sizeSpan = document.getElementById(cartId);
        var sizeValue = sizeSpan.textContent.trim();
        $.ajax({
            type: "GET",
            url: "/auth/checkout/delete/" + productId,
            data : {sizeValue : sizeValue},
            success: function(data) {
                location.reload();
            }
        });
    });
});