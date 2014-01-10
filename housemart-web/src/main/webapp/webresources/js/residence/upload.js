define(function(require, exports, module){
    'use strict';

    require('widget/upload/uploadify');
    require('lib/cookie');
    
    var mvc = require('lib/mvc'),
    	Header = require('../header'),
    	$ = require('jquery');

    function ImgItem(dom){
        this.dom = dom;
        dom.data('img-item', this);
    };

    ImgItem.prototype = {
        constructor: ImgItem
    };

    function ImgsManager(options){
        var self = this;

        self.options = options;
        self.type = options.type;
        self.__cached = [];
    };

    ImgsManager.prototype = {
        constructor: ImgsManager,
        add: function(item){
            var cached = this.__cached,
            	onChange = this.options.onChange;

            cached.push(item);
            onChange && onChange.call(this, cached.length);
        },
        remove: function(item, fn){
            var cached = this.__cached,
            	onChange = this.options.onChange,
                removed = cached.splice($.inArray(item, cached), 1)[0];
            
            removed.dom.remove();
            $.ajax({
	            url: '/ajax/removePic.controller',
	            method: 'get',
	            data: {id: removed.dom.find('img').attr('data-img-id'), sourceType: '3'},
	            success: function(){
		            
	            }
            });
            onChange && onChange.call(this, cached.length);
            fn && fn(removed);
            return this;
        },
        getPicInfo: function(){
            var self = this;
            return {
                type: 0,
                items: $.map(this.__cached, function(item, index){
                    return {
                        id: item.dom.find('img').attr('data-img-id'),
                        order: index
                    };
                })
            };
        },
        _move: function(item, step){
            var self = this,
                cached = self.__cached,
                max = cached.length,
                originIndex = $.inArray(item, cached),
                index = originIndex + step,
                target = cached[index = (index>=max ? 0 : index<0 ? (max-1) : index)],
                tempItem = cached.splice(originIndex, 1)[0];
            //swap cache
            cached.splice(index, 0, tempItem);
            //swap dom 
            item.dom[index < originIndex ? 'insertBefore' : 'insertAfter'](target.dom);  
        },
        up: function(item){
            this._move(item, -1);
        },
        down: function(item){
            this._move(item, 1);
        }
    };
    // init
    var App,
    	remoteImgsStatus = {},
        picsTpl = $('#J_picsTpl').html(),
        typeKeyMap = {
            '3': 'J_imgsWrap-1'
        },
        imgsManager = new ImgsManager({
		    type: 3
		}), //默认第一个tab
        imgsWrap = $('#J_imgsWrap'),
        imgs = imgsWrap.find('li');


    //if any images displayed
    $.each(imgs, function(index, dom){
        imgsManager.add(new ImgItem(imgs.eq(index)));
    });


    App = {
        init: function(options){
        	Header.setCurrent('residence');
        	this.options = options;
			
            this.initUploader();
            this.bindDelegateEvents();
            this.bindEvents();
            this.getRemotePics('0', 'J_imgsWrap-1');
            if(options.isLocked){
        		Header.lock();
	        	return;
        	};
            
            
        },
        bindDelegateEvents: function(){
            imgsWrap.delegate('.hm-img-controls>.btn', 'click', function(e){
                e.preventDefault();

                var target = $(e.target),
                    action = target.attr('data-action');

                action && imgsManager[action](target.parents('li').data('img-item'));
            });
        },
        bindEvents: function(){
            var self = this;

            //提交事件
            $('.J_complete').bind('click', function(e){
                e.preventDefault();
                self.submit($(this).attr('href'));
            });
        },
        uploader: null,
        setPicType: function (type){
        	this.type = type;
        	this.category = typeKeyMap[type];
            this.postData.picType = type;
        },
        /**
         * upload component initializing
         */
        initUploader: function(){
        	var self = this;
        	
            this.postData = self.options.postData;
            this.postData.user_cookie = $.cookie('user');
            this.uploader = $('#J_upload').uploadify({
                formData      : this.postData,
                checkExisting : false,
                fileObjName   : 'imageFile',
                swf           : require.resolve('../widget/upload/uploadify.swf?1'),
                uploader      : '/multiPicUpload.controller',
                height        : 51,
                width         : 150,
                itemTemplate  : $('#J_imgItemTpl').html(),
                queueID       : 'J_imgsWrap-1',
                buttonImage   : require.resolve('../../img/swf-upload-btn.png?1'),
                fileSizeLimit: '10 MB',
                onSelect: function(file){
                	var imageWrap = $('#'+file.id),
                		type = self.type,
                		item = new ImgItem(imageWrap);
                		
                	item.status = file.filestatus;	
                	item.category = self.category;
                	item.type = type;
                	file.picType = type;
	            	imageWrap.data('data-item', item);	   
                },
                onUploadStart : function(file){
					self.uploader.uploadify('settings', 'formData', self.postData);	
                },
                onUploadProgress: function(file, fileBytesLoaded, fileTotalBytes){
                    var imageWrap = $('#'+file.id);

                    imageWrap.find('.progress-bar').css('width', 100 * fileBytesLoaded/fileTotalBytes + '%');
                },
                onUploadSuccess: function(file, data){
                    data = $.parseJSON(data);
                    data = data && data.bizData;

                    if(data && (data=data[0])){

                        var imageWrap = $('#'+file.id),
                        	item,
                            img;
                        //remove progress bar
                        imageWrap.find('.progress').remove();

                        img = imageWrap.find('img');
                        //data={url: 'http://i1.sinaimg.cn/ty/2013/1019/U9336P6DT20131019091816.jpg', id: '123456'}  ;//test code
                        img.attr('src', data.url);
                        img.attr('data-img-id', data.id);
                        imageWrap.children().show();
                        item = imageWrap.data('data-item');
                        item.status = file.filestatus;
                        //add to imgsManager
						imgsManager.add(item);
                        //imgsManager.add(new ImgItem(imageWrap));
                    };
                },
                onSWFReady: function(){
	                self.setPicType(self.postData.picType);
                }
            });
        },
		getRemotePics: function(type, category){
			var self = this;
			
			$.ajax({
				url: '/ajax/getPicListByResidenceId.controller',
				method: 'get',
				dataType: 'json',
				data: {type: type, residenceId: self.postData.residenceId, sort: self.options.sort},
				success: function(data){
					var bizData = data.bizData,
						wrap = $('#'+category),
						manager = imgsManager;

                    if(bizData && bizData.length){
                        wrap.html(mvc.render(picsTpl, {list: bizData}));
                        $.each(wrap.find('li'), function(key, item){
	                    	manager.add(new ImgItem($(item)));			   
                        });
                        remoteImgsStatus[type] = true;
                        
                    };		
				}	
			});	
		},
        getAllPicInfo: function(){
            var managers = [imgsManager],
                info,
                rs = [];

            info = imgsManager.getPicInfo();
            info.items && info.items.length && rs.push(info);
           
            return rs;
        },
        submit: function(url){
            var self = this,
                picInfo = self.getAllPicInfo();

            $.ajax({
                url: '/ajax/addOrUpdateSort.controller',
                method: 'post',
                data: {
                    residenceId: self.postData.residenceId,
                    data: $.toJSON({pics: picInfo})
                },
                success: function(rs){
                    if(rs.code==1){
                        location.href=url;
                    };
                }
            });
            //提交动作    
            //form.submit();
        }
    };

    App.init(__appConfig);
});
