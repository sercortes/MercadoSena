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