$(document).ready(function(){
  var $registerForm=$("#formValidation");

  $registerForm.validate({
      rules:{
          product_code:{
              required: true,
          },
          product_name:{
              required: true,
              lattersonly: true
          },
          product_desciption:{
              required: true,
          },
          product_price:{
            required: true,
            min: 1
          },
          product_discount:{
            required: true,
            digits: true
          },
          product_quantity:{
              required: true,
              min: 1
          },
          size:{
              required: true,
          },
          category_id:{
            required: true,
          },
          file: {
            required: true,
            accept: 'image/*'
          }
      },
      messages:{
          product_code:{
              required:'*Please enter product code',
          },
          product_name:{
              required:'*Please enter product name',
              lattersonly: '*Invalid Name'
          },
          product_desciption:{
              required:'*Please enter product description',
          },
          product_price:{
            required: '*Please enter your product price',
            min: '*Price must be greater than 0'
          },
          product_discount:{
            required: '*Please enter your product discount',
            digits: '*Discount must be no less than 0'
          },
          product_quantity:{
              required:'*Please enter product quantity',
              min: '*Quantity must be greater than 0'
          },
          size:{
            required: '*Please enter product size',
        },
          category_id:{
              required:'*Please choose category',
          },
          file: {
              required: "*File required",
              accept: '*Only accept image'
          }
      }
  })

  // check valid name: not contain digits or special characeters
  jQuery.validator.addMethod('lattersonly', function(value, element){
      return /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/.test(value);
  });

})