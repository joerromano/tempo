var curMoment = moment().startOf('week');

function oneWeekLater(myDate) {
    return myDate.getDate() + 7;
}

// TODO: Javascript Date Object
var curTrainingGroups;
var viewingScheduleGroup = {id: "", name: ""};
var viewingDay = "Sunday";
var workoutsToDisplay;

function reloadWorkoutGroups() {
    $("#trainingPlanTitle").text("Training plan for week of: " + curMoment.format("dddd, MMMM Do YYYY"));
    $.ajax({
        method: "POST",
        url: "/group",
        data: JSON.stringify({start: curMoment.format("MMDDYYYY"), end: moment(curMoment).add(6, 'days').format("MMDDYYYY")}),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            curTrainingGroups = responseObject.groups;
            unassignedAthletes = responseObject.unassigned;
            
            // empty out top portion
            $("#training-groups").empty();
            // empty out the schedule selector
            $("#groupScheduleSelector").empty();
            
            // reset the schedule chooser
            resetSchedules();
            
            // add each group in
            $(curTrainingGroups).each(function(index) {
                // append to schedule selector
                $("#groupScheduleSelector").append('<li workout-id="' + this.id + '"><a href="#chg-group">'+ this.name +'</a></li>');
                
                // REST: appending for the main area
                $("#training-groups").append('<h4 class="pull-left groupName" workout-id="' + this.id + '">' + this.name + '</h4>');
                $("#training-groups").append(
                    '<div class="btn-group pull-right" role="group">' +
                    // Publish button
                    '<button class="btn btn-success" id="publishGroup" type="button" workout-id="' + this.id + '"><span class="glyphicon glyphicon-check"></span> Publish</button>' + 
                    // Delete button
                    '<button class="btn btn-default" id="deleteGroup" type="button" workout-id="' + this.id + '"><span class="glyphicon glyphicon-remove"></span> Delete</button></div>');
                
                var toAppend = "";
                
                if (this.members.length == 0) {
                    // set height
                    toAppend += '<div class="clearfix"></div><ul workout-id="' + this.id + '" class="connectedSortable team-members" style="height: 30px;">';
                } else {
                    // no set height
                    toAppend += '<div class="clearfix"></div><ul workout-id="' + this.id + '" class="connectedSortable team-members">';
                    // add team members for each group
                    $(this.members).each(function(index2) {
                        toAppend += '<li athlete-id="' + this.id + '"><span class="athlete-name">' + this.name + '</span><span class="athlete-agony-bar"><span class="athlete-agony-bar-inner" style="width: 50%;"></span></span></li>';
                    });
                }
                toAppend += '</ul><hr/>';
                $("#training-groups").append(toAppend);
            });
                
                // add unassigned athletes
            $("#training-groups").append('<h4>Unassigned athletes</h4>');
            var toAppendUnassigned = '';
            if (unassignedAthletes.length == 0) {
                toAppendUnassigned += '<ul class="connectedSortable team-members" style="height: 30px;">';
            } else {
                $(unassignedAthletes).each(function(index) {
                    toAppendUnassigned += '<ul class="connectedSortable team-members">';
                    toAppendUnassigned += '<li athlete-id="' + this.id + '"><span class="athlete-name">' + this.name + '</span></li>';
                });
            }
            toAppendUnassigned += '</ul>';
            $("#training-groups").append(toAppendUnassigned);
                
                // set up dragging
                $( "#training-groups ul" ).sortable({
                    connectWith: ".connectedSortable",
                    // Call AJAX on changing sort
                    stop: function( event, ui ) {
                      uploadWorkoutGroups();
                  }
                }).disableSelection();
            
                // set up popovers
                $("[data-toggle=popover]").popover();

                // DEBUG
                console.log("Reloaded workout groups", responseObject);
        }
    });
}

