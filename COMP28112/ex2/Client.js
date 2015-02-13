var Client = React.createClass({
  render:function(){
	  return (<div>Hi</div>);
  }   
});
React.render(<Client/>,document.getElementById('ClientMount'));

var bandAPI = 'http://jewel.cs.man.ac.uk:3020/queue/enqueue';
var username = 'jp2107';
var password = 'QgScXJ';

function request(APIurl,content,callback){
	var timedout = false;
	$.ajax({
		url:APIurl,
		type:'PUT',
		content:content,
		contentType:'application/xml',
		accepts: {xml: 'application/xml'},
		dataType:'xml',
		success:function(d){
			callback(false,d);
		},
		error:function(jqXHR, textStatus, errorThrown){
			debugger;
			callback(true,null);
		}
	});
}
var nextRequestID = Math.floor(Math.random()*9999999)+10000;
function createRequestID(){
	return nextRequestID++;
}

function createRequest(slotID){
	var requestID = createRequestID();
	return $('<reserve>')
		.append($('<requestid>').text(requestID))
		.append($('<username>').text(username))
		.append($('<password>').text(password))
		.append($('<slotid>').text(slotID)).appendTo($('<root>')).parent().html();
}


request(bandAPI,createRequest(167),function(err,d){
	debugger;
	console.log(err,d);
});