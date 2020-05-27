

<%@include file="/views/AdminTemplate/head.jspf"%>

<%@include file="/views/AdminTemplate/menu.jspf"%>



		<div class="container-fluid" id="container-wrapper">




    <div class="row">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-users-cog hvr-icon"></i> Productos</h3>
            <a type="button" href="./newProduct" class="btn btn-primary float-right hvr-push"><i class="fas fa-plus-square"></i> Agregar</a>
        </div>
    </div>
    <hr>
    
     <div class="col-sm-12 d-flex justify-content-center">
           
            <table id="examples" class="table table-striped table-bordered table-responsive-sm">
                                <thead class="letrablanca">
                                    <tr class="tablas">
                                       <th>Nombre</th>
                                        <th>Apellido</th>
                                        <th>Email</th>
                                        <th>Estado</th>
                                        <th>Perfil</th>
                                        <th>Opciones</th>
                                    </tr>
                                </thead>
                                <tbody id="tabla">
                                    
                               </tbody>
                            </table>                 
                            
            
        </div>
    
    
    

</div>

<div id="pager" class="col-sm-12">
    <nav aria-label="Page navigation example">
    <ul id="pagination" class="pagination"></ul>
    </nav>

    </div>


	<%@include file="/views/AdminTemplate/footer.jspf"%>

<script src="./assetsAdmin/js/project/products/showProducts.js" charset="utf-8"></script>
        
<script src="./assetsAdmin/js/pagination/pager.js" charset="utf-8"></script>
