
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#library">Workout Library</a></li>
        <li><a href="#settings">Settings</a></li>
        <li><a href="#login">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron gradient" id="morning-gradient">
    <div class="container">
        <div class="col-md-12">
        <h2>Good morning, Coach Bernie.</h2>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="well">
                <nav>
                <ul class="pager" style="margin: 0">
                <li class="previous"><a href="#"><span aria-hidden="true">&larr;</span> Last Week</a></li>
                <p style="display:inline; line-height: 32px; font-size: 16px; font-weight: bold;">Training plan for the week of: April 11, 2016</p>
                <li class="next"><a href="#">Next Week <span aria-hidden="true">&rarr;</span></a></li>
                </ul>
                </nav>
            </div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 style="margin-top: 7px; margin-bottom: 7px;">Training Groups at a Glance</h4>
                </div>
                <div class="panel-body">
                    <h4>Training group 1</h4>
                    <p>Bernie Sanders</p> 
                    <p>Elizabeth Warren</p>
                    <hr>
                    <h4>Training group 1</h4>
                    <p>Bernie Sanders</p>
                    <p>Elizabeth Warren</p>
                    <hr>
                    <h4>Training group 1</h4>
                    <p>Bernie Sanders</p>
                    <p>Elizabeth Warren</p>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="pull-left" style="margin-top: 7px; margin-bottom: 7px;">Modify Training Groups and Schedules</h4>
                    <div class="btn-group pull-right">
                      <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Training Group 3 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li><a href="#">Training group 1</a></li>
                        <li><a href="#">Training group 2</a></li>
                        <li><a href="#">Training group 4</a></li>
                      </ul>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body">
                    <!-- <div class="row">
                        <div class="col-md-8">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Group Roster
                                </div>
                                <div class="panel-body">
                                    <table class="table">
                                        <tr>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Remove</th>
                                        </tr>
                                        <tr>
                                            <td>Hillary Clinton</td>
                                            <td>Hillary@PrivateEmailServer.com</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Bernie Sanders</td>
                                            <td>me@berniesanders.co</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Elizabeth Warren</td>
                                            <td>elizabeth_warren@senate.gov</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col-md-4">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Training Group Info &amp; Settings
                                </div>
                                <div class="panel-body">
                                    Wow, very informative.
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    
                    <hr/> -->
                    
                    <div class="row">
                        <div class="col-md-12">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#sun" aria-controls="sun" role="tab" data-toggle="tab">Sunday</a></li>
                            <li role="presentation"><a href="#mon" aria-controls="mon" role="tab" data-toggle="tab">Monday</a></li>
                            <li role="presentation"><a href="#tues" aria-controls="tues" role="tab" data-toggle="tab">Tuesday</a></li>
                            <li role="presentation"><a href="#weds" aria-controls="weds" role="tab" data-toggle="tab">Wednesday</a></li>
                            <li role="presentation"><a href="#thu" aria-controls="thu" role="tab" data-toggle="tab">Thursday</a></li>
                            <li role="presentation"><a href="#fri" aria-controls="fri" role="tab" data-toggle="tab">Friday</a></li>
                            <li role="presentation"><a href="#sat" aria-controls="sat" role="tab" data-toggle="tab">Saturday</a></li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="sun">
                                <br>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">AM Workout</h4>
                                            </div>
                                            <div class="panel-body">
                                                <b>Type:</b> Interval<br/>
                                                <b>Mileage:</b> 12mi<hr>
                                                <b>Comments:</b><br/>
                                                Usual warmup<br/>
                                                2 minute rest between intervals
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">PM Workout</h4>
                                            </div>
                                            <div class="panel-body">
                                                <b>Type:</b> Interval<br/>
                                                <b>Mileage:</b> 12mi<hr>
                                                <b>Comments:</b><br/>
                                                Usual warmup<br/>
                                                2 minute rest between intervals
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">Supplemental</h4>
                                            </div>
                                            <div class="panel-body">
                                                <b>Type:</b> Cross training<br/>
                                                <hr>
                                                <b>Comments:</b><br/>
                                                Some comments
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">Coach Notes</h4>
                                            </div>
                                            <div class="panel-body">
                                                Stay on soft surfaces
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                    </div>
                                </div>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="mon">Monday</div>
                            <div role="tabpanel" class="tab-pane" id="tues">Tuesday</div>
                            <div role="tabpanel" class="tab-pane" id="weds">Wednesday</div>
                            <div role="tabpanel" class="tab-pane" id="thu">Thursday</div>
                            <div role="tabpanel" class="tab-pane" id="fri">Friday</div>
                            <div role="tabpanel" class="tab-pane" id="sat">Saturday</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
    
    <br/>
</div>
</#assign>

<#include "main.ftl">