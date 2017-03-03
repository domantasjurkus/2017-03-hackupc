var socket = io();
var socketId;
var data = {};

// Prevent reloading the page
$("form").submit(function(e) {
	$("#join").trigger("click");
	return false;
});

// Update fields
$('.updates').on('input', function() {
	var key = $(this).attr('name');
	data = {
		id: socketId,
		[key]: $(this).val()
	};
	socket.emit("update-player", data);
}); 

// Sending to the server
$("#join").click(function() {
	data.name = $("#name").val();
	data.lat = $("#lat").val();
	data.lng = $("#lng").val();
	data.angle = $("#angle").val();
	data ? socket.emit("login", data) : console.log("Enter data...");

	// playerMarkers = populatePlayer(socketId, playerMarkers);
});

$("#update").click(function() {
	var data = {
		id: socketId,
		lat: $("#lat").val(),
		lng: $("#lng").val(),
		angle: $("#angle").val()
	};
	console.log();
	socket.emit("update-player", data);
});

$("#fire").click(function() {
	socket.emit("fire");
});

$("#emp").click(function() {
	socket.emit("emp");
});

// When this user has logged in
socket.on("logged-in", function(id) {
	socketId = id;
	$("#display").append("<h4>Joined as ID "+id+"</h4>");
});

socket.on("initial-draw", function(players) {
	addInitialMarkers(players);
});

socket.on("additional-draw", function(player) {
	addAdditionalMarker(player);
});

socket.on("remove-marker", function(id) {
	removeMarker(id);
})

socket.on("update", function(players) {
	// console.log(data);
	// if (players[socketId])
	// 	console.log(players[socketId].health, players[socketId].deadFor);
	
	$("#players").html(" ");
	
	for (var key in players) {
		var p = players[key];

		// Show the player info in Godmode
		$("#players").append("<li>"+p.name+" exp: "+p.experience+"</li>");

		updateMarkers(players);
	}
	
});

socket.on("hit", function(name) {
	console.log("You hit "+name+"!");
});

socket.on("got-shot", function(name) {
	console.log(name, "shot you!");
	setRedIcon(socketId);
	window.setTimeout(setBlueIcon, 500);
});

socket.on("mark-as-shot", function(id) {
	setRedIcon(id);
	// window.setTimeout(setBlueIcon(id), 500);
});

socket.on("empd", function() {
	console.log("You got EMPd!");
});


// Keyboard controls
$(document).keydown(function(e) {
	var oldValue;
    switch(e.which) {

        case 37: // left
        oldValue = parseInt($('#angle').val());
        var newVal = oldValue -= 1
        if (newVal < 0) newVal += 360
        if (newVal > 360) newVal -= 360
        $('#angle').val(newVal);
        $('#angle').trigger('input');
        break;

        case 39: // right
        oldValue = parseInt($('#angle').val());
        var newVal = oldValue += 1
        if (newVal < 0) newVal += 360
        if (newVal > 360) newVal -= 360
        $('#angle').val(oldValue += 1);
        $('#angle').trigger('input');
        break;

        case 38: // up
        oldValue = parseFloat($('#lat').val());
        $('#lat').val(oldValue += 0.00005);
        $('#lat').trigger('input');
        break;

        case 40: // down
        oldValue = parseFloat($('#lat').val());
        $('#lat').val(oldValue -= 0.00005);
        $('#lat').trigger('input');
        break;

        case 65: // 'a' key
        oldValue = parseFloat($('#lng').val());
        $('#lng').val(oldValue -= 0.00005);
        $('#lng').trigger('input');
        break;

        case 68: // 'd' key
        oldValue = parseFloat($('#lng').val());
        $('#lng').val(oldValue += 0.00005);
        $('#lng').trigger('input');
        break;

        case 32: // space
        $('#fire').trigger('click');
        break;

        default: return; // exit this handler for other keys
    }
    e.preventDefault(); // prevent the default action (scroll / move caret)
});