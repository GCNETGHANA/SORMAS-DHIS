<%@page import="java.sql.ResultSet"%>
<%@page import="com.mirabilia.org.hzi.Util.dhis.optionFiler"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mirabilia.org.hzi.sormas.doa.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="template/head.jsp"></jsp:include>

        <body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed control-sidebar-slide-open accent-purple">
            <div class="wrapper">


                <!-- Navbar -->
            <jsp:include page="template/nav.jsp"></jsp:include>
                <!-- /.navbar -->

                <!-- Main Sidebar Container -->
            <jsp:include page="template/aside.jsp"></jsp:include> 


                <style>
                    .switch,.switch * {
                        -webkit-tap-highlight-color: transparent;
                        -webkit-user-select: none;
                        -moz-user-select: none;
                        -ms-user-select: none;
                        user-select: none
                    }

                    .switch label {
                        /* cursor:pointer; */
                    }

                    .switch label input[type=checkbox] {
                        opacity: 0;
                        width: 0;
                        height: 0
                    }

                    .switch label input[type=checkbox]:checked+.lever {
                        background-color: #84c7c1
                    }

                    .switch label input[type=checkbox]:checked+.lever:before,.switch label input[type=checkbox]:checked+.lever:after {
                        left: 18px
                    }

                    .switch label input[type=checkbox]:checked+.lever:after {
                        background-color: #26a69a
                    }

                    .switch label .lever {
                        /* content:""; */
                        display: inline-block;
                        position: relative;
                        width: 36px;
                        height: 14px;
                        background-color: rgba(0,0,0,0.38);
                        border-radius: 15px;
                        margin-right: 10px;
                        -webkit-transition: background 0.3s ease;
                        transition: background 0.3s ease;
                        vertical-align: middle;
                        margin: 0 16px;
                    }

                    .switch label .lever:before,.switch label .lever:after {
                        content: "";
                        position: absolute;
                        display: inline-block;
                        width: 20px;
                        height: 20px;
                        border-radius: 50%;
                        left: 0;
                        top: -3px;
                        -webkit-transition: left 0.3s ease, background .3s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, box-shadow 0.1s ease, transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, box-shadow 0.1s ease, transform .1s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease
                    }

                    .switch label .lever:before {
                        background-color: rgba(38,166,154,0.15)
                    }

                    .switch label .lever:after {
                        background-color: #F1F1F1;
                        -webkit-box-shadow: 0px 3px 1px -2px rgba(0,0,0,0.2),0px 2px 2px 0px rgba(0,0,0,0.14),0px 1px 5px 0px rgba(0,0,0,0.12);
                        box-shadow: 0px 3px 1px -2px rgba(0,0,0,0.2),0px 2px 2px 0px rgba(0,0,0,0.14),0px 1px 5px 0px rgba(0,0,0,0.12)
                    }

                    input[type=checkbox]:checked:not(:disabled) ~ .lever:active::before,input[type=checkbox]:checked:not(:disabled).tabbed:focus ~ .lever::before {
                        -webkit-transform: scale(2.4);
                        transform: scale(2.4);
                        background-color: rgba(38,166,154,0.15)
                    }

                    input[type=checkbox]:not(:disabled) ~ .lever:active:before,input[type=checkbox]:not(:disabled).tabbed:focus ~ .lever::before {
                        -webkit-transform: scale(2.4);
                        transform: scale(2.4);
                        background-color: rgba(0,0,0,0.08)
                    }

                    .switch input[type=checkbox][disabled]+.lever {
                        cursor: default;
                        background-color: rgba(0,0,0,0.12)
                    }

                    .switch label input[type=checkbox][disabled]+.lever:after,.switch label input[type=checkbox][disabled]:checked+.lever:after {
                        background-color: #949494
                    }

                    .disabledbutton {
                        pointer-events: none;
                        opacity: 0.4;
                    }

                </style>
                <style>
                    #overlay {
                        position: fixed;
                        display: none;
                        width: 100%;
                        height: 100%;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background-color: rgba(0,0,0,0.5);
                        z-index: 2;
                        cursor: pointer;
                    }

                    #text{
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        font-size: 50px;
                        color: white;
                        transform: translate(-50%,-50%);
                        -ms-transform: translate(-50%,-50%);
                    }


                </style>
                <style>
                    .lds-facebook {
                        display: inline-block;
                        position: relative;
                        width: 80px;
                        height: 80px;
                    }
                    .lds-facebook div {
                        display: inline-block;
                        position: absolute;
                        left: 8px;
                        width: 16px;
                        background: #28a745;
                        animation: lds-facebook 1.2s cubic-bezier(0, 0.5, 0.5, 1) infinite;
                    }
                    .lds-facebook div:nth-child(1) {
                        left: 8px;
                        animation-delay: -0.24s;
                    }
                    .lds-facebook div:nth-child(2) {
                        left: 32px;
                        animation-delay: -0.12s;
                    }
                    .lds-facebook div:nth-child(3) {
                        left: 56px;
                        animation-delay: 0;
                    }
                    @keyframes lds-facebook {
                        0% {
                            top: 8px;
                            height: 64px;
                        }
                        50%, 100% {
                            top: 24px;
                            height: 32px;
                        }
                    }

                    #tableData, #tableData tbody {
                        width: 100%;
                    }

                    #tableData td{
                        text-align: center;
                    }

                    #tableData th {
                        text-align: center;
                    }

                    #tableData tr{
                        border: solid 1px black;
                    }

                    .jsS span{
                        display: none;
                    }

                </style>
                <!-- Content Wrapper. Contains page content -->
                <div class="content-wrapper">
                    <!-- Content Header (Page header) -->
                    <section class="content-header">
                        <div class="container-fluid">
                            <div class="row mb-2">
                                <div class="col-sm-6">
                                    <h2>Configure Reports</h2>
                                </div>
                                <div class="col-sm-6">
                                    <ol class="breadcrumb float-sm-right">
                                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                                        <li class="breadcrumb-item active">Configuration</li>
                                    </ol>
                                </div>
                            </div>
                        </div><!-- /.container-fluid -->
                    </section>


                    <section class="col-lg-12 connectedSortable">
                        <div class="row">
                            <!-- Source creator -->
                            <section class="col-lg-12 connectedSortable">
                                <!-- TO DO List -->
                                <div class="card card-default">
                                    <div class="card-header">
                                        <h3 class="card-title">
                                            <i class="fas fa-file"></i>
                                            Report Scheduler
                                        </h3>
                                    </div>
                                    <div class="row d-flex flex-column justify-content-center align-items-center">
                                        <form onsubmit="event.preventDefault();sendgenerate(event.target)" class="row w-100">
                                            <div class="col-lg-2"></div>


                                            <div class="col-lg-4 d-flex flex-column  align-items-center p-5" >
                                                 <div class="row">
                                                <div class="col s4">
                                                    New Server?

                                                    <div class="switch">
                                                        <label >
                                                            <span id="schedulerOn">Turn on scheduler</span>
                                                            <span id="schedulerOff">Turn off scheduler</span>
                                                            <input type="checkbox" <%//rx.getString(2)%> name="server" id="maintenance">
                                                            <span class="lever"></span>
                                                           
                                                        </label>
                                                    </div>

                                                </div>
                                                <a class="btn btn-flat" onclick="openxx()">
                                                    <i class="fa fa-angle-double-right"></i> Next
                                                </a>

                                            </div>
                                            
                                            </div>

                                           
                                        </form>
                                    </div>
                                </div>
                            </section> 

                        </div>             

                    </section>




                </div>



                <!-- Modal -->
                <div class="modal fade" id="RespondX" role="dialog">
                    <div class="modal-dialog">

                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>

                            </div>
                            <div class="modal-body" style="overflow-x: auto;">
                                <p id="xdb"></p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>

                    </div>
                </div>


                <div id="overlay" onclick="off()">
                    <div id="text"><h2>please wait...</h2></div></div>


            <jsp:include page="template/scripts_footer.jsp"></jsp:include>


            <script>
            
              

            </script>
    </body>
</html>