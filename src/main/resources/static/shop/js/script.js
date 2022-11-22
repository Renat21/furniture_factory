const products = document.getElementById('products');
const mainUrl = "http://localhost:8080/"


function createHTMLOfProducts(data) {
    const div = document.createElement('div')
    div.innerHTML = "<div id=\"" + data["id"] + "\" class=\"card card-body\">\n" +
        "                  <div class=\"media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row\">\n" +
        "                     <div class=\"mr-2 mb-3 mb-lg-0\">\n" +
        "\n" +
        "                        <img src=\"/image/" + data["image"]["id"] + "\" width=\"150\" height=\"150\" alt=\"\">\n" +
        "\n" +
        "                     </div>\n" +
        "\n" +
        "                     <div class=\"media-body\">\n" +
        "                        <h6 class=\"media-title font-weight-semibold\">\n" +
        "                           <span data-abc=\"true\">" + data["name"] + "</span>\n" +
        "                        </h6>\n" +
        "\n" +
        "                        <p class=\"mb-3\">" + data["description"] + "</p>\n" +
        "\n" +
        "                     </div>\n" +
        "\n" +
        "                     <div class=\"mt-3 mt-lg-0 ml-lg-3 text-center\">\n" +
        "                        <h3 class=\"mb-0 font-weight-semibold\">" + data["cost"] + "</h3>\n" +
        "\n" +
        "                        <div>\n" +
        "                           <i class=\"fa fa-star\"></i>\n" +
        "                           <i class=\"fa fa-star\"></i>\n" +
        "                           <i class=\"fa fa-star\"></i>\n" +
        "                           <i class=\"fa fa-star\"></i>\n" +
        "\n" +
        "                        </div>\n" +
        "\n" +
        "                        <button type=\"button\" class=\"btn btn-warning mt-4 text-white\">\n" +
        "                           <i class=\"icon-cart-add mr-2\"></i> Add to cart" +
        "                       </button>\n" +
        "                     </div>\n" +
        "                  </div>\n" +
        "               </div>"
    div.querySelector(".btn-warning").addEventListener('click', addToCart)
    div.style.minWidth = "650px"
    products.appendChild(div)
}

function createQueryNoData(successFunction, query) {
    jQuery.ajax({
        type: "POST",
        url: mainUrl + query,
        contentType: 'application/json',
        success: successFunction
    });
}

($(function () {
    createQueryNoData(productsCreate, "allProducts")
}))

function productsCreate(data) {
    for (let i = 0; i < data.length; i++){
        createHTMLOfProducts(data[i])
    }
}
function addToCart(event) {
    const cart = event.target.parentNode.parentNode.parentNode.id
    createQueryNoData(null, "addProduct/" + cart)
}