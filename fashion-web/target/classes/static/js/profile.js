$(document).ready(function() {
    $(".save-profile").click(function() {
        var user_id = $(this).attr("data-user-id");
        var first_name = document.getElementById("first_name").value;
        var last_name = document.getElementById("last_name").value;
        var email = document.getElementById("email").value;
        const expression = /^((?:[A-Za-z0-9!#$%&'*+\-\/=?^_`{|}~]|(?<=^|\.)"|"(?=$|\.|@)|(?<=".*)[ .](?=.*")|(?<!\.)\.){1,64})(@)((?:[A-Za-z0-9.\-])*(?:[A-Za-z0-9])\.(?:[A-Za-z0-9]){2,})$/i
        var error_lastname = document.getElementById("error_lastname");
        var error_email = document.getElementById("error_email");
        let lastname_flag = true;
        let email_flag = true;
        if (last_name == null || last_name == '') {
            error_lastname.style.display = "inline-block"
            error_lastname.innerHTML = "Please enter your last name!";
            lastname_flag = false;
        }

        if (!expression.test(String(email).toLowerCase()) || email == '') {
            error_email.style.display = "inline-block"
            error_email.innerHTML = "Please enter your email valid!";
            email_flag = false;
        }
        alert(email)
        if (email_flag == true && lastname_flag == true) {
            alert(1232)
            $.ajax({
            
                type: "GET",
                url: "/auth/profile/update",
                data : {
                    user_id : user_id,
                    first_name : first_name,
                    last_name : last_name,
                    email : email
                },
                success: function(flag) {
                    alert(flag)
                    if (flag) {
                        error_email.innerHTML = "Email exist!";
                    }
                }
            });
        }
        
    });
});

$(document).ready(function() {

    $(".save-password").click(function() {
    let currentPassword = document.getElementById("inputPasswordOld").value;
    let newPassword = document.getElementById("inputPasswordNew").value;
    let repeatPassword = document.getElementById("inputPasswordNewVerify").value;
    let password_error = document.getElementById("password_error")
    let repeat_password_error = document.getElementById("repeat_password_error");
    let currentPassword_error = document.getElementById("currentPassword_error");
    let form_changePassword = document.getElementById("form_changePassword");
    let checkPasswordValid = true;
    let checkMatchPassword = true;
    let checkCurrentPassword = true;
    alert(newPassword.length)
    if (newPassword.length < 8 || newPassword.length > 20 || newPassword.includes(" ")) {
        password_error.innerHTML = "Password invalid!";
        checkPasswordValid = false;
    }

    if (newPassword.localeCompare(repeatPassword) != 0 && checkPasswordValid == true) {
        repeat_password_error.innerHTML = "Password is not match!";
        checkMatchPassword = false;
    }

    if (currentPassword == null || currentPassword.length < 8 || currentPassword == '') {
        checkCurrentPassword = false;
    }

    if (checkCurrentPassword && checkMatchPassword && checkPasswordValid) {
        alert(1231)
        $.ajax({
            type: "GET",
            url: "/auth/profile/change-password",
            data : {
                currentPassword : currentPassword,
                newPassword : newPassword
            },
            success: function(flag) {
                if (flag == false) {
                    currentPassword_error.innerHTML = "Current password not correct!"
                } else {
                    form_changePassword.style.display = "none";
                }
            }
    });
    }
    
        
        
    });
});

function openForm() {
    document.getElementsByClassName("change_pass")[0].style.display = "block";
    document.getElementsByClassName("change_pass")[0].style.opacity = 1;
    document.getElementsByClassName("row")[0].style.opacity = 0.6;
  }
  
  function closeForm() {
    document.getElementsByClassName("change_pass")[0].style.display = "none";
    document.body.style.opacity = 1;
}