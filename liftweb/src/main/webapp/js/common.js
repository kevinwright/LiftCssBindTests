function getUrlVars() {
  var vars = [], hash;
  var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
  for(var i = 0; i < hashes.length; i++) {
    hash = hashes[i].split('=');
    vars.push(hash[0]);
    vars[hash[0]] = hash[1];
  }
  return vars;
}

$(function() {
    $( "#selectReportAccordion" ).accordion();
});

var sipPos = 0;

jQuery(document).ready(function(){
  $('.accordion .head').click(function() {
    $(this).next().toggle('slow');
    return false;
  }).next().hide();

  $("#reports-panel-tab").click(function(e) {
    e.preventDefault();
    $("#reports-panel").animate({ top: sipPos }, 800, 'linear', function() {
        if(sipPos == 0) { sipPos = -400; }
        else { sipPos = 0; }
    });
  });
});
