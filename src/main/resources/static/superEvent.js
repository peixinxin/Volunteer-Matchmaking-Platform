document.addEventListener('DOMContentLoaded', function () {
    //添加的...
    function fetchEvents() {
        fetch('/api/supervisor/superviseEvents')
            .then(response => response.json())
            .then(events => {
                const eventsContainer = document.getElementById("superContainer");
                const template = document.getElementById("superTemplate").content;

                events.forEach(event => {
                    const eventClone = document.importNode(template, true);
                    eventClone.querySelector('.card-title').textContent = event.name;
                    eventClone.querySelector('.card-text').textContent = event.description;
                    eventClone.querySelector('.text-muted').textContent =event.startDate;
                    eventClone.querySelector('.btn').href = `/superEventVerify?id=${event.id}`;
                    eventsContainer.appendChild(eventClone);
                });
            })
            .catch(error => {
                console.error('Error loading the events:', error);
                document.getElementById('eventsContainer').innerHTML = '<p>Error loading events.</p>';

            });
    }

    fetchEvents();
        
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("superProfileLink").textContent = username;
    }
    
    
    document.getElementById("userhomeLink").addEventListener("click", function() {
        window.location.href = "/userhome";
    });
    
    document.getElementById("superEventLink").addEventListener("click", function() {
        window.location.href = "/supervisorhome";
    });
    
    document.getElementById("superProfileLink").addEventListener("click", function() {
        window.location.href = "/superProfile";
    });
    
    document.getElementById("superEventVerifyLink").addEventListener("click", function() {
        window.location.href = "/superEventVerify";
    });


    
});
    

