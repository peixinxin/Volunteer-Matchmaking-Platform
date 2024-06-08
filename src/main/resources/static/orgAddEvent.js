document.addEventListener('DOMContentLoaded', function () {
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("orgProfileLink").textContent = username;
    }

    var userId = localStorage.getItem("userId"); // 從 localStorage 獲取 userId
        alert(userId)
        if (!userId) {
            console.error('No userId found in localStorage.');
            return;
        }

    // 表單提交
    document.getElementById('eventForm').addEventListener('submit', function(event) {
        event.preventDefault();
        
        const formData = {
            name: document.getElementById('eventName').value,
            description: document.getElementById('eventDescription').value,
            category: document.getElementById('eventCategory').value,
            location: document.getElementById('eventLocation').value,
            startDate: document.getElementById('eventStartDate').value,
            endDate: document.getElementById('eventEndDate').value,
            cost: document.getElementById('eventCost').value,
            remark: document.getElementById('eventRemark').value,
            host: userId
        };
        

        fetch('/api/events/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            alert('事件已成功创建！');
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('创建事件失败！');
        });
    });

    // 重置表单
    function resetForm() {
        document.getElementById('eventForm').reset();
        document.getElementById('timeSlotsContainer').innerHTML = `
            <div class="time-slot">
                <input type="date" class="form-control" required>
                <button type="button" class="btn btn-danger btn-sm remove-time-slot">-</button>
            </div>
        `;
    }

    // 處理導航連結
    handleNavigationLink('managePaymentLink', '/managePayments');
    handleNavigationLink('orgViewAllEventsLink', '/viewAllEvents');
    handleNavigationLink('orgEditEventLink', '/orgEditEvent');
    handleNavigationLink('orgVerifyVolunteerLink', '/orgVerifyVolunteer');
    handleNavigationLink('orgAddEventLink', '/orgAddEvent');
    handleNavigationLink('eventVolunLink', '/eventVolun');
    handleNavigationLink('discussSecLink', '/discussSec');
    handleNavigationLink('loginLink', '/login');
    handleNavigationLink('userhomeLink', '/userhomePage');
    handleNavigationLink('orgProfileLink', '/orgProfile');
    handleNavigationLink('logoutLink', '/logout');
});

function handleNavigationLink(id, url) {
    const link = document.getElementById(id);
    if (link) {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = url;
        });
    }
}

