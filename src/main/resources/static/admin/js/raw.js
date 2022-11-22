const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"raw_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"quantity\">" + data.quantity + "</th>\n" +
        "        <th class=\"price\">" + data.price + "</th>\n" +
        "        <th class=\"provider\">" + data.providers.name + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < raw.length; i++)
        table.appendChild(createLine(raw[i]).firstChild);
    addListener()
})


// Добавить

async function addRaw() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const name = document.getElementById("name");
    const quantity = document.getElementById("quantity");
    const price = document.getElementById("price");
    const provider = document.getElementById("provider");

    createAjaxQuery("/addRaw/" + provider.innerHTML, {
            name: name.value,quantity: quantity.value, price: price.value},
        addSuccessHandler)
    name.value = "";
    quantity.value = "";
    price.value = "";
    provider.innerHTML = "Выбрать поставщика";
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteRaw(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteRaw/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("raw_" + data))
}


// Изменить
function tryChangeRaw(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getRaw/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("quantity").value = data.quantity;
    document.getElementById("price").value = data.price;
    document.getElementById("provider").innerHTML = data.providers.name;


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const name = document.getElementById("name");
    const quantity = document.getElementById("quantity");
    const price = document.getElementById("price");
    const provider = document.getElementById("provider");

    changeButton.style.display = "none"

    createAjaxQuery("/updateRaw/" + changeButton.dataset.id + "/" + provider.innerHTML,
        {name: name.value, quantity: quantity.value, price: price.value}, updateHandler)

    name.value = ""
    quantity.value = ""
    price.value = ""
    provider.innerHTML = "Выбрать поставщика"
}

function updateHandler(data){
    const tr = document.getElementById("raw_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".quantity").innerHTML = data.quantity
    tr.querySelector(".price").innerHTML = data.price
    tr.querySelector(".provider").innerHTML = data.providers.name
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
        Product[i].addEventListener('click', deleteRaw)
        change[i].addEventListener('click', tryChangeRaw)
    }
}

document.getElementById("addButton").addEventListener("click", addRaw);
