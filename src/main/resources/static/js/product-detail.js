const Quantity = document.querySelector('.quantity-value');

var quantityValue = 1;
function handleIncrement() {

    quantityValue++;
    Quantity.value=quantityValue;
}
function handleDecrement() {
    if(quantityValue > 1) {
        quantityValue--;
        Quantity.value=quantityValue;
    }
}