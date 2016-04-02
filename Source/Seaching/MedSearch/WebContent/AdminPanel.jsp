<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Google Map Template with Geocoded Address</title>
<link rel="stylesheet" href="css/admin.css" />
	<script type="text/javascript" src="js/admin.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>	<!-- Google Maps API -->
	<script>
	var map;	// Google map object
	
	// Initialize and display a google map
	function Init()
	{
		// Create a Google coordinate object for where to initially center the map
		var latlng = new google.maps.LatLng( 38.8951, -77.0367 );	// Washington, DC
		
		// Map options for how to display the Google map
		var mapOptions = { zoom: 12, center: latlng  };
		
		// Show the Google map in the div with the attribute id 'map-canvas'.
		map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	}
	
	// Update the Google map for the user's inputted address
	function UpdateMap(object)
	{
		var geocoder = new google.maps.Geocoder();    // instantiate a geocoder object
		
		// Get the user's inputted address
		 var address = document.getElementById( "address" ).value;
		
	
		// Make asynchronous call to Google geocoding API
		geocoder.geocode( { 'address': address }, function(results, status) {
			var addr_type = results[0].types[0];	// type of address inputted that was geocoded
			if ( status == google.maps.GeocoderStatus.OK ){ 
				
				//Our AJAX Code
				
				var request;
				if(window.XMLHttpRequest)
					{
					request=new XMLHttpRequest();
					}
				else
					{
					request=new ActiveXObject("Microsoft.XMLHttp");
					}
				request.open('GET','Insertdata?type='+document.getElementById("type").value+'&city='+document.getElementById("city").value+'&address='+document.getElementById("address").value);
				request.send();
				
				//
				
				ShowLocation( results[0].geometry.location, address, addr_type );
			}
			else     
				alert("Geocode was not successful for the following reason: " + status);        
		});
	}
	
	// Show the location (address) on the map.
	function ShowLocation( latlng, address, addr_type )
	{
		// Center the map at the specified location
		map.setCenter( latlng );
		
		// Set the zoom level according to the address level of detail the user specified
		var zoom = 12;
		switch ( addr_type )
		{
		case "administrative_area_level_1"	: zoom = 6; break;		// user specified a state
		case "locality"						: zoom = 10; break;		// user specified a city/town
		case "street_address"				: zoom = 15; break;		// user specified a street address
		}
		map.setZoom( zoom );
		
		// Place a Google Marker at the same location as the map center 
		// When you hover over the marker, it will display the title
		var marker = new google.maps.Marker( { 
			position: latlng,     
			map: map,      
			title: address
		});
		
		// Create an InfoWindow for the marker
		var contentString = "" + address + "";	// HTML text to display in the InfoWindow
		var infowindow = new google.maps.InfoWindow( { content: contentString } );
		
		// Set event to display the InfoWindow anchored to the marker when the marker is clicked.
		google.maps.event.addListener( marker, 'click', function() { infowindow.open( map, marker ); });
	}
	
	// Call the method 'Init()' to display the google map when the web page is displayed ( load event )
	google.maps.event.addDomListener( window, 'load', Init );

	</script>
	<style>
	/* style settings for Google map */
	#map-canvas
	{
		width : 500px; 	/* map width */
		height: 500px;	/* map height */
		left : 310pt;
	}

	.txtbx
	{
		width:200pt;
		height:20pt;
		border:gray 2px solid;
		border-radius:5px;
	}
	.btn
	{
		width:70pt;
		height:25pt;
		font-size:18px;
		font-family:arial;
		background-color:silver;
		color: black;
		border-radius:10px;
		cursor:pointer;
	}
	.btn:hover
	{
		width:70pt;
		height:25pt;
		font-size:18px;
		font-family:arial;
		background-color:gray;
		color: white;
		border-radius:10px;
		cursor:pointer;
	}
	#bt
	{
		width:150pt;
		height:30pt;
		font-size:15px;
		background-color:black;
		color:white;
		cursor:pointer;
	}
	#bt:hover
	{
		width:150pt;
		height:30pt;
		font-size:18px;
		background-color:black;
		color:white;
		cursor:pointer;
	}
	</style>
</head>
<body style="text-align:center" onload="filldropdown();"> 
<%
String username=(String)session.getAttribute("username");
out.print("Welcome "+username.toUpperCase());
%>

	<!-- Display Google map here -->
	 <div id='map-canvas' > </div> <br>
		<table align="center" width="100%" cellspacing="50">
		  <tr>
		    <td style="border:solid gray 2px; border-radius:10px">
              <table align=center cellspacing=5>
   			   <tr>
    			<td id=lcn colspan=2 align=center>
			     <label for="location">
				LOCATION
			     </label>
			    </td>
			   </tr>
			   <tr>
			    <td>
				Select
			    </td>
    <td align=center>
	<select name="type" id="type" class="txtbx" >
	<option value="Hospital">----Select Type----</option>
	<option value="Hospital">Hospital</option>
	<option value="Ngo">NGO</option>
	</select>
    </td>
   </tr>
   <tr>
    <td>
	City
    </td>
    <td align=center>
	<input type="text" id="city" name="city" class="txtbx" />
    </td>
   </tr>
   <tr>
    <td>
	Address
    </td>
    <td align=center>
	<input type="text" id="address" name="address" class="txtbx" />
    </td>
   </tr>
   
   <tr>
    <td colspan=2 align=center>
	<button onclick="UpdateMap()" class="btn" >
		Pin
	</button>
    </td>
   </tr>
  </table>
	
	
	<td style="border:solid gray 2px; border-radius:10px">
		<table align=center cellspacing=5 >
				   <tr>
		    <td id=lcn colspan=2 align=center>
		     <label for="location">
			Feed The Treatment from here
		     </label>
		    </td>
  		 </tr>
		<tr>
			<td><input type="text" id="treat" name="treat" class="txtbx" /></td>
		</tr>
		   <tr>
    	<td colspan=2 align=center>
			<button onclick="insertTreatment()" class="btn" >
			Feed
			</button>
   		 </td>
   		</tr>
	</table>
	
	</td><td style="border:solid gray 2px; border-radius:10px">
	<table align=center cellspacing=5>
					   <tr>
		    <td id=lcn colspan=2 align=center>
		     <label for="location">
			Map the treatment to Hospital
		     </label>
		    </td>
  		 </tr>
		<tr><td>
			<select name="type" id="location" class="txtbx" onchange="alreadyMapped();">
			</select></td>
		</tr>
		<tr><td>
			Already mapped to following treatments:<br>
			<select name="type" id="mappedto" class="txtbx" >
			</select></td>
		</tr>
		<tr>
			<td>
			New mapping:<br>
			<select name="type" id="treatment" class="txtbx" >
			</select></td>
		</tr>
		   <tr>
    	<td colspan=2 align=center>
			<button onclick="locationMapping()" class="btn" >
			Mapping
			</button>
   		 </td>
   		</tr>
	</table></td></tr></table>
		
 <div style="top:20pt; left:800pt; width: 70pt; height:25; background-color:silver; text-align:center; position:absolute" >  <a href="Logout" style="color:black; text-decoration:none; font-family:verdana; font-size:15pt" >Logout</a></div>
 <div style="top: 630pt; left:0pt; width:1022pt; height:60pt; text-align:center; position:absolute">
 <button id="bt" onclick="updateAutoComplete()" value="Update AutoComplete">Update AutoComplete</button> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
 <button id="bt" onclick="getFrequency()" value="Get Searches">Get Frequency</button> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
 <button id="bt" onclick="clearFrequency()" value="Clear Frequency">Clear Frequency</button>
 </div>
</body>
</html>