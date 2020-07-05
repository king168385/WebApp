// Call the dataTables jQuery plugin
$(document).ready(function() {
  var table = $('#dataTable').DataTable({
	  cache: true,
	  buttons: [ 'excel']
  });

});
