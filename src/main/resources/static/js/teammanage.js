$(document).on('click', '#editMemberSubmitBtn', function() {
   $.ajax({
       method: "POST",
       url: "/addmember",
       data: JSON.stringify({id: $("#athleteDetail").attr("athlete-id"),
                            name: $("#athleteName").val(),
                            number: $("#athletePhone").val(),
                            email: $("#athleteEmail").val(),
                            location: $("#athleteDetail").attr("athlete-zip")}),
       success: function(responseJSON) { location.reload(); },
       error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied");
       }
   });
});

$(document).on('click', '#editMemberBtn', function() {
    $("#athleteName").val($(this).attr("member-name"));
    $("#athletePhone").val($(this).attr("member-phone"));
    $("#athleteEmail").val($(this).attr("member-email"));
    $("#athleteDetail").attr("athlete-id", $(this).attr("member-id"));
    $("#athleteDetail").attr("athlete-zip", $(this).attr("member-zip"));
});

$(document).on('click', '#deleteMemberBtn', function() {
    $.ajax({
        method: "POST",
       url: "/removemember",
       data: JSON.stringify({id: $(this).attr("member-id")}),
       success: function(responseJSON) { location.reload(); },
       error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied");
       }
    });
});

// ####################################################################################################
// Add new team members
$("#addMemberButton").click(function() {
    $.ajax({
        method: "POST",
        url: "/addmember",
        data: JSON.stringify({name: $("#athleteNameAdd").val(),
              number: $("#athletePhoneAdd").val(),
              email: $("#athleteEmailAdd").val(),
              location: "TBD"}),
        success: function(responseJSON) { location.reload(); },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied");
        }
    });
});


$(document).ready( function() {
});
