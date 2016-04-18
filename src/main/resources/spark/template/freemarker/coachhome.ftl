
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="/library">Workout Library</a></li>
        <li><a href="/settings">Settings</a></li>
        <li><a href="/home">Logout</a></li>
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
                    <h4 class="pull-left" style="margin-top: 7px; margin-bottom: 7px;">Training Groups at a Glance</h4>
                    
                    <div class="btn-group pull-right" role="group">
                        <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addWorkoutGroup">
                            <span class="glyphicon glyphicon-list-alt"></span> Add a training group
                        </button>
                        <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addTeamMember">
                            <span class="glyphicon glyphicon-user"></span> Add a new team member
                        </button>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div id="training-groups" class="panel-body">
                    <h4 class="pull-left">Training group 1</h4>
                    <div class="btn-group pull-right" role="group">
                        <button class="btn btn-success" type="button">
                        <span class="glyphicon glyphicon-check"></span> Publish
                        </button>
                        <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-pencil"></span> Edit
                        </button>
                        <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-remove"></span> Delete
                        </button>
                    </div>
                    <div class="clearfix"></div>
                    <ul class="connectedSortable team-members">
                        <li>
                            <span class="athlete-name">Joe Biden</span>
                            <span class="athlete-agony-bar">
                                <span class="athlete-agony-bar-inner" style="width: 50%;"></span>
                            </span>
                        </li>
                        <li>
                            <span class="athlete-name">Barack Obama</span>
                            <span class="athlete-agony-bar">
                                <span class="athlete-agony-bar-inner" style="width: 70%;"></span>
                            </span>
                        </li>
                        <li>
                            <span class="athlete-name">Elizabeth Warren</span>
                            <span class="athlete-agony-bar">
                                <span class="athlete-agony-bar-inner" style="width: 20%;"></span>
                            </span>
                        </li>
                    </ul>
                    <hr/>
                    <h4 class="pull-left">Training group 2</h4>
                    <div class="btn-group pull-right" role="group">
                        <button class="btn btn-success" type="button">
                        <span class="glyphicon glyphicon-check"></span> Publish
                        </button>
                        <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-pencil"></span> Edit
                        </button>
                        <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-remove"></span> Delete
                        </button>
                    </div>
                    <div class="clearfix"></div>
                    <ul class="connectedSortable team-members">
                        <li>
                            <span class="athlete-name">Donald Trump</span>
                            <span class="athlete-agony-bar">
                                <span class="athlete-agony-bar-inner" style="width: 55%;"></span>
                            </span>
                        </li>
                        <li>
                            <span class="athlete-name">Hillary Clinton</span>
                            <span class="athlete-agony-bar">
                                <span class="athlete-agony-bar-inner" style="width: 40%;"></span>
                            </span>
                        </li>
                    </ul>
                    <hr/>
                    <h4>Unassigned athletes</h4>
                    <ul class="connectedSortable team-members">
                        <li>
                            <span class="athlete-name">Paul Ryan</span>
                        </li>
                        <li>
                            <span class="athlete-name">Bernie Sanders</span>
                        </li>
                    </ul>    
                    
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
                                        <button type="button" class="btn btn-primary btn-block" role="button" data-toggle="collapse" href="#editWorkout" aria-expanded="false" aria-controls="editWorkout">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
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
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="collapse" id="editWorkout">
                                <div class="well">
                                    <h4>Editing workout<br>
                                        <small>AM Workout, Training Group 3</small></h4>
                                    <form>
                                        <div class="form-group">
                                        <label for="athleteName">Type</label>
                                        <input type="text" class="form-control" id="workoutType" placeholder="Workout Type">
                                        </div>
                                        <div class="form-group">
                                        <label for="phoneNumber">Mileage</label>
                                        <input type="text" class="form-control" id="workoutMileage" placeholder="Mileage">
                                        </div>
                                        <div class="form-group">
                                        <label for="exampleInputEmail1">Comments</label>
                                            <textarea class="form-control" id="workoutComments" placeholder="Comments"></textarea>
                                        </div>
                                        <div class="form-group">
                                        <button type="button" class="btn btn-primary">Update workout</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
    
    <br/>
</div>
    
    <!-- Modal: ADD WORKOUT GROUP [addWorkoutGroup] -->
    <div class="modal fade" id="addWorkoutGroup" tabindex="-1" role="dialog" aria-labelledby="addWorkoutGroupLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="addWorkoutGroupLabel">Add a training group</h4>
          </div>
          <div class="modal-body">
            <form>
                <div class="form-group">
                <label for="workoutGroupName">Training Group Name</label>
                <input type="text" class="form-control" id="workoutGroupName" placeholder="Group name">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Add group</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Modal: ADD TEAM MEMBER [addTeamMember] -->
    <div class="modal fade" id="addTeamMember" tabindex="-1" role="dialog" aria-labelledby="addTeamMemberLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="addTeamMemberLabel">Add a new team member</h4>
          </div>
          <div class="modal-body">
            <form>
                <div class="form-group">
                <label for="athleteName">Athlete name</label>
                <input type="text" class="form-control" id="athleteName" placeholder="Name">
                </div>
                <div class="form-group">
                <label for="phoneNumber">Phone number</label>
                <input type="text" class="form-control" id="phoneNumber" placeholder="Name">
                </div>
                <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Add member</button>
          </div>
        </div>
      </div>
    </div>

</#assign>

<#include "main.ftl">