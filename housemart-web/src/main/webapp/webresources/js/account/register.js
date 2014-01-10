define(function(require, exports, module){
    'use strict';
    require('lib/bootstrap');

    var Validate = require('lib/form/validate'),
        Field = require('lib/form/field'),
        Mbox = require('widget/mbox'),
        Header = require('header'),
        PlaceHolder = require('lib/placeholder'),
        Register,
        isSubmit = false,
        $ = require('jquery'),
        releaseForm = $('#J_releaseForm'),
        validator = new Validate(),    
        errorClass = 'has-error',
        isNotEmpty = function(v){
            return !!$.trim(v) || v ===0;
        },
        rNumber = /^[\d\.]+$/;

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

    Register = {
    	initTooltip: function(){ 
            var defaultOptions = {
                trigger: 'focus',
                placement: 'right'
            };
            $('[data-original-title]').tooltip(defaultOptions);
            return this;
        },
        addFields: function(){
            var self = this;

            validator.addFields([
                new Field({
                    dom: $('#J_userName'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_cardID'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_phone'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_company'),
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
                    dom: $('#J_region'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_weixin'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                })
            ]);
            return this;
        },
        _formToJSON: function(){
        	var obj = {};
	    	$('input[type=text][name],textarea[name],input[type=radio][name]:checked').each(function(key, item){
		    	obj[item.getAttribute('name')] = item.value;	
	    	});	 
	    	return obj;  
	        
        },
        submit: function(){
	    	var params = this._formToJSON();
	    	
	    	$.ajax({url: '/brokerApplySave.controller',
	            method: 'post',
	            data: params,
	            success: function(ret){
		        	//console.log(ret);   
	            }
            });    
	    	    
        },
        bindSubmit: function(){
        	var self = this;
            //submit form
            releaseForm.bind('submit', function(e){
            	
				isSubmit = true;
                //check form before submit
                if(!validator.check()){
                	e.preventDefault();
					isSubmit = false;
                    
                }else{
	            	//	self.submit();	    
	                	
                };
            }); 
            return this;   
        },
        fakeRegionPlaceHolder: function(){
        	$('#J_region').placeholder({fake: true, attrName: 'fake-placeholder', text: ' '});
	    	return this;    
        },
        init: function(options){
            this.bindSubmit()
                .initTooltip()
                .fakeRegionPlaceHolder().addFields();
              
            //系统提示    
            if(window.__appConfig.sysMsg){
	        	var box = Mbox.alert(__appConfig.sysMsg);
	        	/*
	        	setTimeout(function(){
		        	box.close();
	        	}, 3000);
	            */
            };    
        }
    }
    
    Register.init();
});