function uploadWorkoutGroups() {
    var listToSend = [];
    $("#training-groups ul").each(function(index) {
        var wktId = $(this).attr("workout-id");
        var listLi = $(this).find('li');
        var athleteIds = [];
        
        $(listLi).each(function(index2) {
            athleteIds.push($(this).attr("athlete-id"));
        });
        listToSend.push({id: wktId, athletes: athleteIds});
    });
    
    console.log("Making AJAX upload with", listToSend);
    
    $.ajax({
        method: "POST",
        url: "/updateweek",
        data: JSON.stringify(listToSend),
        success: function(responseJSON) {
            console.log("Updated wkt groups", responseJSON);
            reloadWorkoutGroups();
        }
    });
}

function resetSchedules() {
    viewingScheduleGroup = {id: "", name: ""};
    $("#groupSelectedforSchedule").html('Select a Group <span class="caret"></span>');
    $("#workoutDetailArea").html('<br><div class="alert alert-danger" role="alert"><b>Oh no!</b> You must select a group above!</div>');
}


function reloadSchedules() {
    if (viewingScheduleGroup.name === "") {
        resetSchedules();
    } else {
        $("#groupSelectedforSchedule").html(viewingScheduleGroup.name + ' <span class="caret"></span>');
        $("#workoutDetailArea").html('We will get the workout info for the day: ' + moment(curMoment).day(viewingDay).format("MMMM D, YYYY") + ' 12:00:00 AM' + ' and the workout group with ID: ' + viewingScheduleGroup.id + ' (' + viewingScheduleGroup.name + ')');
       
        
        var todaysWorkouts = workoutsToDisplay.filter(function(obj) {
            return ('date' in obj && obj.date === moment(curMoment).day(viewingDay).format("MMMM D, YYYY") + ' 12:00:00 AM');
        });
        
        console.log(workoutsToDisplay.filter(function(obj) {
            return ('date' in obj && obj.date === moment(curMoment).day(viewingDay).format("MMMM D, YYYY") + ' 12:00:00 AM');
        }));
        
        var toAppend = '<br><div class="row">';
        
        var amFilt = todaysWorkouts.filter(function(obj) { return ('time' in obj && obj.time === "AM"); });
        var pmFilt = todaysWorkouts.filter(function(obj) { return ('time' in obj && obj.time === "PM"); });
        var suFilt = [];
        
        // AM
        if (amFilt.length == 1) {
            toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">AM Workout</h4></div><div class="panel-body"><b>Type:</b> ' + amFilt[0].type + '<br/><b>Mileage:</b> ' + amFilt[0].score + '<hr><b>Comments:</b><br/>' + 'Must implement comments on backend!' + '<br/></div></div></div>';
        } else {
            toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">AM Workout</h4></div><div class="panel-body">NOTHING?</div></div></div>';
        }
        
        // PM
        if (pmFilt.length == 1) {
            toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">PM Workout</h4></div><div class="panel-body"><b>Type:</b> ' + pmFilt[0].type + '<br/><b>Mileage:</b> ' + pmFilt[0].score + '<hr><b>Comments:</b><br/>' + 'Must implement comments on backend!' + '<br/></div></div></div>';
        } else {
            toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">PM Workout</h4></div><div class="panel-body">NOTHING?</div></div></div>';
        }
        
        // Supplemental and Comments
        toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">Supplemental Workout</h4></div><div class="panel-body">TODO</div></div></div>';
        
        // Weather (TODO)
        toAppend += '<div class="col-md-3"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">Weather</h4></div><div class="panel-body">TODO</div></div></div>';
        
        toAppend +=
            '</div><div class="row"><div class="col-md-3"><button type="button" class="btn btn-primary btn-block" role="button" data-toggle="collapse" href="#editWorkout" aria-expanded="false" aria-controls="editWorkout">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></div>' + 
            '<div class="col-md-3"><button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></div>' + 
            '<div class="col-md-3"><button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></div>' +
            '<div class="col-md-3"><button type="button" class="btn btn-primary btn-block">Edit or Add <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></div></div>';
        
        $("#workoutDetailArea").html(toAppend);
        
    }
}



// ############################################################################################# \\
// ############################################################################################# \\
// Make UI Elements Interactable
// ############################################################################################# \\

