/**
 * Created with JetBrains WebStorm.
 * User: chen
 * Date: 10/6/13
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
define(function(require, exports, module){
    'use strict';
    
    require('../lib/bootstrap');

    var EMPTY = '',
        MSG = {
            username: '请输入正确的账号！',
            password: '请输入正确的密码！'
        },
        $ = require('jquery'),
        Validate = require('../lib/form/validate'),
        Field = require('../lib/form/field'),
        Login,
        originPwd = $('#J_originPwd'),
        newPwd = $('#J_newPwd'),
        repeatPwd = $('#J_repeatPwd'),
        submit = $('#J_submit'),
        isNotEmpty = function(v){
            return !!$.trim(v) || v ===0;
        },
        errorClass = 'has-error',
        isCorrectPWD = false,
        validator = new Validate(),
        ModifyPWD;
        
    function checkHandler(rs, data){
		var wrap = data.dom;
			
        wrap.parents('.form-group')[rs ? 'removeClass' : 'addClass'](errorClass);
        wrap.tooltip(!rs ? 'show' : 'hide');   
    };
    function checkOriginPwd(v){
		$.ajax({
			url: '/ajax/checkPassword.controller',
			method: 'post',
			data: {
				password: v
			},
			success: function(rs){
				isCorrectPWD = rs;
				checkHandler(rs, {dom: originPwd});	
			}
		}); 
		return true;  
    };  
    ModifyPWD = {
	    bindEvents: function(){
	    	var self = this;
			submit.bind('click', function(e){
				if(!self.validate()){
					e.preventDefault();	
				}
			});	  
			return self;  
	    },
	    init: function(){
			this.bindEvents().initTooltip().addFields();   
	    },
	    initTooltip: function(){ 
            var defaultOptions = {
                trigger: 'manual',
                placement: 'right'
            };
            $('[data-original-title]').tooltip(defaultOptions);
            return this;
        },
	    addFields: function(){
			validator.addFields([
				new Field({
					dom: originPwd,
                    rules: [isNotEmpty, checkOriginPwd],
                    onCheck: checkHandler,
                    required: true	
				}),
				new Field({
					dom: newPwd,
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true	
				}),
				new Field({
					dom: repeatPwd,
                    rules: [function(v){
	                	return v === newPwd.val();    
                    }],
                    onCheck: checkHandler,
                    required: true	
				})
			]);	   
	    },
	    validate: function(){
		    return validator.check() && isCorrectPWD;
	    }
    };
	
	ModifyPWD.init();

    
});
