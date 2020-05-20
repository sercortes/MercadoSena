$('#myCarousel').carousel({
    interval: 3000,
 })

 $('#blogCarousel').carousel({
    interval: 3000,
 })



function oculMost(){
    //alert('kajhd');
    var accion=$('#litaCategoria').attr('action');
   // alert(accion);
    if(accion==='no'){
            $('#litaCategoria').attr('action','si');
             $('#litaCategoria').hide();
       
       
       
    }else if(accion==='si'){
        $("#litaCategoria").fadeIn();
        $('#litaCategoria').attr('action','no');
        $('#litaCategoria').show();
    }
}