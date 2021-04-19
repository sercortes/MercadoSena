
document.getElementById('flexRadioDefault1').addEventListener('click', function () {

    $('#ModalTarjeta').modal('show');
});

window.onload = cosasapagar();

document.getElementById('flexRadioDefault2').addEventListener('click', function () {

    $('#ModalPSE').modal('show');

    $.post("process_payment", {accionT: "listadebancos"}, function (rs) {
        var json = JSON.parse(rs);

        if (rs !== 'null') {
            for (x = 0; x < json.length; x++) {

                document.getElementById("selectdebanco").innerHTML += "<option value='" + json[x].financial_institution_code + "'>'" + json[x].financial_institution_name + "'</option>";
            }



        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });

});


function validarFechaMenorActual(date) {
    var objFecha = new Date(date);
    var mes = objFecha.getMonth();
    mes + 1;
    if (mes >= 0) {
        return true;
    } else {
        return false;

    }

}

function validarañomenor(date) {

    var año = (new Date().getFullYear().toString().substr(-2));

    if (date >= año) {
        return true;
    } else {
        return false;

    }
}

function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}


function validarLetras(variable) {
    if (/^([a-z A-Z])*$/.test(variable)) {
        return true;
    } else {
        return false;
    }
}

function validarNumero(variable) {
    if (/^([0-9])*$/.test(variable)) {
        return true;
    } else {
        return false;
    }
}

var input = document.getElementById('emails');
input.addEventListener('input', function () {
    if (this.value.length > 50)
        this.value = this.value.slice(0, 50);
});

var input = document.getElementById('cardhold');
input.addEventListener('input', function () {
    if (this.value.length > 25)
        this.value = this.value.slice(0, 25);
});

var input = document.getElementById('cardNumber');
input.addEventListener('input', function () {
    if (this.value.length > 16)
        this.value = this.value.slice(0, 16);
});

var input = document.getElementById('CVV');
input.addEventListener('input', function () {
    if (this.value.length > 4)
        this.value = this.value.slice(0, 4);
});


var input = document.getElementById('cardExpirationMonth');
input.addEventListener('input', function () {
    if (this.value.length > 2)
        this.value = this.value.slice(0, 2);
});

var input = document.getElementById('cardExpirationYear');
input.addEventListener('input', function () {
    if (this.value.length > 2)
        this.value = this.value.slice(0, 2);
});


function checkSubmit() {

    emails = document.getElementById('emails').value;
    cardNumber = document.getElementById('cardNumber').value;
    cardhold = document.getElementById('cardhold').value;
    CVV = document.getElementById('CVV').value;
    cardExpirationMonth = document.getElementById('cardExpirationMonth').value;
    cardExpirationYear = document.getElementById('cardExpirationYear').value;

    if (!validateCreditCardNumber(document.getElementById('cardNumber').value)) {
        messageInfo('Número de Tarjeta no válida');
        return false;
    } else if (cardNumber === null || cardNumber === '') {
        messageInfo('Por favor complete el campo n&uacute;mero de tarjeta');
        return false;
    } else if (validarNumero(cardNumber) === false) {
        messageInfo('Por favor ingrese solo n&uacute;meros en el campo n&uacute;mero de tarjeta');
        return false;
    } else if (emails === null || emails === '') {
        messageInfo('Por favor complete el campo correo electrónico');
        return false;
    } else if (validarEmail(emails) === false) {
        messageInfo('Por favor ingrese un correo electrónico valido');
        return false;
    } else if (cardhold === null || cardhold === '') {
        messageInfo('Por favor comple el campo, nombre del titular de la tarjeta');
        return false;
    } else if (validarLetras(cardhold) === false) {
        messageInfo('Por favor ingrese solo letras en el campo Nombre del titular de la tarjeta');
        return false;
    } else if (CVV === null || CVV === '') {
        messageInfo('Por favor complete el campo CVV');
        return false;
    } else if (validarNumero(CVV) === false) {
        messageInfo('Por favor ingrese solo n&uacute;meros en el campo CVV');
        return false;
    } else if (cardExpirationMonth === null || cardExpirationMonth === '') {
        messageInfo('Por favor complete el campo mes');
        return false;
    } else if (validarFechaMenorActual(cardExpirationMonth) === false) {
        messageInfo('Por favor ingrese una fecha validad en el campo mes');
        return false;
    } else if (validarNumero(cardExpirationMonth) === false) {
        messageInfo('Por favor ingrese solo n&uacute;meros en el campo mes');
        return false;
    } else if (cardExpirationYear === null || cardExpirationYear === '') {
        messageInfo('Por favor complete el campo año');
        return false;
    } else if (validarNumero(cardExpirationYear) === false) {
        messageInfo('Por favor ingrese solo n&uacute;meros en el campo año');
        return false;
    } else if (validarañomenor(cardExpirationYear) === false) {
        messageInfo('Por favor ingrese una fecha validad en el campo año');
        return false;
    }

    if (getRol() != 2) {
        messageInfo('Rol no válido');
        return false;
    }

    $('#cargas').addClass('is-active');

    if (checkProducts()) {
        $('#cargas').removeClass('is-active');
        mensajeRedirectHome('¡Error en los productos!')
        return false
    }

    if (buyProducts(3)) {
        $('#cargas').removeClass('is-active');
        mensajeRedirectHome('¡Error en los productos!')
        return false
    }

    document.getElementById("pagotarjeta").value = "Enviando...";
    document.getElementById("pagotarjeta").disabled = true;
    return true;
}