// Move between weeks
$("a[href='#forwardWeek']").click(function() {
    curMoment.add(7, 'days');
    reloadWorkoutGroups();
    resetSchedules();
});
$("a[href='#backWeek']").click(function() {
    curMoment.subtract(7, 'days');
    reloadWorkoutGroups();
});

// ####################################################################################################
// Add new training groups
$("#addGroupButton").click(function() {
    $.ajax({
        method: "POST",
        url: "/add",
        data: JSON.stringify({name: $("#workoutGroupName").val(), start: curMoment.format("MMDDYYYY")}),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            reloadWorkoutGroups();
            $("#addWorkoutGroup").modal('hide');
            console.log("Added group", responseObject);
        }
    });
});

// ####################################################################################################
// Add new team members
$("#addMemberButton").click(function() {
    $.ajax({
        method: "POST",
        url: "/addmember",
        data: JSON.stringify({name: $("#athleteName").val(),
              number: $("#athletePhone").val(),
              email: $("#athleteEmail").val(),
              location: "TBD"}),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            reloadWorkoutGroups();
            $("#addTeamMember").modal('hide');
            console.log("Added member", responseObject);
        }
    });
});

// ####################################################################################################
// Publish training group
$(document).on('click', '#publishGroup', function() {
    console.log("Trying to publish a group", $(this).attr("workout-id"));
    $.ajax({
        method: "POST",
        url: "/publish",
        data: JSON.stringify({id: $(this).attr("workout-id")}),
        success: function(responseJSON) {
            // TODO: Indicate to person
            console.log("Published", responseJSON);
            reloadWorkoutGroups();
        }
    });
});

// ####################################################################################################
// Edit [rename] training group
// Click to be able to edit
$(document).on('click', '#training-groups .groupName', function() {
    $(this).replaceWith('<input type="text" id="editName" workout-id="' + $(this).attr("workout-id") + '" value="' + $(this).text() + '" class="form-control pull-left" id="newname" placeholder="Name" style="font-size: 14px; margin-top: 5px; margin-bottom:5px; width: 300px; display: inline; height: 28px;">');
});

// Apply changes function
function applyFieldChanges(domObject) {
    $.ajax({
        method: "POST",
        url: "/renamegroup",
        data: JSON.stringify({id: $(domObject).attr("workout-id"), name: $(domObject).val()}),
        success: function(responseJSON) {
            reloadWorkoutGroups();
        }
    });
}
// Lose focus to apply changes
$(document).on('blur', '#training-groups #editName', function() {
    applyFieldChanges(this);
});
// OR hit enter to apply changes
$(document).on('enterKey', '#training-groups #editName', function() {
    applyFieldChanges(this);
});
$(document).on('keyup', '#training-groups #editName', function(e) {
    if (e.keyCode == 13) { $(this).trigger("enterKey"); }
});

// ####################################################################################################
// Delete a group by ID
$(document).on('click', '#deleteGroup', function(e) {
    console.log("Trying to delete a group", $(this).attr("workout-id"));
    $.ajax({
        method: "POST",
        url: "/deletegroup",
        data: JSON.stringify({id: $(this).attr("workout-id")}),
        success: function(responseJSON) {
            // TODO: Indicate to person
            console.log("Deleted", responseJSON);
            reloadWorkoutGroups();
        }
    });
});

// ####################################################################################################
// Select group to modify workouts
$(document).on('click', '#groupScheduleSelector li', function() {
    viewingScheduleGroup = {id: $(this).attr("workout-id"), name: $(this).text()};
    workoutsToDisplay = (curTrainingGroups.filter(function(obj) {
            return ('id' in obj && obj.id == viewingScheduleGroup.id);
        }))[0].workouts;
        $("#workoutDetailArea").html('');
    console.log("Workouts to display", workoutsToDisplay);
    reloadSchedules();
});

// Select day to view or edit in Workout Details
$(document).on('click', '#workoutDetailDayPicker li', function() {
    viewingDay = $(this).attr("day-view");
    reloadSchedules();
});


$(document).ready( function() {
    // Set up the title of which week we are on
    $("#trainingPlanTitle").text("NOT LOADED YET!");
});