document.addEventListener('DOMContentLoaded', function () {
    // Retrieve basic user info from localStorage
    var username = localStorage.getItem("name");
    var userId = localStorage.getItem("userId");
    var account = localStorage.getItem("account");
    var userType = localStorage.getItem("userType");
    var email = localStorage.getItem("email");  // Assume email is also stored in localStorage

    // Display the retrieved info in the HTML
    if (username) {
        document.getElementById("orgProfileLink").textContent = username;
    }
    if (account) {
        document.getElementById("profileAccount").textContent = account;
    }
    if (username) {
        document.getElementById("profileName").textContent = username;
    }
    if (userId) {
        document.getElementById("profileId").textContent = userId;
    }
    if (userType) {
        document.getElementById("profileType").textContent = userType;
    }

    
    document.getElementById('managePaymentLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/viewAllEvents';
    });
    document.getElementById('orgViewAllEventsLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/viewAllEvents';
    });
    document.getElementById('orgEditEventLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/orgEditEvent';
    });
    document.getElementById('orgVerifyVolunteerLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/orgVerifyVolunteer';
    });
    document.getElementById('orgAddEventLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/orgAddEvent';
    });
    document.getElementById('managePaymentsLink').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/managePayments';
    });
    document.getElementById("eventVolunLink").addEventListener("click", function(event) {
        event.preventDefault();
        window.location.href = "/eventVolun";
    });
    document.getElementById("discussSecLink").addEventListener("click", function(event) {
        event.preventDefault();
        window.location.href = "/discussSec";
    });
    
    document.getElementById("userhomeLink").addEventListener("click", function(event) {
        event.preventDefault();
        window.location.href = "/userhomePage";
    });
    document.getElementById("orgProfileLink").addEventListener("click", function(event) {
        event.preventDefault();
        window.location.href = "/orgProfile";
    });
    
    document.getElementById("loginLink").addEventListener("click", function () {
        window.location.href = "/login";
    });
    document.getElementById("logout").addEventListener("click", function () {
        // handle logout action, such as clearing localStorage and redirecting to login page
        localStorage.clear();
        window.location.href = "/login";
    });
});

