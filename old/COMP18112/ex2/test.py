import im
server = im.IMServerProxy('http://webdev.cs.manchester.ac.uk/~mbax3jp2/IMServer.php')


server['testKey'] = 'testValue'
print server['testKey']
del server['testKey']

