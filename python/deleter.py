#!/usr/bin/env python
import os
import sys
import csv
import getpass
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine")
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine/lib/yaml/lib")
#quick fix
sys.path.insert(0, "/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine/lib/fancy_urllib")
from google.appengine.ext.remote_api import remote_api_stub
from google.appengine.ext import db
import model

if len(sys.argv) < 2:
  print "Error: Missing required parameter.\n"
  print "Usage: %s csvfile [host]" % (sys.argv[0],)
  print "  csvfile must be utf-8 encoding."
  sys.exit(1)

def auth_func():
  return raw_input('Email: '), getpass.getpass('Password: ')

if len(sys.argv) > 2:
  host = sys.argv[2]
else:
  host = 'python.latest.jp-zipcode-search.appspot.com'

print "Please enter login credentials for %s" % host

remote_api_stub.ConfigureRemoteDatastore('jp-zipcode-search', '/remote_api', auth_func, host)

print "Start deleting."
csvfile = open(sys.argv[1])
count = 1
query = db.GqlQuery("SELECT * FROM Zip WHERE zipcode = :1 AND pref = :2 AND city = :3 AND address = :4")
for row in csv.reader(csvfile):
  try:
    query.bind(row[2], unicode(row[6], 'utf-8'), unicode(row[7], 'utf-8'), unicode(row[8], 'utf-8'))
    db.delete(query.fetch(3))
    print "delete line %d" % count
  except:
    print "skip line %d" % count
    continue
  finally:
    count += 1
csvfile.close()

db.put(model.Log())
