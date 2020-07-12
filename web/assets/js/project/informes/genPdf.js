function genPDF() {
    $('#cargas').addClass('is-active');
    $('#graficoCanvas').removeClass('grafico');
    $('#graficoCanvas').removeClass('graficoCircular');
    $('#laX').hide();
    

    kendo.drawing
            .drawDOM("#informePdf",
                    {
                        forcePageBreak: ".page-break", // add this class to each element where you want manual page break
                        paperSize: "A4",
                        margin: {bottom: "1cm"},
                        scale: 0.8,
                        height: 500,
                        template: $("#page-template").html(),
                        keepTogether: ".prevent-split"
                    })
            .then(function (group) {
                kendo.drawing.pdf.saveAs(group, "Informe.pdf");
                $('#modalInforme').modal('hide');
            });

    $('#cargas').removeClass('is-active');
}