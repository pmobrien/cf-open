function allowDrop(event) {
  event.preventDefault();
}

function drag(event) {
  event.dataTransfer.setData("targetId", event.target.id);
}

function drop(event, element) {
  event.preventDefault();
  element.appendChild(document.getElementById(event.dataTransfer.getData("targetId")));
}

function getAthletes() {
  $.getJSON(
      '/api/athletes',
      {},
      function(athletes) {
        for(var i = 0; i < athletes.length; ++i) {
          var athlete = athletes[i];
          var containerId = athlete.team ? '#team-' + athlete.team.ordinal + '-container' : '#no-team-container';
          
          $(containerId).append(
              '<button id="athlete-' + athlete.competitorId + '" type="button" draggable="true" class="btn btn-info btn-block athlete-button" ondragstart="drag(event)" ondrop="return false" ondragover="return false">' + athlete.competitorName + '</button>'
          );
        }
      }
  );
}

function save() {
  var athletes = [];
  
  var buttons = $("#no-team-container :button");
  for(var i = 0; i < buttons.length; ++i) {
    var button = buttons[i];
    
    var athlete = {
      competitorId: button.id.substring('athlete-'.length, button.id.length),
      team: null
    };
    
    athletes.push(athlete);
  }
  
  var buttons = $("#team-0-container :button");
  for(var i = 0; i < buttons.length; ++i) {
    var button = buttons[i];
    
    var athlete = {
      competitorId: button.id.substring('athlete-'.length, button.id.length),
      team: {
        ordinal: 0
      }
    };
    
    athletes.push(athlete);
  }
  
  var buttons = $("#team-1-container :button");
  for(var i = 0; i < buttons.length; ++i) {
    var button = buttons[i];
    
    var athlete = {
      competitorId: button.id.substring('athlete-'.length, button.id.length),
      team: {
        ordinal: 1
      }
    };
    
    athletes.push(athlete);
  }
  
  var buttons = $("#team-2-container :button");
  for(var i = 0; i < buttons.length; ++i) {
    var button = buttons[i];
    
    var athlete = {
      competitorId: button.id.substring('athlete-'.length, button.id.length),
      team: {
        ordinal: 2
      }
    };
    
    athletes.push(athlete);
  }
  
  var buttons = $("#team-3-container :button");
  for(var i = 0; i < buttons.length; ++i) {
    var button = buttons[i];
    
    var athlete = {
      competitorId: button.id.substring('athlete-'.length, button.id.length),
      team: {
        ordinal: 3
      }
    };
    
    athletes.push(athlete);
  }
  
  $.ajax({
    type: 'PUT',
    contentType: 'application/json',
    url: '/api/athletes',
    data: JSON.stringify({
      athletes: athletes
    })
  });
}

$(document).ready(function() {  
  getAthletes();
});
