$(document).ready(function() {
    $(".cancel-order").click(function() {    
        var orderId = $(this).attr("data-order-id");
        alert(orderId)
        $.ajax({
            type: "GET",
            url: "/auth/orders/cancel/" + orderId,
            data: {id : orderId},
            success: function() {
                alert(123)
            }
        });
    });
});