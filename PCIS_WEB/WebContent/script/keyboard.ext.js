var forward=true;

// handle enter replace tab
function documentOnKeyDown(event) {
	var evt = jQuery.event.fix(event);
	var e = evt.target;
	if (!e) {
		return false;
	}
	//(evt.which == 40 && e.tagName!="SELECT")
	if (evt.which == 13 || (evt.which==9 && !evt.shiftKey)) {	
		evt.stopPropagation();				
		evt.preventDefault();
		forward=true;
		switch (e.tagName) {
			case "INPUT":
				handleInput(e,forward);
				break;
			case "SELECT":						
				handleSelect(e,forward);
				break;
			case "TEXTAREA":
				handleTextarea(e,forward);
				break;
		}
	}
	//(evt.which == 38 && e.tagName!="SELECT")
	if (evt.which==9 && evt.shiftKey) {	
		evt.stopPropagation();				
		evt.preventDefault();
		forward=false;
		switch (e.tagName) {
			case "INPUT":
				handleInput(e,forward);
				break;
			case "SELECT":						
				handleSelect(e,forward);
				break;
			case "TEXTAREA":
				handleTextarea(e,forward);
				break;
		}
	}
}
function handleInput(e,forward) {
	switch (e.type) {		
		case "text":
		case "password":
		case "checkbox":
		case "radio":
		case "file":
		case "submit":
		case "button":
		case "image":
			moveFocusToElement(e,forward);
			break;

	}
		
}
function handleSelect(e,forward) {
	moveFocusToElement(e,forward);
}
function handleTextarea(e,forward) {
	moveFocusToElement(e,forward);
}

function moveFocusToElement(e,forward) {
	
	var form=e.form;
	var elementCol = form.elements;
	var curentPos =  0;
	for (var i = 0; i < elementCol.length; i++) {
		if (elementCol[i] == e) {
			curentPos = i;
			break;
		}				
	}
	if (forward)
		focusOnNextElement(elementCol, curentPos);
	else
		focusOnPrevElement(elementCol, curentPos);

}
function focusOnNextElement(elementCol, index) {

	var newIndex = index + 1;
	var newElement = elementCol[newIndex];		
	while (!newElement || !canFocus(newElement)) {
		newIndex++;
		//It's the last element
		if (newIndex + 1 >= elementCol.length) {			
			for (var i = 0; i < elementCol.length; i++) {
				if (canFocus(elementCol[i])) {
					newIndex=i;
					break;
				}				
			}						
		}
		newElement = elementCol[newIndex];	

	}

	
	if (newElement) {				
		try {				
			if (canFocus(newElement)){				
				newElement.focus();	
				
			}
		} catch (e){alert(e);}
	}
	return false;
}
function focusOnPrevElement(elementCol, index) {

	var newIndex = index -1;
	var newElement = elementCol[newIndex];		
	while (!newElement || !canFocus(newElement)) {
		newIndex--;
		if (newIndex <=0) {	
			//It's the first element
			for (var i = elementCol.length-1; i >=0; i--) {
				if (canFocus(elementCol[i])) {
						newIndex=i;
						break;
				}				
			}						
		}
		newElement = elementCol[newIndex];	

		}

	
	if (newElement) {				
		try {				
			if (canFocus(newElement)){					
				newElement.focus();							
			}
		} catch (e){alert(e);}
	}
	return false;
}
function canFocus(o) {
	if (isInput(o) || isButton(o)){
		return o.readOnly!=true && o.disabled!=true && o.style.display != "none" && o.style.visibility != "hidden";						
	}
	return false;
}

function isInput(o){
	return o.tagName=="SELECT" || o.tagName=="TEXTAREA" || 
			(o.tagName=="INPUT" && (o.type == "text" || o.type == "radio" || o.type == "password" || o.type == "checkbox" || o.type == "file"));
		
}
function isButton(o){
	return o.tagName=="BUTTON" || (o.tagName=="INPUT" && (o.type == "button" || o.type == "submit" || o.type == "reset"));
		
}