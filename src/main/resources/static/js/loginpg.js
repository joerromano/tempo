$(document).on('click', '#registerCoach', function() {
    $.ajax({
        method: "POST",
        url: "/newaccount",
        data: JSON.stringify({email: $("#registerEmail").val(),
                             name: $("#registerName").val(),
                             password: $("#registerPassword").val(),
                             location: $("#registerZip").val()}),
        success: function(responseJSON) { alert("Successfully made new account, you may now login!") },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied")
        }
    });
});

$(document).ready( function() {
});
