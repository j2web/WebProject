jQuery.noConflict();
var PCIS = {
	Util : {
		navigate : function(url) {
			window.location.href = url;
		}
	},
	Progress : {

		show : function() {
			var progressDiv = jQuery("#progressDiv");
			var clientWidth = document.documentElement.clientWidth;
			var bodyScrollTop = jQuery(document.documentElement).scrollTop();
			var bodyScrollLeft = jQuery(document.documentElement).scrollLeft();

			var left = clientWidth + bodyScrollLeft - progressDiv.width()
					- parseInt(progressDiv.css("paddingLeft"), 10)
					- parseInt(progressDiv.css("paddingRight"), 10) - 3;
			var top = bodyScrollTop;
			progressDiv.css( {
				top : top,
				left : left
			});

			progressDiv.show();
			progressDiv.showMask();

		},
		hide : function() {
			var progressDiv = jQuery("#progressDiv");
			progressDiv.hide();
			progressDiv.hideMask();
		}
	},
	Layout : {
		onReady : function() {
			PCIS.Layout.initLayout();
			jQuery(window).bind('resize', PCIS.Layout.initLayout);

			var menuImage = jQuery("#menuImage");
			menuImage.click( function() {
				jQuery("#menuContainer").hide();
				jQuery("#sidebar").show();
				jQuery("#splitLine").hide();

				hideLeftMenu();

			});
			menuImage.hover( function() {
				jQuery(this).toggleClass("menuImage-over");
			}, function() {
				jQuery(this).toggleClass("menuImage-over");
			});

			var sidebar = jQuery("#sidebar");
			sidebar.click( function() {
				jQuery("#menuContainer").show();
				jQuery("#sidebar").hide();
				jQuery("#splitLine").show();

				showLeftMenu();
			});

			sidebar.hover( function() {
				jQuery(this).toggleClass("sidebar-over");
			}, function() {
				jQuery(this).toggleClass("sidebar-over");
			});
		},
		initLayout : function() {

			var winWidth = 0;
			var winHeight = 0;

			if (document.documentElement
					&& document.documentElement.clientHeight
					&& document.documentElement.clientWidth) {
				winHeight = document.documentElement.clientHeight;
				winWidth = document.documentElement.clientWidth;

			}

			var headerHeight = 0;
			var header = jQuery("#header")
			if (header.css("display") == "block")
				headerHeight = header.height();

			winHeight = winHeight - headerHeight;

			var mainContent = jQuery("#mainContent");
			if (document.documentElement.scrollWidth <= document.documentElement.clientWidth)
				mainContent.width(winWidth - 4);

			if (document.documentElement.scrollHeight <= document.documentElement.clientHeight)
				mainContent.height(winHeight - 4);

			/* show welcome to top-right side */
			var welcome = jQuery("#welcome");
			var left = winWidth - welcome.width();
			welcome.css("left", left);

		},

		table : {
			onRowMouseOver : function(e) {
				if (!jQuery(e).attr("selected")) {
					e.style.cursor = "pointer";
					e.style.backgroundColor = '#FAE6B0';
					/* jQuery("td", e).css("color", '#FFFFFF'); */
				}
			},
			onRowMouseOut : function(e) {
				if (!jQuery(e).attr("selected")) {
					e.style.cursor = "default";
					e.style.backgroundColor = '#FFFFFF';
					/* jQuery("td", e).css("color", '#000000'); */
				}
			},
			onRowClick : function(e) {
				e.style.backgroundColor = '#274f4d';
				jQuery("td", e).css("color", '#FFFFFF');
				var o = jQuery(e);

				var table = o.parents("table")[0];
				jQuery("tr", table).each( function() {
					if (e != this) {
						this.style.backgroundColor = '#FFFFFF';
						jQuery("td", this).css("color", '#000000');
					}
					jQuery(this).attr("selected", e == this);
				});
			}
		}
	},
	Validation : {

		showErrorMsgBox : function(evt, e) {

			var errMsgBox = jQuery("#" + e.getAttribute("errMsgBox"));
			var event = jQuery.event.fix(evt);

			errMsgBox.css( {
				top : event.pageY + 5,
				left : event.pageX + 5
			});
			if (errMsgBox.css("display") == "none") {
				errMsgBox.show();
				errMsgBox.showMask();
			} else {
				var frame_id = errMsgBox.attr("mask");
				if (frame_id) {
					var mask = jQuery("#" + frame_id);
					mask.css( {
						top : errMsgBox.offset().top,
						left : errMsgBox.offset().left
					});
				}
			}

		},
		hideErrorMsgBox : function(e) {
			var errMsgBox = jQuery("#" + e.getAttribute("errMsgBox"));
			errMsgBox.hide();
			errMsgBox.hideMask();
			errMsgBox.hideShadow();
		},
		bind : function(e) {

			var f = jQuery(e).parents(".error-container");
			var input = jQuery("input,select,textarea", f).eq(0);
			/* for checkbox or radiobutton list */
			var boxlist = jQuery("input:checkbox,input:radio", f);

			if (boxlist.size() > 1) {
				input = f;
				boxlist.css("borderWidth", 0);
				f.addClass("error");
			}
			/* for IE6, style is invalid except width and height for SELECT tag */
			if (jQuery.browser.msie && jQuery.browser.version < 7) {
				var listboxlist = jQuery("select", f);
				if (listboxlist.size() > 0) {
					var width = listboxlist.eq(0).width();
					listboxlist
							.wrap("<div class='error' style='padding-bottom:3px;width:"
									+ width + "'></div>");
				}
			}
			var errMsgBox = jQuery(".errMsgBox", f).eq(0);
			var errMsgbox_id = "errMsgBox_" + jQuery.random();
			errMsgBox.attr("id", errMsgbox_id);
			input.attr("errMsgBox", errMsgbox_id);

			jQuery(document.body).prepend(errMsgBox);

			input.mousedown( function() {
				input.attr("selected", true);
				PCIS.Validation.hideErrorMsgBox(this);

			});

			input.blur( function(evt) {
				input.removeAttr("selected");
				PCIS.Validation.hideErrorMsgBox(this);

			});
			input.mouseenter( function(evt) {
				if (input.attr("selected"))
					return;

				PCIS.Validation.showErrorMsgBox(evt, this);
			});
			input.mouseleave( function(evt) {
				input.removeAttr("selected");
				PCIS.Validation.hideErrorMsgBox(this);

			});
			input.mousemove( function(evt) {
				if (input.attr("selected"))
					return;

				var event = jQuery.event.fix(evt);
				var follow = true;
				var target = event.target;
				if (target.tagName.toLowerCase() == "option"
						&& jQuery(target).parent().attr("size") == 1)
					follow = false;
				if (follow) {
					PCIS.Validation.showErrorMsgBox(evt, this);

				}
			});

		}

	}
};