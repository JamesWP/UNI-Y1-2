#!/usr/bin/env python
import urllib
import re

url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page1.html"
data = urllib.urlopen(url)
tokens = data.read().split()

insideTag = False

for token in tokens:
  if insideTag:
    if re.match('\<\/.*\>$',token):
      insideTag = False
      print
    else: print token,
  elif re.match('<.*>',token):
    if (token == '<title>') or (token == '<p>') or (token == '<h1>'):
      insideTag = token
      print "starting " + token,
  
