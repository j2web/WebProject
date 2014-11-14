jQuery.random = function() {
	var vNum = Math.random();
	vNum = Math.round(vNum * 100000000);
	return vNum;
}
jQuery.fn
		.extend( {
			showMask : function(container) {
				if (!jQuery.browser.msie)
					return this;

				return this
						.each( function() {
							var _object = jQuery(this);
							var frame_id, _frame;
							frame_id = _object.attr("mask");

							if (!container) {
								container = jQuery(document.body);
							} else {
								container = jQuery(container);
							}

							if (!frame_id) {
								var vNum = jQuery.random();
								frame_id = "frame_" + vNum;

								var html = "<iframe frameborder=0 scrolling=no tabindex=-1 style='display:none;position:absolute;top:-9999;left:-9999;overflow:hidden' id="
										+ frame_id + " />";
								_frame = jQuery(html);
								container.prepend(_frame);
								_object.attr("mask", frame_id);
							} else {
								_frame = jQuery("#" + frame_id);
								_frame.show();
							}

							if (container.css("position") == "absolute") {
								_frame.css( {
									top : _object.position().top,
									left : _object.position().left
								});
							} else {

								_frame.css( {
									top : _object.offset().top,
									left : _object.offset().left
								});
							}

							_frame.width(this.offsetWidth);
							_frame.height(this.offsetHeight);
							var zIndex = _object.css("zIndex");
							_frame.css("zIndex", zIndex == undefined ? -1 : parseInt(zIndex, 10) - 1);
							_frame.show();
						});
			},
			hideMask : function() {
				if (!jQuery.browser.msie)
					return this;
				return this.each( function() {
					var _object = jQuery(this);
					var frame_id, _frame;
					frame_id = _object.attr("mask");

					if (frame_id) {
						_frame = jQuery("#" + frame_id);
						_frame.hide();
					}
				});
			},
			showShadow : function(s, container) {
				var offsetTop = 0;
				var backgroundColor = "#000000";
				var offsetLeft = 0;

				if (!container) {
					container = jQuery(document.body);
				} else {
					container = jQuery(container);
				}

				if (s) {
					offsetTop = s.offsetTop || offsetTop;
					offsetLeft = s.offsetLeft || offsetLeft;
					backgroundColor = s.backgroundColor || backgroundColor;
				}
				if (offsetTop == 0 && offsetLeft == 0)
					return this;
				return this.each( function() {
					var _object = jQuery(this);
					var div_id, _div;
					div_id = _object.attr("shadow");

					if (!div_id) {
						var vNum = jQuery.random();
						div_id = "div_" + vNum;

						var html = "<div tabindex=-1 style='display:block;position:absolute;' id=" + div_id + " />";
						_div = jQuery(html);

						container.append(_div);

						_object.attr("shadow", div_id);
					} else {
						_div = jQuery("#" + div_id);
						_div.show();
					}

					_div.css("backgroundColor", backgroundColor);

					if (container.css("position") == "absolute") {
						_div.css("top", _object.offset().top + offsetTop - container.offset().top);
						_div.css("left", _object.offset().left + offsetLeft - container.offset().left);
					} else {
						_div.css("top", _object.offset().top + offsetTop);
						_div.css("left", _object.offset().left + offsetLeft);
					}
					_div.width(_object[0].offsetWidth);
					_div.height(_object[0].offsetHeight);

					var zIndex = _object.css("zIndex");

					_div.css("zIndex", zIndex == undefined ? -1 : parseInt(zIndex, 10) - 1);
					_div.showMask(container);

				});
			},
			hideShadow : function() {
				return this.each( function() {
					var _object = jQuery(this);
					var div_id, _div;
					div_id = _object.attr("shadow");

					if (div_id) {
						_div = jQuery("#" + div_id);
						_div.hide();
						_div.hideMask();
					}
				});
			},
			contain : function(node) {
				var result = false;
				this.each( function() {
					while (node != null && node != document.body) {
						if (node == this) {
							result = true;
							break;
						}

						node = node.parentNode;
					}
				});

				return result;
			}
		})
