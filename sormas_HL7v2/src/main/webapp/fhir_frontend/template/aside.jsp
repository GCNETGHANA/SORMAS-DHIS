  <aside class="main-sidebar elevation-4 sidebar-light-warning">
                <!-- Brand Logo -->
                <a href="#" class="brand-link">
                    <img src="./mira_assets/img/miraLogo.png" alt="mira Logo" class="brand-image"
                         style="opacity: .8">
                    <span class="brand-text font-weight-light">SORMAS HL7/FHIR</span>
                </a>

                <!-- Sidebar -->
                <div class="sidebar os-host os-theme-light os-host-resize-disabled os-host-scrollbar-horizontal-hidden os-host-transition os-host-overflow os-host-overflow-y">
                    <!-- Sidebar user panel (optional) -->
                    <!-- Sidebar Menu -->
                    <nav class="mt-2">
                        <ul class="nav nav-pills nav-sidebar flex-column  nav-compact" data-widget="treeview" role="menu" data-accordion="false">
                            <!-- Add icons to the links using the .nav-icon class
                                 with font-awesome or any other icon font library -->
                            <li class="nav-item has-treeview" id="facl">
                                <a href="#" class="nav-link" >
                                    <p style="font-size:14px">
                                        OrganizationTool Module
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="/fhir_frontend/adapter_frontend.jsp" class="nav-link">
                                            <i class="fas fa-chart-bar nav-icon"></i>
                                            <p style="font-size:12px">Dashboard</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="source_controller.jsp" class="nav-link">
                                            <i class="fas fa-cogs nav-icon"></i>
                                            <p style="font-size:12px">Configuration</p>
                                        </a>
                                    </li>

                                    <li class="nav-item">
                                        <a href="OrgToolOperation.jsp" class="nav-link">
                                            <i class="fas fa-exchange-alt nav-icon"></i>
                                            <p style="font-size:12px">Operations</p>
                                        </a>
                                    </li>
                                   
                                </ul>
                            </li>
                             <li class="nav-item has-treeview " id="pacl">
                                <a href="#" class="nav-link" >
                                    <p style="font-size:14px">
                                        Authorizations
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    
                                    <%
                                        HttpSession sessionx = request.getSession();
                                        String access = (String)sessionx.getAttribute("access");
                                        if(access.equals("admin")){
                                        %>
                                    
                                    <li class="nav-item">
                                        <a href="/fhir_frontend/auth.jsp" class="nav-link">
                                            <i class="fas fa-plus nav-icon"></i>
                                            <p style="font-size:12px">Add user</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="/fhir_frontend/users.jsp" class="nav-link">
                                            <i class="fas fa-user nav-icon"></i>
                                            <p style="font-size:12px">Manage users</p>
                                        </a>
                                    </li>
                                    <%
                                        }
                                        %>
                                    <li class="nav-item">
                                        <a href="/fhir_frontend/change.jsp" class="nav-link">
                                            <i class="fas fa-lock nav-icon"></i>
                                            <p style="font-size:12px">Change Password</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview " id="caxl">
                                <a href="#" class="nav-link" >
                                    <p style="font-size:14px">
                                        Case Based Module
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                
                                <ul class="nav nav-treeview">
                                    <!--li class="nav-item">
                                        <a href="#" class="nav-link">
                                            <i class="fas fa-chart-bar nav-icon"></i>
                                            <p style="font-size:12px">Dashboard</p>
                                        </a>
                                    </li-->
                                    <li class="nav-item">
                                        <a href="controller.jsp" class="nav-link">
                                            <i class="fas fa-cogs nav-icon"></i>
                                            <p style="font-size:12px">Maintenance</p>
                                        </a>
                                    </li>
                                    <!--li class="nav-item">
                                        <a href="sormas2.html" class="nav-link">
                                            <i class="fas fa-exchange-alt nav-icon"></i>
                                            <p style="font-size:12px">Operations</p>
                                        </a>
                                    </li-->
                                   
                                </ul>
                            </li>

                            
                              <li class="nav-item has-treeview" id="raxl">
                                <a href="#" class="nav-link">
                                    <p style="font-size:14px">
                                        Reporting
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                
                                <ul class="nav nav-treeview">
                                   
                                    <li class="nav-item">
                                        <a href="greport.jsp" class="nav-link">
                                            <i class="fas fa-file nav-icon"></i>
                                            <p style="font-size:12px">Generate Report</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="reportconfig.jsp" class="nav-link">
                                            <i class="fas fa-cogs nav-icon"></i>
                                            <p style="font-size:12px">Report configuration</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>

                            <li class="nav-item has-treeview">
                            <a href="#" class="nav-link">
                                    <p style="font-size:14px">
                                        Settings
                                        <i class="right fas fa-cogs"></i>
                                    </p>
                                </a>
                                
                             <ul class="nav nav-treeview">
                                <li class="nav-item">
                                <a href="#" class="nav-link">
                                    <i class="fas fa-user"></i>
                                    <p>
                                        User Management
                                    </p>
                                </a>
                            </li>
                            
                            <li class="nav-item">
                                <a href="#" class="nav-link">
                                    <i class="fas fa-chart-line"></i>
                                    <p>
                                        Analytics
                                    </p>
                                </a>
                            </li>
                                    
                                   
                                </ul>
                            
                            </li>
                           
                    </nav>
                    <!-- /.sidebar-menu -->
                </div>
                <!-- /.sidebar -->
                
            </aside>