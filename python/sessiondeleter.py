#!/usr/bin/env python
import os
import sys
import csv
import getpass
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine")
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine/lib/yaml/lib")
from google.appengine.ext.remote_api import remote_api_stub
from google.appengine.ext import db
import model

def auth_func():
  return raw_input('Email: '), getpass.getpass('Password: ')

if len(sys.argv) > 1:
  host = sys.argv[1]
else:
  host = 'python.latest.jp-zipcode-search.appspot.com'

print "Please enter login credentials for %s" % host

remote_api_stub.ConfigureRemoteDatastore('jp-zipcode-search', '/remote_api', auth_func, host)

print "Start deleting."
query = db.GqlQuery("SELECT * FROM _ah_SESSION")
for i in range(100):
  try:
    db.delete(query.fetch(300))
    print "delete 300 entities %d" % count
  except:
    print "retrying"
    continue
  finally:
    count += 1

