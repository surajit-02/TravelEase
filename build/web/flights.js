const searchForm = document.getElementById('flightSearchForm');
const resultsContainer = document.getElementById('resultsContainer');

searchForm.addEventListener('submit', function(e) {
  e.preventDefault();

  // Show loading message
  resultsContainer.innerHTML = '<div class="loading">Searching flights...</div>';

  // Get form data
  const formData = new FormData(searchForm);
  const params = new URLSearchParams();
  params.append('source', formData.get('source'));
  params.append('destination', formData.get('destination'));

  // Call servlet
  fetch('ShowFlights?' + params, { method: 'GET' })
    .then(response => response.text())
    .then(data => {
      // Show flights returned from servlet
      resultsContainer.innerHTML = data;
    })
    .catch(error => {
      resultsContainer.innerHTML = '<div class="error">Error fetching flights. Please try again.</div>';
      console.error('Error:', error);
    });
});
