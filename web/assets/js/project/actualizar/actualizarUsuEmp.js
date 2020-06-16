$(document).ready(validarEmpresa());
function validarEmpresa(){
    consultaCiudad('#ciudad','ciudadUsuarioActualizar',$('#ciudadUsusario').val());
    consultaTipoDoc($('#tipoDocUsusario').val());
    consultagenero($('#generoUsusario').val());
    
   var rol=$('#rolUsuario').val();
   if(rol==='3' || rol===3){
   consultaCiudad('#ciudadEmpresa','ciudadEmpresaActualizar',$('#ciudEmpresaActualizar').val());
   var esEmpresa=$('#esEmpresa').val();
   console.log(esEmpresa);
        if (esEmpresa===0 || esEmpresa==='0'){
            limpiarFormulario('#actualizarEmpresa');
            
        }else{
            
            $('#opcionEmpresa').empty();
            $('#btnActualizarEmpresa').empty();
            $('#opcionEmpresa').html('<i class="fa fa-building" style="color: rgb(252, 115, 30)" ></i> Modificar datos de empresa');
            $('#btnActualizarEmpresa').html('Actualizar');
        }
   }else{
       $('.ocultar').hide();
   }
};

