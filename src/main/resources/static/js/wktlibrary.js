var curMin = 1;

function getWorkouts(startPt, endPt) {
    $.ajax({
        method: "POST",
        url: "/library",
        data: JSON.stringify({sort: "popular", from: startPt, to: endPt}),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            console.log("Got workouts", responseObject);
            $("#wktList").empty();
            $("#wktDisplayInfo").text("Displaying workouts " + startPt + " through " + endPt);
            $(responseObject).each(function(index) {
                var toAppend = '<li class="list-group-item">' +
                    '<h5 style="margin-top: 4px;">' + this.time + ' Workout: ' + this.type + '</h5>with intensity: ' + this.intensity + ' and score: ' + this.score + '</li>';
                $("#wktList").append(toAppend);
            });
        }
    });
}

function refreshWkts() {
    getWorkouts(curMin, curMin+19);
}

$(document).on('click', '#next20', function() {
    curMin += 20;
    refreshWkts();
});

$(document).on('click', '#last20', function() {
    if (curMin == 1) {
        return;
    } else {
        curMin -= 20;
        refreshWkts();
    }
});

$(document).ready( function() {
    refreshWkts();
});
