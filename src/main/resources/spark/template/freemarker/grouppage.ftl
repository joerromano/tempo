<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron" style="background: darkgrey;">
    <div class="container">
        <div class="col-md-12">
        <h2>Group Page: ${group.name}<br><small>Week of ${group.date?date}</small></h2>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h3>Group info</h3>
            <b>Name:</b> ${group.name}<br>
            <b>Date:</b> ${group.date?date}<br>
            <b>Group ID:</b> ${group.id}
        </div>
        <div class="col-md-4">
            <h3>Group roster</h3>
            <ul class="list-group">
            <#list group.members as member>
                <li class="list-group-item">
                    ${member.name}
                </li>
            </#list>
            </ul>
        </div>
        <div class="col-md-4">
            <h3>Group workouts</h3>
            <ul class="list-group">
            <#list group.workout as workout>
                <li class="list-group-item">
                    <b>${workout.date?date}, ${workout.time} Workout</b><br>
                    <b>Type:</b> ${workout.type}  |  <b>Mileage:</b> ${workout.score}<br>
                    <small><a href="/workout/${workout.id}">Full workout details</a></small>
                </li>
            </#list>
            </ul>
        </div>
    </div>

</div>
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">