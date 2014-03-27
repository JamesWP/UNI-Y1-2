import sys
from ex3utils import Server
from sets import Set
import re

class MyServer(Server):

  def onStart(self):
    self.totalClients = 0
    self.clients = {}
    print "MyServer: started"
  
  def onMessage(self, socket, message):
    (command,sep,params) = message.strip().partition(' ')
    print "MyServer: client", self.clients[socket], "message received "
    print "command:", command, "\tparams:",params
    username = self.clients[socket].get('name','anon')
    if command == 'setname':
      self.clients[socket]['name'] = params.replace(':','')
    elif command == 'message':
      for eachsocket in self.clients:
        if eachsocket != socket:
          eachsocket.send("all " + username + ":" + params)
    elif command == 'pm':
      (name,sep,message) = params.partition(' ')
      regex = re.compile(name+'.*')
      for socket,info in self.clients.iteritems():
        pname = info.get('name','anon')
        if regex.match(pname):
          socket.send("pm " + username + ":" + message)
    elif command == 'list':
      regex = re.compile(params+'.*')
      if len(params)>0:
        socket.send("raw Clients matching " + params)
      else:
        socket.send("raw Clients")
      for eachsocket,info in self.clients.iteritems():
        name = info.get('name','anon')
        if regex.match(name):
          socket.send("raw > " + name)
    return True

  def onConnect(self, socket):
    self.clients[socket] = {}
    print "MyServer: client", self.clients[socket], "connected"
    print "Total clients:",len(self.clients)

  def onDisconnect(self, socket):
    del self.clients[socket]
    print "MyServer: client disconnected"
    print "Total clients:",len(self.clients)

ip = "localhost" # sys.argv[1]
port = 8090 #int(sys.argv[2])

server = MyServer()

server.start(ip, port)

print "Listening on ",ip,port,"..."

