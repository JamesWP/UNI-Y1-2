"""

Server proxy module, for providing local functions that execute the three remote
functions of IMServer.php. That is: SET, GET and UNSET.

"""


# Import URL library
import urllib2
from urllib import quote as enc

class IMServerProxy:

  def __init__(self, url):
    self.url = url

  def __getitem__(self, key):
    return urllib2.urlopen('%s?action=get&key=%s'%(self.url, enc(key))).read()

  def __setitem__(self, key, value):
    urllib2.urlopen('%s?action=set&key=%s&value=%s'%(self.url, enc(key), enc(value)))
    
  def __delitem__(self, key):
    urllib2.urlopen('%s?action=unset&key=%s'%(self.url, enc(key)))
    
