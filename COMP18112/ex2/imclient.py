import uuid
import im
server = im.IMServerProxy('http://webdev.cs.manchester.ac.uk/~mbax3jp2/IMServer.php')

userid = str(uuid.uuid1())

try:
  while True:
    server['message'] = message = raw_input('message> ')
    
    if message == 'exit': break;
    
    server['user'] = userid

    while server['user']==userid: pass;

    print "message<", server['message']
except KeyboardInterrupt:
  raise SystemExit
