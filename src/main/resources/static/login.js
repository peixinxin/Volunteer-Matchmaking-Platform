$(document).ready(function () {
    localStorage.clear();
    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        var email = $('#email').val();
        var password = $('#password').val();
        var userType = $('input[name="userType"]:checked').val();

        var url = '/api/login';  // 統一登入URL

        $.ajax({
            url: url,
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({ email: email, password: password, userType: userType }),  // 發送的資料
            success: function (response) {
                alert('Welcome, ' + response.name);
                localStorage.setItem('name', response.name);
                localStorage.setItem('userId', response.userId);
                localStorage.setItem('userType', response.userType);
                localStorage.setItem('account', response.email);

                // 根據usertype跳轉到不同的profile頁面
                if (userType === 'organization') {
                    window.location.href = '/orgProfile';
                } else if (userType === 'supervisor') {
                    window.location.href = '/superProfile';
                } else {
                    window.location.href = '/volunteerProfile';
                }
            },
            error: function (response) {
                alert('Error: ' + response.responseText);
            }
        });
    });

    // Handle navigation links
    document.getElementById("eventVolunLink").addEventListener("click", function() {
        window.location.href = "/eventVolun";
    });

    document.getElementById("discussSecLink").addEventListener("click", function() {
        window.location.href = "/discussSec";
    });

    document.getElementById("loginLink").addEventListener("click", function() {
        window.location.href = "/login";
    });

    // Retrieve and display the username from localStorage
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("profileLink").textContent = username;
    }

    document.getElementById("profileLink").addEventListener("click", function() {
        window.location.href = "/volunteerProfile";
    });
});