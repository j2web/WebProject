jQuery.noConflict();

function check(currentCheckBox) {
	checkMinPrivilege(currentCheckBox);
	checkChild(currentCheckBox);
	checkParent(currentCheckBox);
}

function checkMinPrivilege(currentCheckBox) {

	var parentTable = jQuery(currentCheckBox).parents("table").eq(0);
	var childDiv = parentTable.next("div").eq(0);
	var isFunction = jQuery("input:checkbox", childDiv).length == 0;

	if (isFunction) {//The current checkbox must be Function privilege
		var state = currentCheckBox.checked;
		var container = parentTable.parent("div");
		var index = jQuery("input:checkbox", container).index(currentCheckBox);

		if (state && index > 0) {
			jQuery("input:checkbox", container).get(0).checked = state;
		}
		if (!state && index == 0) {
			jQuery("input:checkbox", container).each( function() {
				this.checked = state;
			});
		}

	}
}

function checkChild(currentCheckBox) {

	var state = currentCheckBox.checked;
	// for child checbox

	var parentTable = jQuery(currentCheckBox).parents("table").eq(0);
	var childDiv = parentTable.next("div").eq(0);

	jQuery("input:checkbox", childDiv).each( function() {
		this.checked = state;
	});
}

function checkParent(currentCheckBox) {
	var state = currentCheckBox.checked;
	var parentTable = jQuery(currentCheckBox).parents("table").eq(0);
	var parentDiv = jQuery(currentCheckBox).parents("div").eq(0);
	var parentCheckbox = jQuery("input:checkbox", parentDiv.prev("table").eq(0))[0];
	if (parentCheckbox) {
		if (state)
			parentCheckbox.checked = state;

		var size = jQuery("input[@type=checkbox][checked]",
				parentTable.siblings("table")).size();
		var allSize = jQuery("input[type=checkbox]",
				parentTable.siblings("table")).size() + 1;
		if ((state && size + 1 == allSize) || (!state && size == 0)) {
			parentCheckbox.checked = state;

		}
		checkParent(parentCheckbox);
	}
}