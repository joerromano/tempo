
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li class="active"><a href="#">Workout Library</a></li>
        <li><a href="/settings">Settings</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron" style="background: url('/img/library.jpg'); background-size: cover;">
    <div class="container">
        <div class="col-md-12">
        <h2>Workout library</h2>
    </div>
    </div>
</div>
<div class="container">
    
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="pull-left" style="margin-top: 7px; margin-bottom: 7px;">Workouts by popularity</h4>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body" id="addWorkouts">
                    <nav>
                    <ul class="pager" style="margin: 0">
                    <li id="last20" class="previous"><a href="#last20"><span aria-hidden="true">&larr;</span> Last 20</a></li>
                    <p style="display:inline; line-height: 32px; font-size: 16px; font-weight: bold;" id="wktDisplayInfo">Need to load workouts...</p>
                    <li id="next20" class="next"><a href="#next20">Next 20 <span aria-hidden="true">&rarr;</span></a></li>
                    </ul>
                    </nav><br>
                    <ul class="list-group" id="wktList">
                      <li class="list-group-item">Cras justo odio</li>
                      <li class="list-group-item">Dapibus ac facilisis in</li>
                      <li class="list-group-item">Morbi leo risus</li>
                      <li class="list-group-item">Porta ac consectetur ac</li>
                      <li class="list-group-item">Vestibulum at eros</li>
                    </ul>
                </div>
            </div>
            
        </div>
    </div>
    
    <br/>
</div>
</#assign>

<#assign scripts>
    <script src="js/wktlibrary.js"></script>
</#assign>

<#include "main.ftl">