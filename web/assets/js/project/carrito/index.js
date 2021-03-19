/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

    if (getRol() != 2) {
        messageInfo('Rol no válido');
        return false;
    }
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
    } else if (cardhold === null || cardhold === '') {
        messageInfo('Por favor comple el campo, Nombre del titular de la tarjeta');
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
        messageInfo('Por favor complete el campo mes');
        return false;
    } else if (validarNumero(cardExpirationYear) === false) {
        messageInfo('Por favor ingrese solo n&uacute;meros en el campo año');
        return false;
    } else if (validarañomenor(cardExpirationYear) === false) {
        messageInfo('Por favor ingrese una fecha validad en el campo año');
        return false;
    }


    $('#cargas').addClass('is-active');

    if (buyProducts(3)) {
        $('#cargas').removeClass('is-active');
        messageInfo('Error en la verificación de los productos')
        return false
    }
    document.getElementById("pagotarjeta").value = "Enviando...";
    document.getElementById("pagotarjeta").disabled = true;
    return true;
}

function checkSubmit2() {
    buyProducts(4)
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
                                <p class="mb-0 text-dark d-inline-block align-middle text-justify">${item.nombreProducto}</p>                                </div>
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


