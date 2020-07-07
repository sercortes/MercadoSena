

function consultarDatosFormulario(res) {

    $('#exampleModal').modal('hide');
    consultaTipoDoc();
    consultagenero();
    consultaCiudad('#ciudad', 'ciudadUsuario');
    if (res === 'si') {
        modalRegistroRe();
    } else {
        modalRegistro();
    }
}

function consultaTipoDoc(valor, div) {
    var divE = '#tipoDoc';
    var tipDoc = 'tipoDocUsuario';
    if (div !== undefined && div !== null && div !== 'undefined' && div !== '') {
        divE = div;
        tipDoc = 'tipoDocUsuarioRec';
    }
    console.log(divE);
    console.log(tipDoc);
    console.log(div);
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./registro?accion=consultaTipoDoc",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            modalRegistro();
            // $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
        },
        success: function (data) {
            $('#carga').removeClass('is-active');
            selects(data, divE, tipDoc, valor);


        }
    })

}
function consultagenero(valor) {
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./registro?accion=consultaGenero",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            modalRegistro();
            // $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
        },
        success: function (data) {
            $('#carga').removeClass('is-active');
            selects(data, '#genero', 'generoUsuario', valor);

        }
    })
}
function consultaCiudad(idDiv, idInput, valor) {
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./registro?accion=consultaCiudad",
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            $('#bloqueo').hide();
            $('#modalRegistro').hide();
            // $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
        },
        success: function (data) {
            $('#carga').removeClass('is-active');
            selects(data, idDiv, idInput, valor);


        }
    })

}

function modalRegistro() {
    $('body').attr('Style', '');
    $('#bloqueo').toggle();
    $('#modalRegistro').toggle();
}
function modalRegistroRe() {

    $('body').attr('Style', 'overflow: hidden');



    $('#bloqueo').toggle();
    $('#modalRegistro').toggle();
}

function selects(datos, idDiv, idInput, valor, accion) {
    valor = parseInt(valor);
    //console.log(idDiv + ' ' + idInput + ' ' + valor + ' ' + accion);
    var select = '<select id="' + idInput + '" name="' + idInput + '" class="form-control was-validated" required>';
    select += '<option value="" selected>Seleccione...</option>';
    if (datos !== null) {
        if (idDiv === '#genero') {
            for (var i = 0; i < datos.length; i++) {
                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idGenero === valor) {
                        select += '<option value="' + datos[i].idGenero + '" selected >' + datos[i].genero + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idGenero + '">' + datos[i].genero + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idGenero + '">' + datos[i].genero + '</option>';
                }
            }

        } else if (idDiv === '#tipoDoc' || idDiv === '#tipoDocActu') {
            for (var i = 0; i < datos.length; i++) {

                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idTipoDoc === valor) {
                        select += '<option value="' + datos[i].idTipoDoc + '" selected>' + datos[i].tipoDoc + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idTipoDoc + '">' + datos[i].tipoDoc + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idTipoDoc + '">' + datos[i].tipoDoc + '</option>';
                }
            }

        } else if (idDiv === '#ciudad' || idDiv === '#ciudadBucar') {
            if (idDiv === '#ciudadBucar') {
                select = '<select title="Ciudades" id="' + idInput + '" name="' + idInput + '" class="form-control was-validated" >';
                select += '<option value="" selected>Ciudades...</option>';
            }

            for (var i = 0; i < datos.length; i++) {

                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idCiudad === valor) {

                        select += '<option value="' + datos[i].idCiudad + '" selected>' + datos[i].nombreCiudad + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
                }
            }
        } else if (idDiv === '#ciudadEmpresa') {
            for (var i = 0; i < datos.length; i++) {

                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idCiudad === valor) {
                        select += '<option value="' + datos[i].idCiudad + '" selected>' + datos[i].nombreCiudad + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
                }
            }
        } else if (accion === 'categorias') {
            select = '<select id="' + idInput + '" title="Categorías" name="' + idInput + '" class="form-control was-validated" >';
            select += '<option value="" selected>Categorías...</option>';
            for (var i = 0; i < datos.length; i++) {

                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idCiudad === valor) {
                        select += '<option value="' + datos[i].idcategoria + '" selected>' + datos[i].nombreCategoria + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idcategoria + '">' + datos[i].nombreCategoria + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idcategoria + '">' + datos[i].nombreCategoria + '</option>';
                }
            }
        
        } else if (accion === 'vendedor') {
            select = '<select id="' + idInput + '" name="' + idInput + '" class="form-control was-validated" title="Vendedores" >';
            select += '<option value="" selected>Vendedores...</option>';
            for (var i = 0; i < datos.length; i++) {

                if (valor !== undefined && valor !== '' && valor !== null) {

                    if (datos[i].idEmpresa === valor) {
                        select += '<option value="' + datos[i].idEmpresa + '" selected>' + datos[i].nombreEmpresa + '</option>';
                    } else {
                        select += '<option value="' + datos[i].idEmpresa + '">' + datos[i].nombreEmpresa + '</option>';
                    }
                } else {
                    select += '<option value="' + datos[i].idEmpresa + '">' + datos[i].nombreEmpresa + '</option>';
                }
            }
        }

        //  console.log(select);
        select += '</select>';
        $(idDiv).html(select);
    }
}



