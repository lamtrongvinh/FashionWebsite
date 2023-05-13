$(document).ready(function() {
    $(".add-to-cart").click(function() {
        var productId = $(this).attr("data-product-id");
        var e = document.getElementById("ddlViewBy");
        let length = e.options.length;
        if (e != null) {
            var size_choose = e.options[e.selectedIndex].text;
            if (size_choose != 'Select size' && length > 2) {
                $.ajax({
                    type: "GET",
                    url: "/auth/checkout/add/" + productId,
                    data : {size : size_choose},
                    success: function(data) {
                        location.reload()
                    }
                });
            } else if (length <= 2) {
                $.ajax({
                    type: "GET",
                    url: "/auth/checkout/add/" + productId,
                    data : {size : "no-size"},
                    success: function(data) {
                        location.reload()
                    }
                });
                
            } else {
                alert("Vui Lòng Chọn Size");
            }
        } else if (e == null) {
            $.ajax({
                type: "GET",
                url: "/auth/checkout/add/" + productId,
                success: function(data) {
                    location.reload()
                }
            });
        }
    });
});