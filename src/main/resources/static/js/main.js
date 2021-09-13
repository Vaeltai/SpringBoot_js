$(document).ready(function (){

    let usersTable = $('#usersTable');
    showUsers();

// *****    Получаем роли     ************************************************************************************
    function getRoles(user){
        let roles = "";
        let i = user.roles.length - 1;
        while (i != -1){
            roles += user.roles[i].name;
            roles += " ";
            i--
        }
        return roles;
    }


// ***********     Вывод таблицы пользователей    ********************************************************************
    function showUsers() {
        usersTable.empty();
        fetch('api/admin')
            .then(response => response.json() )
            .then(users => {
                for(let i = 0; i < users.length; ++i){
                    let user = users[i];
                    createRow(user);
                }
            });
    }

//**  Добавить пользователя    ***************************************************************************************
    document.querySelector("#addButton").onclick = function () {
        let user = {
            "firstName": $('#firstNameNew').val(),
            "lastName": $('#lastNameNew').val(),
            "age": $('#ageNew').val(),
            "email": $('#emailNew').val(),
            "password": $('#passwordNew').val(),
            "roles": $('#roleNew').val(),
        };

        fetch("http://localhost:8080/api/admin/new", {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(user),
        }).then(function () {
            $('#nav-home-tab').tab('show');
            showUsers();
        })
    }


//*  Добавляем строку в таблицу     *********************************************************************************
    function createRow(user){
        let trID = "user" + user.id
        usersTable.append(`<tr id=` + trID + `>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${getRoles(user)}</td>
            <td><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#editModal"
            data-userid="${user.id}"
            data-userfirstname="${user.firstName}" 
            data-userlastname="${user.lastName}" 
            data-userage="${user.age}" 
            data-useremail="${user.email}"
            data-roles=${getRoles(user)}>Edit</button></td>
            <td><button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteModal"
            data-userid="${user.id}"
            data-userfirstname="${user.firstName}" 
            data-userlastname="${user.lastName}" 
            data-userage="${user.age}" 
            data-useremail="${user.email}"
            data-roles=${getRoles(user)}>Delete</button></td>
            </tr>`
        )
    }

//*** Заполнение модалки редактирования ************************************************************************8
    $('#editModal').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget)
        const modal = $(this)

        modal.find('#idEdit').val(button.data('userid'))
        modal.find('#firstNameEdit').val(button.data('userfirstname'))
        modal.find('#lastNameEdit').val(button.data('userlastname'))
        modal.find('#ageEdit').val(button.data('userage'))
        modal.find('#emailEdit').val(button.data('useremail'))
        if (button.data('roles').includes("ADMIN")){
            modal.find('#ROLE_ADMIN').attr("selected", "selected")
            modal.find('#ROLE_USER').attr("selected", "selected")
        } else {
            modal.find('#ROLE_USER').attr("selected", "selected")
        }

    })

//***  Редактирование Пользователя  *******************************************************************************


    $('#editButton').on('click', function (){
        const id = document.getElementById("idEdit").value
        const name = document.getElementById("firstNameEdit").value
        const lastname = document.getElementById("lastNameEdit").value
        const age = document.getElementById("ageEdit").value
        const email = document.getElementById("emailEdit").value
        const pass = document.getElementById("passwordEdit").value

        let roles = $('#rolesEdit').val()

        let user = {
            id: id,
            firstName: name,
            lastname: lastname,
            age: age,
            email: email,
            pass: pass,
            roles: roles
        }

        let trID = "user" + user.id

        fetch("http://localhost:8080/api/admin/edit", {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "PUT",
            body: JSON.stringify({id: id, firstName: name, lastName: lastname, age: age, email: email, password: pass, roles: roles})
        }).then(function (){
            $('.modal').modal('hide');
            showUsers()

        })
    })

//*** Заполнение модалки удаления ************************************************************************8
    $('#deleteModal').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget)
        const modal = $(this)

        modal.find('#idDelete').val(button.data('userid'))
        modal.find('#firstNameDelete').val(button.data('userfirstname'))
        modal.find('#lastNameDelete').val(button.data('userlastname'))
        modal.find('#ageDelete').val(button.data('userage'))
        modal.find('#emailDelete').val(button.data('useremail'))
        if (button.data('roles').includes("ADMIN")){
            modal.find('#ROLE_ADMIN').attr("selected", "selected")
            modal.find('#ROLE_USER').attr("selected", "selected")
        } else {
            modal.find('#ROLE_USER').attr("selected", "selected")
        }

    })

//***  Удаление Пользователя  *******************************************************************************
    $('#deleteButton').on('click', function (){
        const id = document.getElementById("idDelete").value
        let urlDelete = "http://localhost:8080/api/admin/delete/" + id;
        fetch(urlDelete , {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "DELETE",
        }).then(() => {
            $('.modal').modal('hide');
            showUsers()
        })
    })


});