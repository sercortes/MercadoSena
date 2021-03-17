/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


document.getElementById('flexRadioDefault1').addEventListener('click', function () {

    $('#ModalTarjeta').modal('show');
   
});

window.onload=cosasapagar();

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

function checkSubmit() {
    
    if (getRol() != 2) {
        messageInfo('Rol no válido')
        return false;
    }
    
     $('#cargas').addClass('is-active');
    
    if (buyProducts(3)) {
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

document.getElementById('pagotarjeta').addEventListener('click', function () {
    var cardValid = 0;

    //card number validation
    $('#cardhold').validateCreditCard(function (result) {
        if (result.valid) {
            $("#cardhold").removeClass('required');
            cardValid = 1;
        } else {
            $("#cardhold").addClass('required');
            cardValid = 0;
        }
    });

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
        $('#paymentForm input[type=text]').on('keyup', function () {
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


