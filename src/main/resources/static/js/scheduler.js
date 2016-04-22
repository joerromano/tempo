var curWeekStart = "04112016";
var curTrainingGroups;

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
        data: {start: curWeekStart, end: incrementWeek(curWeekStart)},
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            console.log(responseObject);
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
        data:{name: $("#workoutGroupName").val(), start: curWeekStart},
        success: function(responseJSON) {
            reloadWorkoutGroups();
            $("#addWorkoutGroup").modal('hide');
        }
    });
});

// Add new team members
$("#addMemberButton").click(function() {
    $.ajax({
        method: "POST",
        url: "/addmember",
        data:{name: $("#athleteName").val(),
              number: $("#athletePhone").val(),
              email: $("#athleteEmail").val(),
              location: "TBD"},
        success: function(responseJSON) {
            reloadWorkoutGroups();
            $("#addTeamMember").modal('hide');
        }
    });
});

// Draggable athletes
$( "#training-groups ul" ).sortable({
      connectWith: ".connectedSortable"
    }).disableSelection();

$(document).ready( function() {
    // Set up the title of which week we are on
    $("#trainingPlanTitle").text("Training plan for week of: " + weekParser(curWeekStart));
});