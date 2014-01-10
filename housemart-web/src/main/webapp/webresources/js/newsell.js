define(function(require, exports, module){
    'use strict';
    require('lib/bootstrap');

    var Validate = require('lib/form/validate'),
        Field = require('lib/form/field'),
        TagSelector = require('lib/tagselector'),
        Header = require('header'),
        NewSell,
        $ = require('jquery'),
		isSubmit = false,
        validator = new Validate(),
        releaseForm = $('#J_releaseForm'),
        errorClass = 'has-error',
        selectedTagsInput = $('#J_selectedTags'),
        tagsIds = $('#J_tagsIds'),
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
    function fillSelectedTags(){
        selectedTagsInput.val(this.getMappedSelected().join(','));
        tagsIds.val(this.getMappedSelected(function(item){
            return item.getAttribute('data-id');
        }).join(','));
    };

    NewSell = {
    	initTooltip: function(){ 
            var defaultOptions = {
                trigger: 'focus',
                placement: 'right'
            };
            $('[data-original-title]').tooltip(defaultOptions);
        },
        addFields: function(){
            var self = this;
            //add fields for form

            self.blockField =  new Field({
                dom: $('#J_blockNo'),
                rules: [isNotEmpty],
                onCheck: checkHandler,
                required: false
            });

            validator.addFields([
                new Field({
                    dom: $('#J_residence'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                /*
                new Field({
                    dom: $('#J_residenceName'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                */
                new Field({
                    dom: $('#J_houseType'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: true
                }),
                self.blockField,
                new Field({
                    dom: $('#J_cellNo'),
                    rules: [isNotEmpty],
                    onCheck: checkHandler,
                    required: false
                }),
                new Field({
                    dom: $('#J_area'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_cover'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: false
                }),
                new Field({
                    dom: $('#J_price'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_rooms'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_parlor'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_washroom'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_veranda'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_floor'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_decorating'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_direction'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_history'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: true
                }),
                new Field({
                    dom: $('#J_special'),
                    rules: [rNumber],
                    onCheck: checkHandler,
                    required: false
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
        initTagSelector: function(selectedLabels){
            var tags;

            tags = new TagSelector({
                selectedCls: 'btn-info',
                wrap: $('#J_tagsWrap'),
                itemFilter: function(tag, labels){
                    //return labels.indexOf($(tag).attr('data-id'))>-1;
                    return $.inArray($(tag).attr('data-id'), labels)>-1;
                },
                onSelect: $.proxy(fillSelectedTags, tags),
                onCancel: $.proxy(fillSelectedTags, tags)
            });
            selectedLabels && selectedLabels.length && tags.selectByLabel(selectedLabels);
            return this;        
        },
        initSelectName: function(){
            var name = $('#J_residenceName');

            $('#J_residence').bind('change', function(e){
            	var txt = $.trim($(this).find('option:selected').text());
                name.val($.trim(txt=='选择小区'?'':txt.substr(1)));
            });
            name.val()!='' && name.trigger('change');
            return this;
        },
        initSelectType: function(){
            var type = $('#J_houseType'),
                self = this,
                isRequired = function(v){
                    return v==4 || v == 5;  //4 : 公寓, 5: 老公房
                };

            type.bind('change', function(e){
                var required = isRequired($(this).val());

                self.blockField.required = required;
                type.parents('.form-group').next().find('.hm-required').css('display', required ? '': 'none');
            });
            type.trigger('change');
            return self; 
        },
        initCtrlTags: function(){
            var btn = $('#J_ctrlTags'),
                view = $('#J_tagsCtrlWrap');

            btn.bind('click', function(e){
                var isToggled = btn.attr('data-toggled') == 1;

                e.preventDefault();
                view[isToggled ? 'hide' : 'show']();
                btn.attr('data-toggled', isToggled ? 0 : 1);
            });
            return this;
        },
        initSalePayment: function(){
            var pay = $('#J_salePay'),
            payment=$('#J_payment');

            pay.bind('change', function(){
                payment.val(pay.find("option:selected").html());
            });
            return this;
        },
        init: function(options){
            this.bindSubmit()
                .addFields()
                .initSelectName()
                .initTagSelector(options.selectedLabels)
                .initSelectType()
                .initSalePayment()
                .initCtrlTags()
                .initTooltip();
                Header.setCurrent('house');
        }
    }
    
    NewSell.init({
        selectedLabels: tagsIds.val() && tagsIds.val().split(',')
    });
});
