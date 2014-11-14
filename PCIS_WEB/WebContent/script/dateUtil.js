/* DateUtil written by justin*/
PCIS.DateUtil = {
			dateDelimiter : '/',
			prevValueLength : 0,
			checkDateDelimiter:function(datePattern){
				var str = datePattern;
				if (str != '') dateDelimiter = str.charAt(2);
				if (dateDelimiter != '/' && dateDelimiter != '-') dateDelimiter = '/';
			},
			onReady:function(datePattern){
				this.checkDateDelimiter(datePattern);
				this.bindAutoCompleterConverter();
			},
			calcPrevDateLength:function(thisObj){
				prevValueLength = jQuery(thisObj).val().length;
				return true;
			},
			isNumber:function(e){
				    if (jQuery.browser.msie) {   
				        if ( ((event.keyCode > 47) && (event.keyCode < 58)) ||
				        	 ((event.keyCode > 95) && (event.keyCode < 106))) {   
				            return true;   
				        } else {   
				            return false;   
				        }   
				    } else {   
				        if ( ((e.which > 47) && (e.which < 58)) ||
				        	 ((e.which > 95) && (e.which < 106))) {   
				            return true;   
				        } else {   
				            return false;   
				        }   
				    }   
				},
				isExclusiveKey:function(e){
					if (jQuery.browser.msie) {   
				        if ((event.keyCode == 9) || (event.keyCode == 13)||
				             (event.keyCode == 8) || (event.keyCode == 46)||
				             (event.keyCode == 37) || (event.keyCode == 39)||
				             (event.keyCode == 16) || (event.keyCode == 17)||(event.keyCode == 18)||
				             (event.keyCode == 35) || (event.keyCode == 36)||
				             (event.keyCode==83 || event.altKey)) {   
				            return true;   
				        } else {   
				            return false;   
				        }   
				    } else {   
				        if ( (e.which == 9) || (e.which == 13)||
				             (e.which == 8) || (e.which == 46)||
				             (e.which == 37) || (e.which == 39)||
				             (e.which == 16) || (e.which == 17)||(e.which == 18)||
				             (e.which == 35) || (e.which == 36)||
				             (e.keyCode==83 || e.altKey)) {   
				            return true;   
				        } else {   
				            return false;   
				        }   
				    }	
			},
			limitLength:function(thisObj){
				 var dateValue = jQuery(thisObj).val();
				 if (dateValue.length >= 10) jQuery(thisObj).val(dateValue.substring(0,10));
			},
			addDateMask:function(thisObj){
				 var dateValue = jQuery(thisObj).val();
				 var nonDigitPattern = /\D/;
				 if(dateValue.length==2 && prevValueLength == 1 && !nonDigitPattern.test(dateValue.substring(0,2)))
				 {
				  dateValue = dateValue + dateDelimiter;
				 }
				 if(dateValue.length==5 && prevValueLength == 4 && !nonDigitPattern.test(dateValue.substring(3,5)))
				 {
				  dateValue = dateValue + dateDelimiter;
				 }
				 jQuery(thisObj).val(dateValue);
				 return true;
			},
			convertDate:function(thisObj){
				var srcDateStr = jQuery(thisObj).val();
				var destDateStr = srcDateStr;
				var srcDatePattern = /[0-9]{8,}/;
				if (srcDatePattern.test(srcDateStr)){
							destDateStr = srcDatePattern.exec(srcDateStr)[0];
							destDateStr = destDateStr.substring(0,2)+dateDelimiter+destDateStr.substring(2,4)+dateDelimiter+destDateStr.substring(4,8);
				}
				jQuery(thisObj).val(destDateStr);
		    },
			bindAutoCompleter:function(){
			    jQuery(".rich-calendar-input").keyup(function(){
			    	PCIS.DateUtil.addDateMask(jQuery(this));
					}).keydown(function(){
						PCIS.DateUtil.calcPrevDateLength(jQuery(this));
					});
			},
			bindConverter:function(ids){
			    jQuery(ids).blur(function(){
			    		PCIS.DateUtil.convertDate(jQuery(this));
					}).change(function(){
						PCIS.DateUtil.convertDate(jQuery(this));
					});
			},
			bindAutoCompleterConverter:function(){
				/*method1*/
			    /*jQuery(".rich-calendar-input").keyup(function(e){
			    		PCIS.DateUtil.limitLength(jQuery(this));
			    		PCIS.DateUtil.addDateMask(jQuery(this));
					}).keydown(function(e){
						if (!PCIS.DateUtil.isNumber(e)) return false;
						PCIS.DateUtil.calcPrevDateLength(jQuery(this));
					}).blur(function(){
						PCIS.DateUtil.limitLength(jQuery(this));
			    		PCIS.DateUtil.convertDate(jQuery(this));
			    		prevValueLength = 0;
					}).change(function(){
						PCIS.DateUtil.limitLength(jQuery(this));
						PCIS.DateUtil.convertDate(jQuery(this));
					});*/
			    /*method2*/
			    jQuery(".rich-calendar-input").keyup(function(e){
			    	if (!PCIS.DateUtil.isExclusiveKey(e)){
			    		PCIS.DateUtil.addDateMask(jQuery(this));
			    	}
				}).keydown(function(e){
					if (!PCIS.DateUtil.isExclusiveKey(e)){
						if (!PCIS.DateUtil.isNumber(e)) return false;
					}
					PCIS.DateUtil.calcPrevDateLength(jQuery(this));
				}).blur(function(){
		    		PCIS.DateUtil.convertDate(jQuery(this));
		    		prevValueLength = 0;
				}).change(function(){
					PCIS.DateUtil.convertDate(jQuery(this));
				}).attr("maxlength",10);
			    
			    jQuery(".rich-calendar-button").removeAttr("tabIndex");
			}
};