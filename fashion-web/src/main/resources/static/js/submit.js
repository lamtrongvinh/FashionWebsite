const usernameFirstname = document.getElementById("firstName");
const usernameLastname = document.getElementById("lastName");
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("rp_password");
;
const emailInput = document.getElementById("email");

const checkBox = document.getElementById("form2Example3c");

usernameFirstname.onblur = function () {
console.log(checkBox.checked);
    checkUsername();
}
usernameLastname.onblur = function () {
    checkUsername2();
}
passwordInput.onblur = function () {
    checkPassword();
}
confirmPasswordInput.onblur = function () {
    checkConfirmPassword();
}
emailInput.onblur = function () {
    checkEmail();
}
<<<<<<< HEAD
const submit = document.querySelector("#submit1");
=======
const submit = document.querySelector('#submit1');
>>>>>>> 455e5b311cabc8b5d4b7159102f768a4a914650c


submit.addEventListener("submit", (event) => {
    const errorMessages = document.querySelectorAll(".error-message");
    if(!checkBox.checked) {
        event.preventDefault();
    }
    for (let i = 0; i < errorMessages.length; i++) {
        if (errorMessages[i].innerHTML !== "" ) {
            event.preventDefault();
            break;
        }
    }

});

const showError = (input, message) => {
    const errorDiv = input.parentElement.querySelector('.error-message');
    input.parentElement.classList.add('invalid');
    errorDiv.innerHTML = message;

};

const hideError = (input) => {
    const errorDiv = input.parentElement.querySelector('.error-message');
    errorDiv.innerHTML = "";
    input.parentElement.classList.remove('invalid');

};

const checkUsername = () => {
    const usernameValue = usernameFirstname.value.trim();
    if (usernameValue === "") {
        showError(usernameFirstname, "Firstname is not blank!");
    } else {
        hideError(usernameFirstname);
    }
};

const checkUsername2 = () => {
    const usernameValue = usernameLastname.value.trim();
    if (usernameValue === "") {
        showError(usernameLastname, "Lastname is not blank!");
    } else {
        hideError(usernameLastname);
    }
};

const checkPassword = () => {
    const passwordValue = passwordInput.value.trim();
    if (passwordValue === "") {
        showError(passwordInput, "Passwords is not blank!");
    } else if (passwordValue.length < 8) {
        showError(passwordInput, "Passwords must be at least 8 characters!");
    } else {
        hideError(passwordInput);
    }
};

const checkConfirmPassword = () => {
    const confirmPasswordValue = confirmPasswordInput.value.trim();
    const passwordValue = passwordInput.value.trim();
    if (confirmPasswordValue === "") {
        showError(confirmPasswordInput, "Please enter password!");
    } else if (confirmPasswordValue !== passwordValue) {
        showError(confirmPasswordInput, "Passwords must match!");
    } else {
        hideError(confirmPasswordInput);
    }
};

const checkEmail = () => {
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailValue === "") {
        showError(emailInput, "Email is not blank!");
    } else if (!emailRegex.test(emailValue)) {
        showError(emailInput, "Email invalid!");
    } else {
        hideError(emailInput);
    }
};



