var curWeekStart = "04102016";
var curTrainingGroups;
var viewingSchedule;

// Parse a 8 character date into the correct string for that date
function weekParser(weekString) {
    var weekParserMonth;
    switch(weekString.substr(0, 2)) {
        case "01":
            weekParserMonth = "January";
            break;
        case "02":
            weekParserMonth = "February";
            break;
        case "03":
            weekParserMonth = "March";
            break;
        case "04":
            weekParserMonth = "April";
            break;
        case "05":
            weekParserMonth = "May";
            break;
        case "06":
            weekParserMonth = "June";
            break;
        case "07":
            weekParserMonth = "July";
            break;
        case "08":
            weekParserMonth = "August";
            break;
        case "09":
            weekParserMonth = "September";
            break;
        case "10":
            weekParserMonth = "October";
            break;
        case "11":
            weekParserMonth = "November";
            break;
        case "12":
            weekParserMonth = "December";
            break;
        default:
            weekParserMonth = "MONTH";
    }
    return weekParserMonth + " " + weekString.substr(2, 2) + ", " + weekString.substr(4, 4);
}

// Return an incremented week (TODO: NEED TO MAKE MONTHS/YEARS WORK)
function incrementWeek(weekString) {
    newWeekNum = parseInt(weekString.substr(2, 2)) + 7;
    var newWeekStr;
    if (newWeekNum < 10) {
        newWeekStr = "0" + newWeekNum.toString();
    } else {
        newWeekStr = newWeekNum.toString();
    }
    return weekString.substr(0, 2) + newWeekStr + weekString.substr(4, 4);
}

// Return a decremented week (TODO: NEED TO MAKE MONTH/YEARS WORK)
function decrementWeek(weekString) {
    newWeekNum = parseInt(weekString.substr(2, 2)) - 7;
    var newWeekStr;
    if (newWeekNum < 10) {
        newWeekStr = "0" + newWeekNum.toString();
    } else {
        newWeekStr = newWeekNum.toString();
    }
    return weekString.substr(0, 2) + newWeekStr + weekString.substr(4, 4);
}



function reloadWorkoutGroups() {
    $.ajax({
        method: "POST",
        url: "/group",
        data: JSON.stringify({start: curWeekStart, end: incrementWeek(curWeekStart)}),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            curTrainingGroups = responseObject;
            
            // empty it out
            $("#training-groups").empty();
            
            // add each group in
            $(curTrainingGroups).each(function(index) {
                $("#training-groups").append('<h4 class="pull-left groupName" workout-id="' + this.id + '">' + this.name + '</h4>');
                $("#training-groups").append(
                    '<div class="btn-group pull-right" role="group">' +
                    // Publish button
                    '<button class="btn btn-success" id="publishGroup" type="button" value="pub-' + this.id + '"><span class="glyphicon glyphicon-check"></span> Publish</button>' + 
//                    // Edit button [#editGroup] value="edt-' + this.id + '"
//                    '<button class="btn btn-default" type="button" data-toggle="popover" container="body" data-placement="left" data-html="true" title="Set New Group Name" data-content="Test<div>Test' + 
//                        // Edit form
//                    'Test'
//                    
//                    + '</div>"> <span class="glyphicon glyphicon-pencil"></span> Edit</button>' + 
                    // Delete button
                    '<button class="btn btn-default" id="deleteGroup" type="button" value="del-' + this.id + '"><span class="glyphicon glyphicon-remove"></span> Delete</button></div>');
                
                var toAppend = "";
                
                if (this.members.length == 0) {
                    // set height
                    toAppend += '<div class="clearfix"></div><ul class="connectedSortable team-members" style="height: 30px;">';
                } else {
                    // no set height
                    toAppend += '<div class="clearfix"></div><ul class="connectedSortable team-members">';
                    // add team members for each group
                    $(this.members).each(function(index2) {
                        toAppend += '<li><span class="athlete-name">' + this.name + '</span><span class="athlete-agony-bar"><span class="athlete-agony-bar-inner" style="width: 50%;"></span></span></li>';
                    });
                }
                toAppend += '</ul><hr/>';
                $("#training-groups").append(toAppend);
            });
                
                // add unassigned athletes
                $("#training-groups").append('<h4>Unassigned athletes</h4><ul class="connectedSortable team-members"><li><span class="athlete-name">Paul Ryan</span></li><li><span class="athlete-name">Bernie Sanders</span></li></ul>');
                
                // set up dragging
                $( "#training-groups ul" ).sortable({
                    connectWith: ".connectedSortable",
                    // Call AJAX on changing sort
                    stop: function( event, ui ) {
                      reloadWorkoutGroups();
                  }
                }).disableSelection();
            
                // set up popovers
                $("[data-toggle=popover]").popover();

                // DEBUG
                console.log("Reloaded workout groups", responseObject);
                // console.log("Current week", curWeekStart);
        }
    });
}



// ############################################################################################# \\
// ############################################################################################# \\
// Make UI Elements Interactable
// ############################################################################################# \\

// Move between weeks
$("a[href='#forwardWeek']").click(function() {
    curWeekStart = incrementWeek(curWeekStart);
    $("#trainingPlanTitle").text("Training plan for week of: " + weekParser(curWeekStart));
    reloadWorkoutGroups();
});
$("a[href='#backWeek']").click(function() {
    curWeekStart = decrementWeek(curWeekStart);
    $("#trainingPlanTitle").text("Training plan for week of: " + weekParser(curWeekStart));
    reloadWorkoutGroups();
});

// Add new training groups
$("#addGroupButton").click(function() {
    $.ajax({
        method: "POST",
        url: "/add",
        data: JSON.stringify({name: $("#workoutGroupName").val(), start: curWeekStart}),
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
// Lose focus to apply changes
$(document).on('blur', '#training-groups #editName', function() {
    $.ajax({
        method: "POST",
        url: "/renamegroup",
        data: JSON.stringify({id: $(this).attr("workout-id"), name: $(this).val()}),
        success: function(responseJSON) {
            reloadWorkoutGroups();
        }
    });
});


$(document).ready( function() {
    // Set up the title of which week we are on
    $("#trainingPlanTitle").text("Training plan for week of: " + weekParser(curWeekStart));
});