var input = document.getElementById('email');
input.addEventListener('input', function () {
    if (this.value.length > 50)
        this.value = this.value.slice(0, 50);
});


var input = document.getElementById('Nombre');
input.addEventListener('input', function () {
    if (this.value.length > 15)
        this.value = this.value.slice(0, 15);
});


var input = document.getElementById('Apellido');
input.addEventListener('input', function () {
    if (this.value.length > 15)
        this.value = this.value.slice(0, 15);
});


var input = document.getElementById('docNumbers');
input.addEventListener('input', function () {
    if (this.value.length > 12)
        this.value = this.value.slice(0, 12);
});

function checkSubmit2() {
    
    email = document.getElementById('email').value;
    Nombre = document.getElementById('Nombre').value;
    Apellido = document.getElementById('Apellido').value;
    docTypes = document.getElementById('docTypes').value;
    docNumbers = document.getElementById('docNumbers').value;
    selectdebanco = document.getElementById('selectdebanco').value;
    TPersona = document.getElementById('TPersona').value;

    if (email === null || email === '') {
        messageInfo('Por favor complete el campo correo electrónico');
        return false;
    } else if (validarEmail(email) === false) {
        messageInfo('Por favor ingrese un correo electrónico valido');
        return false;
    } else if (Nombre === null || Nombre === '') {
        messageInfo('Por favor complete el campo nombre');
        return false;
    } else if (validarLetras(Nombre) === false) {
        messageInfo('Por favor ingrese un nombre valido');
        return false;
    } else if (Apellido === null || Apellido === '') {
        messageInfo('Por favor complete el campo apellido');
        return false;
    } else if (validarLetras(Apellido) === false) {
        messageInfo('Por favor ingre un apellido valido');
        return false;
    } else if (docTypes === null || docTypes === '') {
        messageInfo('Por favor seleccione su tipo de documento');
        return false;
    }else if (docNumbers === null || docNumbers === '') {
        messageInfo('Por favor complete el campo n&uacute;mero de documento');
        return false;
    } else if (validarNumero(docNumbers) === false) {
        messageInfo('Por favor ingrese un n&uacute;mero de documento valido');
        return false;
    } else if (selectdebanco === null || selectdebanco === '') {
        messageInfo('Por favor selecione un banco');
        return false;
    } else if (TPersona === null || TPersona === '') {
        messageInfo('Por favor seleccione un tipo de persona');
        return false;
    }

    
    if (getRol() != 2) {
        messageInfo('Rol no válido');
        return false;
    }

    $('#cargas').addClass('is-active');

    if (checkProducts()) {
        $('#cargas').removeClass('is-active');
        mensajeRedirectHome('¡Error en los productos!')
        return false
    }

    if (buyProducts(4)) {
        $('#cargas').removeClass('is-active');
        mensajeRedirectHome('¡Error en los productos!')
        return false
    }
    
    document.getElementById("pagoPSE").value = "Enviando....";
    document.getElementById("pagoPSE").disabled = true;
    return true;
}

