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

function checkDate(dateStr, inputDate, errorDiv){
	
	if(dateStr === ''){
		return true;
	}
	
	var regex = /^\d\d\d\d-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	var res = regex.test(dateStr);
	
	if(res == false){
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
	
	var introducedData = introducedStr.split('-');
	var discontinuedData = discontinuedStr.split('-');
	
	var introducedDate = new Date(parseInt(introducedData[0]), parseInt(introducedData[1])-1, parseInt(introducedData[2]));
	var discontinuedDate = new Date(parseInt(discontinuedData[0]), parseInt(discontinuedData[1])-1, parseInt(discontinuedData[2]));
	
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
	

    if (name === '' || name.length > 30) {
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