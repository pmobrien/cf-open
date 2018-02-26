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
