window.addEvent('domready', function(){
  if(window.attachEvent){
    document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
    var style_sheet = document.createStyleSheet();
    style_sheet.cssText = "v\\:roundrect{behavior: url(#default#VML); display:block;height:100%;margin:0.5em 0;padding:5px;background-color:transparent;}";
    $$('.round').each(function(i){
      var color = (i.getStyle('background-color') || "#fff");
      var vml = new Element('v:roundrect', {'fillcolor':color, 'strokecolor':color, 'arcsize':0.1})
      vml.wraps(i);
      i.setStyles({margin:0,padding:0});
    });
  }
  $('address_search_submit').addEvent('click', function(){
    this.disabled = true;
    addressSearch('pref', 'city', 'address');
    return false;
  });
  $('zipcode_search_submit').addEvent('click', function(){
    this.disabled = true;
    zipcodeSearch('zipcode');
    return false;
  });
})

function addressSearch(pref, city, address){
  var pref = $('pref').value, city = $('city').value, address = $('address').value, request;
  if(pref && city && address){
    request = pref + '/' + city + '/' + address;
  }else if(pref && city){
    request = pref + '/' + city;
  } else if(pref){
    request = pref;
  }
  new Request.JSONP({
    url: '/address/' + request,
    onComplete: function(result){
      $('address_search_result').innerHTML = '';
      var list = new Element('ul', {'class': 'tMargin'});
      if(result.error){
        list.grab(new Element('li', {'html': result.error}));
      }else{
        for(var i = 0, size = result.length; i < size; i++){
          var zipcode = '〒' + result[i].zipcode.replace(/(\d{3})/, '$1-');
          var address = result[i].pref + result[i].city + result[i].address;
          var phonetic = ' (' + result[i].pref_phonetic + result[i].city_phonetic + result[i].address_phonetic + ')';
          var elem = new Element('a', {'href':'#', 'html': zipcode + ' : ' + address + ' ' + phonetic});
          elem.addEvent('click', function(event){
            event.stop();
            createMap(zipcode, address);
          }.bind(zipcode).bind(address));
          list.grab(new Element('li').grab(elem));
        }
      }
      $('address_search_result').grab(list);
      $('address_search_submit').disabled = false;
    }
  }).send();
}

function zipcodeSearch(id){
  new Request.JSONP({
    url: '/zipcode/' + $(id).value,
    data: {multiple: 'true'},
    onComplete: function(result){
      $('zipcode_search_result').innerHTML = '';
      var list = new Element('ul', {'class': 'tMargin'});
      if(result.error){
        list.grab(new Element('li', {'html': result.error}));
      }else{
        for(var i = 0, size = result.length; i < size; i++){
          var zipcode = '〒' + result[i].zipcode.replace(/(\d{3})/, '$1-');
          var address = result[i].pref + result[i].city + result[i].address;
          var phonetic = ' (' + result[i].pref_phonetic + result[i].city_phonetic + result[i].address_phonetic + ')';
          var elem = new Element('a', {'href':'#', 'html': zipcode + ' : ' + address + ' ' + phonetic});
          elem.addEvent('click', function(event){
            event.stop();
            createMap(zipcode, address);
          }.bind(zipcode).bind(address))
          list.grab(new Element('li').grab(elem));
        }
      }
      $('zipcode_search_result').grab(list);
      $('zipcode_search_submit').disabled = false;
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
      alert(address + " not found");
    }else{
      map.setCenter(point, 13);
      var marker = new GMarker(point);
      map.addOverlay(marker);
      marker.openInfoWindowHtml(zipcode + '<br />' + address);
    }
  })
}