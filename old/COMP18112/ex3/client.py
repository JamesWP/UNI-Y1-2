"""

IRC client exemplar.

"""

import sys
from ex3 import Client

import time


class IRCClient(Client):

	def onMessage(self, socket, message):
		# *** process incoming messages here ***
		return True


# Parse the IP address and port you wish to connect to.
ip = sys.argv[1]
port = int(sys.argv[2])
screenName = sys.argv[3]

# Create an IRC client.
client = IRCClient()

# Start server
client.start(ip, port)

# *** register your client here, e.g. ***
client.send('REGISTER %s' % screenName)

while client.isRunning():
	try:
		command = raw_input("> ").strip()
		# *** process input from the user in a loop here ***
		# *** use client.send(someMessage) to send messages to the server
	except:
		client.stop();

client.stop()
