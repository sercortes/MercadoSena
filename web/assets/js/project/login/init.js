

document.getElementById('formOnes').addEventListener('input', e => {

    e.preventDefault()
    var form = $("#formOnes")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    form.addClass('was-validated');

})

document.getElementById('formOnes').addEventListener('submit', e => {

    e.preventDefault()
    
    var form = $("#formOnes")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    form.addClass('was-validated');
    
    if (!checkInputs()) {
        messageInfo('complete el formulario')
        return false
    }
    
    if (!checkInputsTwo()) {
        messageInfo('complete el formularios')
        return false
    }
    
    let ema = document.getElementById('emails').value
    let pas = document.getElementById('passs').value
    let url = window.location.pathname;
    
    url = localStorage.getItem('page')
    console.log(url)
    
    let datas = {
        email : ema,
        pass : pas,
        url : url
    }
    
        $('#carga').addClass('is-active');

    $.ajax({
        type: "POST",
        url: './login',
        datatype: 'json',
        data:datas
    }).done(function (data) {

        if (data) {
            window.location.replace(window.location.pathname);
        }else{
            messageInfo('cuenta incorrecta')
        }
        clean()
       
    }).fail(function (data){
        
        console.log(data)
        clean()
        
    })


})

function clean() {
    $('#formOnes').trigger('reset')
    var form = $("#formOnes")
    form.removeClass('was-validated');
    $('#carga').removeClass('is-active')
}

function checkInputs() {

    let ema = document.getElementById('emails').value
    let pas = document.getElementById('passs').value

    if (ema == '' || pas == '' || ema.length <= 2 ||
            pas.length <= 8 || ema == null) {
        return false
    }

    return true
}

function checkInputsTwo() {

    let ema = document.getElementById('emails').value
    let pas = document.getElementById('passs').value
    
    console.log(ema)
    console.log(pas)

    if (!ema.replace(/\s/g, '').length || !pas.replace(/\s/g, '').length ) {
        return false
    }

    return true
}