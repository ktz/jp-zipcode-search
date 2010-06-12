window.addEvent('domready', function(){
  var req = new Request.JSON({
    url: '/get',
    onSuccess: function(response){
      $('date').set('text', response.body);
    }
  }).get();
  $('search_by_address_submit').addEvent('click', function(event){
    event.stop();
    var pref = $('pref').value, city = $('city').value, address = $('address').value, params = {};
    if (pref) {params.pref = pref};
    if (city) {params.city = city};
    if (address) {params.address = address};
    fetch('/address', params, 'search_by_address_submit');
  });
  $('search_by_zipcode_submit').addEvent('click', function(event){
    event.stop();
    fetch('/zipcode', {zipcode: $('zipcode').value}, 'search_by_zipcode_submit');
  });
});

function fetch(request, params, id){
  $(id).disabled = true;
  new Request.JSONP({
    url: request,
    data: params,
    onComplete: function(result){
      $('result').innerHTML = '';
      var list = new Element('ul', {'class': 'tMargin bMargin'});
      if (result.status === 'SUCCESS') {
        var res = result.body;
        for(var i = 0, size = res.length; i < size; i++){
          var zipcode = ['〒', res[i].zipcode.replace(/(\d{3})/, '$1-')].join('');
          var address = [res[i].pref, res[i].city, res[i].address].join('');
          var phonetic = [' (', res[i].pref_phonetic, res[i].city_phonetic, res[i].address_phonetic, ')'].join('');
          var elem = new Element('a', {'class': 'zip','href':'#', 'html': ['<span class="zipcode">', zipcode, '</span>', '<span class="address">' ,address, '</span>', phonetic].join('')});
          list.grab(new Element('li').grab(elem));
        }
      }
      $('result').grab(list);
      $(id).disabled = false;
      $$('.zip').addEvent('click', function(event){
        event.stop();
        createMap(this.getElement('span.zipcode').get('text'), this.getElement('span.address').get('text'));
        var fx = new Fx.Scroll(window).toElement('map');
      });
    }
  }).send();
}

function createMap(zipcode, address){
  var target = $('map').setStyles({width:'100%',height:'400px'});
  var map = new GMap2(target);
  map.setCenter(new GLatLng(35.67431, 139.69082), 13);
  map.addControl(new GLargeMapControl());
  map.addControl(new GMapTypeControl());
  var geocoder = new GClientGeocoder();
  geocoder.getLatLng(address, function(point){
    if(!point){
      alert([address, " not found"].join(''));
    }else{
      map.setCenter(point, 13);
      var marker = new GMarker(point);
      map.addOverlay(marker);
      marker.openInfoWindowHtml([zipcode, '<br />', address].join(''));
    }
  });
}
