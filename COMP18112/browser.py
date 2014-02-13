#!/usr/bin/env python
import urllib
import re
import sys
from BeautifulSoup import BeautifulSoup 
import os

#vars
#url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page1.html"
#url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page2.html"
url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page3.html"

#console colors
BOLD = '\033[1m'
RESET = '\033[0;0m'
RED = '\033[0;31m'

def browse (url):
  os.system('clear')
  data = urllib.urlopen(url)
  dom = BeautifulSoup(data)

  for element in dom.html.body:
    print element

  raise SystemExit

  if len(links)>0:
    print '\n\nLINKS:'
    linkNumber = 0
    for link in links:
      print ' (' + RED + str(linkNumber) + RESET + ')' + '\t',
      print RED + link['text'] + RESET + '(' + link['href'] + ')'
      linkNumber += 1
    print 
    print 'Which link:',
    
    
    linkNo = -1
    
    # read acceptable char from user
    while linkNo == -1 or len(links) < linkNo:
      try:
        char = sys.stdin.read(1)
        if(char=='e'): raise SystemExit
        linkNo = int(char)
      except ValueError:
        linkNo = -1 
    print linkNo
    link = links[linkNo] 
    browse(re.match('(.*/)',url).groups()[0] + link['href'])

browse(url)
