define(function(require, exports, module){
    'use strict';

    var $ = require('jquery'),
        pagebarTpl = require('ftl/pagebar'),
        PageBar = require('widget/pagebar'),
        Mbox = require('widget/mbox'),
        Header = require('header'),
        appConfig = __appConfig,
        List;


    List = {
        init: function(options){
            var self = this;

            self.pageHidden = options.pageHidden;
            self.pagebar = new PageBar({
                tpl: options.pagebarTpl,
                total: options.totalPage,
                current: options.currentPage,
                wrap: options.pagebarWrap,
                onPaging: function(page){
                    self.query(page);
                }
            });
            self.options = options;
            self.wrap = options.wrap;
            self.form = options.form;

            self._initFilter();
            self._bindEvents();
            Header.setCurrent('house');
        },
        query: function(page){
            page = page || 1;

            this.pageHidden.val(page-1);
            this.form.submit();
        },
        _ajaxAction: function(data, action, title, target, callback){
            var self = this;
            
            Mbox.confirm('您确定要'+title+'"#'+data.id+'"这套房子吗？', title, function(){
            	target[0].removeAttribute('data-action');
				target.html(target.html()+'中…');
                this.close();
                $.ajax(self.options.ajaxURLs[action], {
                    data: data,
                    method: 'post',
                    dataType: 'json',
                    success: function(rs){
                        callback && callback();
                        //Mbox.alert(title+'成功！');
                        Header.showSysInfo(title+'成功！');
                    }
                });
            });
        },
        'delete': function(id, target){
            this._ajaxAction({id: id},'delete', '删除', target, function(){
                $(target).parents('tr').remove();
            });
        },
        deactive: function(id, target){
            this._ajaxAction({id: id}, 'deactive', '下架',target, function(){
                $(target).parents('tr').remove();
            });
        },
        active: function(id, target){
            this._ajaxAction({id: id}, 'active', '申请上架',target, function(){
                $(target).parents('tr').remove();
            });
        },
        modify: function(id){

        },
        _bindEvents: function(){
            var self = this;

            self.wrap.delegate('a[data-action]', 'click', function(e){
                var target = $(e.target),
                    actionStr = target.attr('data-action');

                self[actionStr](target.attr('data-house-id'), target);
            });
        },
        _initFilter: function(){
            var filterWrap = $('#J_filterWrap'),
                self = this;
            //radio filter
            filterWrap.find('[name=saleRent]').bind('click', function(){
                self.query();
            });
            //select filter
            filterWrap.find('select').bind('change', function(){
                self.query();
            });
        }
    };

    List.init({
        pageHidden: $('#J_currentPage'),
        ajaxURLs : {
            'delete': '/ajax/deleteHouse.controller',
            deactive: '/ajax/deactiveHouse.controller',
            active: '/ajax/applyHouse.controller'
        },
        totalPage: appConfig.totalPage,
        currentPage: appConfig.currentPage+1,
        form: $('#J_listForm')[0],
        wrap: $('#J_listWrap'),
        pagebarTpl: pagebarTpl,
        pagebarWrap: $('#J_pagebarWrap')
    });
    module.exports = List;
});