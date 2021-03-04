
function buyProducts(metodo) {

    let arraf = JSON.parse(localStorage.getItem('objects'));

    $.ajax({
        type: 'POST',
        url: './generateSale',
        datatype: 'json',
        data: {
            metodo: metodo, arrayP: JSON.stringify(arraf)
        }, error: function (datas) {
            messageInfo('Error en los productos')
        },
        success: function (datas) {

            if (datas !== 0) {
                
                cleanCar()
                
                if (metodo === 1 || metodo === 2) {
                    vendedor(datas)
                } else if (metodo === 3) {
                    redirect(datas)
                }

            } else {
                messageInfo('Error')
            }

        }
    })

}

$("#btnpagar").click(function () {

     if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }
    
    valor = document.getElementById('valor').value;
    location.href = "process_payment?valor=" + valor;    
    
});

function cleanCar() {
    var arrayBuy = []
    localStorage.setItem('objects', JSON.stringify(arrayBuy))
}

