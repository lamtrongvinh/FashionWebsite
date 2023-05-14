const checkboxes = document.querySelectorAll('.item-checkbox');
const totalPriceElement = document.querySelector('#total-price');

let totalPrice = 0;

checkboxes.forEach((checkbox) => {
  checkbox.addEventListener('click', () => {
    let itemPrice;
    let currentItem = checkbox.parentElement.nextElementSibling;

    while (currentItem) {
      if (currentItem.classList.contains('item-price')) {
        itemPrice = parseFloat(currentItem.innerText.replace('$', ''));
        break;
      }
      currentItem = currentItem.nextElementSibling;
    }

    if (checkbox.checked) {
      totalPrice += itemPrice;
    } else {
      totalPrice -= itemPrice;
    }
    totalPriceElement.innerText = `$${totalPrice}`;
  });

});
