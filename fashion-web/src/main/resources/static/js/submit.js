$(document).ready(function(){
    var $registerForm=$("#formValidation");

    $registerForm.validate({
        rules:{
            first_name:{
                required: true,
                lattersonly: true
            },
            last_name:{
                required: true,
                lattersonly: true
            },
            email:{
                required: true,
                email: true
            },
            password:{
                required: true,
                checkPass: true,
            },
            repeat_password:{
                required: true,
                equalTo: '#password'
            }
        },
        messages:{
            first_name:{
                required:'*Please enter your first name',
                lattersonly: '*Invalid Name'
            },
            last_name:{
                required:'*Please enter your last name',
                lattersonly: '*Invalid Name'
            },
            email:{
                required:'*Please enter your email address',
                email: '*Invalid Email'
            },
            password:{
                required:'*Please enter your password',
                checkPass: '*Invalid Password (at least 8 characters, disallowed space)'
            },

            repeat_password:{
                required:'*Please enter your confirm password',
                equalTo: '*Password mismatch'
            }
        }
    })

    // check valid name: not contain digits or special characeters
    jQuery.validator.addMethod('lattersonly', function(value, element){
        return /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/.test(value);
    });

    // check valid email: not contain "", at least 8 characters
    jQuery.validator.addMethod('checkPass', function(value, element){
        return /^(?!.* ).{8,}$/.test(value);
    });

})