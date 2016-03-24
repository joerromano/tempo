
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#feed">Feed</a></li>
        <li><a href="#upload">Upload Activity</a></li>
        <li><a href="#settings">Settings</a></li>
        <li><a href="#login">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron gradient" id="morning-gradient">
    <div class="container">
        <div class="col-md-12">
        <h2>Good morning, Bernie.</h2>
    </div>
    </div>
</div>
<div class="container">
    
    <div class="row">
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Recent Updates</h3>
                </div>
                <div class="panel-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quam velit, vulputate eu pharetra nec, mattis ac neque:</p>
                    * Duis vulputate commodo lectus
                    * Ac blandit elit tincidunt id
                    * Sed rhoncus, tortor sed eleifend tristique
                    * Tortor mauris molestie elit, et lacinia

                    Ipsum quam nec dui. Quisque nec mauris sit amet elit iaculis pretium sit amet quis magna. Aenean velit odio, elementum in tempus ut, vehicula eu diam. Pellentesque rhoncus aliquam mattis. Ut vulputate eros sed felis sodales nec vulputate justo hendrerit. Vivamus varius pretium ligula, a aliquam odio euismod sit amet. Quisque laoreet sem sit amet orci ullamcorper at ultricies metus viverra. Pellentesque arcu mauris, malesuada quis ornare accumsan, blandit sed diam.
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Recent Training</h3>
                </div>
                <div class="panel-body">
                    <div><canvas id="recentTrainingChart" width="400" height="300px"></canvas></div>
                    <div id="chartLegend"></div>
                </div>
            </div>
        </div>
    </div>
    <br>
    
    <div class="row">
        
        <div class="col-md-12">
            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <nav>
                    <ul class="pager" style="margin: 0">
                    <li class="previous"><a href="#"><span aria-hidden="true">&larr;</span> Last Week</a></li>
                    <p style="display:inline; line-height: 32px;">Your Workouts for the Week of: WEEK NUMBER</p>
                    <li class="next"><a href="#">Next Week <span aria-hidden="true">&rarr;</span></a></li>
                    </ul>
                    </nav>
                </div>
                <div class="panel-body">
                    <div>
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#sun" aria-controls="sun" role="tab" data-toggle="tab">Sunday</a></li>
                    <li role="presentation"><a href="#mon" aria-controls="mon" role="tab" data-toggle="tab">Monday</a></li>
                    <li role="presentation"><a href="#tues" aria-controls="tues" role="tab" data-toggle="tab">Tuesday</a></li>
                    <li role="presentation"><a href="#weds" aria-controls="weds" role="tab" data-toggle="tab">Wednesday</a></li>
                    <li role="presentation"><a href="#thu" aria-controls="thu" role="tab" data-toggle="tab">Thursday</a></li>
                    <li role="presentation"><a href="#fri" aria-controls="fri" role="tab" data-toggle="tab">Friday</a></li>
                    <li role="presentation"><a href="#set" aria-controls="sat" role="tab" data-toggle="tab">Saturday</a></li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="sun">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3">
                                    <h4>AM workout</h4>
                                    <p>4 miles <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></p>
                                    <p>Shoe one</p>
                                    <!-- For pre-toggled buttons, you must add the .active class and the aria-pressed="true" attribute to the button yourself. -->
                                    <div class="btn-group" role="group" aria-label="...">
                                        <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                        <button type="button" class="btn btn-primary">Mark complete</button>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <h4>PM workout</h4>
                                    <p>4 miles</p>
                                    <p>Shoe one</p>
                                    <div class="btn-group" role="group" aria-label="...">
                                        <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                        <button type="button" class="btn btn-primary active" aria-pressed="true">Complete</button>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <h4>Supplemental</h4>
                                    <p>Core/UB3</p>
                                    <div class="btn-group" role="group" aria-label="...">
                                        <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                                        <button type="button" class="btn btn-primary">Mark complete</button>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <h4>Coach notes</h4>
                                    <p>Stay on soft surface</p>
                                    <button type="button" class="btn btn-info">Message coach</button>
                                </div>
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
    <br><br>
</div>
</#assign>

<#include "main.ftl">