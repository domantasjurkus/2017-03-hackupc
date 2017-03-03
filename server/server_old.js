var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var _ = require('lodash');
// var L = require('leaflet');
// var r = require('leaflet-rotatedmarker');

//
//
// KEEP THIS FILE FOR REFERENCE
// Work on new stuff on server.js
//
//

// Game variables
var players = {};
var defaultHealth = 9;
var defaultExperience = 0;
var empdFor = 0;
var empDuration = 10;

// ngrok.exe http -subdomain=montd 3000

app.get('/', function(req, res) {
	res.sendFile(__dirname + '/views/index.html');
});

app.get('/map', function(req, res) {
	res.sendFile(__dirname + '/static/js/map.js');
})

app.get('/script', function(req, res) {
	res.sendFile(__dirname + '/static/js/script.js');
});

// Broadcast all player locations
setInterval(function() {
	// console.log("Trigger update: ", players);
	
	var clone = _.extend({}, players);
	io.emit('update', clone);

}, 500);

// Check for revival
setInterval(function() {

	for (var p in players) {
		p = players[p];

		// Respawn logic
		if (p.deadFor > 0) p.deadFor--;
		if (p.health==0 && p.deadFor==0) p.health = defaultHealth;

		// EMP logic
		if (empdFor > 0) {
			empdFor--;
			p.empd = true;
			console.log("EMP active for", empdFor);
		} else {
			p.empd = false;
		}
	}

}, 1000);


io.on('connection', function(socket) {

	// console.log('Connected: ', socket.id);
	socket.emit('initial-draw', players);

	socket.on('login', function(rawdata) {
		console.log("login rawdata:", rawdata);

		if    (typeof rawdata == 'object') data = rawdata;
		else  data = JSON.parse(rawdata);

		players[socket.id] = {
			name: data.name,
			lat: data.lat,
			lng: data.lng,
			angle: data.angle,
			health: defaultHealth,
			experience: defaultExperience,
			hasEmp: true,
			empd: false,
			deadFor: 0
		};

		// Send socket ID to sender
		socket.emit('logged-in', socket.id);
		socket.emit('initial-draw', players);
		var p = players[socket.id];
		p.id = socket.id;
		console.log(p);
		socket.broadcast.emit('additional-draw', p);
	});
	
	// Update player property
	socket.on('update-player', function(rawdata) {

		var data;
		if (typeof rawdata == 'object') {
			data = rawdata;
		} else {
			data = JSON.parse(rawdata);
		}

		_.assign(players[socket.id], data);
		// console.log("updating player ", socket.id+":", data);
	});

	socket.on('fire', function(id) {
		//console.log("FIRING");
		var self = players[socket.id];

		// Loop through all players
		for (var id in players) {
			if (id == socket.id) {
				// console.log("Found you, skipping...");
				continue;
			}

			var target = players[id];

			// If player is dead - skip
			if (target.health < 1) continue;			

			// Find angle between other players
			// Don't judge me, it's a hackathon ಠ_ಠ
			var xdiff = parseFloat(target.lng) - parseFloat(self.lng);
			var ydiff = parseFloat(target.lat) - parseFloat(self.lat);
			//console.log("xdiff: ", xdiff);
			//console.log("ydiff: ", ydiff);
			var azimuth = Math.atan2(xdiff, ydiff)*180/Math.PI;
			if (azimuth < 0) azimuth += 360;

			//console.log("Checking if", self.name, "hit", target.name);
			console.log("angle: ", self.angle, "azimuth: ", azimuth);

			// If the difference is small enough - shot!
			if (Math.abs(self.angle - azimuth) < 7) {
				console.log(target.name, "was shot");

				target.health -= 1;
				if (target.health < 1) {
					target.deadFor = 10;
				}

				self.experience += 5;

				// Send event to target
				socket.to(target.id).emit("got-shot", self.name);

				// Send event to marksman
				// socket.to(socket.id).emit("hit");
				io.sockets.connected[socket.id].emit('hit', target.name);

				// Send marker info to everyone
				io.emit('mark-as-shot', id);
			}

		}

		// socket.broadcast.emit("fire");
	});

	// If someone fires an EMP
	socket.on('emp', function() {
		
		// Safety checks
		if (!players[socket.id].hasEmp) return;
		if (empdFor > 0) return;

		players[socket.id].hasEmp = false;

		empdFor = empDuration;
		for (var key in players) {
			var p = players[key];
			p.empd = true;
		}

		io.emit('empd');
	});

	socket.on('disconnect', function() {
		console.log( (players[socket.id]) ? players[socket.id].name : socket.id, ' disconnected');
		
		// Remove from players array
		delete players[socket.id];

		socket.broadcast.emit('remove-marker', socket.id);
	});
	
	//to send socket to specific person
	//io.to(socketid).emit('message', 'for your eyes only');

});

port = 3000;
http.listen(port, function() {
	console.log('listening on *:'+port);
});

app.get('/blue', function(req, res) { res.sendFile(__dirname + '/blue.png'); });
app.get('/bluedir', function(req, res) { res.sendFile(__dirname + '/bluedir.png'); });
app.get('/red',  function(req, res) { res.sendFile(__dirname + '/red.png'); });
app.get('/gray', function(req, res) { res.sendFile(__dirname + '/gray.png'); });

app.get('/rotate', function(req, res) { res.sendFile(__dirname + '/node_modules/leaflet-rotatedmarker/leaflet.rotatedMarker.js'); });

