function selectAll(e) {
	var parentTable = jQuery(e).parents("table").eq(0);
	var checkboxList = jQuery("tbody tr td input[@typpe=checkbox]:first-child", parentTable);
	checkboxList.each( function() {
		this.checked = e.checked;
	});
}

function selectCheckbox(e) {
	var state = e.checked;

	var parentTable = jQuery(e).parents("table").eq(0);
	var size = jQuery("tbody tr td input[type=checkbox]:first-child", parentTable).length;

	var checkedSize = jQuery("tbody tr td input[type=checkbox][checked]:first-child", parentTable).length;
	var selectAllCheckbox = jQuery("thead tr input[type=checkbox]:first-child", parentTable)[0];
	if (state && size == checkedSize)
		selectAllCheckbox.checked = true;
	else
		selectAllCheckbox.checked = false;

}