const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    raws = ""
    for (let i = 0; data.raws != null && i < data.raws.length; i++){
        raws += data.raws[i].name;
        if (data.raws.length !== i + 1)
            raws += ", "
    }
    div.innerHTML =  "<tr id=\"provider_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"telephoneNumber\">" + data.telephoneNumber + "</th>\n" +
        "        <th class=\"raws\">" + raws + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < provider.length; i++)
        table.appendChild(createLine(provider[i]).firstChild);
    addListener()
})


// Добавить

async function addProvider() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const name = document.getElementById("name");
    const telephoneNumber = document.getElementById("telephoneNumber");

    createAjaxQuery("/addProvider", {
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
function deleteProvider(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteProvider/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("provider_" + data))
}


// Изменить
function tryChangeProvider(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getProvider/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("telephoneNumber").value = data.telephoneNumber;


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const name = document.getElementById("name");
    const telephoneNumber = document.getElementById("telephoneNumber");

    changeButton.style.display = "none"

    createAjaxQuery("/updateProvider/" + changeButton.dataset.id,
        {name: name.value, telephoneNumber: telephoneNumber.value}, updateHandler)

    name.value = ""
    telephoneNumber.value = ""
}

function updateHandler(data){
    const tr = document.getElementById("provider_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".telephoneNumber").innerHTML = data.telephoneNumber
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
        Product[i].addEventListener('click', deleteProvider)
        change[i].addEventListener('click', tryChangeProvider)
    }
}

document.getElementById("addButton").addEventListener("click", addProvider);
