$(document).on('click', '#updatePasswordBtn', function() {
   $.ajax({
       method: "POST",
       url: "/update",
       data: JSON.stringify({old_password: $("#registerPasswordOld").val(),
                             new_password: $("#registerPasswordNew").val()}),
       success: function(responseJSON) { location.reload(); },
       error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied");
       }
   });
});

$(document).on('click', '#updateDemoBtn', function() {
    $.ajax({
        method: "POST",
        url: "/update",
        data: JSON.stringify({name: $("#resetName").val()}),
        success: function(responseJSON) { location.reload(); },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Error: " + errorThrown + " | Your changes have not been applied");
        }
    });
});

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
    $("#teamIdHidden").val($(this).attr("team-id"));
});

$(document).on('click', '#renameTeamModalBtn', function() {
    $.ajax({
        method: "POST",
        url: "/renameteam",
        data: JSON.stringify({team: $("#teamIdHidden").val(), name: $("#teamNameBox").val()}),
        success: function(responseJSON) { location.reload(); },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Error: " + errorThrown + " | Your changes have not been applied");
        }
    });
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
});

$(document).on('click', '#deleteAccountBtn', function() {
    $(this).attr('id', 'confirmDeleteBtn');
    $(this).removeClass('btn-warning');
    $(this).addClass('btn-danger');
    $(this).text("CLICK TO CONFIRM DELETION - THIS IS NOT AN UNDOABLE ACTION.")
});

$(document).on('click', '#confirmDeleteBtn', function() {
    $.ajax({
        method: "GET",
        url: "/delete",
        success: function(responseJSON) {
            location.reload();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("COULD NOT DELETE ACCOUNT, FAILURE");
        }
    });
});

$(document).ready( function() {
});
