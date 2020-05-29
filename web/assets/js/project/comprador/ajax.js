

function consultarDatosFormulario() {
    consultaTipoDoc();
    consultagenero();
    consultaCiudad();
    consultaRol();
    modalRegistro();
}

function consultaRol() {
    $.ajax({
        url: "./comprador?accion=consultaRol",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        success: function (data) {
            //console.log(data);
            for (var i = 0; i < data.length; i++) {
                //console.log( data[i].rol); 
                if (data[i].rol === 'Vendedor') {
                    $('#rol').val(data[i].idRol);
                }

            }

        }
    })
}
function consultaTipoDoc() {
    $.ajax({
        url: "./comprador?accion=consultaTipoDoc",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        success: function (data) {
            selects(data, '#tipoDoc', 'tipoDocUsuario');


        }
    })
}
function consultagenero() {
    $.ajax({
        url: "./comprador?accion=consultaGenero",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        success: function (data) {
            selects(data, '#genero', 'generoUsuario');


        }
    })
}
function consultaCiudad() {
    $.ajax({
        url: "./comprador?accion=consultaCiudad",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        success: function (data) {
            selects(data, '#ciudad', 'ciudadUsuario');


        }
    })
}

function modalRegistro() {
  $('#bloqueo').toggle(500);
  $('#modalRegistro').toggle(500);
}

function selects(datos, idDiv, idInput) {
    //console.log(datos);
    var select = '<select id="' + idInput + '" name="' + idInput + '" >';
    if (idDiv === '#genero') {
        for (var i = 0; i < datos.length; i++) {
            select += '<option value="' + datos[i].idGenero + '">' + datos[i].genero + '</option>';
        }

    } else if (idDiv === '#tipoDoc') {
        for (var i = 0; i < datos.length; i++) {
            select += '<option value="' + datos[i].idTipoDoc + '">' + datos[i].tipoDoc + '</option>';
        }

    } else if (idDiv === '#ciudad') {
        for (var i = 0; i < datos.length; i++) {
            select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
        }
    }

    //  console.log(select);
    select += '</select>';
    $(idDiv).html(select);
}
$(function () {
    
    
  

    $('#registroUsuario').submit(function (e) {
        e.preventDefault();
        var datos = $('form#registroUsuario').serialize();
        var formulario = $("#registroUsuario");
        
        if (formulario[0].checkValidity()) {
        formulario.addClass('was-validated');
//        $.ajax({
//            url: "./comprador?accion=registrarUsuario&" + datos,
//            type: 'POST',
//            contentType: false,
//            processData: false,
//            success: function (data) {
//                alert(data);
//
//            }
//        })
alert('mjhsagd');
        }else{
            formulario.addClass('was-validated');
        }
    })
    
//    s

//validarclaves
function validarClave(idBtn) {

    var btn = document.getElementById(idBtn);
    btn.disabled = true;
    var con1 = $('#cont1').val();
    var con2 = $('#cont2').val();
    if (con1.length >= 10) {
        $('#spValidar1').empty();
        if (con2.length >= 10) {
            if (con1 !== con2) {

                $('#spValidar2').empty();
                $('#spValidar2').html('Las cotraseñas no conciden');

                return false;
            } else {
                $('#spValidar2').empty();
                $('#spValidar1').empty();
                btn.disabled = false;
                return true;
            }
        } else {
            $('#spValidar2').empty();
            $('#spValidar2').html('Mínimo 10 carácteres');

        }
    } else {
        $('#spValidar1').empty();
        $('#spValidar1').html('Mínimo 10 carácteres');
    }

}
    
})

  document.getElementById('registroUsuario').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#registroUsuario");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})


 