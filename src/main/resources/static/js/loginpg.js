$("#mainPgLogin").submit(function( event ) {
    event.preventDefault();
    $.ajax({
        method: "POST",
        url: "/login",
        data: JSON.stringify({
            email: $("#emailLogin").val(),
            password: $("#passwordLogin").val()
        }),
        success: function(responseJSON) {
            var responseObject = JSON.parse(responseJSON);
            if (responseObject.success) {
                alert("Successful login!");
            } else {
                alert("Could not log in, try a new password");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) { 
           alert("Error: " + errorThrown + " | Your changes have not been applied")
        }
    });
});

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
