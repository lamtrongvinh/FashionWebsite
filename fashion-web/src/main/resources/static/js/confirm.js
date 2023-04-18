let btn_confirm_delete = document.getElementById("btn_confirm_delete");
let form_delete_product = document.getElementById("form_delete_product");
let flag = false;
btn_confirm_delete.addEventListener("click", () => {
    flag = true;
})
form_delete_product.addEventListener("submit", (event) => {
    if (flag == false) {
        event.preventDefault();
    }
})