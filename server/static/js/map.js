var markerList = {};

var blueIcon = L.icon({
	iconUrl: '/blue',
	iconSize: [30, 30],
	iconAnchor: [15, 15]
});
var redIcon  = L.icon({
	iconUrl: '/red',
	iconSize: [30, 30],
	iconAnchor: [15, 15]
});
var grayIcon = L.icon({
	iconUrl: '/gray',
	iconSize: [30, 30],
	iconAnchor: [15, 15]
});

/* var blueSmallIcon = L.icon({ iconUrl: '/blue', iconSize: [8, 8], });
var redSmallIcon  = L.icon({ iconUrl: '/red',  iconSize: [8, 8] });
var graySmallIcon = L.icon({ iconUrl: '/gray', iconSize: [6, 6] }); */

function addInitialMarkers(players) {
	for (key in players) {
		var p = players[key];
		var options = {
			icon: blueIcon,
			rotationAngle: p.angle
		};
		markerList[key] = new L.marker([p.lat, p.lng], options).addTo(mapLeaflet);
		// markerList[key].setIcon(redIcon);
		// console.log(markerList[key]);
	}    
	mapLeaflet.scrollWheelZoom.disable();
}

function addAdditionalMarker(player) {
	// console.log(player.id);
	if (markerList[player.id]) return;
	var options = {
		icon: blueIcon
	};
	markerList[player.id] = new L.marker([player.lat, player.lng], options).addTo(mapLeaflet);
}

function updateMarkers(players) {
	for (key in players) {
		if (!markerList[key]) {
			console.log(key, "hasn't been added to the map - skipping...");
			continue;
		};
		var p = players[key];
		markerList[key].setLatLng(L.latLng(p.lat, p.lng));
		markerList[key].setRotationAngle(p.angle);

		if (p.health < 1) {
			markerList[key].setIcon(grayIcon)	
		} else {
		    markerList[key].setIcon(blueIcon);	
		}
	}
}

function setRedIcon(id) {
	if (!id) id = socketId;
	if (!markerList[id]) {
		console.log("cannot set red icon");
		return;
	};
	markerList[id].setIcon(redIcon);
}

function setBlueIcon(id) {
	if (!id) id = socketId;
	if (!markerList[id]) return;
	markerList[id].setIcon(blueIcon);
}

function removeMarker(id) {
	mapLeaflet.removeLayer(markerList[id]);
}