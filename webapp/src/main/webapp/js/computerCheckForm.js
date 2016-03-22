$(document).ready(function() {
	
	updateSubmit();
});

$("#computerName").bind( "change input propertychange", function() {
	
	var nameOk = checkName();
	
	if(nameOk == true){
		removeErrorText($("#computerNameErr"));
	    setSuccess($("#computerName"));
		updateSubmit();
		
	}else{
    	
		setError($("#computerName"));
		setErrorText($("#computerNameErr"), strings['error.name']);
    	
    	$("#submit").attr('disabled',true);
	}
});

$("#introducedDate").bind( "change input propertychange", function() {
	var dateOk = checkDate($.trim($('#introducedDate').val()));
	
	if(dateOk == true){
		
	    removeErrorText($("#introducedErr"))
	    setSuccess($("#introducedDate"));
		
		updateSubmit();
	}else{
		
		setError($("#introducedDate"));
		setErrorText($("#introducedErr"), strings['error.date.format']);
		
		$("#submit").attr('disabled',true);
	}
});

$("#discontinuedDate").bind( "change input propertychange", function() {
	var dateOk = checkDate($.trim($('#discontinuedDate').val()));
	
	if(dateOk == true){
		removeErrorText($('#discontinuedErr'))
	    setSuccess($('#discontinuedDate'))

	    updateSubmit();
	}else{
		setError($('#discontinuedDate'));
		setErrorText($("#discontinuedErr"), strings['error.date.format']);
		
		$("#submit").attr('disabled',true);
	}
});

function setError(input){
	input.parent().removeClass("has-success");
	input.parent().addClass("has-error");
}

function setSuccess(input){
	input.parent().removeClass("has-error");
	input.parent().addClass("has-success");
}

function setErrorText(errField, textToShow){
	errField.addClass("alert alert-danger");
	errField.text(textToShow);
}

function removeErrorText(errField){
	errField.removeClass("alert alert-danger");
	errField.text("");
}

function getDateFromStr(dateStr){
	
	var array = dateStr.split("-"); 
	
	if(local === 'en'){
		date = new Date(array[2], array[0], array[1]);
	}
	else if(local === 'fr'){
		date = new Date(array[2], array[1], array[0]);
	}
	
	return date;
	
}

function checkDate(dateStr, inputDate, errorDiv){
	
	if(dateStr === ''){
		return true;
	}
	
	dateStr = dateStr.replace(new RegExp('/','g'), "-");
	
	var regex = new RegExp(strings['date.jquery.regex']);
	var res = regex.test(dateStr);
	
	if(res == false){
		return false;
	}
		
	var date = getDateFromStr(dateStr);
	
	var minDate = new Date("1970-01-02");
	
	if(date < minDate){
		return false;
	}

    return true;	
}

function checkDateConsistency(){
	
	var introducedStr = $.trim($('#introducedDate').val());
	var discontinuedStr = $.trim($('#discontinuedDate').val());

	if(discontinuedStr === '' && introducedStr === ''){
		removeErrorText($('#introducedErr'));
		removeErrorText($('#discontinuedErr'));
		setSuccess($('#introducedDate'));
		setSuccess($('#discontinuedDate'));
		return true;
	}else if(introducedStr !== '' && discontinuedStr === ''){
		removeErrorText($('#introducedErr'));
		removeErrorText($('#discontinuedErr'));
		setSuccess($('#introducedDate'));
		setSuccess($('#discontinuedDate'));
		return true;
	}
	else if(discontinuedStr !== '' && introducedStr === ''){
		setErrorText($('#introducedErr'),strings['error.date.introducedNullDiscontinuedNotNull']);
		setErrorText($('#discontinuedErr'),strings['error.date.discontinuedNotNullIntroducedNull']);
		setError($('#introducedDate'));
		setError($('#discontinuedDate'));
		return false;
	}
	
	
	
	var introducedDate = getDateFromStr(introducedStr.replace(new RegExp('/','g'), "-"));
	var discontinuedDate = getDateFromStr(discontinuedStr.replace(new RegExp('/','g'), "-"));
	
	if(introducedDate.getTime() >= discontinuedDate.getTime()){
		setErrorText($('#introducedErr'),strings['error.date.introducedAfterDiscontinued']);
		setErrorText($('#discontinuedErr'),strings['error.date.discontinuedBeforeIntroduced']);
		return false;
	}
	
	removeErrorText($('#introducedErr'));
	removeErrorText($('#discontinuedErr'));
	setSuccess($('#introducedDate'));
	setSuccess($('#discontinuedDate'));
	return true;
	
}

function checkName(){

	var name = $.trim($('#computerName').val());
	

    if (name === '' || name.length > 100) {
    	return false;
    }
        
    return true;
}

function updateSubmit(){
	
	var nameOk = checkName();
	var introducedOk = checkDate($.trim($('#introducedDate').val()));
	var discontinuedOk = checkDate($.trim($('#discontinuedDate').val()));
	
	var dateConsistence = false;
	
	 if(introducedOk == true && discontinuedOk == true){
		checkDateConsistency();
	}
	
	if(nameOk == true && checkDateConsistency()){
		$("#submit").attr('disabled',false);
	}
	else{
		$("#submit").attr('disabled',true);
	}
}