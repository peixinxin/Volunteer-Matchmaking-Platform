
document.addEventListener("DOMContentLoaded", function() {
  // 初始載入所有活動
  const searchButton = document.getElementById("search");
  
  // 確認模板元素是否存在
  const firstTemplate = document.getElementById('firstEventTemplate');
  const eventTemplate = document.getElementById('eventTemplate');

  if (!firstTemplate || !eventTemplate) {
    console.error("Template elements not found");
    return;
  }
  
  fetch('/api/events/clickdesc')
    .then(response => response.json())
    .then(events => {
      console.log("Events data:", events);
      const eventsContainer = document.getElementById('eventsContainer');
      const firstTemplateContent = firstTemplate.content;
      const templateContent = eventTemplate.content;

      events.forEach((event, index) => {
        console.log("Processing event:", event);
        let eventClone;
        if (index === 0) {
          eventClone = document.importNode(firstTemplateContent, true);
        } else {
          eventClone = document.importNode(templateContent, true);
        }
        eventClone.querySelector('.text-muted').textContent = event.startDate;
        eventClone.querySelector('.card-title').textContent = event.name;
        eventClone.querySelector('.card-text').textContent = event.description;
        eventClone.querySelector('.btn-primary').href = `/volEventDetail?id=${event.id}`;
        eventsContainer.appendChild(eventClone);
      });
    })
    .catch(error => {
      console.error('Error loading the events:', error);
    });

  // 從localStorage中檢索並顯示用戶
  var username = localStorage.getItem('name');
  if (username) {
    document.getElementById("profileLink").textContent = username;
  }

  document.getElementById("profileLink").addEventListener("click", function() {
    window.location.href = "/volunteerProfile";
  });

  document.getElementById("discussSecLink").addEventListener("click", function() {
    window.location.href = "/discussSec";
  });

  document.getElementById("logoutLink").addEventListener("click", function() {
    localStorage.clear();
    window.location.href = "/login";
  });

  // 搜尋和篩選活動
  if (searchButton) {
    searchButton.addEventListener("click", function() {
      console.log("Search button clicked");

      const minFee = document.getElementById("min").value;
      const maxFee = document.getElementById("max").value;
      const startDate = document.getElementById("eventDateStart").value;
      const endDate = document.getElementById("eventDateEnd").value;
      const searchTerm = document.getElementById("searchTerm").value;

      let url = `/api/events/search?minFee=${minFee}&maxFee=${maxFee}`;
      if (startDate) {
        url += `&startDate=${startDate}`;
      }
      if (endDate) {
        url += `&endDate=${endDate}`;
      }
      if (searchTerm) {
        url += `&searchTerm=${searchTerm}`;
      }

      console.log("Request URL:", url);

      fetch(url)
        .then(response => response.json())
        .then(events => {
          const eventsContainer = document.getElementById('eventsContainer');
          eventsContainer.innerHTML = ''; // 清除當前的活動
          const firstTemplateContent = firstTemplate.content;
          const templateContent = eventTemplate.content;

          events.forEach((event, index) => {
            console.log("Processing event:", event);
            let eventClone;
            if (index === 0) {
              eventClone = document.importNode(firstTemplateContent, true);
            } else {
              eventClone = document.importNode(templateContent, true);
            }
            eventClone.querySelector('.text-muted').textContent = event.startDate;
            eventClone.querySelector('.card-title').textContent = event.name;
            eventClone.querySelector('.card-text').textContent = event.description;
            eventClone.querySelector('.btn-primary').href = `/volEventDetail?id=${event.id}`;
            eventsContainer.appendChild(eventClone);
          });
        })
        .catch(error => {
          console.error('Error loading the filtered events:', error);
        });
    });
  } else {
    console.error("Search button not found");
  }
});



  