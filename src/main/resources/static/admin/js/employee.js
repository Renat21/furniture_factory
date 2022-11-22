const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"employee_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"speciality\">" + data.speciality + "</th>\n" +
        "        <th class=\"experience\">" + data.experience + "</th>\n" +
        "        <th class=\"name\">" + data.name + "</th>\n" +
        "        <th class=\"surname\">" + data.surname + "</th>\n" +
        "        <th class=\"department\">" + data.department.address + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < employee.length; i++)
        table.appendChild(createLine(employee[i]).firstChild);
    addListener()
})


// Добавить

async function addEmployee() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const name = document.getElementById("name");
    const speciality = document.getElementById("speciality");
    const experience = document.getElementById("experience")
    const surname = document.getElementById("surname");

    const department = document.getElementById("department");

    createAjaxQuery("/addEmployee/" + department.innerHTML, {
            name: name.value, speciality: speciality.value,
            experience: experience.value, surname:surname.value},
        addSuccessHandler)
    name.value = ""
    speciality.value = ""
    experience.value = ""
    surname.value = ""
    department.innerHTML = ""
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteEmployee(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteEmployee/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("employee_" + data))
}


// Изменить
function tryChangeEmployee(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getEmployee/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("speciality").value = data.speciality;
    document.getElementById("surname").value = data.surname;
    document.getElementById("experience").value = data.experience;

    document.getElementById("department").innerHTML = data.department.address;


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const name = document.getElementById("name");
    const speciality = document.getElementById("speciality");
    const surname = document.getElementById("surname")
    const experience = document.getElementById("experience");

    const department = document.getElementById("department");

    changeButton.style.display = "none"

    createAjaxQuery("/updateEmployee/" + changeButton.dataset.id + "/" + department.innerHTML,
        {name: name.value, speciality: speciality.value,
            surname: surname.value, experience: experience.value}, updateHandler)

    name.value = ""
    speciality.value = ""
    experience.value = ""
    surname.value = ""
    department.innerHTML = ""
}

function updateHandler(data){
    const tr = document.getElementById("employee_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".surname").innerHTML = data.surname
    tr.querySelector(".experience").innerHTML = data.experience
    tr.querySelector(".speciality").innerHTML = data.speciality
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
        Product[i].addEventListener('click', deleteEmployee)
        change[i].addEventListener('click', tryChangeEmployee)
    }
}

document.getElementById("addButton").addEventListener("click", addEmployee);
