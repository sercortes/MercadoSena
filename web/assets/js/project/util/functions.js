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