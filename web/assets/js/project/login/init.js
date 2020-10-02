var idCompany
$(function () {

    if (sessionStorage.getItem('falls') === null) {
        sessionStorage.setItem('falls', 0)
    }

})

$('#login').click(function () {
    
    $('#exampleModal').modal('show');
    
})

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
    let datas = {
        email: ema,
        pass: pas,
        url: url,
    }
    $('#carga').addClass('is-active');

    parseInt(sessionStorage.falls++)
    sessionStorage.getItem('falls')

    if (sessionStorage.getItem('falls') <= 9) {

    datas.ok = sessionStorage.getItem('ok')
    datas.fall = sessionStorage.getItem('falls')
    datas.check = 1

        $.ajax({
            type: "POST",
            url: './login',
            datatype: 'json',
            data: datas
        }).done(function (data) {


            if (data.length > 1) {

                if (data[1] === 'true' && data[0] === 'true') {
                    
                    sessionStorage.setItem('falls', 0);
                    window.location.replace(window.location.pathname);
                    idCompany = data[2]
                    sessionStorage.setItem('idCompany', data[2]);

                } else if (data[0] === 'false') {
                    
                    $('#exampleModal').toggle();
                    sessionStorage.setItem('falls', 0)
                    sessionStorage.setItem('idCompany', data[2]);
                    idCompany = data[2]
                    modalPregunta();
                    
                }
            } else if (!data) {
                
                messageInfo('Datos incorrectos');
                
            }
            
            clean()

        }).fail(function (data) {

            clean()

        })

    } else {
        
        clean()
        messageInfo('cuenta incorrecta')
        
    }

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

    if (!ema.replace(/\s/g, '').length || !pas.replace(/\s/g, '').length) {
        return false
    }

    return true
    
}