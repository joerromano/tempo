$(document).on('click', '#changeTeamNav', function() {
    $.ajax({
        method: "POST",
        url: "/switchteam",
        data: JSON.stringify({team: $(this).attr("team-id")}),
        success: function(responseJSON) { location.reload(); },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied")
        }
    });
})

$(document).ready( function() {

});