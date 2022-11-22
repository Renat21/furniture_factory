const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"product_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"characteristic\">" + data.characteristic + "</th>\n" +
        "        <th class=\"type\">" + data.type + "</th>\n" +
        "        <th class=\"cost\">" + data.cost + "</th>\n" +
        "        <th class=\"description\">" + data.description + "</th>\n" +
        "        <th class=\"image\"><img class=\"currentImage\" src=\"/image/" + data["image"]["id"] + "\" width=\"150\" height=\"150\" alt=\"\"></th>" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < products.length; i++)
        table.appendChild(createLine(products[i]).firstChild);
    addListener()
})


// Добавить

async function createImage(images) {
    var request = {};
    let elem;
    request.images = images; // some data

    let formData = new FormData();
    for (let i = 0; i < images.length; i++) {
        formData.append("file", images[i]);
    }
    const response = await fetch(currentLocation + "/image/create", {
        method: "POST",
        body: formData,
    }).then((data) => {
        elem = data.json();
    })
    return elem
}

async function addProduct() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    let images = document.querySelector("#imageList").files
    if (images.length !== 0) {
        let imageConverted = createImage(images)
        await imageConverted.then(async function (value) {
            imageValue = value
        })
    }

    const name = document.getElementById("name");
    const characteristic = document.getElementById("characteristic");
    const type = document.getElementById("type")
    const cost = document.getElementById("cost");
    const description = document.getElementById("description")

    console.log(name.value, characteristic.value, type.value, cost.value, description.value)
    createAjaxQuery("/addProducts/" + imageValue[0], {
            name: name.value, characteristic: characteristic.value,
            type: type.value, cost: cost.value, description: description.value},
        addSuccessHandler)
    name.value = ""
    characteristic.value = ""
    type.value = ""
    cost.value = ""
    description.value = ""
    document.querySelector("#imageList").value = ''
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteProduct(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteProducts/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("product_" + data))
}


// Изменить
function tryChangeProduct(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getProducts/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("characteristic").value = data.characteristic;
    document.getElementById("type").value = data.type;
    document.getElementById("cost").value = data.cost;
    document.getElementById("description").value = data.description;
    document.querySelector(".imageChanger").id = data["image"]["id"]


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const name = document.getElementById("name");
    const characteristic = document.getElementById("characteristic");
    const type = document.getElementById("type")
    const cost = document.getElementById("cost");
    const description = document.getElementById("description")
    imageValue = document.querySelector(".imageChanger").id


    let images = document.querySelector("#imageList").files

    if (images.length !== 0) {
        let imageConverted = createImage(images)
        await imageConverted.then(async function (value) {
            imageValue = value
        })
    }

    changeButton.style.display = "none"

    createAjaxQuery("/updateProducts/" + changeButton.dataset.id + "/" + imageValue,
        {name: name.value, characteristic: characteristic.value,
            type: type.value, cost: cost.value, description: description.value}, updateHandler)

    name.value = ""
    characteristic.value = ""
    type.value = ""
    cost.value = ""
    description.value = ""
    document.querySelector("#imageList").value = ''
}

function updateHandler(data){
    const tr = document.getElementById("product_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".characteristic").innerHTML = data.characteristic
    tr.querySelector(".type").innerHTML = data.type
    tr.querySelector(".cost").innerHTML = data.cost
    tr.querySelector(".description").innerHTML = data.description
    tr.querySelector(".currentImage").src = "/image/" + data["image"]["id"]
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
        Product[i].addEventListener('click', deleteProduct)
        change[i].addEventListener('click', tryChangeProduct)
    }
}

document.getElementById("addButton").addEventListener("click", addProduct);
