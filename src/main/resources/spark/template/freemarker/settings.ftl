
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li><a href="/library">Workout Library</a></li>
        <li class="active"><a href="#">Settings</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    
<div class="jumbotron" style="background: url('/img/settings-top.jpg'); background-size: cover;">
    <div class="container">
    <div class="col-md-12">
    <h2>User settings</h2>
    </div>
    </div>
</div>
    
<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h3>Password change</h3>
            <form>
                <div class="form-group">
                <label for="registerPassword">Old Password</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>
                <div class="form-group">
                <label for="registerPassword">New Password</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>
                <div class="form-group">
                <label for="registerPassword">New Password (confirm)</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>
                <button type="button" class="btn btn-primary">Update Password</button>
            </form>
            
            <hr>
    
            <h3>Profile information</h3>
            <p>You may not update email information at this time.</p>
            <form>
                <div class="form-group">
                <label for="resetName">Your name</label>
                <input type="text" class="form-control" id="resetName" placeholder="Name" value="Bernie Sanders">
                </div>
                <div class="form-group">
                <label for="resetPhone">Phone number</label>
                <input type="text" class="form-control" id="resetPhone" placeholder="Name" value = "401-679-1738">
                </div>
                <button type="button" class="btn btn-primary">Update Name and/or Phone</button>
            </form>
            
            <hr>
            
            <h3>Team management</h3>
            <ul class="list-group">
                <li class="list-group-item">
                    <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default">Rename</button>
                        <button type="button" class="btn btn-sm btn-danger">Disband</button>
                    </div>
                    <b>Team Democrats</b>
                    <p style="margin: 0;">Roster includes: Elizabeth Warren, Barack Obama, Hillary Clinton, and 231 more.</p>
                    <div class="clearfix"></div>
                  </li>
                <li class="list-group-item">
                    <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default">Rename</button>
                        <button type="button" class="btn btn-sm btn-danger">Disband</button>
                    </div>
                    <b>Team Republicans</b>
                    <p style="margin: 0;">Roster includes: Donald Trump, Jeb Bush, Paul Ryan, and 346 more.</p>
                    <div class="clearfix"></div>
                </li>
                <li class="list-group-item">
                    <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default">Rename</button>
                        <button type="button" class="btn btn-sm btn-danger">Disband</button>
                    </div>
                    <b>Team Independents</b>
                    <p style="margin: 0;">Roster includes: Bernie Sanders and Angus King.</p>
                    <div class="clearfix"></div>
                </li>
              </ul>
            
            <hr>
            
            <h3>Delete account</h3>
            <div class="alert alert-warning" role="alert">
              <b>Notice: </b>Deleting your account is irreversible and deletes all records of your teams.
            </div>
            <button type="button" class="btn btn-danger">Permanently delete your account, workouts, and teams</button>
        </div>
    </div>
    
    <br/>
</div>
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">