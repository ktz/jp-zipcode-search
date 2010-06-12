from google.appengine.ext import db
from google.appengine.tools import bulkloader
import sys
sys.path.append('python')
import model

class ZipLoader(bulkloader.Loader):
  def __init__(self):
    bulkloader.Loader.__init__(self, 'Zip', [
      ('code', str),
      ('z02', str),
      ('zipcode', str),
      ('pref_phonetic', lambda x: unicode(x, 'utf-8')),
      ('city_phonetic', lambda x: unicode(x, 'utf-8')),
      ('address_phonetic', lambda x: unicode(x, 'utf-8')),
      ('pref', lambda x: unicode(x, 'utf-8')),
      ('city', lambda x: unicode(x, 'utf-8')),
      ('address', lambda x: unicode(x, 'utf-8')),
      ('z10', str),
      ('z11', str),
      ('z12', str),
      ('z13', str),
      ('z14', str),
      ('z15', str)
    ])
  
loaders = [ZipLoader]