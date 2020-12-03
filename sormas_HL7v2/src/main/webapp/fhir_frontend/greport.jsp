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
                                    <h2>Generate DHIMS Report</h2>
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
                                            Report generator
                                        </h3>
                                    </div>
                                    <div class="row d-flex flex-column justify-content-center align-items-center">
                                        <form onsubmit="event.preventDefault();sendgenerate(event.target)" class="row w-100">
                                            <div class="col-lg-2"></div>


                                            <div class="col-lg-4 d-flex flex-column  align-items-center p-5" >
                                                <div class="w-100">
                                                    <div class="form-group w-100">
                                                        <label>Select Report</label>
                                                        <br>
                                                        <select name="year" class="form-control js-select2" >
                                                            <option selected>Monthly COVID-19 report</option>


                                                        </select>
                                                    </div>
                                                    <div class="form-group w-100">
                                                        <label>Select Year</label>
                                                        <br>
                                                        <select name="year" class="form-control js-select2" id="state_x" >
                                                            <option selected>2020</option>
                                                            <option >2019</option>
                                                            <option >2018</option>
                                                            <option >2017</option>
                                                            <option >2016</option>
                                                            <option >2015</option>
                                                            <option >2014</option>
                                                            <option >2013</option>
                                                            <option >2012</option>

                                                        </select>
                                                    </div>
                                                    <div class="form-group w-100">
                                                        <label>Select Month</label>
                                                        <br>
                                                        <select name="month" class="form-control js-select2" id="state_x" >
                                                            <option value="1" selected>January</option>
                                                            <option value="2" >February</option>
                                                            <option value="3" >March</option>
                                                            <option value="4" >April</option>
                                                            <option value="5" >May</option>
                                                            <option value="6" >June</option>
                                                            <option value="7" >July</option>
                                                            <option value="8" >August</option>
                                                            <option value="9" >September</option>
                                                            <option value="10" >October</option>
                                                            <option value="11" >November</option>
                                                            <option value="12" >December</option>

                                                        </select>
                                                    </div>
                                                    <div class="w-100 d-flex flex-row justify-content-center">
                                                        <div style="display:none;" class="lds-facebook"><div></div><div></div><div></div></div>
                                                    </div>
                                                    
                                                    <div class="w-100 d-flex flex-row justify-content-center">
                                                        <button id="generateBtn" type="submit" class="btn btn-success btn-flat">
                                                            Generate & Send to DHIMS
                                                        </button>


                                                    </div>

                                                </div>
                                            </div>

                                            <div class="col-lg-4 d-flex flex-column  align-items-center p-5">
                                                <div class="w-100 d-flex flex-column align-items-center">
                                                    <div class="form-group w-100 jsS">
                                                        <label>Select Region</label>
                                                        <br/>
                                                        <select name="region" class="form-control regionSelect" >
                                                        </select>
                                                    </div>
                                                    <div class="form-group w-100 jsS">
                                                        <label>Select District</label>
                                                        <br/>
                                                        <select name="district" class="form-control districtSelect" >
                                                        </select>
                                                    </div>
                                                    <div class="form-group w-100 jsS">
                                                        <label>Select Facility</label>
                                                        <br/>
                                                        <select name="facility" class="form-control facilitySelect" >
                                                        </select>
                                                    </div>

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
                function countrySelect(val){
                    if($("#countrySelector").is(':checked')){
                        $(".regionSelect").empty();
                        $(".districtSelect").empty();
                        $(".facilitySelect").empty();
                    }else{
                        getRegions();
                    }
                }
                function getRegions(){
                      $.ajax({
                        type: "GET",
                        url: "/orgsunit",
                        data: {
                            region: "yes"
                        },
                        success: function (f) {
                            var cs = f.map((t, ind) => {
                                return {
                                    id: ind,
                                    text: t
                                }
                            })
                           
                            fillRegions(transformResponse(cs));
                        }
                    })
                }
                try {
                  getRegions();
                } catch (e) {
                    console.error(e)
                }
                
                function transformResponse(data){
                    return data.map(p=>({text: p.text.name, id: p.text.id}));
                }

                function fillRegions(data) {
                    $(".regionSelect").select2({
                        data: [{text: "Select a region", id:0},...data],
                        placeholder: "Select an option",
                        allowClear: true,
                        templateSelection: fetchDistricts
                    })
                }

                function fetchDistricts(state) {
                    if (!state.id || state.text == "Select a region") {
                        $(".districtSelect").empty()
                        return state.text;
                    }
                    var region = state.id;
                    $.ajax({
                        type: "GET",
                        url: "/orgsunit",
                        data: {
                            regionSelected: region
                        },
                        success: function (d) {
                            var cs = d.map((t, ind) => {
                                return {
                                    id: ind,
                                    text: t
                                }
                            })
                            fillDistricts(transformResponse(cs));
                        }
                    })
                }

                function fillDistricts(data) {
                    $(".districtSelect").empty()
                    $(".districtSelect").select2({
                        data: [{text: "Select a district", id:0},...data],
                        placeholder: "Select a district",
                        allowClear: true,
                        templateSelection: fetchFacilities
                    })
                }
                
                function fetchFacilities(state){
                       if (!state.id || state.text == "Select a district") {
                         $(".facilitySelect").empty()
                        return state.text;
                    }
                    var district = state.id;
                    $.ajax({
                        type: "GET",
                        url: "/orgsunit",
                        data: {
                            districtSelected: district
                        },
                        success: function (d) {
                            var cs = d.map((t, ind) => {
                                return {
                                    id: ind,
                                    text: t
                                }
                            })
                            fillFacilities(transformResponse(cs));
                            
                        }
                    })
                    
                }
                
                
                function fillFacilities(data){
                    $(".facilitySelect").empty()
                     $(".facilitySelect").select2({
                        data: [{text: "Select a facility", id:0},...data],
                        allowClear: true,
                        placeholder: "Select a facility"

                    })
                }

                function sendgenerate(e) {
//                    let region = $(".regionSelect option:selected").text() == "Select a region" || $(".regionSelect option:selected").text() == ""?"none":$(".regionSelect option:selected").text();
//                    let district  = $(".districtSelect option:selected").text() == "Select a district" || $(".districtSelect option:selected").text() == ""?"none":$(".districtSelect option:selected").text();
//                    let facility = $(".facilitySelect option:selected").text() == "Select a facility" || $(".facilitySelect option:selected").text() == ""?"none":$(".facilitySelect option:selected").text();
//                    let finaldata = $(e).serializeArray();
//                    finaldata.find(v=>v.name == "region").value=region;
//                    finaldata.find(v=>v.name == "district").value=district;
//                    finaldata.find(v=>v.name == "facility").value=facility;
//                    let finaldataF = (ar)=>{
//                        let str = "";
//                        ar.map((v, ind)=>{
//                            str += v.name+"="+v.value;
//                            str+= ind !== ar.length-1?'&':'';
//                        })
//                        return str;
//                    }
                    $('.lds-facebook').show();
                    $('#generateBtn').prop('disabled', true);
                    $.ajax({
                        type: "POST",
                        url: '/greport',
                        data: $(e).serialize(),
                        success: function (s) {
                            $('.lds-facebook').hide();
                            $('#generateBtn').prop('disabled', false);
                            var f = JSON.parse(s.responseData)
                            console.log(f);
                            var impCount = f.importCount;
                            var deleted = impCount.deleted;
                            var ignored = impCount.ignored;
                            var imported = impCount.imported;
                            var updated = impCount.updated;



                            if (f.status != "SUCCESS") {
                                Swal.fire("Error", "Please try again", "error");
                                return;
                            }


                            Swal.fire(
                                    {
                                        title: "Report generated & sent successfully",
                                        icon: "success",
                                        html: "<div class='w-100 d-flex flex-row justify-content-around'><div class='d-flex flex-column'><h1><strong>" + imported + "</strong></h1><div class='d-flex w-100 flex-row justify-content-around '><i  class='fas fa-file text-success mr-1'></i><h2>Imported</h2></div></div> <div class='d-flex flex-column'><h1>" + updated + "</h1><div class='d-flex flex-row w-100 justify-content-around '><i  class='fas fa-edit text-primary mr-1'></i><h2>Updated</h2></div></div> <div class='d-flex flex-column'><h1><strong>" + ignored + "</strong></h1><div class='d-flex w-100 flex-row justify-content-around'><i  class='fas fa-exclamation text-warning mr-1'></i><h2>Ignored</h2></div></div> <div class='d-flex flex-column'><h1><strong>" + deleted + "</strong></h1><div class='d-flex w-100 flex-row justify-content-around '><i  class='fas fa-trash text-danger mr-1'></i><h2>Deleted</h2></div></div> </div>"
//                                        html: "<table id='tableData'><thead><tr><th>Data</th><th>Value</th></tr></thead><tbody><tr><td>Imported</td><td>"+imported+"</td></tr><tr><td>Updated</td><td>"+updated+"</td></tr><tr><td>Ignored</td><td>"+ignored+"</td></tr><tr><td>Deleted</td><td>"+deleted+"</td></tr></tbody></table>"
                                    }
                            )
                        }

                    });
                }
                function step(e) {
                    $("#" + e).addClass("disabledbutton");
                    $("#" + e).toggleClass("callout-success");
                    // alert("#2_" + e);
                    $("#2_" + e).show(500);
                    checker();
                }
                function checker() {
                    if ($("#only_").prop("checked") === true) {
                        $(".only_").prop("checked", false);
                        $('#non_app').show();
                        $('#non_app_').html('Please select the lowest level you will like the sync with DHIS2');
                        // alert("You have selected Aggregate only, hence, all other options are disabled");
                    } else {
                        //  alert("You have selected Aggregate only");
                        $('#non_app').hide();
                        $('#non_app_').html('Not applicable... please click on next');
                    }
                    ;

                    if ($("#maintenance").prop("checked") === true) {

                    } else {

                        $('#p_maintenance').show(100);
                        $('#2_stepx').remove();
                    }
                    ;

                }



                //maintenance

                function checkerX() {
                    if ($("#instll").prop("checked") === true) {
                        $("#instllx").show(1000);
                        $("#instllx_").hide(1000);


                    } else {
                        $("#instllx").hide(1000);
                        $("#instllx_").show(1000);
                    }

                }






                ;

                $('a.nope').click(function () {
                    return false;
                })


                function start_pushX_() {


                    document.getElementById("overlay").style.display = "block";
                    $('#text').html("Pushing all available matched data to sormas...");
                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', '../iopujlksrefdxcersdfxcedrtyuilkmnbvsdfghoiuytrdcvbnmkiuytrewsazsedfcd345678?aggregatToDHIS=true', true);
                    xhr.responseType = 'text';
                    xhr.onload = function () {

                        if (xhr.readyState === xhr.DONE) {
                            if (xhr.status === 200) {

                                document.getElementById("overlay").style.display = "none";

                                alert('response from server  : ' + xhr.responseText);

                            }
                        }
                    };
                    xhr.send(null);

                }
                ;






                function get() {
                    $('#fomX').submit();
                    req = new XMLHttpRequest();
                    req.open("GET", '../iopujlksrefdxcersdfxcedrtyuilkmnbvsdfghoiuytrdcvbnmkiuytrewsazsedfcd345678?PUSHRESULTS=true', true);
                    req.send();
                    req.onload = function () {

                        json = JSON.parse(req.responseText);
                        var jsonViewer = new JSONViewer();
                        document.querySelector("#xdb").appendChild(jsonViewer.getContainer());


                        jsonViewer.showJSON(json, -1, -1);

                    };


                    $('#RespondX').modal('show');
                }



            </script>
    </body>
</html>