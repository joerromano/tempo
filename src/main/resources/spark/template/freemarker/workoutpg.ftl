<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron" style="background: url('/img/library.jpg'); background-size: cover;">
    <div class="container">
        <div class="col-md-12">
        <h2>Workout Static Page (TODO)</h2>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            Workout location: ${workout.location.postalCode}
            <iframe
              width="300"
              height="300"
              frameborder="0" style="border:0"
              src="https://www.google.com/maps/embed/v1/place?key=AIzaSyDSJ-zKZ-bs4uCFCtXOZ0q-LNN16-gmrUI
                &q=${workout.location.postalCode}" allowfullscreen>
            </iframe>
        </div>
    </div>
    No workout object being passed to me...
    <br>
    COACH INFO: <#if coach??>${coach}<#else>No coach info</#if>
    <br>
    WORKOUT INFO ${workout}
    <br>
    
    
    
    <br/>
</div>
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">