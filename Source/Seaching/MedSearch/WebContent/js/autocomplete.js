function updatefreq(){
	
	var query = document.getElementById('query').value;
	var words = query.split(" ");
	var length = words.length;
	
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	request.open('POST','UpdateFrequency?query='+words[length-1],true);
	request.send();
}


function autoComplete() {
	var request;
	if(window.XMLHttpRequest) request=new XMLHttpRequest();
	else request=new ActiveXObject("Microsoft.XMLHttp");
	
	request.open('GET','AutoComplete?string='+document.getElementById('query').value,true);
	request.send();
	request.onreadystatechange=function() {
	  if (request.readyState == 4 && (request.status == 200 || request.status == 0 && req.responseText)) {
		  var data=request.responseText;
		  document.getElementById("words").innerHTML=data;
	    }
	  } ;
}