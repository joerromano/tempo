var curMoment = moment().startOf('week');

function oneWeekLater(myDate) {
    return myDate.getDate() + 7;
}

// TODO: Javascript Date Object
var curTrainingGroups;
var viewingScheduleGroup = {id: "", name: ""};
var viewingDay = "sun";

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
                    '<button class="btn btn-success" id="publishGroup" type="button" value="pub-' + this.id + '"><span class="glyphicon glyphicon-check"></span> Publish</button>' + 
                    // Delete button
                    '<button class="btn btn-default" id="deleteGroup" type="button" value="del-' + this.id + '"><span class="glyphicon glyphicon-remove"></span> Delete</button></div>');
                
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
        $("#workoutDetailArea").html('We will get the workout info for the day: ' + viewingDay + ' and the workout group with ID: ' + viewingScheduleGroup.id + ' (' + viewingScheduleGroup.name + ')');
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

// Publish training group
$(document).on('click', '#publishGroup', function() {
    console.log("Trying to publish a group", $(this).attr("value").substr(4));
    $.ajax({
        method: "POST",
        url: "/publish",
        data: JSON.stringify({id: ($(this).attr("value")).substr(4)}),
        success: function(responseJSON) {
            // TODO: Indicate to person
            console.log("Published", responseJSON);
            reloadWorkoutGroups();
        }
    });
});

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

// Select group to modify
$(document).on('click', '#groupScheduleSelector li', function() {
    viewingScheduleGroup = {id: $(this).attr("workout-id"), name: $(this).text()};
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