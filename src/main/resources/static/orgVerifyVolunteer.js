document.addEventListener("DOMContentLoaded", function() {
    var username = localStorage.getItem("name");
    if (username) {
        document.getElementById("orgProfileLink").textContent = username;
    }
    const organizationId = localStorage.getItem("userId");
    alert(organizationId)
    if (organizationId) {
        fetch(`/api/registration/fororgcheck?organizationId=${organizationId}`)
            .then(response => response.json())
            .then(data => {
                
                const eventsContainer = document.getElementById('applicantsContainer');
                const template = document.getElementById('applicantsTemplate').content;

                data.forEach(applicant => {
                    if (applicant.auditStatus == '審核中') {
                        const eventClone = document.importNode(template, true);
                        eventClone.querySelector('.card-title').textContent = `志工編號: ${applicant.volunteerId}`;
                        eventClone.querySelector('.card-text').textContent = `活動編號: ${applicant.eventId}`;
                        
                        let auditStatusText = applicant.auditStatus === '審核中' ? '未審核' : applicant.auditStatus;
                        eventClone.querySelector('.text-muted').textContent = `審核狀態: ${auditStatusText}`;
                        eventClone.querySelector('.btn-primary').href = `/orgVolunteerDetail?id=${applicant.volunteerId}&regisId=${applicant.registrationId}`;

                        eventsContainer.appendChild(eventClone);
                    }
                });
            
            })
            .catch(error => console.error('Error:', error));
    } else {
        console.error('Organization ID not found in localStorage');
    }
    document.getElementById('orgProfileLink').addEventListener('click', function () {
        window.location.href = '/orgProfile';
    });

});


function goBack() {
    window.location.href = '/viewAllEvents';
}
