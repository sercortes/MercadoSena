
$('#fotoUsuario').change(function (){

if(validarImagen('fotoUsuario')){
        var uImg = document.getElementById('fotoUsuario');
        var img=uImg.files[0];
        var urlImg = URL.createObjectURL(img);

         $('#previsualizar').empty();
        $('#previsualizar').html('<img id="fotoPerfil" class="fotoPerfil" src="'+urlImg+'" width="200px" height="200"/>');
    }else{ $('#fotoUsuario').val('');}
    
     })

function validarImagen(id) {


    var archivo = document.getElementById(id).files;
    if (archivo.length === 1) {
        var name = archivo[0].name;
        name = name.toLowerCase();
        var ext = name.substring(name.lastIndexOf('.'));
        if (ext === '.jpg' || ext === '.jpeg' || ext === '.png') {

            return true;
        } else {
            messageError('Ingrese un archivo válido (jpg, jpeg, png).');

            return false;
        }
    } else {
        if (archivo.length === 0) {
            return true;
        }
        messageInfo('Ingrese sólo un archivo');
        return false;
    }

}
