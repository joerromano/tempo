<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="/schedule">Scheduler</a></li>
        <li><a href="/library">Library</a></li>
        <li><a href="/teammanage">Roster</a></li>
        <li><a href="/settings">Settings</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right" style="margin-right: 25px;">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Active Team: ${team.name} <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <#list coach.teams as cTeam>
                <li id="changeTeamNav" team-id="${cTeam.id}"><a href="#">${cTeam.name}</a></li>
            </#list>
          </ul>
        </li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron gradient" id="${curTime}-gradient">
    <div class="container">
        <div class="col-md-12">
            <#if curTime == "morning">
                <h2>Good morning, Coach ${coach.name}.</h2>
            <#elseif curTime == "noon">
                <h2>Good noontime, Coach ${coach.name}.</h2>
            <#elseif curTime == "afternoon">
                <h2>Good afternoon, Coach ${coach.name}.</h2>
            <#elseif curTime == "evening">
                <h2>Good evening, Coach ${coach.name}.</h2>
            <#else>
                <h2>Good night, Coach ${coach.name}.</h2>
            </#if>
            <p>Active team: ${team.name}</p>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="well">
                <nav>
                <ul class="pager" style="margin: 0">
                <li class="previous"><a href="#backWeek"><span aria-hidden="true">&larr;</span> Last Week</a></li>
                <p style="display:inline; line-height: 32px; font-size: 16px; font-weight: bold;" id="trainingPlanTitle">Training plan for the week of: April 11, 2016</p>
                <li class="next"><a href="#forwardWeek">Next Week <span aria-hidden="true">&rarr;</span></a></li>
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
                        <button class="btn btn-success" type="button" value="0-groupName-Publish">
                        <span class="glyphicon glyphicon-check"></span> Publish
                        </button>
                        <button class="btn btn-default" type="button" value="0-groupName-Edit">
                        <span class="glyphicon glyphicon-pencil"></span> Edit
                        </button>
                        <button class="btn btn-default" type="button" value="0-groupName-Delete">
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
                      <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="groupSelectedforSchedule">
                        Select a Group <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" id="groupScheduleSelector">
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
                            <ul class="nav nav-tabs" id="workoutDetailDayPicker" role="tablist">
                            <li day-view="Sunday" role="presentation" class="active">
                                <a href="#sun" aria-controls="sun" role="tab" data-toggle="tab">Sunday</a></li>
                            <li day-view="Monday" role="presentation">
                                <a href="#mon" role="tab" data-toggle="tab">Monday</a></li>
                            <li day-view="Tuesday" role="presentation">
                                <a href="#tue" role="tab" data-toggle="tab">Tuesday</a></li>
                            <li day-view="Wednesday" role="presentation">
                                <a href="#wed" role="tab" data-toggle="tab">Wednesday</a></li>
                            <li day-view="Thursday" role="presentation">
                                <a href="#thu" role="tab" data-toggle="tab">Thursday</a></li>
                            <li day-view="Friday" role="presentation">
                                <a href="#fri" role="tab" data-toggle="tab">Friday</a></li>
                            <li day-view="Saturday" role="presentation">
                                <a href="#sat" role="tab" data-toggle="tab">Saturday</a></li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="workoutDetailArea">
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
                                        <button type="button" class="btn btn-primary btn-block" role="button" id="deleteWorkout">Delete Workout</button>
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
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="collapse" id="editWorkout">
                                <div class="well">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <h4>Editing/adding workout<br>
                                                    <small id="editingSubtitle">AM Workout, Training Group 3</small></h4>
                                                <form>
                                                    <div class="form-group">
                                                    <label for="workoutType">Type</label>
                                                    <input type="text" class="form-control" id="workoutType" placeholder="Workout Type">
                                                    </div>
                                                    <div class="form-group">
                                                    <label for="workoutMileage">Mileage</label>
                                                    <input type="text" class="form-control" id="workoutMileage" placeholder="Mileage">
                                                    </div>
                                                    <div class="form-group">
                                                    <label for="workoutIntensity">Intensity</label>
                                                    <input type="text" class="form-control" id="workoutIntensity" placeholder="Intensity, 1 to 10">
                                                    </div>
                                                    <div class="form-group">
                                                    <button type="button" class="btn btn-primary" id="updateWorkoutSubmit">Update workout</button>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="col-md-4">
                                                <h4>Use Intelligent Suggestions</h4>
                                                <button type="button" class="btn btn-primary btn-block intelligentBtn" sugg-type="average" role="button">Average week</button>
                                                <button type="button" class="btn btn-primary btn-block intelligentBtn" sugg-type="light" role="button">Light week</button>
                                                <button type="button" class="btn btn-primary btn-block intelligentBtn" sugg-type="hard" role="button">Hard week</button>
                                                <button type="button" class="btn btn-primary btn-block intelligentBtn" sugg-type="common" role="button">Common week</button>
                                                <button type="button" class="btn btn-primary btn-block intelligentBtn" sugg-type="recent" role="button">Recent week</button>
                                            </div>
                                        </div>
                                    </div>
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
            <button type="button" class="btn btn-primary" id="addGroupButton">Add group</button>
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
                <input type="text" class="form-control" id="athletePhone" placeholder="Phone">
                </div>
                <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input type="email" class="form-control" id="athleteEmail" placeholder="Email">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="addMemberButton">Add member</button>
          </div>
        </div>
      </div>
    </div>

</#assign>
            
<#assign scripts>
    <script src="js/scheduler.js"></script>
</#assign>

<#include "main.ftl">