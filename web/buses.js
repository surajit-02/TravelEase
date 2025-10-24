document.getElementById("busSearchForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const source = document.getElementById("source").value;
  const destination = document.getElementById("destination").value;
  const response = await fetch(`ShowBuses?source=${source}&destination=${destination}`);
  const html = await response.text();

  document.getElementById("resultsContainer").innerHTML = html;
});
