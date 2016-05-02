
<#assign navbar>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
    </ul>
     <form action = "/login" method = "post" class="navbar-form navbar-right" style="margin-right: 10px;" role="search">
            <div class="form-group">
            <input type="email" class="form-control" placeholder="Email">
            </div>
            <div class="form-group">
            <input type="password" class="form-control" placeholder="Password">
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
        <div class="col-md-6">
            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" data-target="#teamRegister">New team registration</button>
        </div>
        <div class="col-md-6">
            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" data-target="#teamSignin">Team sign in</button>
        </div>
    </div>
    <br>
    
    <div class="row">
        
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Features</h3>
                </div>
                <ul class="list-group">
                    <li class="list-group-item">
                        <img class="img-responsive" src="http://placehold.it/1000x200" alt="...">
                        Track past, current, and future workouts</li>
                    <li class="list-group-item">
                        <img class="img-responsive" src="http://placehold.it/1000x200" alt="...">
                        Browseable workout library</li>
                    <li class="list-group-item">
                        <img class="img-responsive" src="http://placehold.it/1000x200" alt="...">
                        Intelligent workout suggestions</li>
                    <li class="list-group-item">
                        <img class="img-responsive" src="http://placehold.it/1000x200" alt="...">
                        Manage your roster and contact players</li>
                    <li class="list-group-item">
                        <img class="img-responsive" src="http://placehold.it/1000x200" alt="...">
                        Embedded weather, focused for running</li>
                </ul>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Recent Feed</h3>
                </div>
                <div class="panel-body">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quam velit, vulputate eu pharetra nec, mattis ac neque:

                    * Duis vulputate commodo lectus
                    * Ac blandit elit tincidunt id
                    * Sed rhoncus, tortor sed eleifend tristique
                    * Tortor mauris molestie elit, et lacinia

                    Ipsum quam nec dui. Quisque nec mauris sit amet elit iaculis pretium sit amet quis magna. Aenean velit odio, elementum in tempus ut, vehicula eu diam. Pellentesque rhoncus aliquam mattis. Ut vulputate eros sed felis sodales nec vulputate justo hendrerit. Vivamus varius pretium ligula, a aliquam odio euismod sit amet. Quisque laoreet sem sit amet orci ullamcorper at ultricies metus viverra. Pellentesque arcu mauris, malesuada quis ornare accumsan, blandit sed diam.
                </div>
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
                        A little bit about you. A little bit about you. A little bit about you. A little bit about you.
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" src="http://placehold.it/64x64" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Luci Cooke</h4>
                        A little bit about you. A little bit about you. A little bit about you. A little bit about you.
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" src="http://placehold.it/64x64" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Tom Hale</h4>
                        A little bit about you. A little bit about you. A little bit about you. A little bit about you.
                        </div>
                    </div>
                    <div class="media">
                        <div class="media-left">
                        <img class="media-object img-circle" style="width: 64px;" src="/img/joe-profile.jpg" alt="...">
                        </div>
                        <div class="media-body">
                        <h4 class="media-heading">Joe Romano</h4>
                        Brown CS '19.<br>Frontend and design.
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
                <label for="registerEmail">Email address</label>
                <input type="email" class="form-control" id="registerEmail" placeholder="Email">
                </div>
                <div class="form-group">
                <label for="registerPassword">Password</label>
                <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                </div>
                <div class="form-group">
                <label for="athleteName">Your name</label>
                <input type="text" class="form-control" id="athleteName" placeholder="Name">
                </div>
                <div class="form-group">
                <label for="athleteName">Team name</label>
                <input type="text" class="form-control" id="athleteName" placeholder="Name">
                </div>
                <div class="form-group">
                <label for="phoneNumber">Phone number</label>
                <input type="text" class="form-control" id="phoneNumber" placeholder="Name">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Register team</button>
          </div>
        </div>
      </div>
    </div>
    
        <!-- Modal: TEAM SIGNIN [teamSignin] -->
    <div class="modal fade" id="teamSignin" tabindex="-1" role="dialog" aria-labelledby="teamSigninLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="teamSigninLabel">Sign into your team</h4>
          </div>
          <div class="modal-body">
            <form>
                <div class="form-group">
                <label for="signinEmail">Email address</label>
                <input type="email" class="form-control" id="signinEmail" placeholder="Email">
                </div>
                <div class="form-group">
                <label for="signinPassword">Password</label>
                <input type="password" class="form-control" id="signinPassword" placeholder="Password">
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Sign in</button>
          </div>
        </div>
      </div>
    </div>
    
</#assign>

<#assign scripts>
</#assign>

<#include "main.ftl">