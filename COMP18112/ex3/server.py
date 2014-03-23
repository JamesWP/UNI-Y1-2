"""

Network server skeleton.

This shows how you can create a server that listens on a given network socket, dealing
with incoming messages as and when they arrive. To start the server simply call its
start() method passing the IP address on which to listen (most likely 127.0.0.1) and 
the TCP port number (greater than 1024). The Server class should be subclassed here, 
implementing some or all of the following five events. 

  onStart(self)
      This is called when the server starts - i.e. shortly after the start() method is
      executed. Any server-wide variables should be created here.
      
  onStop(self)
      This is called just before the server stops, allowing you to clean up any server-
      wide variables you may still have set.
      
  onConnect(self, socket)
      This is called when a client starts a new connection with the server, with that
      connection's socket being provided as a parameter. You may store connection-
      specific variables directly in this socket object. You can do this as follows:
          socket.myNewVariableName = myNewVariableValue      
      e.g. to remember the time a specific connection was made you can store it thus:
          socket.connectionTime = time.time()
      Such connection-specific variables are then available in the following two
      events.

  onMessage(self, socket, message)
      This is called when a client sends a new-line delimited message to the server.
      The message paramater DOES NOT include the new-line character.

  onDisconnect(self, socket)
      This is called when a client's connection is terminated. As with onConnect(),
      the connection's socket is provided as a parameter. This is called regardless of
      who closed the connection.

"""

import sys
from ex3utils import Server


# Create an echo server class
class EchoServer(Server):

	def onStart(self):
		print "Echo server has started"
		
	def onMessage(self, socket, message):
		# This function takes two arguments: 'socket' and 'message'.
		#     'socket' can be used to send a message string back over the wire.
		#     'message' holds the incoming message string (minus the line-return).
	
		# convert the string to an upper case version
		message = message.upper()
		# Just echo back what we received
		socket.send(message)
		
		# Signify all is well
		return True


# Create an ego server class
class EgoServer(Server):

	def onStart(self):
		self.colour = 'red'
		print 'Ego server has started'

	def onMessage(self, socket, message):
		# Egomaniacally deal with an incoming message.

		# Ignore the message, and tell your adoring fan your favourite colour
		socket.send("My favourite colour is %s\n" % self.colour)
		
		# Disconnect!
		return False


# Parse the IP address and port you wish to listen on.
ip = sys.argv[1]
port = int(sys.argv[2])

# Create an echo server.
server = EchoServer()

# If you want to be an egomaniac, comment out the above command, and uncomment the
# one below...
#server = EgoServer()

# Start server
server.start(ip, port)

