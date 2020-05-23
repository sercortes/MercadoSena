

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/views/AdminTemplate/head.jspf"%>

<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid" id="container-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-users-cog hvr-icon"></i> Nuevo Producto</h3>
        </div>
    </div>
    <hr>

    <div class="row">


        <form  action="UploadProduct" method="POST" enctype="multipart/form-data" id="formProduct" name="formProduct">
            <div class="col-sm-12 text-center">
                <div class="input-group">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="fileToUpload" name="fileToUpload" multiple>
                        <label class="custom-file-label" for="inputGroupFile04" data-browse="Elegir" >Seleccione el archivo</label>
                    </div>
                </div>
            </div>
            <hr class="sidebar-divider">
            <button type="submit" id="send" class="btn btn-primary btn-lg hvr-push"><i class="far fa-file-excel fa-1x"></i> Cargar archivo</button>
        </form>

    </div>
    <hr>
    
    <div class="row">
        <div class="col-md-8">
            <div class="container1">
                <div>
                    <form method="post" action="server/form_process.php">

                        <!-- This area will show the uploaded files -->
                        <div class="row">
                            <div id="uploaded_images">

                            </div>
                        </div>

                        <br>
                        <br>

                        <div id="select_file">
                            <div class="form-group">
                                <label>Upload Image</label>
                            </div>
                            <div class="form-group">
                                <!-- The fileinput-button span is used to style the file input field as button -->
                                <span class="btn btn-success fileinput-button">
                                <i class="glyphicon glyphicon-plus"></i>
                                <span>Select file...</span>
                                    <!-- The file input field used as target for the file upload widget -->
                                <input id="fileupload" type="file" name="files" accept="image/x-png, image/gif, image/jpeg" >
                            </span>
                                <br>
                                <br>
                                <!-- The global progress bar -->
                                <div id="progress" class="progress">
                                    <div class="progress-bar progress-bar-success"></div>
                                </div>
                                <!-- The container for the uploaded files -->
                                <div id="files" class="files"></div>
                                <input type="text" name="uploaded_file_name" id="uploaded_file_name" hidden>
                                <br>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary" name="submit">Submit</button>

                    </form>

                </div>
            </div>
        </div>
    </div>
    
    <hr>
    
        <c:if test="${not empty MESSAGE}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong></strong> ${MESSAGE}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>


</div>


<%@include file="/views/AdminTemplate/footer.jspf"%>