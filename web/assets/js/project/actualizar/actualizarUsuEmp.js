$(document).ready(validarEmpresa());
function validarEmpresa() {
    //consultaCiudad('#ciudad', 'ciudadUsuarioActualizar', $('#ciudadUsusario').val());
    consultaTipoDoc($('#tipoDocUsusario').val());
    consultagenero($('#generoUsusario').val());

    var rol = $('#rolUsuario').val();
    if (rol === '3' || rol === 3) {
        consultaCiudad('#ciudadEmpresa', 'idCiudadEmpresa', $('#ciudEmpresaActualizar').val());
        var esEmpresa = $('#esEmpresa').val();
        if (esEmpresa === 0 || esEmpresa === '0') {
            limpiarFormulario('#actualizarEmpresa');

        } else {

            $('#opcionEmpresa').empty();
            $('#btnActualizarEmpresa').empty();
            $('#opcionEmpresa').html('<i class="fa fa-building" style="color: rgb(252, 115, 30)" ></i> Modificar datos de empresa');
            $('#btnActualizarEmpresa').html('Actualizar');
        }
    } else {
        $('.ocultar').hide();
    }
}
;

function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}
function validarNumero(telefono) {
    if (/^([0-9]{5,13})*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}
function validarLetras(leetras) {
    if (/^([a-z A-Z]{3,20})*$/.test(leetras)) {
        return true;
    } else {
        return false;
    }
}

//actualizar persona

function muestra_oculta(id){
if (document.getElementById){ //se obtiene el id
var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div
}
}
window.onload = function(){/*hace que se cargue la función lo que predetermina que div estará oculto hasta llamar a la función nuevamente*/
muestra_oculta('contenido');/* "contenido_a_mostrar" es el nombre que le dimos al DIV */
}

document.getElementById('datosActualizarpresona').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#datosActualizarpresona");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

$('#datosActualizarpresona').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();

    var datos = new FormData($('#datosActualizarpresona')[0]);
    if ($('#datosActualizarpresona')[0].checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
        var btn = document.getElementById('actualizarPersona');
        $('#cargando').addClass('is-active');
        btn.disabled = true;
        $.ajax({
            url: "./actualizaUsuEmp?accion=actualizarPersonas",
            type: 'POST',
            data: datos,
            contentType: false,
            processData: false,
            error: function (jqXHR, textStatus, errorThrown) {
                $('#cargando').removeClass('is-active');
                btn.disabled = false;
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
            }, success: function (data, textStatus, jqXHR) {
                btn.disabled = false;
                $('#cargando').removeClass('is-active');
                if (data === true || data === 'true') {
                    messageOk('Se ha actualizado correctamente!!');

                } else {
                    messageError('Ha ocurrido un errror, favor verificar datos');
                }
            }
        })
    } else {

        $('#datosActualizarpresona').addClass('was-validated');
    }
})


//actualizar clave

document.getElementById('actualizarUsuario').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#actualizarUsuario");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

$('#actualizarUsuario').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();


    if ($('#actualizarUsuario')[0].checkValidity() && validarClave()) {
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();

        var btn = document.getElementById('btnActualizarUsuario');
        $('#cargando').addClass('is-active');
        btn.disabled = true;

        var datos = $('#actualizarUsuario').serialize();
        $.ajax({
            url: "./actualizaUsuEmp?accion=actualizarUsuarios",
            type: 'POST',
            data: datos,
            error: function (jqXHR, textStatus, errorThrown) {
                btn.disabled = false;
                $('#cargando').removeClass('is-active');
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
            }, success: function (data, textStatus, jqXHR) {
                btn.disabled = false;
                $('#cargando').removeClass('is-active');
                if (data === true || data === 'true') {
                    messageOk('Su usuario ha sido actualizado!!');
                } else {
                    messageError('Ha ocurrido un error, favor verificar datos.');
                }
            }
        })
    } else {

        $('#actualizarUsuario').addClass('was-validated');
    }
})

//actualizar empresa
document.getElementById('actualizarEmpresa').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#actualizarEmpresa");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

$('#actualizarEmpresa').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();

    var formulario = $("#actualizarEmpresa");
    var datosVal = [
        nombreEmpresa = $('#nombreEmpresa').val(),
        celularEmpresa = $('#celularEmpresa').val(),
        telefonoEmpresa = $('#telefonoEmpresa').val(),
        correoEmpresa = $('#correoEmpresa').val(),
        direccionEmpresa = $('#direccionEmpresa').val(),
        idCiudadEmpresa = $('#idCiudadEmpresa').val()


    ];
    //console.log(datosVal);

    if ($('#actualizarEmpresa')[0].checkValidity() && valCampos(datosVal)) {
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
        var datos = $('#actualizarEmpresa').serialize();
        var btn = document.getElementById('btnActualizarEmpresa');
        $('#cargando').addClass('is-active');
        btn.disabled = true;
        $.ajax({
            url: "./actualizaUsuEmp?accion=actualizarEmpresa&" + datos,
            type: 'POST',
            contentType: false,
            processData: false, error: function (jqXHR, textStatus, errorThrown) {
                $('#cargando').removeClass('is-active');
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
                btn.disabled = false;
            },
            success: function (data) {
                $('#cargando').removeClass('is-active');
                if (data === 'true') {
                    messageOk('Operación realizada!!');
                } else {
                    messageError('Ha ocurrido un error, favor verificar datos.');
                }
                formulario.addClass('was-validated');
                btn.disabled = false;
            }
        })
    } else {
        formulario.addClass('was-validated');
    }
})










