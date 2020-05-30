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