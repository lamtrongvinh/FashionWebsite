const usernameFirstname = document.getElementById("firstName");
const usernameLastname = document.getElementById("lastName");
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("rp_password");

const emailInput = document.getElementById("email");

usernameFirstname.onblur = function () {
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
const submit = document.getElementById("submit1");


submit.addEventListener("submit", (event) => {
    const errorMessages = document.querySelectorAll(".form-message");
    for (let i = 0; i < errorMessages.length; i++) {
        if (errorMessages[i].innerHTML !== "" ) {
            event.preventDefault();
            break;
        }
    }

});

const showError = (input, message) => {
    const errorDiv = input.parentElement.querySelector('.form-message');

    errorDiv.innerHTML = message;

};

const hideError = (input) => {
    const errorDiv = input.parentElement.querySelector('.form-message');
    errorDiv.innerHTML = "";


};

const checkUsername = () => {
    const usernameValue = usernameFirstname.value.trim();
    if (usernameValue === "") {
        showError(usernameFirstname, "First Name cannot be empty");
    } else {
        hideError(usernameFirstname);
    }
};

const checkUsername2 = () => {
    const usernameValue = usernameLastname.value.trim();
    if (usernameValue === "") {
        showError(usernameLastname, "Last Name cannot be empty");
    } else {
        hideError(usernameLastname);
    }
};

const checkPassword = () => {
    const passwordValue = passwordInput.value.trim();
    if (passwordValue === "") {
        showError(passwordInput, "Password cannot be empty");
    } else if (passwordValue.length < 8) {
        showError(passwordInput, "Password must contain at least 8 characters");
    } else {
        hideError(passwordInput);
    }
};

const checkConfirmPassword = () => {
    const confirmPasswordValue = confirmPasswordInput.value.trim();
    const passwordValue = passwordInput.value.trim();
    if (confirmPasswordValue === "") {
        showError(confirmPasswordInput, "Password cannot be empty");
    } else if (confirmPasswordValue !== passwordValue) {
        showError(confirmPasswordInput, "Password incorrect");
    } else {
        hideError(confirmPasswordInput);
    }
};

const checkEmail = () => {
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailValue === "") {
        showError(emailInput, "Email cannot be empty");
    } else if (!emailRegex.test(emailValue)) {
        showError(emailInput, "Email invalid");
    } else {
        hideError(emailInput);
    }
};



