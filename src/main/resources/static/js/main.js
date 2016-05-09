$(document).on('click', '#changeTeamNav', function() {
    console.log("doing it", {id: $(this).attr("team-id")});
    $.ajax({
        method: "POST",
        url: "/switchteam",
        data: JSON.stringify({id: $(this).attr("team-id")}),
        success: function(responseJSON) { location.reload(); },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied")
        }
    });
})

$(document).ready( function() {

});