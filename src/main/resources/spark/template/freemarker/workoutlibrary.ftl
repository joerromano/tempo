
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li class="active"><a href="#">Workout Library</a></li>
        <li><a href="/settings">Settings</a></li>
        <li><a href="#login">Logout</a></li>
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
                    <h4 class="pull-left" style="margin-top: 7px; margin-bottom: 7px;">Popular AM Workouts</h4>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body">
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
                    </div>
                    <button type="button" class="btn btn-primary btn-block">View more</span></button>
                </div>
            </div>
            
        </div>
    </div>
    
    <br/>
</div>
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">