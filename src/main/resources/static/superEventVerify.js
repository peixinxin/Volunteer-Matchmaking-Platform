document.addEventListener('DOMContentLoaded', function () {
    // 取得 URL 中的參數
    const urlParams = new URLSearchParams(window.location.search);
    const eventId = urlParams.get('id'); // 取得活動 ID

    // 監聽表單提交事件
    document.getElementById('reviewForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 阻止默認的表單提交行為

        const reviewDecision = document.getElementById('reviewDecision').value; // 獲取審核結果選擇的值
        const reviewReason = document.getElementById('reviewReason').value; // 獲取審核理由的值

        const updatedEvent = {
            id: eventId, 
            superviseStatus: reviewDecision, 
            reviewReason: reviewReason 
        };

        // 發送 POST 請求到後端的更新活動審核狀態路由
        fetch(`/api/supervisor/updateEventReview/${eventId}`, {
            method: 'POST', // 請求方法為 POST
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(updatedEvent) 
        })
        .then(response => response.text()) 
        .then(data => {
            alert(data); // 彈出提示框顯示回應訊息
        })
        .catch(error => {
            console.error('錯誤:', error); // 當請求失敗時，在控制台打印錯誤訊息
        });
    });


    //顯示詳細活動資訊
    if (eventId) {
        fetch(`/api/supervisor/${eventId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(event => {
                const eventsContainer = document.getElementById('superDetailContainer');
                const template = document.getElementById('superDetailTemplate').content;
                
                const eventClone = document.importNode(template, true);
                eventClone.querySelector('#eventName').textContent = event.name;
                eventClone.querySelector('#eventDescription').textContent = event.description;
                eventClone.querySelector('#eventDate').textContent = `時間: ${event.startDate}`;
                eventClone.querySelector('#eventCategory').textContent = `類別: ${event.category}`;
                eventClone.querySelector('#eventLocation').textContent = `地點: ${event.location}`;
                eventClone.querySelector('#eventFee').textContent = `價格: ${event.cost}`;
                eventClone.querySelector('#eventRemark').textContent = `備註: ${event.remark}`;
                eventsContainer.appendChild(eventClone);
            })
            .catch(error => {
                console.error('Error fetching event details:', error);
                document.getElementById('eventDetailsCard').innerHTML = '<p>無法加載活動詳細信息。</p>';
            });
    } else {
        document.getElementById('eventDetailsCard').innerHTML = '<p>未指定活動ID。</p>';
    }

    


    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("superProfileLink").textContent = username;
    }
    const links = {
        userhomeLink: "/userhome",
        superEventLink: "/supervisorhome",
        superProfileLink: "/superProfile",
        superEventVerifyLink: "/superEventVerify"
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
    window.location.href = "/supervisorhome";
}