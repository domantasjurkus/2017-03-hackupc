<<<<<<< HEAD
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

// Code goes here
=======
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

// Backend - would be good to split this logic
// into two files in order to avoid conflicts

// To simplify everything - store app data
// in a JSON for now
data = {
	users: [
		'Steve': {
			score: 0,
			isAlerted: false,
			lat: null,
			lng: null
		}
	]
}

// Handle socket stuff (everything real-time related)
io.on('connection', function(socket) {

	// console.log('Connected: ', socket.id);
	//socket.emit('initial-draw', players);

	// TODO create new user
	socket.on('login', function(rawdata) {
		console.log("login rawdata:", rawdata);

		if (typeof rawdata == 'object') data = rawdata;
		else data = JSON.parse(rawdata);

		data.users[socket.user] = {
			score: 0,
			isAlerted: false,
			lat: null,
			lng: null
		};

		// May be needed, not sure
		/*socket.emit('logged-in', socket.id);
		socket.emit('initial-draw', players);
		var p = players[socket.id];
		p.id = socket.id;
		console.log(p);
		socket.broadcast.emit('additional-draw', p);*/
	});
	
	// TOFIX update player's position on the map
	socket.on('update-player', function(rawdata) {

		if (typeof rawdata == 'object') data = rawdata;
		else data = JSON.parse(rawdata);

		data.users[data.user].lat = data.lat;
		data.users[data.user].lng = data.lng;
	});



	// TODO delete user on socket disconnect
	socket.on('disconnect', function() {
		console.log('socket::disconnect:');
		console.log(socket.user);
		
		// TODO
		// delete data.users[socket.user];

		socket.broadcast.emit('user-disconnected', socket.user);
	});
	
	//to send socket to specific person
	//io.to(socketid).emit('message', 'for your eyes only');

});
>>>>>>> de2a8a861b93fdf568f1a5862f5bc55ceed95d80