$('#registroUsuario').submit(function (e) {

    // $('#registrarUsuario').click(function(e) {
    e.preventDefault();
    e.stopPropagation();

    var formulario = $("#registroUsuario");
    var datosVal = [
        nombreUsuario = $('#nombreUsuario').val(),
        apellidoUsuario = $('#apellidoUsuario').val(),
        correoUsuario = $('#correoUsuario').val(),
        celularUsuario = $('#celularUsuario').val(),
        documentoUsuario = $('#documentoUsuario').val(),
        ciudadUsuario = $('#ciudadUsuario').val(),
        tipoDocUsuario = $('#tipoDocUsuario').val,
        generoUsuario = $('#generoUsuario').val()

    ];
    // console.log(datosVal);

    if ($('#registroUsuario')[0].checkValidity() && valCampos(datosVal) && validarClave()) {
        $('#carga').addClass('is-active');
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
        var datos = $('form#registroUsuario').serialize();
        var btn = document.getElementById('registrarUsuario');
        btn.disabled = true;

        $.ajax({
            url: "./registro?accion=registrarUsuario&" + datos,
            type: 'POST',
            contentType: false,
            processData: false,
            error: function (jqXHR, textStatus, errorThrown) {
                btn.disabled = false;
                $('#carga').removeClass('is-active');
                modalRegistro();
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
            },
            success: function (data) {

                $('#carga').removeClass('is-active');
                modalRegistro();
                if (data === 'true') {

                    messageInfo('Registro realizado, hemos enviado al correo registrado sus datos de ingreso y el link de activación para su cuenta.')
                } else {
                    messageError('Error al relizar el registro');
                }

                limpiarFormulario('#registroUsuario');
                formulario.addClass('was-validated');
                btn.disabled = false;
            }
        })

    } else {

        formulario.addClass('was-validated');
    }
})

//    s

//validarclaves


function validarClave() {
    var con1 = $('#clave1').val();
    var con2 = $('#clave2').val();
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

document.getElementById('registroUsuario').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#registroUsuario");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

function valCampos(campos) {
    var rta = true;
    for (var i = 0; i < campos.length; i++) {
        if (campos[i] === '' || campos[i] === null) {
            rta = false;
            // alert('akskhhd');
            return rta;
        }
    }
    return rta;
}

function limpiarFormulario(formularioRec) {
    var formulario = document.querySelector(formularioRec);
    var campos = formulario.querySelectorAll("input, select");

    for (var i = 0; i < campos.length; i++) {
        var campo1 = campos[i];

        if (campo1.nodeName === "INPUT" && campo1.type !== "submit" && campo1.type !== "hidden")
            campo1.value = "";
        else if (campo1.nodeName === "SELECT")
            campo1.selectedIndex = 0;
    }
    // campo1[0].focus();
}

function consultarDatosFormularioEmpresa() {
    consultaCiudad('#ciudadEmpresa', 'idCiudadEmpresa');
}


$('#registroEmpresa').submit(function (e) {

    // $('#registrarUsuario').click(function(e) {
    e.preventDefault();
    e.stopPropagation();

    var formulario = $("#registroEmpresa");
    var datosVal = [
        nombreEmpresa = $('#nombreEmpresa').val(),
        celularEmpresa = $('#celularEmpresa').val(),
        telefonoEmpresa = $('#telefonoEmpresa').val(),
        correoEmpresa = $('#correoEmpresa').val(),
        direccionEmpresa = $('#direccionEmpresa').val(),
        idCiudadEmpresa = $('#idCiudadEmpresa').val()


    ];
    //console.log(datosVal);

    if ($('#registroEmpresa')[0].checkValidity() && valCampos(datosVal)) {
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
        var datos = $('#registroEmpresa').serialize();
        var btn = document.getElementById('registrarEmpresa');
        $('#carga').addClass('is-active');
        btn.disabled = true;

        $.ajax({
            url: "./registro?accion=registroEmpresa&" + datos,
            type: 'POST',
            contentType: false,
            processData: false, error: function (jqXHR, textStatus, errorThrown) {
                btn.disabled = false;
                $('#carga').removeClass('is-active');
                $('#modalRegistroEmpresa').hide();
                $('#bloqueo').hide();
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
            },
            success: function (data) {

                $('#carga').removeClass('is-active');
                if (data === 'true') {
                    cerrar('#modalRegistroEmpresa');
                    //messageOk('Empresa registrada exitosamente');
                } else {
                    messageError('Error al realizar el registro');
                }
                limpiarFormulario('#registroEmpresa');
                formulario.addClass('was-validated');
                btn.disabled = false;
            }
        })

    } else {

        formulario.addClass('was-validated');
    }
})

function modalPregunta() {
    $('#modalPregunta').toggle();
    $('#bloqueo').toggle();
}

function cerrar(id) {
    $(id).toggle();
    $('#bloqueo').hide();
    location.reload();
}

function modalRegistroEmpresa() {
    consultarDatosFormularioEmpresa();
    //$('#bloqueo').toggle();
    $('#modalPregunta').toggle();
    $('#modalRegistroEmpresa').toggle();

}


