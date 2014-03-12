import uuid
import im
from threading import Thread
import sys
from time import sleep

server = im.IMServerProxy('http://webdev.cs.manchester.ac.uk/~mbax3jp2/IMServer.php')

#last message id
lmid = server['lmid']


#server['message'] = 'new user logged in'
#server['lmid'] = lmid

def messageReceiver():
  try:
    global lmid
    while True:
      while server['lmid']==lmid: sleep(1);
      print
      print server['message']
      lmid = server['lmid']
  except KeyboardInterrupt:
    raise SystemExit 

def messageSender():
  try:
    global lmid
    while True:
      message = raw_input()
      if message == 'exit': break;
      server['message'] = message
      server['lmid'] = lmid = str(uuid.uuid1())
  except (KeyboardInterrupt, EOFError):
    print
    raise SystemExit

if len(sys.argv) == 1 or sys.argv[1] == 'recv':
  Thread(target=messageReceiver).start()
if len(sys.argv) == 1 or sys.argv[1] == 'send':
  Thread(target=messageSender).start()
