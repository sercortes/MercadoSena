

$(function(){
    
   $('#myCarousel').carousel({
    interval: 3000,
 })

 $('#blogCarousel').carousel({
    interval: 3000,
 }) 
    

    if (window.location.pathname !== "/MercadoSena/") {   
        oculMost() 
    }
    
})


