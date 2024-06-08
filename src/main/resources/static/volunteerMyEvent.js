let username = localStorage.getItem('name');
let userType = localStorage.getItem('userType');
let account = localStorage.getItem('account');
let userId = localStorage.getItem('userId');
console.log(username, userType, account, userId);

document.getElementById("eventVolunLink").addEventListener("click", function() {
    window.location.href = "/eventVolun";
  });
  
document.getElementById("discussSecLink").addEventListener("click", function() {
    window.location.href = "/discussSec";
  });
  
document.getElementById("logoutLink").addEventListener("click", function() {
    localStorage.clear();
    window.location.href = "/login";
  });  
  


// Retrieve and display the username from localStorage
if (username) {
  document.getElementById("profileLink").textContent = username;
}

document.getElementById("profileLink").addEventListener("click", function() {
  window.location.href = "/volunteerProfile";
});

document.addEventListener("DOMContentLoaded", function() {
  const volunteerId = localStorage.getItem("userId");

  fetch(`/api/events/volunteer/${volunteerId}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
    })
    .then(registrations => {
      const registrationsContainer = document.getElementById('registrationsContainer');
      const template = document.getElementById('registrationTemplate').content;

      if (registrations.length === 0) {
        registrationsContainer.innerHTML = "<p>沒有活動資料</p>";
      } else {
        registrations.forEach(registration => {
          const regEventId = registration.eventId;
          const eventClone = document.importNode(template, true);
          const button = eventClone.querySelector('.btn');
          button.dataset.status = registration.auditStatus;

          switch (registration.auditStatus) {
            case '審核通過':
              button.className = 'btn btn-success';
              button.textContent = '審核通過';
              break;
            case '審核中':
              button.className = 'btn btn-secondary';
              button.textContent = '審核中';
              break;
            case '審核失敗':
              button.className = 'btn btn-warning';
              button.textContent = '審核失敗';
              break;
          }
          button.href = `/volEventDetail?id=${regEventId}`;
          // 在此處進行另一個 fetch 請求
          fetch(`/api/events/regEvent/${regEventId}`)
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
              }
              return response.json();
            })
            .then(event => {
              // 更新 eventClone 以顯示更多 eventDetails 的信息
              eventClone.querySelector('.card-title').textContent = event.name;
              eventClone.querySelector('.card-text').textContent = event.description;
              eventClone.querySelector('.text-muted').textContent = event.startDate;

              registrationsContainer.appendChild(eventClone); // 將克隆的活動卡片添加到容器中
            })
            .catch(error => {
              console.error('Error loading the events:', error);
            });
        });
      }
      console.log('API response:', registrations);
    })
    .catch(error => {
      console.error('Error loading the events:', error);
    });


});