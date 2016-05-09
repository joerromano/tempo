<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="/home">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>
</#assign>

<#assign content>
    <#if workout.location.postalCode??>
        <div class="jumbotron" style="background: url('https://maps.googleapis.com/maps/api/staticmap?center=${workout.location.postalCode}&zoom=12&size=640x200&scale=2&style=feature:poi|visibility:off&style=saturation:-70|lightness:37|gamma:1.15&style=element:labels|gamma:0.26|visibility:off&style=feature:road|lightness:0|saturation:0|hue:#ffffff|gamma:0&style=feature:road|element:labels.text.stroke|visibility:off&style=feature:road.arterial|element:geometry|lightness:20&style=feature:road.highway|element:geometry|lightness:50|saturation:0|hue:#ffffff&style=feature:administrative.province|visibility:on|lightness:-50&style=feature:administrative.province|element:labels.text.stroke|visibility:off&style=feature:administrative.province|element:labels.text|lightness:20&key=AIzaSyDSJ-zKZ-bs4uCFCtXOZ0q-LNN16-gmrUI'); background-size: cover;">
    <#else>
        <div class="jumbotron" style="background: darkgrey;">
    </#if>
    <div class="container">
        <div class="col-md-12">
        <h2>Workout Static Page</h2>
    </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h3>Workout info</h3>
            <b>Type:</b> ${workout.type}<br>
            <b>Date:</b> ${workout.date?date}<br>
            <b>Score/mileage:</b> ${workout.score}<br>
            <b>Workout ID:</b> ${workout.id}
            <hr>
            <i>Contact your coach for details or clarification.</i>
        </div>
        <div class="col-md-4">
                <h3>Workout location</h3>
                <#if workout.location.postalCode??>
                    Workout location: ${workout.location.postalCode}
                    <iframe
                      width="300"
                      height="300"
                      frameborder="0" style="border:0"
                      src="https://www.google.com/maps/embed/v1/place?key=AIzaSyDSJ-zKZ-bs4uCFCtXOZ0q-LNN16-gmrUI
                        &q=${workout.location.postalCode}" allowfullscreen>
                    </iframe>
            <#else>
                No workout location information included.
            </#if>
        </div>
        <div class="col-md-4">
            <img class="img-responsive" src="http://placehold.it/500x500" alt="...">
        </div>
    </div>

</div>
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">