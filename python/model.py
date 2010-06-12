from google.appengine.ext import db

class Zip(db.Model):
  code = db.StringProperty()
  z02 = db.StringProperty()
  zipcode = db.StringProperty()
  pref_phonetic = db.StringProperty()
  city_phonetic = db.StringProperty()
  address_phonetic = db.StringProperty()
  pref = db.StringProperty()
  city = db.StringProperty()
  address = db.StringProperty()
  z10 = db.StringProperty()
  z11 = db.StringProperty()
  z12 = db.StringProperty()
  z13 = db.StringProperty()
  z14 = db.StringProperty()
  z15 = db.StringProperty()

class Log(db.Model):
  date = db.DateTimeProperty(auto_now_add=True)

class _ah_SESSION(db.Model):
  _test = db.StringProperty()

