$(document).ready(function () {

        $('.input-images-1').imageUploader({
            maxSize: 2 * 1024 * 1024,
            maxFiles: 5
        });

    });

    $('#formProduct').submit(function (e) {

        e.preventDefault()

        if (!checkOne()) {
            messageInfo('seleccione las imagenes')
            return false
        }

        if (!checkextension()) {
            messageInfo('suba imagenes validas png o jpg')
            generateOtherDiv()
            return false
        }

        if (!checkSizeItems()) {
            messageInfo('Seleccione solo 5 imagenes')
            generateOtherDiv()
            return false
        }

        if (!checkSize()) {
            messageInfo('Los archivos estan muy grandes')
            generateOtherDiv()
            return false
        }



        var form = $('#formProduct')[0]
        var data = new FormData(form)

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "UploadProduct",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {


                console.log(data)
                $('#formProduct').trigger('reset')
                generateOtherDiv()

                if (data === 1) {

                    messageOk('Subido con éxito')

                } else if (data === 0) {

                    messageInfo('error =(')

                } else if (data === 3) {

                    messageInfo('servlet solo es pa subir imagenes')

                } else {

                    messageInfo(':D')

                }


            },
            error: function (e) {


                messageError('=(' + e)

            }
        });





    })

    function generateOtherDiv() {

        $('.input-images-1 .has-files').remove()

        $('.input-images-1').imageUploader({
            maxSize: 2 * 1024 * 1024,
            maxFiles: 5
        });
    }
    
    $('#reset').on('click', function(){
   
         if($('.input-images-1 .has-files').remove().length !== 0){
             generateOtherDiv()
         }
    })

    function checkextension() {
        var file = document.getElementsByName("images[]");
        let array = file[0].files

        for (var item of array) {
            if (item.type !== 'image/png' && item.type !== 'image/jpeg') {
                return false
            }
        }

        return true
    }

    function checkSize() {
        var file = document.getElementsByName("images[]");
        let array = file[0].files
        let tam = 0
        for (var item of array) {
            tam += item.size
        }

        if (tam >= 1000000) {
            return false
        }
        return true

    }

    function checkSizeItems() {
        var file = document.getElementsByName("images[]");
        let array = file[0].files

        if (array.length >= 6) {
            return false
        }
        return true
    }

    function checkOne() {
        var file = document.getElementsByName("images[]");
        let array = file[0].files

        if (array.length <= 0) {
            return false
        }
        return true
    }



