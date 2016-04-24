
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
                <input type="text" class="form-control" id="resetName" placeholder="Name" value=${coach.name}>
                </div>
                <div class="form-group">
                <label for="resetEmail">Email</label>
                <input type="text" class="form-control" id="resetEmail" placeholder="Email" value = ${coach.email}>
                </div>
                <button type="button" class="btn btn-primary">Update Name and/or Email</button>
            </form>
            
            <hr>
            
            <h3>Team management</h3>
            <p></p>
            <ul class="list-group">
                <#list coach.teams as team>
                    <li class="list-group-item">
                        <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default">Rename</button>
                        <button type="button" class="btn btn-sm btn-danger">Disband</button>
                        </div>
                        <b>${team.name}</b>
                        <p style="margin: 0;">Roster includes: 
                            <#list team.roster as member>
                            <#if team.roster?size < 4>
                                <#if member == (team.roster)?last>
                                    and ${member.name}
                                <#else>
                                    ${member.name}, 
                                </#if>
                            <#else>
                                <#assign ct = 0>
                                <#if member == (team.roster)?last>
                                    and ${ct + 1} more.
                                <#elseif member == (team.roster)[4]>
                                    <#assign ct = ct + 1>
                                <#else>
                                    ${member.name}, 
                                </#if>
                            </#if>
                            </#list>
                        </p>
                        <div class="clearfix"></div>
                    </li>
                </#list>
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