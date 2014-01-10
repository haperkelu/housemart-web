/**
 * Created with JetBrains WebStorm.
 * User: chen
 * Date: 10/21/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */

define(function(require, exports, module){
    var $ = require('jquery'),
        sysInfo = $('#J_systemInfo'),
        userInfo = $('#J_userInfo'),
        mvc = require('lib/mvc'),
        userInfoTpl = '<li><a href="/myAccount.controller"><%=name%>(<%=positionType%>)</a></li>'+
                      '<%if(id>0){%><li><a href="/changeMyPwd.controller">修改密码</a></li>'+
                      '<li class="active"><a href="/logout.controller">退出登录</a></li><%}%>',
        Header;

    Header = {
        showSysInfo: function(msg, autoCloseTime){
            msg && sysInfo.find('strong').html(msg);
            sysInfo.show();
            setTimeout(function(){
                sysInfo.hide();
            }, autoCloseTime || 3e3);
            return this;
        },
        lock: function(){
	    	$('form input,form select').attr('disabled', 'disabled');   
        },
        wrap: $('#J_navBar'),
        init: function(){
        	//获取用户信息
        	this.getUserInfo();
        	//如果有系统信息则显示
            $.trim(sysInfo.find('strong').html())!='' && this.showSysInfo();
            //绑定关闭系统信息事件
			$('#J_closeSysInfo').bind('click', function(){
				sysInfo.hide();	
			});
        },
        getUserInfo: function(){
            $.ajax({
                type: "get",
                url: "/ajax/getCurrentAccount.controller",
                data: {},
                dataType: "json",
                success: function (data) {
                    data && userInfo.html(mvc.render(userInfoTpl, data));
                }
            });
        },
        setCurrent: function(category){
	    	this.wrap.find("li[data-category="+category+"]").addClass('active');	    
        }
    };

    Header.init();

    module.exports = Header;

});