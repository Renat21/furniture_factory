const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    products = ""
    for (let i = 0; data.products != null && i < data.products.length; i++){
        products += data.products[i].name;
        if (data.products.length !== i + 1)
            products += ", "
    }

    console.log(data)
    div.innerHTML =  "<tr id=\"order_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"price\">" + data.price + "</th>\n" +
        "        <th class=\"onProduction\">" + data.onProduction + "</th>\n" +
        "        <th class=\"client\">" + data.client.username + "</th>\n" +
        "        <th class=\"products\">" + products + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < orders.length; i++)
        table.appendChild(createLine(orders[i]).firstChild);
    addListener()
})


// Добавить

async function addOrder() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const name = document.getElementById("name");
    const telephoneNumber = document.getElementById("telephoneNumber");

    createAjaxQuery("/addOrder", {
            name: name.value, telephoneNumber: telephoneNumber.value},
        addSuccessHandler)
    name.value = ""
    telephoneNumber.value = ""
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteOrder(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteOrder/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("order_" + data))
}


// Изменить
function tryChangeOrder(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getOrder/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("price").value = data.price;


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const name = document.getElementById("name");
    const price = document.getElementById("price");

    changeButton.style.display = "none"

    createAjaxQuery("/updateOrder/" + changeButton.dataset.id,
        {name: name.value, price: price.value}, updateHandler)

    name.value = ""
    price.value = ""
}

function updateHandler(data){
    const tr = document.getElementById("order_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".price").innerHTML = data.price
}

function createAjaxQuery(path, Product, toFunction){
    $.ajax({
        url: currentLocation + path,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(Product),
        success: toFunction
    })
}


function addListener(){
    const Product = document.querySelectorAll(".fa-trash")
    const change = document.querySelectorAll(".fa-wrench")
    for (let i = 0; i < Product.length; i++){
        Product[i].addEventListener('click', deleteOrder)
        change[i].addEventListener('click', tryChangeOrder)
    }
}

document.getElementById("addButton").addEventListener("click", addOrder);
