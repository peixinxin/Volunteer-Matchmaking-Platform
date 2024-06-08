let username = localStorage.getItem('name');
let userType = localStorage.getItem('userType');
let account = localStorage.getItem('account');
let userId = localStorage.getItem('userId');
console.log(username, userType, account, userId);

document.getElementById("eventVolunLink").addEventListener("click", function () {
    window.location.href = "/eventVolun";
});

document.getElementById("discussSecLink").addEventListener("click", function () {
    window.location.href = "/discussSec";
});

document.getElementById("logoutLink").addEventListener("click", function () {
    localStorage.clear();
    window.location.href = "/login";
});



// Retrieve and display the username from localStorage
if (username) {
    document.getElementById("profileLink").textContent = username;
}

document.getElementById("profileLink").addEventListener("click", function () {
    window.location.href = "/volunteerProfile";
});

document.addEventListener('DOMContentLoaded', function () {
    // Fetch event details if eventId is present in URL
    const urlParams = new URLSearchParams(window.location.search);
    const eventId = urlParams.get('id');
    console.log("Event ID:", eventId);
    if (eventId) {
        fetch(`/api/events/${eventId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(event => {
                const eventsContainer = document.getElementById('eventsContainer');
                const template = document.getElementById('eventTemplate').content;

                const eventClone = document.importNode(template, true);
                eventClone.querySelector('#eventName').textContent = event.name;
                eventClone.querySelector('#eventDescription').textContent = event.description;
                eventClone.querySelector('#eventDate').textContent = `時間: ${event.startDate}`;
                eventClone.querySelector('#eventCategory').textContent = `類別: ${event.category}`;
                eventClone.querySelector('#eventLocation').textContent = `地點: ${event.location}`;
                eventClone.querySelector('#eventCost').textContent = `費用: ${event.cost}`;
                eventClone.querySelector('#eventHost').textContent = `主辦方: ${event.host}`;
                eventClone.querySelector('#eventRemark').textContent = `備註: ${event.remark}`;

                const likeBtn = document.getElementById('likeBtn');
                const regBtn = document.getElementById('regBtn');
                
                let liked = false;
                let registered = false;

                // Fetch like status
                fetch(`/api/like/find/${userId}/${eventId}`)
                    .then(response => {
                        if (!response.ok) { throw new Error('Network response was not ok ' + response.statusText);}
                        return response.json();
                    })
                    .then(data => {
                        if (data.success) {
                            liked = true;
                            likeBtn.textContent = "取消收藏活動";
                        } else {
                            liked = false;
                            likeBtn.textContent = "加入收藏清單";
                        }
                    }).catch(error => {console.error('Error fetching like status:', error);});
                
                // Fetch registration status
                fetch(`/api/registration/find/${userId}/${eventId}`)
                    .then(response => {
                        if (!response.ok) { throw new Error('Network response was not ok ' + response.statusText);}
                        return response.json();
                    })
                    .then(data => {
                        if (data.success) {
                            registered = true;
                            regBtn.textContent = "取消申請活動";
                        } else {
                            registered = false;
                            regBtn.textContent = "申請參加活動";
                        }
                    }).catch(error => {console.error('Error fetching registration status:', error);});

                // likeBtn
                likeBtn.addEventListener('click', function () {
                    const action = liked ? "remove" : "add";
                    fetch(`/api/like/${action}`, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json',},
                        body: JSON.stringify({
                            "eventId": eventId,
                            "volunteerId": userId
                        })
                    })
                    .then(response => response.json())
                    .then(data => {
                        if(data.success) {
                            liked = !liked;
                            alert(liked ? '已加入收藏' : '已取消收藏');
                            likeBtn.textContent = liked ? "取消收藏活動" : "加入收藏清單";
                        } else {
                            alert(liked ? '未加入收藏！' : '未取消收藏！');
                        }
                    })
                    .catch(error => { console.error('Error updating like status:', error);});
                    window.location.reload();
                });

                // regBtn
                regBtn.addEventListener('click', function () {
                    const action = registered ? "remove" : "add";
                    fetch(`/api/registration/${action}`, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json',},
                        body: JSON.stringify({
                            "eventId": eventId,
                            "volunteerId": userId
                        })
                    })
                    .then(response => response.json())
                    .then(data => {
                        if(data.success) {
                            registered = !registered;
                            alert(registered ? '已申請成功，請靜候團隊審核！' : '已取消申請！');
                            regBtn.textContent = registered ? "取消申請活動" : "申請參加活動";
                        } else {
                            alert(registered ? '申請失敗!請重試。' : '取消申請失敗！');
                        }
                    })
                    .catch(error => { console.error('Error updating registration status:', error);});
                    window.location.reload();
                });

                eventsContainer.appendChild(eventClone);
            })
            .catch(error => {
                console.error('Error fetching event details:', error);
                document.getElementById('eventsContainer').innerHTML = '<p>無法加載活動詳細信息。</p>';
            });
    } else {
        document.getElementById('eventsContainer').innerHTML = '<p>未指定活動ID。</p>';
    }
});