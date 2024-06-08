document.addEventListener('DOMContentLoaded', function () {
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("orgProfileLink").textContent = username;
        const superProfileLink = document.getElementById("superProfileLink");
        if (superProfileLink) {
            superProfileLink.textContent = username;
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    const rigisId = parseInt(urlParams.get('regisId'), 10);
    const volunteerId = urlParams.get('id');

    console.log("URL Params:", urlParams.toString());
    console.log("Volunteer ID:", volunteerId);
    if (volunteerId) {
        fetch(`/api/volunteers/${volunteerId}`)
            .then(response => response.json())
            .then(data => {
                // 確認回傳的資料結構
                console.log("Fetched volunteer data:", data);

                // 假設你的 API 回應的結構是 { "volunteer": { ...志願者資料... } }
                const volunteer = data.volunteer;

                // 確保 volunteer 不是 null 或 undefined
                if (volunteer) {
                    const volunteerContainer = document.getElementById('volunteerContainer');
                    const template = document.getElementById('volunteerTemplate').content;
                    const clone = document.importNode(template, true);
                    clone.querySelector('.volunteerName').textContent = `姓名: ${volunteer.name || 'N/A'}`;
                    clone.querySelector('.volunteerEmail').textContent = `Email: ${volunteer.email || 'N/A'}`;
                    clone.querySelector('.volunteerGender').textContent = `性別: ${volunteer.gender || 'N/A'}`;
                    clone.querySelector('.volunteerIntroduction').textContent = `自介: ${volunteer.introduction || 'N/A'}`;
                    clone.querySelector('.volunteerAge').textContent = `年齡: ${volunteer.age || 'N/A'}`;

                    volunteerContainer.appendChild(clone);
                } else {
                    console.error('Volunteer data is missing in the response');
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        console.error('Volunteer ID not found in URL parameters');
    }
    // 監聽表單提交事件
    document.getElementById('reviewForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止默認的表單提交行為

        const reviewDecision = document.getElementById('reviewDecision').value; // 獲取審核結果選擇的值
        

        fetch(`/api/registration/approve`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify( {registrationId: rigisId ,status: reviewDecision})
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                window.location.href = "/orgVerifyVolunteer";
            })
            .catch(error => console.error('Error:', error));
    });

    

    var links = {
        "eventVolunLink": "/eventVolun",
        "discussSecLink": "/discussSec",
        "loginLink": "/login"
    };

    Object.keys(links).forEach(function (key) {
        const element = document.getElementById(key);
        if (element) {
            element.addEventListener('click', function () {
                window.location.href = links[key];
            });
        }
    });
});

function goBack() {
    window.location.href = "/orgVerifyVolunteer";
}