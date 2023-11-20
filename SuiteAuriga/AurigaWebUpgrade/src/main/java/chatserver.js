/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// scaricare http://nodejs.org/dist/v6.11.4/node-v6.11.4-linux-x64.tar.gz
// copiare il tar.gz in /tmp e da /usr/local lanciare il comando: tar --strip-components 1 -xzf /tmp/node-v*.tar.gz
// npm config set proxy http://<username>:<password>@<proxy-server-url>:<port>
// npm config set https-proxy http://<username>:<password>@<proxy-server-url>:<port>
// npm install -g sockjs (da /usr/local)
// npm install -g request (da /usr/loca)
// avviare il server con il comando: node chatserver.js

var http = require('http'), sockjs = require('sockjs'), request = require('request'),
		   server = http.createServer(), webSockets, buffer = [], connections = {}, usernames = {};
var applicationUrl = 'http://localhost:8080/AurigaWeb/';
console.log(applicationUrl);

function whisper(id, message) {
	if (!connections[id])
		return;
	connections[id].write(JSON.stringify(message));
}

function broadcast(message, exclude) {
	for (var i in connections) {
		if (i != exclude)
			connections[i].write(JSON.stringify(message));
	}
}

function getUsers() {
	var users = [];
	for (var i in usernames) {
		users.push({ username: i, connid: usernames[i] });
	}
	return users;
}

function getUsernameByConn(conn) {
	for (var i in usernames) {
		if (usernames[i] == conn.id)
			return i;
	}
}

function onConnection(conn) {
	connections[conn.id] = conn;
	buffer = [];
	request.get(applicationUrl + 'springdispatcher/chat/getHistory', function (error, response, body) {
		body = JSON.parse(body);
		whisper(conn.id, {
	    	type : 'init',
	    	users : getUsers(),
	    	history : body.history,
	    	id : conn.id
	    });		
	});	
	conn.on('data', function onData(data) {
		data = JSON.parse(data);					
		if (!usernames[data.username] && data.type == 'login') {
			usernames[data.username] = conn.id;
			broadcast({
				type : 'newUser',
				username : data.username,
				users : getUsers()
			}, conn.id);			
		} else { 
			if (!usernames[data.username]) 
				return
			else if (usernames[data.username] != conn.id) {
				whisper({
					type : 'error',
					error : 'The user ' + data.username + ' already has joined'
				}, conn.id);	
				return;			
			}
			if (data.type == 'sendMessage') {
				if (!data.message)
					return;
				data.message = data.message.substr(0, 128);
				//if (buffer.length > 15) buffer.shift();
				buffer.push(data.username + ': ' + data.message);
				request.post(applicationUrl + 'springdispatcher/chat/sendMessage', {form:{ username: data.username, messaggio: data.message }}, function (error, response, body) {
				    if (!error && response.statusCode == 200) {
				    	broadcast({
							type : 'message',
							username : data.username,
							message : data.message,
							id : conn.id
						});
				    }
				});			
			}
		}
	});
	conn.on('close', function onClose() {
		username = getUsernameByConn(conn);
		delete connections[conn.id];
		delete usernames[username];		
		broadcast({
			type : 'userLeft',
			username : username,
			users : getUsers()
		}, conn.id);				
	});

}

webSockets = sockjs.createServer();
webSockets.on('connection', onConnection);

webSockets.installHandlers(server, {
	prefix : '/chat'
});
server.listen(9999, '0.0.0.0');