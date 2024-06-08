document.addEventListener('DOMContentLoaded', function () {
    const eventsContainer = document.getElementById('eventsContainer');
    const urlParams = new URLSearchParams(window.location.search);
    const eventId = urlParams.get('id');
    console.log("Event ID:", eventId);
    
    if (eventId) {
        localStorage.setItem("eventId", eventId);
        fetch(`/api/events/${eventId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(event => {
                const template = document.getElementById('eventTemplate').content;
                const eventClone = document.importNode(template, true);

                // 填充模板
                eventClone.querySelector('#eventName').value = event.name;
                eventClone.querySelector('#eventDescription').value = event.description;
                eventClone.querySelector('#eventLocation').value = event.location;
                eventClone.querySelector('#eventDate').value = event.startDate;
                eventClone.querySelector('#eventCost').value = event.cost;
                eventClone.querySelector('#eventRemark').value = event.remark;

                // 插入 DOM
                eventsContainer.appendChild(eventClone);

                // 在表單插入到DOM之后绑定事件
                const editEventForm = document.getElementById('editEventForm');
                editEventForm.addEventListener('submit', function(event) {
                    event.preventDefault(); 
                    const storedEventId = localStorage.getItem('eventId');

                    if (!storedEventId) {
                        alert('無法獲得活動ID');
                        return;
                    }

                    const formData = {
                        id: storedEventId,
                        name: document.getElementById('eventName').value,
                        description: document.getElementById('eventDescription').value,
                        location: document.getElementById('eventLocation').value,
                        startDate: document.getElementById('eventDate').value,
                        cost: parseInt(document.getElementById('eventCost').value, 10),
                        remark: document.getElementById('eventRemark').value
                    };

                    fetch(`/api/events/update/${storedEventId}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(formData)
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Success:', data);
                        alert('事件已更新成功！');
                        // 成功后清除 eventId
                        localStorage.removeItem('eventId');
                        // 更新 URL，保留 eventId
                        history.replaceState(null, '', `?id=${storedEventId}`);
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                        alert('更新事件失敗！');
                    });
                });
            })
            .catch(error => {
                console.error('Error fetching event details:', error);
                eventsContainer.innerHTML = '<p>無法加載活動訊息。</p>';
            });
    } else {
        eventsContainer.innerHTML = '<p>未指定活動ID。</p>';
    }

    // Handle navigation links
    const links = {
        orgViewAllEventsLink: '/viewAllEvents',
        managePaymentLink: '/viewAllEvents',
        orgEditEventLink: '/orgEditEvent',
        orgVerifyVolunteerLink: '/orgVerifyVolunteer',
        orgAddEventLink: '/orgAddEvent',
        managePaymentsLink: '/managePayments',
        eventVolunLink: '/eventVolun',
        discussSecLink: '/discussSec',
        loginLink: '/login',
        userhomeLink: '/userhomePage',
        orgProfileLink: '/orgProfile',
        profileLink: '/volunteerProfile'
    };

    Object.keys(links).forEach(id => {
        const linkElement = document.getElementById(id);
        if (linkElement) {
            linkElement.addEventListener('click', function (event) {
                event.preventDefault();
                window.location.href = links[id];
            });
        }
    });

    document.querySelector('.btn-primary').addEventListener('click', function () {
        localStorage.setItem('eventId', eventId);
        window.location.href = `/orgEditEvent`;
    });
});

function resetForm() {
    const form = document.getElementById('editEventForm');
    if (form) {
        form.reset();
    }
}




