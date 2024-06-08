let username = localStorage.getItem('name');
let userType = localStorage.getItem('userType');
let account = localStorage.getItem('account');
let userId = localStorage.getItem('userId');
if (!username) {
    console.error('No username found in localStorage');
}
if (!userType) {
    console.error('No userType found in localStorage');
}
if (!account) {
    console.error('No account found in localStorage');
}
if (!userId) {
    console.error('No userId found in localStorage');
}
if (isNaN(userId)) {
    console.error('userId in localStorage is not a number');
}
console.log(username, userType, account, userId);

if (username) {
    document.getElementById("profileLink").textContent = username;
}
document.getElementById("volunteerFriendsLink").addEventListener("click", function(event) {
    event.preventDefault();
    window.location.href = "/volunteerFriends";
});

document.getElementById("volunteerLikedEventLink").addEventListener("click", function(event) {
    window.location.href = "/volunteerLikedEvent";
});

document.getElementById("volunteerPaymentsLink").addEventListener("click", function(event) {
    event.preventDefault();
    window.location.href = "/managePayments";
});

document.getElementById("volunteerMyEventLink").addEventListener("click", function(event) {
    window.location.href = "/volunteerMyEvent";
});

document.getElementById("volunteerCertificateLink").addEventListener("click", function(event) {
    event.preventDefault();
    // Replace with the correct path for the certificate
    window.location.href = "/volunteerCertificate";
});

document.getElementById("volunteerSettingsLink").addEventListener("click", function(event) {
    window.location.href = "/volunteerSettings";
});

document.getElementById("homeLink").addEventListener("click", function() {
    window.location.href = "/userhome";
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


// Handle navigation links
document.addEventListener("DOMContentLoaded", function() {
    // Load and display saved self-introduction
    var volunteerId = localStorage.getItem("userId");  // 从localStorage中獲取帳戶
    if (volunteerId) {
        volunteerId = parseInt(volunteerId,10);
        fetch(`/api/volunteers/introduction/${volunteerId}`)  // 发送GET请求到后端，獲取自我介绍
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(volunteer => {
                // 如果存在已保存的自我介绍，將其顯示在文本框中
                if (volunteer && volunteer.introduction) {
                    document.querySelector("textarea").value = volunteer.introduction;
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
    
    // Save user intro
    document.getElementById("saveButton").addEventListener("click", function() {
        // 獲取文本框中的自我介绍内容
        var selfIntroduction = document.querySelector("textarea").value;
        if (account) {
            // 发送POST請求到後端，保存自我介绍
            fetch(`/api/volunteers/${account}/introduction`, {
                method: 'POST',  // 使用POST方法
                headers: {
                    'Content-Type': 'application/json',  // 設置内容類型為JSON
                },
                body: JSON.stringify({ introduction: selfIntroduction }),  // 將自我介绍内容轉換為JSON字符串并作為請求主體
            })
            .then(response => response.json())  // 將響應轉換為JSON
            .then(data => {
                // 根據後端返回的结果顯示相應的提示信息
                if (data.success) {
                    alert("自我介绍已保存！");
                } else {
                    alert("保存失敗，請重試。");
                }
            })
            .catch((error) => {
                // 如果發生錯誤，顯示錯誤訊息
                console.error('Error:', error);
            });
        }
    });
    
    document.getElementById("logout").addEventListener("click", function(event) {
        event.preventDefault();
        document.getElementById("logout").style.display = "none";
        alert("您已成功登出！");
        localStorage.clear();
        document.getElementById("logout").style.display = "block";
        window.location.href = "/login";
    });
    
});