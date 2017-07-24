var map;
function initmap() {
	// set up the map
	var winHeight = $( window ).height();
	$("#map").css("height",winHeight+"px")
	map = new L.Map('map');

	// create the tile layer with correct attribution
	var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
	var osmAttrib='Map data <a href="http://openstreetmap.org">OpenStreetMap</a> contributors';
	var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});		

	// start the map in South-East England
	map.setView(new L.LatLng(20.5, 78.9),4);
	map.addLayer(osm);
	console.log('ok')
}
function updateLocationsinglemarker(lat,lng,time){
	marker = L.marker([lat, lng]).addTo(map);
	marker.bindPopup("<b>Your Location at Time:</b><br>"+time).openPopup();
}

function updateLocation(lat,lng,time){
	//map.panTo(new L.LatLng(lat,lng));
	var marker = L.marker([lat, lng]).addTo(map);
	marker.bindPopup("<b>Your Location at Time:</b><br>"+time).openPopup();
}

function updateCamera(lat,lng,time){
	//map.panTo(new L.LatLng(lat,lng));
	map.setView(new L.LatLng(lat,lng),15);
}

$( document ).ready(function() {
    initmap();
});
