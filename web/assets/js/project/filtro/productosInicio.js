
function productosRamdom(){
    
      $.ajax({
        type: "POST",
        url: './getProductsRandom',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        if (data.length == 0) {
            queryEmphy()
            return false
        }
        
        generatePageQuery(data, 12)

    })
    
}
