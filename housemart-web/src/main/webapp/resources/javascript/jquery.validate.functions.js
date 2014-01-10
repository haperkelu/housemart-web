(function validateFunctions() {
	/**//* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			form.submit();
		}
	});

	// 字符验证
	jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[\u0391-\uFFE5\w\._|\_|\.|-]+$/.test(value);
	}, "只能包括中文字、英文字母、数字、下划线、中横行或者句点");

	// 验证邮箱
	jQuery.validator
			.addMethod(
					"checkEmail",
					function(value, element) {
						var myreg = /^[a-zA-Z0-9_|\_|\.|-]+@[a-zA-Z0-9_|\_|\.|-]+\.[a-zA-Z]{2,3}$/;
						if (value != '') {
							if (!myreg.test(value)) {
								return false;
							}
						}
						;
						return true;
					}, "请输入有效的E_mail！()");

	// 中文字两个字节
	jQuery.validator.addMethod("byteRangeLength", function(value, element,
			param) {
		var length = value.length;
		for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
				length++;
			}
		}
		return this.optional(element)
				|| (length >= param[0] && length <= param[1]);
	}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");

	// 身份证号码验证
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
		return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
	}, "请正确输入您的身份证号码");

	// 企业ID验证
	jQuery.validator.addMethod("isShortName", function(value, element) {
		var companyId = /^([a-zA-Z\d\._\u4e00-\u9fa5_|\_|\.|-]+)$/;
		return this.optional(element) || (companyId.test(value));
	}, "请正确填写公司账户名，");

	// 企业ID验证
	jQuery.validator.addMethod("isCompanyId", function(value, element) {
		var companyId = /^([a-zA-Z\d\._\u4e00-\u9fa5_|\_|\.|-]+)$/;
		return this.optional(element) || (companyId.test(value));
	}, "请正确填写公司账户名，");

	// 手机号码验证
	jQuery.validator.addMethod("isMobile", function(value, element) {
		var length = value.length;
		var mobile = /^[\d]{1,20}$/;
		return this.optional(element) || (mobile.test(value));
	}, "请正确填写您的手机号码");

	// 电话号码验证
	jQuery.validator.addMethod("isTel", function(value, element) {
		var tel = /^\d{3,4}-?\d{7,9}$/; // 电话号码格式010-12345678
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的电话号码");

	// 联系电话(手机/电话皆可)验证
	jQuery.validator.addMethod("isPhone", function(value, element) {
		var length = value.length;
		var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
		var tel = /^\d{3,4}-?\d{7,9}$/;
		return this.optional(element)
				|| (tel.test(value) || mobile.test(value));

	}, "请正确填写您的联系电话");

	// 邮政编码验证
	jQuery.validator.addMethod("isZipCode", function(value, element) {
		var tel = /^[0-9]{6}$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的邮政编码");
	
	// 邮政编码验证
	jQuery.validator.addMethod("isDate", function(value, element) {
		var tel = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的日期");
	jQuery.validator.addMethod('greaterThan', function(value, element, param) {
		return ( parseInt(value) > parseInt(jQuery(param).val()));
	}, '值必须大于' );
	jQuery.validator.addMethod('greaterEqualTo', function(value, element, param) {
		return ( parseInt(value) >= parseInt(jQuery(param).val()));
	}, '值必须大于等于' );
	jQuery.validator.addMethod('lesserThan', function(value, element, param) {
		return ( parseInt(value) < parseInt(jQuery(param).val()));
	}, '值必须小于' );
	jQuery.validator.addMethod('lesserEqualTo', function(value, element, param) {
		return ( parseInt(value) <= parseInt(jQuery(param).val()));
	}, '值必须小于等于' );
	
})();

$.validator.rules={
	loginEmail:"{required:true,checkEmail:true,messages:{required:'请输入登录邮箱',checkEmail:'请输入登录邮箱'}}",
	loginPassword:"{required:true,messages:{required:'请输入密码'}}",
	email:"{required:true,checkEmail:true,byteRangeLength:[1, 128],messages:{required:'请输入您的常用邮箱',byteRangeLength:'请输入正确的邮箱地址',checkEmail:'请输入正确的邮箱地址'}}",
	password:"{required:true,messages:{required:'请输入密码'}}",
	rePassword:"{required:true,equalTo:'#regPassword',messages:{required:'请输入确认密码',equalTo:'两次密码输入不一致'}}",
	name:"{required:true,byteRangeLength:[1, 128],messages:{required:'请输入姓名',byteRangeLength:'请输入有效的姓名'}}",
	license:"{required:true,messages:{required:'确认用户协议后才能继续申请'}}",
	captcha:"{required:true,equalTo:'#validCode',messages:{required:'请输入验证码',equalTo:'验证码有误'}}",
	mobile:"{required:true,isMobile:true,messages:{required:'请输入您的手机号码',isMobile:'请输入正确的手机号码'}}",
	residenceName:"{required:true,byteRangeLength:[1, 128],messages:{required:'请输入小区名称',byteRangeLength:'请输入有效的小区名称'}}",
	address:"{required:true,byteRangeLength:[1, 128],messages:{required:'请输入地址',byteRangeLength:'请输入有效的地址'}}",	
	headCount:"{required:true,digits:true,messages:{required:'请输入小区户数',digits:'请输入整数'}}",
	parking:"{required:true,digits:true,messages:{required:'请输入停车位',digits:'请输入整数'}}",
	greenRate:"{required:true,digits:true,messages:{required:'请输入绿化率',digits:'请输入整数'}}",
	volumeRate:"{number:true,messages:{digits:'请输入小数'}}",
	propertyFee:"{required:true,byteRangeLength:[1, 128],messages:{required:'请输入物业费',byteRangeLength:'请输入有效的物业费'}}",	
	developer:"{required:true,byteRangeLength:[1, 128],messages:{required:'请输入开发商',byteRangeLength:'请输入有效的开发商'}}",	
	finishedTime:"{required:true,messages:{required:'请输入竣工时间'}}",
	number:"{required:true,number:true,messages:{required:'请输入小数',digits:'请输入小数'}}"
}


//根据规则创建Js Validate
$.validator.draw = function(){
	$(".validate").each(function(){
		var vType = $(this).attr("_vType");
		if(vType != undefined){
			var rule = $.validator.rules[vType];
			if(rule != undefined){
				$(this).addClass(rule);
			}
		}
	});
}