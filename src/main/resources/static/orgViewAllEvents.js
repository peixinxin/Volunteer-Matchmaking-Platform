document.addEventListener("DOMContentLoaded", function() {
    function fetchEvents() {
        var userId = localStorage.getItem("userId"); // 從 localStorage 獲取 userId
        alert(userId)
        if (!userId) {
            console.error('No userId found in localStorage.');
            return;
        }

        fetch(`/api/events/byhost/${userId}`)
            .then(response => response.json())
            .then(events => {
                const eventsContainer = document.getElementById('eventsContainer');
                const template = document.getElementById('eventTemplate').content;

                events.forEach(event => {
                    const eventClone = document.importNode(template, true);
                    eventClone.querySelector('.card-title').textContent = event.name;
                    eventClone.querySelector('.card-text').textContent = event.description;
                    eventClone.querySelector('.text-muted').textContent = event.startDate;
                    eventClone.querySelector('.btn-primary').href = `/orgEventDetail?id=${event.id}`;
                    eventsContainer.appendChild(eventClone);
                });
            })
            .catch(error => {
                console.error('Error loading the events:', error);
            });
    }

    fetchEvents();

    // Setup navigation links
    const links = {
        userhomeLink: "/userhome",
        orgViewAllEventsLink: "/viewAllEvents",
        orgEditEventLink: "/orgEditEvent",
        orgVerifyVolunteerLink: "/orgVerifyVolunteer",
        orgAddEventLink: "/orgAddEvent",
        orgProfileLink: "/orgProfile",
        eventDetailLink1: "/orgEventDetail",
        eventDetailLink2: "/orgEventDetail", 
        eventDetailLink3: "/orgEventDetail",
        viewAllEventsLink: "/viewAllEvents",
        editEventLink: "/orgEditEvent",
        verifyVolunteerLink: "/orgVerifyVolunteer",
        addEventLink: "/orgAddEvent",
        managePaymentLink:'/viewAllEvents'
        
    };

    // Assign click events to navigation links
    Object.keys(links).forEach(function(key) {
        const element = document.getElementById(key);
        if (element) {
            element.addEventListener('click', function(event) {
                event.preventDefault();  // Prevent default link behavior
                window.location.href = links[key];  // Navigate programmatically
            });
        }
    });

    // Update user profile link with username from localStorage
    var username = localStorage.getItem("name");
    if (username) {
        const profileLink = document.getElementById("orgProfileLink");
        if (profileLink) profileLink.textContent = username;
    }
});



