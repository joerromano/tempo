
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
    </ul>
     <form class="navbar-form navbar-right" style="margin-right: 10px;" id="mainPgLogin">
            <div class="form-group">
            <input type="email" name = "email" class="form-control" placeholder="Email" id="emailLogin">
            </div>
            <div class="form-group">
            <input type="password" name = "password" class="form-control" placeholder="Password" id="passwordLogin">
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
</div>
</#assign>

<#assign content>
<div class="jumbotron" style="background: url('/img/main-pg.jpg'); background-size: cover;">
    <div class="container">
        <br>
        <br>
        <h1 id="mainHeader">Welcome to Tempo</h1>
        <h1 id="mainHeader"><small>Your Team's Ultimate Tool</small></h1>
        <br>
        <br>
    </div>
</div>
    
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" data-target="#teamRegister">New coach registration</button>
        </div>
    </div>
    <br>
    
    <div class="row">
        
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Project Info</h3>
                </div>
                <div class="panel-body">
                    Tempo is a workout scheduling tool for coaches to coordinate their team members.
                    <hr>
                    Created for a cs032 Term Project, Spring 2016.
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Features</h3>
                </div>
                <ul class="list-group">
                    <li class="list-group-item">Track past, current, and future workouts</li>
                    <li class="list-group-item">Daily weather</li>
                    <li class="list-group-item">Workout suggestions</li>
                    <li class="list-group-item">Manage your roster</li>
                    <li class="list-group-item">Control multiple teams</li>
                    <li class="list-group-item">Publish workouts to athletes</li>
                </ul>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">About Us</h3>
                </div>
                <div class="panel-body">
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" src="http://placehold.it/64x64" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Simon Belete</h4>
                        Suggestions &amp; class structure
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" src="http://placehold.it/64x64" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Luci Cooke</h4>
                        Database management
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" src="http://placehold.it/64x64" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Tom Hale</h4>
                        Publishing workouts &amp; Spark setup
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" style="width: 64px;" src="/img/joe-profile.jpg" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Joe Romano</h4>
                        Frontend &amp; design
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>
    
    <!-- Modal: TEAM REGISTRATION [teamRegister] -->
    <div class="modal fade" id="teamRegister" tabindex="-1" role="dialog" aria-labelledby="teamRegisterLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="teamRegisterLabel">Register your team</h4>
          </div>
          <div class="modal-body">
            <form>
                <div class="form-group">
                <label for="registerEmail">Email Address</label>
                <input type="email" class="form-control" id="registerEmail" placeholder="Email">
                </div>
                <div class="form-group">
                <label for="registerPassword">Password</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>
                <div class="form-group">
                <label for="registerName">Your Name</label>
                <input type="text" class="form-control" id="registerName" placeholder="Name, first and last">
                </div>
                <div class="form-group">
                <label for="registerZip">ZIP Code</label>
                <input type="text" class="form-control" id="registerZip" placeholder="ZIP Code, eg. 02474">
                </div>
                <div class="form-group">
                <label for="registerTeam">First Team Name</label>
                <input type="text" class="form-control" id="registerTeam" placeholder="Your first team's name, you can add more teams later">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="registerCoach">Register</button>
          </div>
        </div>
      </div>
    </div>
    
</#assign>

<#assign scripts>
    <script src="js/loginpg.js"></script>
</#assign>

<#include "main.ftl">