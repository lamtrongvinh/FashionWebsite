
$(document).ready(function() {
    $(".add-to-cart").click(function() {
        var productId = $(this).attr("data-product-id");
        var e = document.getElementById("ddlViewBy");
        if (e != null) {
            var size_choose = e.options[e.selectedIndex].text;
            if (size_choose != 'Select size') {
                $.ajax({
                    type: "GET",
                    url: "/auth/checkout/add/" + productId,
                    data : {size : size_choose},
                    success: function(data) {
                        location.reload();
                    }
                });
            } else {
                alert("Vui Lòng Chọn Size");
            }
        } else {
            $.ajax({
                type: "GET",
                url: "/auth/checkout/add/" + productId,
                data : {size : size_choose},
                success: function(data) {
                    location.reload();
                }
            });
        }
    });
});