function validateCreditCardNumber(cardNumber) {
    cardNumber = cardNumber.split(' ').join("");
    if (parseInt(cardNumber) <= 0 || (!/\d{15,16}(~\W[a-zA-Z])*$/.test(cardNumber)) || cardNumber.length > 16) {
        return false;
    }
    var carray = new Array();
    for (var i = 0; i < cardNumber.length; i++) {
        carray[carray.length] = cardNumber.charCodeAt(i) - 48;
    }
    carray.reverse();
    var sum = 0;
    for (var i = 0; i < carray.length; i++) {
        var tmp = carray[i];
        if ((i % 2) != 0) {
            tmp *= 2;
            if (tmp > 9) {
                tmp -= 9;
            }
        }
        sum += tmp;
    }
    return ((sum % 10) == 0);
}


document.getElementById('pagotarjeta').addEventListener('click', function () {
    var cardValid = 0;

    //card details validation
    var cardName = $("#name_on_card").val();
    var expMonth = $("#cardExpirationMonth").val();
    var expYear = $("#expiry_year").val();
    var cvv = $("#cvv").val();
    var regName = /^[a-z ,.'-]+$/i;
    var regMonth = /^01|02|03|04|05|06|07|08|09|10|11|12$/;
    var regYear = /^2017|2018|2019|2020|2021|2022|2023|2024|2025|2026|2027|2028|2029|2030|2031$/;
    var regCVV = /^[0-9]{3,3}$/;
    if (cardValid == 0) {
        $("#cardNumber").addClass('required');
        $("#cardNumber").focus();
        return false;
    } else if (!regMonth.test(expMonth)) {
        $("#cardNumber").removeClass('required');
        $("#cardExpirationMonth").addClass('required');
        $("#cardExpirationMonth").focus();
        return false;
    } else if (!regYear.test(expYear)) {
        $("#cardNumber").removeClass('required');
        $("#cardExpirationMonth").removeClass('required');
        $("#cardExpirationYear").addClass('required');
        $("#cardExpirationYear").focus();
        return false;
    } else if (!regCVV.test(cvv)) {
        $("#cardNumber").removeClass('required');
        $("#cardExpirationMonth").removeClass('required');
        $("#cardExpirationYear").removeClass('required');
        $("#CVV").addClass('required');
        $("#CVV").focus();
        return false;
    } else if (!regName.test(cardName)) {
        $("#cardNumber").removeClass('required');
        $("#cardExpirationMonth").removeClass('required');
        $("#cardExpirationYear").removeClass('required');
        $("#CVV").removeClass('required');
        $("#cardhold").addClass('required');
        $("#cardhold").focus();
        return false;
    } else {
        $("#cardNumber").removeClass('required');
        $("#cardExpirationMonth").removeClass('required');
        $("#cardExpirationYear").removeClass('required');
        $("#CVV").removeClass('required');
        $("#cardhold").removeClass('required');
        return true;
    }

    $(document).ready(function () {

        //card validation on input fields
        $('#formpagosT input[type=text]').on('keyup', function () {
            cardFormValidate;
        });
    });

});


function cosasapagar() {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    let total = 0;
    let str = '';
    for (var item of arraf) {
        str += `<tr><td>`;
        if (item.imagenUnitaria !== undefined) {
            str += `<img src="${item.imagenUnitaria}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        } else {
            str += `<img src="${item.listaImagenes[0].url}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        }
        str += `</td>
                   <td> 
                        <div class="ml-3 d-inline-block align-middle">
                              <h5 class="mb-0">
                                <p class="mb-0 text-dark d-inline-block align-middle">${item.nombreProducto}</p>                                </div>
                            </div>
                        </td>
                        <td class="border-0 align-middle pl-3"><strong>${item.color}</strong></td>
                        <td class="border-0 align-middle pl-5"><strong>${item.cantidad}</strong></td>
                        <td class="border-0 align-middle"><strong>${money(item.valorProducto)}</strong></td>
                    </tr>`
        total += item.cantidad * item.valorProducto;
    }
    document.getElementById('cards').innerHTML = str;
    $.post("process_payment", {accionT: "Guardarprecio", valor: total});

    str = `<hr style="margin-top: 0rem;margin-bottom: 0rem;">
                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong><strong>${money(total)}</strong></li>
                                
                                <!--<li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong>-->
                                    <!--<h5 class="font-weight-bold float-right">$45.000.00</h5>-->
                                </li>`;

    document.getElementById('totals').innerHTML = str;

}


