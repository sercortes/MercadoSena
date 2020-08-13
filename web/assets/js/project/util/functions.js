var bande = 0

$(function(){
    
     $('#barraBusqueda').hide()
    
   $('#myCarousel').carousel({
    interval: 3000,
 })

    if (window.location.pathname !== "/MercadoSena/") {   
        oculMost() 
    }
 
})

function redirect(){
    if (sessionStorage.getItem('idCompany') == null) {
        window.location.replace('./logout');
    }
}


function oculMost() {
    
    var accion = $('#litaCategoria').attr('action');

    if (accion === 'no') {
    
        $('#litaCategoria').attr('action', 'si');
        $('#litaCategoria').hide();

    } else if (accion === 'si') {
        $("#litaCategoria").fadeIn();
        $('#litaCategoria').attr('action', 'no');
        $('#litaCategoria').show();
    }
    
}


$(document).on('click', '#logoutFire', function(){
    
    sessionStorage.removeItem('idCompany')
    sessionStorage.removeItem('falls')
    
})

function messageOk(message) {
    Swal.fire({
        icon: 'success',
        title: message,
        showConfirmButton: true
    })
}

function messageInfo(message) {
    Swal.fire({
        icon: 'info',
        title: message,
        showConfirmButton: true
    })
}
function messageError(message) {
    Swal.fire({
        icon: 'error',
        title: message,
        showConfirmButton: true
    })
}

$(document).on('click','#buttonSearch', function(){
    console.log('sergio')
    if (bande === 0) {
        $('#barraBusqueda').show('slow')
        bande = 1
    }else{
        $('#barraBusqueda').hide('slow')
        bande = 0
    }
    
})