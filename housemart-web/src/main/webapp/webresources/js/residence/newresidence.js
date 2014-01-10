define(function(require, exports, module){
    'use strict';
    require('lib/bootstrap');

    var Validate = require('lib/form/validate'),
        Field = require('lib/form/field'),
        DatePicker = require('widget/calendar'),
        Header = require('../header'),
        NewResidence,
        $ = require('jquery'),
        isSubmit = false,
        validator = new Validate(),
        releaseForm = $('#J_releaseForm'),
        region = $('#J_region'),
        plate = $('#J_plate'),
        errorClass = 'has-error',
        isNotEmpty = function(v){
            return !!$.trim(v) || v ===0;
        },
        rNumber = /^[\-]?[\d\.]+$/;

    function checkHandler(rs, data){
        var dom = data.dom,
        	next,
			wrap = dom.parents('.form-group');
			
        wrap[rs ? 'removeClass' : 'addClass'](errorClass);
		if(!rs){
			isSubmit && dom[0].focus();
			next = dom.next();
			(next && next.length) ? next.tooltip('show') : dom.tooltip('show');
			
		}else{
			next = dom.next();
			(next && next.length) ? next.tooltip('hide') : dom.tooltip('hide');
		};
        
    };

    NewResidence = {
        initTooltip: function(){ 
            var defaultOptions = {
                trigger: 'focus',
                placement: 'right'
            };
            $('[data-original-title]').tooltip(defaultOptions);
        },
        addFields: function(isEdit){
            var self = this,
            	fields = isEdit ? [] : [
            		new Field({
	                    dom: region,
	                    rules: [isNotEmpty],
	                    onCheck: checkHandler,
	                    required: true
	                }),
	                new Field({
	                    dom: plate,
	                    rules: [isNotEmpty],
	                    onCheck: checkHandler,
	                    required: true
	                })
            	];
            //add fields for form
			fields.length && validator.addFields(fields); 
            validator.addFields([
            	new Field({
                    dom: $('#J_residence'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
            	new Field({
	                dom: $('#J_address'),
	                rules: [isNotEmpty],
	                onCheck: checkHandler,
	                required: true
	            }),
            	new Field({
                    dom: $('#J_headCount'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_parking'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_propertyType'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }), 
                new Field({
                    dom: $('#J_greenRate'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_volumeRate'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: false
                }),
                new Field({
                    dom: $('#J_propertyFee'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_developer'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_finishedTime'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_annualPriceIncrement'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: false
                }),
                new Field({
                    dom: $('#J_annualTurnoverPercent'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_rentRevenue'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                })
            ]);
            return this;
        },

        bindSubmit: function(){
            //submit form
            releaseForm.bind('submit', function(e){
				isSubmit = true;
                //check form before submit
                if(!validator.check()){
					isSubmit = false;
                    e.preventDefault();
                }
            }); 
            return this;   
        },
        _bindRegionEvent: function(region, config){
        	var self = this;
	    	region.bind('change', function(){
		    	self.getPlate($(this).val(), config);		
	    	});	    
        },
        initRegion: function(config){
        	var self = this;
	    	$.ajax({
			    type: "post",
				url: config.regionAjax,
				data: {},
				dataType: "json",
				success: function (data) {		
					var currentRegion = config.region,
						bizData = data && data.bizData||[];
					
					region.html(self._renderOptions(bizData, currentRegion));
					self._bindRegionEvent(region, config);
					region.trigger('change');		
				}	
	    	});
	    	return self;	    
        },
        _renderOptions: function(data, current){
        	var html='',
        		len = data.length,
        		i=0, item;
        	
        	for(; i<len; i++){
				item = data[i];
				html += '<option '+(item.id==current?'selected="selected"':'')+' value=' + item.id+'>' + item.name + '</option>';
			};
	
	    	return html;    
        },
        getPlate: function(regionId, config){
        	var self = this;
	    	$.ajax({
				type: "post",
				url: config.plateAjax,
				data: {parentId: regionId},
				dataType: "json",
				success: function (data) {		
					var currentPlate = config.plate,
						bizData = data && data.bizData||[];
						
					plate.html(self._renderOptions(bizData, currentPlate));		
				}
		  	});	    
        },
        initCalendar: function(){
            new DatePicker($('#J_finishedTime'), {
                format: 'yyyy-mm-dd'
            });
            return this;
        },
        init: function(options){
        	Header.setCurrent('residence');
        	if(options.isLocked){
        		Header.lock();
	        	return;
        	};
			this.bindSubmit()
			.initRegion(options).addFields(options.isEdit)
			.initCalendar()
			.initTooltip();
        }
    }
    
    NewResidence.init(__appConfig);
});
