/**
 * Created with JetBrains WebStorm.
 * User: chen
 * Date: 10/6/13
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
define(function(require, exports, module){
    'use strict';

	var $ = require('jquery'),
		Header = require('header');
	
	$('#J_uploadHead,#J_uploadID,#J_uploadCard').on('change', function(){
		$(this).next().css('visibility', 'visible');	
		
	});
	
	$('#J_accountImgs img').on('click', function(e){
		var target = $(this);
		if(confirm('确定'+target.attr('title')+'吗？')){
			$.ajax({
				url: '/ajax/deleteMyAccountPic.controller',
				type: 'get',
				data: {
					type: target.attr('data-type')
				},
				success: function(){
					target.remove();
					Header.showSysInfo('删除成功！');	
				}
			});	
		};
			
	});
        

    
});
