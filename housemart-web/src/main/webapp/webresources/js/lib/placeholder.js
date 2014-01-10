/**
 * @description: placeholder module for jQuery
 * @warns: you must use placeholder after domready, or it will cause some problem in IEs
 * @version: v1.0
 * @blog: 12d.iteye.com
 * @email: next_100@sina.com
 * @author: xuwei.chen
 */
define(function(require, exports, module){
 "use strict";
 
var C = require('jquery'),
	doc = document,
    mix = C.extend,
    trim = C.trim,
    browser = C.browser,
    supportNativePlaceHolder = 'placeholder' in doc.createElement('input'),
    // @const
    INPUT_TYPE_RE = /(<\s*input.*\s+type=['"]*)(?:\w+)(['"]*\s+.*>)/gi,
    EMPTY = '',
    IElte8 = browser.msie && parseFloat(browser.version)<9,
    //IElte8 = true,
    IElte8_PASSWORD = false,
    PASSWORD = 'password',
    FAKE_DOM = 'data-fakedom',
    FAKED = 'data-faked',
    DEFAULT_OPTIONS = {
            text: EMPTY,
            attrName: 'placeholder',
            cls: 'placeholder' // class supports browsers that have no native placeholder expando
    };



function triggerPlaceHolder(data, force) {
    var self = C(this),
            v = trim(self.val()),
            p = data.txt,
            hasVal = !(v==EMPTY || v==p);
    
    self[(hasVal || force) ? 'removeClass' : 'addClass'](data.cls);
    return hasVal;
}
function fakePassword(dom){
    var div = C('<div></div>'),
            fake;

    dom = C(dom);
    dom.clone().removeAttr('id').removeAttr('name').attr('real-type', 'password').appendTo(div); 
    div.html(div.html().replace(INPUT_TYPE_RE, "$1text$2"));
    dom.after(fake = div.children().first());
    dom.hide();
    fake.bind('focus', function(){
            C(this).hide();
            dom.show();
            dom[0].focus();
    });
    return fake;
}

function makeSimulator(isPassword, attrName){
    return {
            focus: function (evt){
                    var dom = C(this);
                    /* ie=9 */
                    if (isPassword && dom.data(FAKED)){
                            this.setAttribute('type', 'password');//  jQuery is not allowed to set type of input
                            dom.data(FAKED, false);
                    } 
                    if(!triggerPlaceHolder.call(this, evt.data, true)){
                            dom.val(EMPTY);
                    };

            },
            blur: function (evt){
                    var data = evt.data,
                            fake, txt,
                            dom = C(this);

                    data.txt = dom.data('placeholder')._placeholder||data.txt;
                    if(!triggerPlaceHolder.call(this, data)){
                            dom.val(txt=data.txt);
                            if(fake = dom.data(FAKE_DOM)) { //ie<9
                                    dom.hide();
                                    fake.show().val(txt);
                            }else if(isPassword){ //ie=9
                                    this.setAttribute('type', 'text');
                                    dom.data(FAKED, true);
                            };
                    };
            }
    }

}
function forcePlaceHolder(dom, cls, txt, isPassword, attrName){
    var si = makeSimulator(isPassword, attrName),
            obj = {cls: cls, txt: txt};
    
    (IElte8_PASSWORD = isPassword && IElte8) && dom.data(FAKE_DOM, fakePassword(dom).addClass(cls).val(txt));        
    dom.bind('focus', obj, si.focus).bind('blur', obj, si.blur);
}
/**
* @param dom {DOM} the DOM element waiting for initialize placeholder
* @param options {Object} (optical) options for placeholder
*/
function PlaceHolder(dom, options){
    var self = this,
        txt;

    
    self.dom = dom;
    options = mix(self.options, DEFAULT_OPTIONS, options||{});
    (txt=options.text) && self.text(txt);
    if(!dom || supportNativePlaceHolder && !options.fake) return this;
    self.initialize(options);
    dom.data('placeholder', self);
    dom.trigger('blur');
}
PlaceHolder.prototype = {
    options: {},
    constructor: PlaceHolder,
    initialize: function (options){
            var dom = this.dom,
            	placeholder = options.attrName,
                    txt;

            forcePlaceHolder(dom, options.cls, txt||dom.attr(placeholder), dom.attr('type') == PASSWORD, placeholder);
    },
    _placeholder: EMPTY,
    /**
     * @setter, @getter for placeholder attribute
     * @param placeholder {String} (optical), placeholder text, no args will be a getter for placeholder
     * @returns {PlaceHolder||String}
     */
    text: function (str){
            var self = this,
            	placeholder = this.options.attrName,
                    str = str && trim(str.toString());
			
            self._placeholder = str||self.dom.attr(placeholder)|| EMPTY;
            return str ? (function (sf){ // setter
                    return sf.dom.attr(placeholder, self._placeholder = str);
            })(self) : (function (sf){ //getter
                    return sf._placeholder;// || sf.dom.attr('placeholder') || EMPTY
            })(self);
    }
}

C.fn.placeholder = function (options){
        return this.data('placeholder') || new PlaceHolder(this, options||{});
};
module.PlaceHolder = PlaceHolder;
});