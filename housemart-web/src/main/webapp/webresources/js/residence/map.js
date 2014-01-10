define(function(require, exports, module){
    'use strict';
	require('lib/bootstrap');
	
	var App,
		WIN = window,
		$ = require('jquery'),
		mvc = require('../lib/mvc'),
		Header = require('../header'),
		mvc = require('lib/mvc'),
		infoWinTpl = $('#J_infoWinTpl').html(),
		appConfig = __appConfig;
	
	function html2DOM(){
		
		
	};
	
	App = {
		init: function(options){
			var self = this;
			Header.setCurrent('residence');
        	if(options.isLocked){
        		Header.lock();
	        	return;
        	};
			
			self.options = options;	
			self.createMap().bindMapEvent().createDefaultMarkers();
			!options.enableMap && self.getRemotePics(0, options.residenceId);
		},
		createMap: function(){
			var options = this.options,
				center = options.center,
				map;
			 //var point = new GLatLng(31.197162,121.440599);
			if(options.points.length == 0){
				center = new google.maps.LatLng(31.197162,121.440599);
				//map.setCenter(, 13); 
			} else {
				//map.setCenter(, 13);
				center = new google.maps.LatLng(center.lat, center.lng); 	
			};
			map = new google.maps.Map(this.options.mapContainer[0], {
				center: center,
		        zoom: 13,
		        mapTypeId: google.maps.MapTypeId.ROADMAP
			});	
			//map.fitBounds();
			this.map = map;
			return this;	
		},
		bindMapEvent: function(){
			var self = this;
			
			self.options.enableMap && google.maps.event.addListener(self.map, 'click', function(point) {	
				point && self.addMarker(point.latLng);
			});
			return self;			
		},
		showMapInfo: function(marker, info){
			var infoWin = new google.maps.InfoWindow({
				content: info	
			});
			//marker.openInfoWindowHtml(info);
			infoWin.open(this.map, marker);
						
		},
		createDefaultMarkers: function(){
			var self = this,
				pts = self.options.points,
				i = 0,
				len = pts.length;
			
			for(; i<len; i++){
				pts[i] && self.addMarker(pts[i], true);
			};	
			//self.map.setUIToDefault();
			return self;		
		},
		addMarker: function(point, isAuto){
			if(!point.isMain && isAuto) return this;
			
			var self = this,
				marker,
				pt = {},
				html,
				//icon = new GIcon(G_DEFAULT_ICON),
				map = this.map;
			
			
            if(isAuto){
	        	pt.x=point.lng;
	        	pt.y=point.lat;
	        	pt.id=point.id;  
            }else{
	            pt.x=point.lng();
	            pt.y=point.lat();
            }
            //icon.image = "/webresources/img/icons.png";
            //marker = point.isMain ? new GMarker(new GLatLng(point.x, point.y), {icon: icon}) : new GMarker(new GLatLng(point.x, point.y));
            marker = new google.maps.Marker({position: new google.maps.LatLng(pt.y, pt.x), map: map});
            marker.isAuto = isAuto;
            //map.addOverlay(marker);
			html = mvc.render(infoWinTpl, pt);			

			self.options.enableMap && google.maps.event.addListener(marker, "click", function() { 
				self.showMapInfo(marker, html);	   
			});	
		},
		saveMarker: function(btn){
			var isAuto = btn.attr('data-is-auto')==1,
				residenceId = this.options.residenceId;
			
			$.ajax({
				type: "post",
				url: isAuto ? "/ajax/confirmResidencePosition.controller" : "/ajax/confirmResidencePositionMannally.controller",
				data: isAuto ? {residenceId: residenceId,mapPlaceId: btn.attr('data-id')} : {residenceId: residenceId, lat: btn.attr('data-lat'), lng: btn.attr('data-lng')},
				dataType: "json",
				success: function (data) {	
					Header.showSysInfo('主坐标设置成功');
					//location.href = "/external/residencePicConsole.controller?residenceId=" + residenceId;
					
				}
	  		}); 
		},
		getRemotePics: function(type, residenceId){
			var self = this,
				picsTpl = $('#J_picsTpl').html(),
				wrap = $('#J_imgsWrap');
			
			$.ajax({
				url: '/ajax/getPicListByResidenceId.controller',
				method: 'get',
				dataType: 'json',
				data: {type: type, residenceId: residenceId},
				success: function(data){
					var bizData = data.bizData;

                    if(bizData && bizData.length){
                        wrap.html(mvc.render(picsTpl, {list: bizData}));
                    };		
				}	
			});	
		}	
	};
	App.init({
		mapContainer: $('#J_map'),
		points: appConfig.points,
		center: appConfig.center,
		enableMap: appConfig.enableMap,
		residenceId: appConfig.residenceId
	});
	window.__saveMarker = function(target){
		App.saveMarker($(target));	
	};
    //modules.exports = App;
});
