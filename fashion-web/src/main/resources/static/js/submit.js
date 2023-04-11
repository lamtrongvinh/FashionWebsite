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
const submit = document.querySelector('#submit111');


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
        showError(usernameFirstname, "Tên không được bỏ trống");
    } else {
        hideError(usernameFirstname);
    }
};

const checkUsername2 = () => {
    const usernameValue = usernameLastname.value.trim();
    if (usernameValue === "") {
        showError(usernameLastname, "Tên không được bỏ trống");
    } else {
        hideError(usernameLastname);
    }
};

const checkPassword = () => {
    const passwordValue = passwordInput.value.trim();
    if (passwordValue === "") {
        showError(passwordInput, "Mật khẩu không được bỏ trống");
    } else if (passwordValue.length < 8) {
        showError(passwordInput, "Mật khẩu phải chứa ít nhất 8 ký tự");
    } else {
        hideError(passwordInput);
    }
};

const checkConfirmPassword = () => {
    const confirmPasswordValue = confirmPasswordInput.value.trim();
    const passwordValue = passwordInput.value.trim();
    if (confirmPasswordValue === "") {
        showError(confirmPasswordInput, "Vui lòng nhập lại mật khẩu");
    } else if (confirmPasswordValue !== passwordValue) {
        showError(confirmPasswordInput, "Mật khẩu nhập lại không khớp");
    } else {
        hideError(confirmPasswordInput);
    }
};

const checkEmail = () => {
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailValue === "") {
        showError(emailInput, "Email không được bỏ trống");
    } else if (!emailRegex.test(emailValue)) {
        showError(emailInput, "Email không hợp lệ");
    } else {
        hideError(emailInput);
    }
};



