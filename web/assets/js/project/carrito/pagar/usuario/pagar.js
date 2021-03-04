idVenta = 0

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
            
            idVenta = datas
            if (datas !== 0) {
                document.getElementById('ventaId').value = datas
                document.getElementById('ventaIds').value = datas
                cleanCar()
            }else{
                location.href = "/Store";  
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

