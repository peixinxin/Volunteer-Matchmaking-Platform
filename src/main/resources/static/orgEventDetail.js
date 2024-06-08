document.addEventListener('DOMContentLoaded', function () {
    // Retrieve and display the username from localStorage
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("orgProfileLink").textContent = username;
    }

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
                eventsContainer.appendChild(eventClone);
            })
            .catch(error => {
                console.error('Error fetching event details:', error);
                document.getElementById('eventDetailsCard').innerHTML = '<p>無法加載活動詳細信息。</p>';
            });
    } else {
        document.getElementById('eventDetailsCard').innerHTML = '<p>未指定活動ID。</p>';
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
        window.location.href = `/orgEditEvent?id=${eventId}`;
    });

    // Delete event
    const deleteButton = document.getElementById('deleteEventButton');
    if (deleteButton) {
        deleteButton.addEventListener('click', function () {
            if (confirm('確定要刪除這個活動嗎？')) {
                fetch(`/api/events/${eventId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    alert('活動已刪除');
                    window.location.href = '/viewAllEvents';
                })
                .catch(error => {
                    console.error('Error deleting event:', error);
                    alert('無法刪除活動。');
                });
            }
        });
    }
});


