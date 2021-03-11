
function productosRamdom(){
    
      $.ajax({
        type: "POST",
        url: './getProductsRandom',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        if (data == undefined) {
            queryEmphy()
            return false
        }
            
        generatePageQuery(data, 12)

    })
    
}
