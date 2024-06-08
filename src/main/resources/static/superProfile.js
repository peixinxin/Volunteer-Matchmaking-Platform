document.addEventListener("DOMContentLoaded", function () {
    var username = localStorage.getItem("name");
    var userId = localStorage.getItem("userId");
    var userType = localStorage.getItem("userType");
    var account = localStorage.getItem("account");

    if (username) {
        document.getElementById("superProfileLink").textContent = username;
        document.getElementById("name").textContent = username;
    }
    if(account) {
        document.getElementById("account").textContent = account;
    }
    if (userId) {
        document.getElementById("userId").textContent = userId;
    }

    if (userType) {
        document.getElementById("userType").textContent = userType;
    }

    document.getElementById("userhomeLink").addEventListener("click", function () {
        window.location.href = "/userhome";
    });

    document.getElementById("superEventLink").addEventListener("click", function () {
        window.location.href = "/supervisorhome";
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
