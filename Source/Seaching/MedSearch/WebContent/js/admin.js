function updateAutoComplete(){
	var result = confirm("This will update the Autocomplete file..!! Proceed with it ???");
	if(result) {
		var request;
		if(window.XMLHttpRequest) request=new XMLHttpRequest();
		else request=new ActiveXObject("Microsoft.XMLHttp");
		
		request.open('GET','XMLWriter',true);
		request.send();
		request.onreadystatechange=function() {
		  if (request.readyState == 4 && request.status == 200) {
			  var data=request.responseText;
			  alert(data);
		    }
		  } ;
	}
}

function clearFrequency(){
	var result = confirm("This clear the frequencies in database... Proceed with it ???");
	if(result) {
		var request;
		if(window.XMLHttpRequest) request=new XMLHttpRequest();
		else request=new ActiveXObject("Microsoft.XMLHttp");
		
		request.open('GET','ClearFrequency',true);
		request.send();
		request.onreadystatechange=function() {
		  if (request.readyState == 4 && request.status == 200) {
			  var data=request.responseText;
			  alert(data);
		    }
		  } ;
	}
}

function getFrequency(){
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	
	request.open('GET','GetFrequency',true);
	request.send();
	request.onreadystatechange=function() {
	  if (request.readyState == 4 && request.status == 200) {
		  var data=request.responseText;
		  alert(data);
	    }
	  } ;
}


function insertTreatment(){
	var treatment_name = document.getElementById("treat").value;
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	
	request.open('GET','InsertTreatment?treat='+treatment_name,true);
	request.send();
	request.onreadystatechange=function() {
	  if (request.readyState == 4 && request.status == 200) {
		  var data=request.responseText;
		  alert(data);
	    }
	  } ;
}


function filldropdown(){
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
		
	request.open('GET','GetDropDown',true);
	request.send();
	request.onreadystatechange=function()
	{
		if(request.readyState==4 && request.status==200)
			{
			var data=request.responseText;
			var result = data.split("###");
			document.getElementById("location").innerHTML = result[0];
			document.getElementById("treatment").innerHTML = result[1];
			}
	};
}


function locationMapping(){
	var treatment_id = document.getElementById("treatment").value;
	var location_id = document.getElementById("location").value;
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	
	request.open('GET','LocationMapping?treatment_id='+treatment_id+'&location_id='+location_id,true);
	request.send();
	request.onreadystatechange=function() {
	  if (request.readyState == 4 && request.status == 200) {
		  var data=request.responseText;
		  alert(data);
	    }
	  } ;
}

function alreadyMapped(){
	var location_id = document.getElementById("location").value;
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	request.open('GET','AlreadyMapped?location_id='+location_id,true);
	request.send();
	request.onreadystatechange=function() {
	  if (request.readyState == 4 && request.status == 200) {
		  document.getElementById("mappedto").innerHTML=request.responseText;
	    }
	  } ;
}