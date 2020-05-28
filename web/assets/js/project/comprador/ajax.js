

function consultarDatosFormulario() {
    consultaTipoDoc();
    consultagenero();
    consultaCiudad();
    consultaRol();
}

function consultaRol() {
    $.ajax({
        url: "./comprador?accion=consultaRol",
            type: 'POST',
            dataType: 'json',
            contentType: false,
            processData: false,
        success: function (data) {
            //console.log(data);
        for(var i=0;i<data.length;i++){
          //console.log( data[i].rol); 
        if(data[i].rol==='Vendedor'){
            $('#rol').val(data[i].idRol);}
             
        } 

        }
    })
    }
function consultaTipoDoc() {
    $.ajax({
        url: "./comprador?accion=consultaTipoDoc",
            type: 'POST',
            dataType: 'json',
            contentType: false,
            processData: false,
        success: function (data) {
            selects(data,'#tipoDoc','tipoDocUsuario');
           

        }
    })
}
function consultagenero() {
    $.ajax({
        url: "./comprador?accion=consultaGenero",
            type: 'POST',
            dataType: 'json',
            contentType: false,
            processData: false,
        success: function (data) {
            selects(data,'#genero','generoUsuario');
           

        }
    })
}
function consultaCiudad() {
    $.ajax({
        url: "./comprador?accion=consultaCiudad",
            type: 'POST',
            dataType: 'json',
            contentType: false,
            processData: false,
        success: function (data) {
            selects(data,'#ciudad','ciudadUsuario');
           

        }
    })
}



function selects(datos,idDiv,idInput){
    //console.log(datos);
    var select = '<select id="'+idInput+'" name="'+idInput+'" >';
    if(idDiv==='#genero'){
            for (var i = 0; i < datos.length; i++) {
                select += '<option value="' + datos[i].idGenero + '">' + datos[i].genero + '</option>';
            }
           
        }else if(idDiv==='#tipoDoc'){
              for (var i = 0; i < datos.length; i++) {
                select += '<option value="' + datos[i].idTipoDoc + '">' + datos[i].tipoDoc + '</option>';
            }
            
        }else if(idDiv==='#ciudad'){
              for (var i = 0; i < datos.length; i++) {
                select += '<option value="' + datos[i].idCiudad + '">' + datos[i].nombreCiudad + '</option>';
            }
        }
        
            //  console.log(select);
            select += '</select>';
            $(idDiv).html(select); 
}
 $(function () {
     
     $('#registrarUsuario').click(function (e){
         e.preventDefault();
         var datos=$('form#registroUsuario').serialize();
        $.ajax({
        url: "./comprador?accion=registrarUsuario&"+datos,
            type: 'POST',
            contentType: false,
            processData: false,
        success: function (data) {
           alert(data);

        }
    })
     })
 })
 