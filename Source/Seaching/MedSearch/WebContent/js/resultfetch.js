//This JS file is responsible for fetching the results from the server.


function searcher(){
	var page=1;
	getInfo(page);
	getHospital(page);
	getNgo(page);
}

function getInfo(page){
	var requestInfo;
	var divarray = ["c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10"];
	if(window.XMLHttpRequest) requestInfo = new XMLHttpRequest();
	else requestInfo = new ActiveXObject("Microsoft.XMLHttp");

		requestInfo.open('GET', 'SearchInput?query='+document.getElementById("query").value+"&type=1&page="+page, true);
		requestInfo.send();
		requestInfo.onreadystatechange = function(){
			if(requestInfo.readyState==4 && requestInfo.status==200){
				var results = requestInfo.responseXML.getElementsByTagName("RESULTS")[0];
				var limit = results.childElementCount;
			for(var index=1;index<=limit;index++){
				var result = results.getElementsByTagName("RESULT")[index-1];
				var title = result.getElementsByTagName("TITLE")[0].childNodes[0].nodeValue;
				var info = result.getElementsByTagName("INFO")[0].childNodes[0].nodeValue;
				var link = result.getElementsByTagName("LINK")[0].childNodes[0].nodeValue.replace(/amp;/g,'&');
				document.getElementById(divarray[index-1]).innerHTML = "<h3>"+title+"</h3><h4>"+info+"<br><a href="+link+">"+link+"</a></h4><br>";   
			}
			if(limit<10){
				document.getElementById(divarray[limit]).innerHTML = "<pre><h3>END OF RESULT</h3></pre>";
				for(index=limit+1;index<10;index++) document.getElementById(divarray[index]).innerHTML = "";  
			}
		}
	};
}

function getHospital(page){
	var requestHosp;
	var divarray = ["c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20"];
	if(window.XMLHttpRequest) requestHosp = new XMLHttpRequest();
	else requestHosp = new ActiveXObject("Microsoft.XMLHttp");

		requestHosp.open('GET', 'SearchInput?query='+document.getElementById("query").value+"&type=3&page="+page, true);
		requestHosp.send();
		requestHosp.onreadystatechange = function(){
			if(requestHosp.readyState==4 && requestHosp.status==200){
				var results = requestHosp.responseXML.getElementsByTagName("RESULTS")[0];
				var limit = results.childElementCount;
			for(var index=1;index<=limit;index++){
				var result = results.getElementsByTagName("RESULT")[index-1];
				var title = result.getElementsByTagName("TITLE")[0].childNodes[0].nodeValue;
				var info = result.getElementsByTagName("INFO")[0].childNodes[0].nodeValue;
				var link = result.getElementsByTagName("LINK")[0].childNodes[0].nodeValue.replace(/amp;/g,'&');
				document.getElementById(divarray[index-1]).innerHTML = "<h3>"+title+"</h3><h4>"+info+"<br><a href="+link+">"+link+"</a></h4><br>";   
			}
			if(limit<10){
				document.getElementById(divarray[limit]).innerHTML = "<pre><h3>END OF RESULT</h3></pre>";
				for(index=limit+1;index<10;index++) document.getElementById(divarray[index]).innerHTML = "";  
			}
		}
	};
}

function getNgo(page){
	var requestNgo;
	var divarray = ["c21", "c22", "c23", "c24", "c25", "c26", "c27", "c28", "c29", "c30"];
	if(window.XMLHttpRequest) requestNgo = new XMLHttpRequest();
	else requestNgo = new ActiveXObject("Microsoft.XMLHttp");

		requestNgo.open('GET', 'SearchInput?query='+document.getElementById("query").value+"&type=2&page="+page, true);
		requestNgo.send();
		requestNgo.onreadystatechange = function(){
			if(requestNgo.readyState==4 && requestNgo.status==200){
				var results = requestNgo.responseXML.getElementsByTagName("RESULTS")[0];
				var limit = results.childElementCount;
			for(var index=1;index<=limit;index++){
				var result = results.getElementsByTagName("RESULT")[index-1];
				var title = result.getElementsByTagName("TITLE")[0].childNodes[0].nodeValue;
				var info = result.getElementsByTagName("INFO")[0].childNodes[0].nodeValue;
				var link = result.getElementsByTagName("LINK")[0].childNodes[0].nodeValue.replace(/amp;/g,'&');
				document.getElementById(divarray[index-1]).innerHTML = "<h3>"+title+"</h3><h4>"+info+"<br><a href="+link+">"+link+"</a></h4><br>";   
			}
			if(limit<10){
				document.getElementById(divarray[limit]).innerHTML = "<pre><h3>END OF RESULT</h3></pre>";
				for(index=limit+1;index<10;index++) document.getElementById(divarray[index]).innerHTML = "";  
			}
		}
	};
}

function instant(event){
	var key = event.keyCode?event.keyCode:event.which; ;
	if(key==32 || key==13) {
		searcher();
		updatefreq();
	}
}

function nextpage(){
	var tab = document.getElementById("tab").innerHTML;
	var page = parseInt(document.getElementById(tab).innerHTML);
	if(tab == "hometab") {
		var last = document.getElementById("c10").innerHTML;
		if(last!=""){
			getInfo(page+1);
			document.getElementById(tab).innerHTML=page+1;
		}
	}
	if(tab == "hosptab") {
		var last = document.getElementById("c20").innerHTML;
		if(last!=""){
			getHospital(page+1);
			document.getElementById(tab).innerHTML=page+1;
		}
	}
	if(tab == "ngotab") {
		var last = document.getElementById("c10").innerHTML;
		if(last!=""){
			getNgo(page+1);
			document.getElementById(tab).innerHTML=page+1;
		}
	}
}

function prevpage(){
	var tab = document.getElementById("tab").innerHTML;
	var page = parseInt(document.getElementById(tab).innerHTML);
	if(page>1){
		if(tab == "hometab") getInfo(page-1);
		if(tab == "hosptab") getHospital(page-1);
		if(tab == "ngotab") getNgo(page-1);
		document.getElementById(tab).innerHTML=page-1;
	}
}