// Load the Visualization API and the ready-made Google table visualization.
google.load('visualization', '1', {'packages':['table,piechart,orgchart,barchart,motionchart']});

// Set a callback to run when the API is loaded.
google.setOnLoadCallback(init);

// Send the queries to the data sources.
function init() {
  var sourceUrl = document.getElementById("jsonDataLink").href;
  var query = new google.visualization.Query(sourceUrl);
  query.send(handleSqlResponse);
}

// Handle the simple data source query response
function handleSqlResponse(response) {
  if (response.isError()) {
    alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
    return;
  }

  var data = response.getDataTable();
  handleData(data);
}
