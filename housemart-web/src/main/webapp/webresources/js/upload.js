define(function(require, exports, module){
    'use strict';

    require('lib/bootstrap');
    require('widget/upload/uploadify');
    require('lib/cookie');
    
    var mvc = require('lib/mvc'),
    	$ = require('jquery'),
    	Header = require('header');

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
            var cached = this.__cached;

            cached.push(item);
            this.options.onChange.call(this, cached.length);
        },
        remove: function(item, fn){
            var cached = this.__cached,
                removed = cached.splice($.inArray(item, cached), 1)[0];
            
            removed.dom.remove();
            $.ajax({
	            url: '/ajax/removePic.controller',
	            method: 'get',
	            data: {id: removed.dom.find('img').attr('data-img-id')},
	            success: function(){
		            
	            }
            });
            this.options.onChange.call(this, cached.length);
            fn && fn(removed);
            return this;
        },
        getPicInfo: function(){
            var self = this;
            return {
                type: self.type,
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
    	appOptions = __appConfig,
    	remoteImgsStatus = {},
        imgsTabsWrap = $('#J_imgCategory'),
        picsTpl = $('#J_picsTpl').html(),
        imgsWrapCount1 = imgsTabsWrap.find('li>a[data-category=J_imgsWrap-1]>i'),
        imgsWrapCount2 = imgsTabsWrap.find('li>a[data-category=J_imgsWrap-2]>i'),
        imgsWrapCount3 = imgsTabsWrap.find('li>a[data-category=J_imgsWrap-3]>i'),
        typeKeyMap = {
            '3': 'J_imgsWrap-1',
            '2': 'J_imgsWrap-2',
            '0': 'J_imgsWrap-3'
        },
        currentCategory = typeKeyMap[appOptions.picType||'3'],
        imgsManagers = {
           'J_imgsWrap-1': new ImgsManager({
                type: 3,
                onChange: function(count){
                    imgsWrapCount1.html(count);
                }
           }),
           'J_imgsWrap-2': new ImgsManager({
                type: 2,
                onChange: function(count){
                    imgsWrapCount2.html(count);
                }
           }),
           'J_imgsWrap-3': new ImgsManager({
                type: 0,
                onChange: function(count){
                    imgsWrapCount3.html(count);
                }
           })
        },
        imgsManager = imgsManagers[currentCategory], //默认第一个tab
        imgsWrap = $('#J_imgsWrap'),
        imgs = imgsWrap.find('li');


    //if any images displayed
    $.each(imgs, function(index, dom){
        imgsManager.add(new ImgItem(imgs.eq(index)));
    });


    App = {
        init: function(){
            this.initUploader();
            this.bindDelegateEvents();
            this.initTabs();
            this.bindEvents();
            Header.setCurrent('house');
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
            $('#J_complete').bind('click', function(e){
                e.preventDefault();
                self.submit($(this).attr('href'), false);
            });
            
            //提交并审核
            $('#J_complete_request').bind('click', function(e){
                e.preventDefault();
                self.submit($(this).attr('href'), true);
            });
        },
        uploader: null,
        setPicType: function (type){
        	this.type = type;
        	this.category = typeKeyMap[type];
            this.postData.picType = type;
        },
        getPostData: function(file){
        	var postData = this.postData;
        	
        	postData.picType = $('#'+file.id).data('data-item').type;
        	this.postData = postData;
	    	return postData;	   
        },
        /**
         * upload component initializing
         */
        initUploader: function(){
        	var self = this;
        	
            this.postData = appOptions.postData;
            this.postData.user_cookie = $.cookie('user');
            this.uploader = $('#J_upload').uploadify({
                formData      : this.postData,
                checkExisting : false,
                fileObjName   : 'imageFile',
                swf           : require.resolve('widget/upload/uploadify.swf?1'),
                uploader      : '/multiPicUpload.controller',
                height        : 51,
                width         : 150,
                debug		  : false,
                itemTemplate  : $('#J_imgItemTpl').html(),
                queueID       : currentCategory,
                buttonImage   : require.resolve('../img/swf-upload-btn.png?1'),
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
					self.uploader.uploadify('settings', 'formData', self.getPostData(file));	
                },
                onUploadProgress: function(file, fileBytesLoaded, fileTotalBytes){
                    var imageWrap = $('#'+file.id);
                    imageWrap.find('.progress-bar').css('width', 100 * fileBytesLoaded/fileTotalBytes + '%');
                },
                onUploadError: function(file, data){
	            	var imageWrap = $('#'+file.id);

                    imageWrap.find('.progress-bar').css('width', '0%');	 
                    imageWrap.remove();  
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
                        
                        item = imageWrap.data('data-item');
                        imageWrap.find('img').show();
                        item.type!=0 && imageWrap.find('.btn-group').show();//非小区图片显示控制
                        item.status = file.filestatus;
                        //add to imgsManager
						imgsManagers[item.category].add(item);
                        //imgsManager.add(new ImgItem(imageWrap));
                    };
                },
                onSWFReady: function(){
	                self.setPicType(self.postData.picType);
                }
            });
        },
        initTabs: function(){
            var self = this,
                uploadify = self.uploader,
                isLocked = appOptions.isLocked,
                tabs = $('#J_imgCategory a[data-toggle="tab"]');

            tabs.on('shown.bs.tab', function (e) {
                var target = $(e.target),
                    category = target.attr('data-category'),
                    type = target.attr('data-pic-type');

                self.setPicType(type);
                imgsManager = imgsManagers[category];
                uploadify.uploadify('settings', 'queueID', category);
                (isLocked||category==1) && self.lockResidence(type);
                
            });
            $.each(tabs, function(key, item){
            	var target = $(item),
            		category = target.attr('data-category'),
                    type = target.attr('data-pic-type');
                    
	        	!remoteImgsStatus[type] && self.getRemotePics(type, category);    
            });
            

        },
        lockResidence: function(type){
        	var msg = $('#J_lockedMsg'),
        		residencePic = $('#J_imgsWrap-3'),
        		uploaderWrap = $('#J_uploadWrap');
        		
	    	if(type==0){				
		    	msg.show();
		    	uploaderWrap.hide();	
	    	}else{
	    		uploaderWrap.show();
		    	msg.hide();		
	    	}; 
	    	residencePic.find('.hm-img-controls').css('display', 'none');
	    	 
        },
		getRemotePics: function(type, category){
			var self = this;
			
			$.ajax({
				url: '/ajax/getPicListByHouseId.controller',
				method: 'get',
				dataType: 'json',
				data: {type: type, houseId: self.postData.houseId},
				success: function(data){
					var bizData = data.bizData,
						wrap = $('#'+category),
						manager = imgsManagers[category];

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
            var managers = imgsManagers,
                info,
                rs = [];

            $.each(typeKeyMap, function(key, value){
                info = managers[value].getPicInfo();
                info.items && info.items.length && rs.push(info);
            });
            return rs;
        },
        submit: function(url,requestNewHouse){
            var self = this,
                picInfo = self.getAllPicInfo();

            $.ajax({
                url: '/ajax/addOrUpdateSort.controller',
                method: 'post',
                data: {
                    houseId: self.postData.houseId,
                    data: $.toJSON({pics: picInfo})
                },
                success: function(rs){
                    if(rs.code==1){
                        location.href=url;
                    };
                }
            });
            
            if(requestNewHouse == true){
            	$.ajax({
                    url: '/ajax/requestAddNewHouse.controller',
                    method: 'post',
                    data: {
                        houseId: self.postData.houseId,
                    },
                    success: function(rs){
                    }
                });	
            }
            //提交动作    
            //form.submit();
        }
    };

    App.init();
});
