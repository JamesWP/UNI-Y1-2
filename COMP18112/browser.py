#!/usr/bin/env python
import urllib
import re
import sys

#vars
#url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page1.html"
#url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page2.html"
url = "http://studentnet.cs.manchester.ac.uk/ugt/COMP18112/page3.html"
labels = {"<title>":"Page title : ","<h1>":"HEADING: ","<p>":"PARAGRAPH: "}

#console colors
BOLD = '\033[1m'
RESET = '\033[0;0m'
RED = '\033[0;31m'

def browse (url):
  links = []
  data = urllib.urlopen(url)
  tokens = data.read().split()

  insideTag = False
  insideLink = False

  for token in tokens:
    if insideTag:
      if insideTag.replace('<','</') == token:
        insideTag = False
        print
      elif token=='<em>':  print BOLD,
      elif token=='</em>' or token=='</a>': insideLink = False ;print RESET,
      elif re.match('<a',token):
        insideLink = True
        print RED,  
      elif re.match('href=".*"',token):
        links.append({'href':re.match('href="(.*)"',token).groups()[0],'text':''})
      elif insideLink:
        links[-1]['text']+=token+' '
        print token,
      else: print token,
    elif re.match('<.*>',token):
      label = labels.get(token,False)
      if label:
        insideTag = token
        print '\033[1m' + label + '\t\033[0;0m',

  if len(links)>0:
    print '\n\nLINKS:'
    linkNumber = 0
    for link in links:
      print ' (' + RED + str(linkNumber) + RESET + ')' + '\t',
      print link
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
    print '!!CLEAR SCREEN HERE!!'
    browse(re.match('(.*/)',url).groups()[0] + link['href'])

browse(url)
