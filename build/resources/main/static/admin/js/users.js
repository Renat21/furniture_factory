const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    console.log(data)
    orders = ""
    for (let i = 0; data.orders != null && i < data.orders.length; i++){
        if (data.orders[i].onProduction == false) {
            orders += data.orders[i].name;
            if (data.orders.length !== i + 1)
                orders += ", "
        }
    }
    div.innerHTML =  "<tr id=\"user_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"username\">" + data.username + "</th>\n" +
        "        <th class=\"password\">" + data.password.slice(0, 25) + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"surname\">" + data.surname + "</th>\n" +
        "        <th class=\"telephone\">" + data.telephone + "</th>\n" +
        "        <th class=\"raws\">" + orders + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < users.length; i++)
        table.appendChild(createLine(users[i]).firstChild);
    addListener()
})


// Добавить

async function addUser() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const name = document.getElementById("name");
    const surname = document.getElementById("surname");
    const telephone = document.getElementById("telephone");

    createAjaxQuery("/addUser", {
            username: username.value, password: password.value, name: name.value, surname: surname.value,
            telephone: telephone.value},
        addSuccessHandler)
    username.value = ""
    password.value = ""
    name.value = ""
    surname.value = ""
    telephone.value = ""
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteUser(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteUser/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("user_" + data))
}


// Изменить
function tryChangeUser(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getUser/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("username").value = data.username;
    document.getElementById("password").value = "";
    document.getElementById("name").value = data.name;
    document.getElementById("surname").value = data.surname;
    document.getElementById("telephone").value = data.telephone


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const name = document.getElementById("name");
    const surname = document.getElementById("surname");
    const telephone = document.getElementById("telephone");


    changeButton.style.display = "none"

    createAjaxQuery("/updateUser/" + changeButton.dataset.id,
        {name: name.value, password: password.value, username: username.value, surname: surname.value,
        telephone: telephone.value}, updateHandler)

    username.value = ""
    password.value = ""
    name.value = ""
    surname.value = ""
    telephone.value = ""
}

function updateHandler(data){
    const tr = document.getElementById("user_" + data.id)
    tr.querySelector(".username").innerHTML = data.username
    tr.querySelector(".password").innerHTML = data.password.slice(0, 25);
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".surname").innerHTML = data.surname
    tr.querySelector(".telephone").innerHTML = data.telephone
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
        Product[i].addEventListener('click', deleteUser)
        change[i].addEventListener('click', tryChangeUser)
    }
}

document.getElementById("addButton").addEventListener("click", addUser);
