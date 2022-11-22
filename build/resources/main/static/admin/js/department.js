const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    employees = ""
    for (let i = 0; data.raws != null && i < data.employees.length; i++){
        employees += data.employees[i].speciality;
        if (data.employees.length !== i + 1)
            employees += ", "
    }
    div.innerHTML =  "<tr id=\"department_" + data.id + "\">\n" +
        "        <th scope=\"row\">" + data.id + "</th>\n" +
        "        <th class=\"address\">" + data.address + "</th>\n" +
        "        <th class=\"telephoneNumber\">" + data.telephoneNumber + "</th>\n" +
        "        <th class=\"specialization\">" + data.specialization + "</th>\n" +
        "        <th class=\"raws\">" + employees + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>\n" +
        "            <i class=\"fa fa-trash\" aria-hidden=\"true\"></i>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

$(function (){
    for (let i = 0; i < department.length; i++)
        table.appendChild(createLine(department[i]).firstChild);
    addListener()
})


// Добавить

async function addDepartment() {
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const address = document.getElementById("address");
    const telephoneNumber = document.getElementById("telephoneNumber");
    const specialization = document.getElementById("specialization");

    createAjaxQuery("/addDepartment", {
            address: address.value, telephoneNumber: telephoneNumber.value,
            specialization: specialization.value},
        addSuccessHandler)
    address.value = ""
    telephoneNumber.value = ""
    specialization.value = ""
}

function addSuccessHandler(data){
    table.appendChild(createLine(data).firstChild);
    addListener()
}

// Удалить
function deleteDepartment(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteDepartment/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("department_" + data))
}


// Изменить
function tryChangeDepartment(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getDepartment/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("address").value = data.address;
    document.getElementById("telephoneNumber").value = data.telephoneNumber;
    document.getElementById("specialization").value = data.specialization;


    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

async function updateChanges() {
    const changeButton = document.getElementById("changeButton")

    const address = document.getElementById("address");
    const telephoneNumber = document.getElementById("telephoneNumber");
    const specialization = document.getElementById("specialization");

    changeButton.style.display = "none"

    createAjaxQuery("/updateDepartment/" + changeButton.dataset.id,
        {address: address.value, telephoneNumber: telephoneNumber.value,
            specialization: specialization.value}, updateHandler)

    address.value = ""
    telephoneNumber.value = ""
    specialization.value = ""
}

function updateHandler(data){
    const tr = document.getElementById("department_" + data.id)
    tr.querySelector(".specialization").innerHTML = data.specialization
    tr.querySelector(".telephoneNumber").innerHTML = data.telephoneNumber
    tr.querySelector(".address").innerHTML = data.address
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
        Product[i].addEventListener('click', deleteDepartment)
        change[i].addEventListener('click', tryChangeDepartment)
    }
}

document.getElementById("addButton").addEventListener("click", addDepartment);
