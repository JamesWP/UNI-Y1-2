import sys
from ex3utils import Client
import time
import readline
from threading import Thread
import os
from termsize import get_terminal_size
from time import strftime

HEADER = '\033[95m'
OKBLUE = '\033[94m'
OKGREEN = '\033[92m'
WARNING = '\033[93m'
FAIL = '\033[91m'
ENDC = '\033[0m'

def printRight(message,color = ENDC):
  width = get_terminal_size()[0]
  padding = " " * (width - len(message) - 1)
  print color,
  print '\r',padding,message
  print ENDC,

def clear():
  print "\n" * 200

class MyClient(Client):

  def onMessage(self, socket, message):
    (type,sep,message) = message.strip().partition(' ')
    (username,sep,nmessage) = message.strip().partition(':')
    if type == 'raw':
      print message
    else:
      printRight(nmessage + " <: " + username + " @ " + strftime("%H:%M:%S")
               , (WARNING if type == 'pm' else OKBLUE))
    return True
  def sendAsync(self,message):
    Thread(target=self.send,args=(message,)).start()

ip = "localhost" #sys.argv[1]
port = 8090 #int(sys.argv[2])

client = MyClient()

client.start(ip, port)

clear()

username = raw_input("Username: ")

clear()

client.send('setname %s' % username)

while client.isRunning():
  try:
    message = raw_input().strip()
    if message.find('newname ') == 0:
      client.sendAsync('setname %s' % message.partition('newname ')[2])
    elif message.find('pm ') == 0:
      client.sendAsync('pm %s' % message.partition('pm ')[2])
    elif message.find('list') == 0:
      client.sendAsync(message)
    else:
      client.sendAsync('message %s' % message)

    printRight (strftime("%H:%M:%S"),HEADER)
  except:
    client.stop();

client.stop()
