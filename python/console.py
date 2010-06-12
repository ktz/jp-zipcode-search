#!/usr/bin/env python
import code
import getpass
import os
import sys
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine")
sys.path.append("/Applications/GoogleAppEngineLauncher.app/Contents/Resources/GoogleAppEngine-default.bundle/Contents/Resources/google_appengine/lib/yaml/lib")
from google.appengine.ext.remote_api import remote_api_stub
from google.appengine.ext import db

def auth_func():
  return raw_input('Email: '), getpass.getpass('Password: ')

if len(sys.argv) > 1:
  host = sys.argv[1]
else:
  host = 'python.latest.jp-zipcode-search.appspot.com'

remote_api_stub.ConfigureRemoteDatastore('jp-zipcode-search', '/remote_api', auth_func, host)
code.interact('App Engine interactive console for jp-zipcode-search', None, locals())