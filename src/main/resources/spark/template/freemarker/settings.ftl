
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
                <label for="registerPasswordOld">Old Password</label>
                <input type="password" class="form-control" id="registerPasswordOld" placeholder="Password">
                </div>
                <div class="form-group">
                <label for="registerPasswordNew">New Password</label>
                <input type="password" class="form-control" id="registerPasswordNew" placeholder="Password">
                </div>
                <!--<div class="form-group">
                <label for="registerPassword">New Password (confirm)</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>-->
                <button type="button" class="btn btn-primary" id="updatePasswordBtn">Update Password</button>
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
                <label for="resetEmail">Email</label> ${coach.email}                </div>
                <button type="button" class="btn btn-primary" id="updateDemoBtn">Update Name</button>
            </form>
            
            <hr>
            
            <h3>Team management<br><small>Active team: ${team.name}</small></h3>
            <p></p>
            <ul class="list-group">
                <#list coach.teams as team>
                    <li class="list-group-item">
                        <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default" id="renameTeamBtn" data-toggle="modal" data-target="#renameTeam" team-id="${team.id}" team-name="${team.name}">Rename</button>
                        <button type="button" class="btn btn-sm btn-danger" id="disbandTeamBtn" team-id="${team.id}">Disband</button>
                        </div>
                        <b>${team.name}</b>
                        <p style="margin: 0;">Roster includes: 
                            <#list team.roster as member>
                            <#if team.roster?size < 5>
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
                                    <br>
                <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" data-target="#addTeam">Add a team (FIX THIS SO ITS NOT BIG)</button>
              </ul>
            
            <hr>
            
            <h3>Delete account</h3>
            <div class="alert alert-warning" role="alert">
              <b>Notice: </b>Deleting your account is irreversible and deletes all records of your teams.
            </div>
            <button type="button" class="btn btn-warning" id="deleteAccountBtn">Permanently delete your account, workouts, and teams</button>
        </div>
    </div>
    
    <br/>
</div>
                
<div class="modal fade" id="addTeam" tabindex="-1" role="dialog" aria-labelledby="addTeam">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addTeamTitle">Add Team</h4>
      </div>
      <div class="modal-body">
        <form>
            <div class="form-group">
            <label for="teamName">Team Name</label>
            <input type="text" class="form-control" id="teamName" placeholder="Name">
            </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="addTeamBtn">Add Team</button>
      </div>
    </div>
  </div>
</div>
                
<div class="modal fade" id="renameTeam" tabindex="-1" role="dialog" aria-labelledby="renameTeam">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="renameTeamTitle">Rename Team:</h4>
      </div>
      <div class="modal-body">
        <form>
            <div class="form-group">
            <label for="teamNameBox">New Team Name</label>
            <input type="text" class="form-control" id="teamNameBox" placeholder="Name">
            <input type="hidden" id="teamIdHidden">
            </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="renameTeamModalBtn">Rename Team</button>
      </div>
    </div>
  </div>
</div>
</#assign>

<#assign scripts>
    <script src="js/settingspg.js"></script>
</#assign>

<#include "main.ftl">