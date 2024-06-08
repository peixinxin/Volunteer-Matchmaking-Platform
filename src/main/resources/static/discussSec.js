document.addEventListener("DOMContentLoaded", function() {
    // Handle navigation links
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("profileLink").textContent = username;
    }
    document.getElementById("userhomeLink").addEventListener("click", function() {
      window.location.href = "/userhomePage";
    });
  
    document.getElementById("eventVolunLink").addEventListener("click", function() {
      window.location.href = "/eventVolun";
    });
  
    document.getElementById("discussSecLink").addEventListener("click", function() {
      window.location.href = "/discussSec";
    });
  
    document.getElementById("loginLink").addEventListener("click", function() {
      window.location.href = "/login";
    });
  
    document.getElementById("profileLink").addEventListener("click", function() {
      window.location.href = "/volunteerProfile";
    });
  });