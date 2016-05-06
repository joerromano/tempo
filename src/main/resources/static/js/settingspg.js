$(document).on('click', '#addTeamBtn', function() {
    $.ajax({
        method: "POST",
        url: "/newteam",
        data: JSON.stringify({name: $('#teamName').val()}),
        success: function(responseJSON) {
            location.reload();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Error: " + errorThrown + " | Your changes have not been applied");
        }
    });
});

$(document).on('click', '#renameTeamBtn', function() {
    $("#renameTeamTitle").text("Rename Team: " + $(this).attr("team-name"));
    $("#teamNameBox").val($(this).attr("team-name"));
});

$(document).on('click', '#disbandTeamBtn', function() {
    $.ajax({
        method: "POST",
        url: "/disbandteam",
        data: JSON.stringify({team: $(this).attr("team-id")}),
        success: function(responseJSON) {
            location.reload();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Error: " + errorThrown + " | Your changes have not been applied");
        }
    })
})

$(document).ready( function() {
});
