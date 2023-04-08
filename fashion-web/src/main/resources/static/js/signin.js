let form = document.getElementById("form");
let username = document.getElementById("username");
let lastname = document.getElementById("last_name");
let email = document.getElementById("email");
let password = document.getElementById("password");
let confirmPassword = document.getElementById("conpassword");
let check_box = document.getElementById("checkbox");


function showError(input, message) {
  let small = input.parentElement.querySelector('small')
  small.innerText = message
  small.style.color = "red"
}

function showSuccess(input) {
  let small = input.parentElement.querySelector('small')
  small.innerText = ''
}

function checkEmptyError(listInput) {
  let isEmptyError = false;
  listInput.forEach(input => {
    input.value = input.value.trim()

    if(!input.value) {
      isEmptyError = true;
      showError(input, 'Please complete this field')
    } else {
      showSuccess(input)
    }
  });

  return isEmptyError
}

function checkEmailError(input) {
  const regexEmail =
  /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  input.value = input.value.trim()

  let isEmailError = !regexEmail.test(input.value)
  if (regexEmail.test(input.value)) {
    showSuccess(input)
  } else {
    showError(input, 'Email Invalid')
  }

  return isEmailError
}

function checkLengthError(input, min, max) {
  input.value = input.value.trim()

  if (input.value.length < min) {
    showError(input, `At least ${min} characters`)
    return true
  }

  if (input.value.length > max) {
    showError(input, `No more ${max} characters`)
    return true
  }

  showSuccess(input)
  return false
}

function checkMatchPasswordError(passwordInput, cfPasswordInput) {
  if (passwordInput.value !== cfPasswordInput.value) {
    showError(cfPasswordInput, "Password doesn't match")
    return true
  }

  showSuccess(input)
  return false
}

function checkedBoxError() {
  if(!check_box.checked) {
    check_box.style.borderColor = "red";
    return true
  }

  showSuccess(check_box)
  return false
}
let notAgree = checkedBoxError()
  let isEmptyError = checkEmptyError([username, lastname, email, password, confirmPassword])
  let isEmailError = checkEmailError(email)
  let isNameError = checkLengthError(username, 3, 10)
  let isPasswordError = checkLengthError(password, 6, 15)
  let isMatchError = checkMatchPasswordError(password, confirmPassword)

form.addEventListener("submit", function(e){
  if (isEmptyError == true || 
    !isEmailError || !isNameError || !isPasswordError || !isMatchError) {
      e.preventDefault()
  }
  return true
})