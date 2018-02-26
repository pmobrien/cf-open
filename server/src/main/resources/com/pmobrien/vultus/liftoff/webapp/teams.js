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
          
          console.log($(containerId));
          
          $(containerId).append(
              '<button id="athlete-' + athlete.competitorId + '" type="button" draggable="true" class="btn btn-info btn-block athlete-button" ondragstart="drag(event)" ondrop="return false" ondragover="return false">' + athlete.competitorName + '</button>'
          );
        }
      }
  );
}

$(document).ready(function() {  
  getAthletes();
});
