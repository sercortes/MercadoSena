var input = ''
var $pagination = $('#pagination'),
        totalRecords = 0,
        records = [],
        displayRecords = [],
        recPerPage = 4,
        page = 1,
        totalPages = 0,
        initiateStartPageClick = true

$(function(){
    listarActivitysSearch()
})

function listarActivitysSearch() {


    $.ajax({
        type: "POST",
        url: './getProducts',
        async: true,
        datatype: 'json'
    }).done(function (data) {
        
        
        for(var item of data){
            console.log(item.imagenesProducto.url)
        }
        
        console.log(data)
        
        records = data
        totalRecords = data.length
        totalPages = Math.ceil(totalRecords / recPerPage)

        apply_pagination()
    })


}



function apply_pagination() {

    $pagination.twbsPagination({
        totalPages: totalPages,
        visiblePages: 4,
        onPageClick: function (event, page) {
            displayRecordsIndex = Math.max(page - 1, 0) * recPerPage;
            endRec = (displayRecordsIndex) + recPerPage;

            displayRecords = records.slice(displayRecordsIndex, endRec);
            generateTableBuscador()
        }
    });
}


function cambiarFecha() {
    $pagination.twbsPagination('destroy');
}


function generateTableBuscador() {

    let select = document.getElementById('tabla');
    let str = ``
                                          
    for (var item of displayRecords) {

        str += `<tr idActividad="${item.idRealActividad}" class="chiquito1 text-justify">
                                                    <td class="elements">${item.idProducto}</td>
                                                    <td class="elements">${item.nombreProducto}</td>
                                                    <td class="elements">
         <img src="${item.imagenesProducto.url}" class="img-fluid w-25" style="max-height: 150px;"  alt="Responsive image">

</td>
                                                    <td class="elements">${item.responsable}</td>
                                                    <td>${item.cantidad}</td>
                                                 <td class="text-center">         
                                                      <button class="seeAprendices btn btn-info btn-xs" >
                                                            <i class="fas fa-list-ol"></i> 
                                                        </button>            
                                                </td>
                                                </tr> `
    }

 

    select.innerHTML = str;
}




