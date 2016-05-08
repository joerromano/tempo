<#assign curTime = "evening">
    
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="#">Home</a></li>
        <li><a href="/library">Workout Library</a></li>
        <li class="active"><a href="/teammanage">Update Rosters</a></li>
        <li><a href="/settings">Settings</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
<div class="jumbotron gradient" style="background-color:darkgrey;">
    <div class="container">
        <div class="col-md-12">
            <h2>Update Team Rosters</h2>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            Active team: ${team.name}<br>
            <ul class="list-group">
                <#list team.roster as member>
                <li class="list-group-item">
                    <div class="btn-group pull-right" style="margin-left: 10px;">
                        <button type="button" class="btn btn-sm btn-default" id="editMemberBtn" data-toggle="modal" data-target="#editMember" member-name="${member.name}" member-id="${member.id}" member-email="${member.email}" member-phone="CELL" member-loc="${member.location.postalCode}">Edit Info</button>                        
                        <button type="button" class="btn btn-sm btn-danger" id="deleteMemberBtn" member-id="${member.id}">Delete Member</button>    
                        </div>
                    ${member.name}<br>
                    ${member.email}<br>
                    PHONE NUMBER HERE
                    <!--{member.number}-->
                </li>
                </#list>
            </ul>
            <button class="btn btn-primary btn-block" type="button" data-toggle="modal" data-target="#addTeamMember">
                            <span class="glyphicon glyphicon-user"></span> Add a new team member to active team: ${team.name}
                        </button>
        </div>
    </div>
</div>

<!-- Modal: EDIT TEAM MEMBER [editMember] -->
<div class="modal fade" id="editMember" tabindex="-1" role="dialog" aria-labelledby="addTeamMemberLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="editMemberBtnLabel">Edit a new team member</h4>
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
            <input type="hidden" id="athleteDetail" athlete-id="" athlete-zip="">
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="editMemberSubmitBtn">Edit member</button>
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
            <input type="text" class="form-control" id="athleteNameAdd" placeholder="Name">
            </div>
            <div class="form-group">
            <label for="phoneNumber">Phone number</label>
            <input type="text" class="form-control" id="athletePhoneAdd" placeholder="Phone">
            </div>
            <div class="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input type="email" class="form-control" id="athleteEmailAdd" placeholder="Email">
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
    <script src="js/teammanage.js"></script>
</#assign>

<#include "main.ftl">