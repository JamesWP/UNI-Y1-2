import uuid
import im
from multiprocessing import Process

server = im.IMServerProxy('http://webdev.cs.manchester.ac.uk/~mbax3jp2/IMServer.php')

#last message id
#lmid = str(uuid.uuid1())

#server['message'] = 'new user logged in'
#server['lmid'] = lmid

def messageReceiver():
  lmid = str(uuid.uuid1())
  while True:
    while server['lmid']==lmid: pass;
    print "\nmessage<", server['message'], "\nmessage>",
    lmid = server['lmid']

def messageSender():
  lmid = str(uuid.uuid1())
  while True:
    server['message'] = message = raw_input('message> ')
    if message == 'exit': break;
    server['lmid'] = lmid = str(uuid.uuid1())

try:
  Process(target=messageReceiver).start()
  #Process(target=messageSender).start()
  messageSender()
except KeyboardInterrupt:
  raise SystemExit
