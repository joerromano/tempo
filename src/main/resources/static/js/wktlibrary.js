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

$(document).ready( function() {
    getWorkouts(1, 20);
});
