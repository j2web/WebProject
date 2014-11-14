PCIS.Menu = {
	j_menu : [],

	/* offset_arg:{offsetTop:2,offsetLeft:2,backgroundColor:"#9c9c9c"},*/

	onReady : function() {
		j_menu = jQuery("#menu");

		var subMenu = jQuery(".subMenu:first-child", j_menu);
		/*
		 * subMenu.showMask(j_menu); subMenu.showShadow(offset_arg,j_menu);
		 */

		j_menu.bind("mouseout", PCIS.Menu.hideMenu);
		jQuery("tr", j_menu).hover(PCIS.Menu.mouseEnter, PCIS.Menu.mouseLeave).click(PCIS.Menu.navigate);
	},

	mouseEnter : function(evt) {
		var j_menuItem;
		if (evt.target.tagName != "TR")
			j_menuItem = jQuery(evt.target).parents("tr");
		else
			j_menuItem = jQuery(evt.target);

		PCIS.Menu.highlightRow(j_menuItem, evt, true);
		PCIS.Menu.showSubMenu(j_menuItem);
	},
	mouseLeave : function(evt) {
		var j_menuItem;
		if (evt.target.tagName != "TR")
			j_menuItem = jQuery(evt.target).parents("tr");
		else
			j_menuItem = jQuery(evt.target);
		PCIS.Menu.highlightRow(j_menuItem, evt, false);

	},
	showSubMenu : function(j_menuItem) {

		var _subMenu, subMenu_id;
		var j_parentMenu = PCIS.Menu.getCurrentSubMenu(j_menuItem);
		subMenu_id = "subMenu_" + j_menuItem.attr("id");

		if (subMenu_id) {
			j_parentMenu.attr("subMenu", subMenu_id);
			_subMenu = jQuery("#" + subMenu_id);
			_subMenu.css("zIndex", parseInt(j_parentMenu.css("zIndex"), 10) + 2);
			_subMenu.css("top", j_menuItem.offset().top - j_menu.offset().top);
			_subMenu.css("left", j_parentMenu.offset().left + j_parentMenu.width() - j_menu.offset().left);
			/*
			 * var left=j_parentMenu.offset().left + j_parentMenu.width() -
			 * j_menu.offset().left; var top=j_menuItem.offset().top -
			 * j_menu.offset().top;
			 * 
			 * //default right,if right area has enough space if
			 * (j_parentMenu.offset().left +
			 * j_parentMenu.width()+_subMenu.width()<jQuery(document.body).width()){
			 * _subMenu.css("left",left); } else{//if right area has not enough
			 * space,use left area if (_subMenu.width()<=j_parentMenu.offset().left){
			 * _subMenu.css("left",j_parentMenu.offset().left
			 * -_subMenu.width()-j_menu.offset().left); } else{
			 * _subMenu.css("left",left); } }
			 * 
			 * //for top //alert(jQuery("form").height()) ; if
			 * (j_menuItem.offset().top +_subMenu.height()<=jQuery(document.body).height())
			 * _subMenu.css("top", top); else{//if bottom area have not enough
			 * space if (j_menuItem.offset().top <_subMenu.height())//if top
			 * area has not enough space at the same time,sub menu should
			 * overflow bottom _subMenu.css("top", top); else
			 * _subMenu.css("top",j_menuItem.offset().top+
			 * j_menuItem.height()-_subMenu.height()-j_menu.offset().top+3); }
			 * 
			 */
			_subMenu.show();
			_subMenu.showMask(j_menu);
			/* _subMenu.showShadow(offset_arg,j_menu); */
		}
	},

	highlightRow : function(j_menuItem, evt, enter) {
		var j_parentMenu;
		var tds = jQuery("td", j_menuItem);

		var _subMenu, subMenu_id;

		j_parentMenu = PCIS.Menu.getCurrentSubMenu(j_menuItem);

		if (enter) {
			j_menuItem.attr("className", "overMenu");
			tds.each( function(i) {
				if (i == 0)
					jQuery(this).attr("className", "overMenuLeftCell");
				if (i == 1) {
					jQuery(this).attr("className", "overMenuCenterCell");
				}
				if (i == 2) {
					if (jQuery(this).attr("className") == "outMenuCenterCell_HasChild") {
						jQuery(this).attr("className", "overMenuCenterCell_HasChild");
					}
					if (jQuery(this).attr("className") == "outMenuCenterCell_NoChild") {
						jQuery(this).attr("className", "overMenuCenterCell_NoChild");
					}
				}
				if (i == 3) {
					jQuery(this).attr("className", "overMenuRightCell");
				}
			});

			if (j_parentMenu.attr("subMenu") && j_parentMenu.attr("id") != j_parentMenu.attr("subMenu")) {
				subMenu_id = j_parentMenu.attr("subMenu");

				while (subMenu_id) {
					_subMenu = jQuery("#" + subMenu_id);
					_subMenu.hide();
					_subMenu.hideMask();
					_subMenu.hideShadow();

					subMenu_id = _subMenu.attr("subMenu");
				}
			}
		} else {
			/* for mouse leave */
			j_menuItem.attr("className", "outMenu");
			tds.each( function(i) {
				if (i == 0)
					jQuery(this).attr("className", "outMenuLeftCell");
				if (i == 1) {
					jQuery(this).attr("className", "outMenuCenterCell");
				}
				if (i == 2) {
					if (jQuery(this).attr("className") == "overMenuCenterCell_HasChild") {
						jQuery(this).attr("className", "outMenuCenterCell_HasChild");
					}
					if (jQuery(this).attr("className") == "overMenuCenterCell_NoChild") {
						jQuery(this).attr("className", "outMenuCenterCell_NoChild");
					}
				}
				if (i == 3) {
					jQuery(this).attr("className", "outMenuRightCell");
				}
			});

		}
	},
	navigate : function(evt) {
		var j_menuItem;
		if (evt.target.tagName != "TR")
			j_menuItem = jQuery(evt.target).parents("tr");
		else
			j_menuItem = jQuery(evt.target);
		var url = jQuery("a", j_menuItem).attr("href");

		if (url)
			window.location.href = url;

	},
	hideMenu : function(evt) {

		var toElement = evt.relatedTarget;
		if (!j_menu.contain(toElement)) {
			PCIS.Menu.hiddenSubMenu();
		}

	},
	hiddenSubMenu : function() {
		if (j_menu) {
			var _subMenus = jQuery("div.subMenu", j_menu);
			var _subMenu;

			_subMenus.each( function(i) {
				if (i > 0) {
					_subMenu = jQuery(this);
					_subMenu.hide();
					_subMenu.hideMask();
					/* _subMenu.hideShadow(); */
				}
			});
		}
	},
	//return JQ object of menu container
	getCurrentSubMenu : function(menuItem) {
		return menuItem.parents("div.subMenu");
	}
}
