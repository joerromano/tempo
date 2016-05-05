<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <div class="jumbotron" style="background: url('https://maps.googleapis.com/maps/api/staticmap?center=${workout.location.postalCode}&zoom=12&size=640x200&scale=2&style=feature:poi|visibility:off&style=saturation:-70|lightness:37|gamma:1.15&style=element:labels|gamma:0.26|visibility:off&style=feature:road|lightness:0|saturation:0|hue:#ffffff|gamma:0&style=feature:road|element:labels.text.stroke|visibility:off&style=feature:road.arterial|element:geometry|lightness:20&style=feature:road.highway|element:geometry|lightness:50|saturation:0|hue:#ffffff&style=feature:administrative.province|visibility:on|lightness:-50&style=feature:administrative.province|element:labels.text.stroke|visibility:off&style=feature:administrative.province|element:labels.text|lightness:20&key=AIzaSyDSJ-zKZ-bs4uCFCtXOZ0q-LNN16-gmrUI'); background-size: cover;">
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