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
		setErrorText($("#computerNameErr"), "The computer name must be specified !");
    	
    	$("#submit").attr('disabled',true);
	}
});

$("#introduced").bind( "change input propertychange", function() {
	var dateOk = checkDate($.trim($('#introduced').val()));
	
	if(dateOk == true){
		
	    removeErrorText($("#introducedErr"))
	    setSuccess($("#introduced"));
		
		updateSubmit();
	}else{
		
		setError($("#introduced"));
		setErrorText($("#introducedErr"), "Wrong format entered");
		
		$("#submit").attr('disabled',true);
	}
});

$("#discontinued").bind( "change input propertychange", function() {
	var dateOk = checkDate($.trim($('#discontinued').val()));
	
	if(dateOk == true){
		removeErrorText($('#discontinuedErr'))
	    setSuccess($('#discontinued'))

	    updateSubmit();
	}else{
		setError($('#discontinued'));
		setErrorText($("#discontinuedErr"), "Wrong format entered");
		
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
	
	var introducedStr = $.trim($('#introduced').val());
	var discontinuedStr = $.trim($('#discontinued').val());
	
	if(discontinuedStr === '' && introducedStr === ''){
		removeErrorText($('#introducedErr'));
		removeErrorText($('#discontinuedErr'));
		setSuccess($('#introduced'));
		setSuccess($('#discontinued'));
		return true;
	}else if(introducedStr !== '' && discontinuedStr === ''){
		removeErrorText($('#introducedErr'));
		removeErrorText($('#discontinuedErr'));
		setSuccess($('#introduced'));
		setSuccess($('#discontinued'));
		return true;
	}
	else if(discontinuedStr !== '' && introducedStr === ''){
		setErrorText($('#introducedErr'),"The introduced value must be specified if you put the discontinued one !");
		setErrorText($('#discontinuedErr'),"You can't specify the discontinued date if you don't set the introduced one !");
		setError($('#introduced'));
		setError($('#discontinued'));
		return false;
	}
	
	var introducedData = introducedStr.split('-');
	var discontinuedData = discontinuedStr.split('-');
	
	var introducedDate = new Date(parseInt(introducedData[0]), parseInt(introducedData[1])-1, parseInt(introducedData[2]));
	var discontinuedDate = new Date(parseInt(discontinuedData[0]), parseInt(discontinuedData[1])-1, parseInt(discontinuedData[2]));
	
	if(introducedDate.getTime() >= discontinuedDate.getTime()){
		setErrorText($('#introducedErr'),"The introduced value must be set before the discontinued one !");
		setErrorText($('#discontinuedErr'),"The discontinued value must be set after the introduced one !");
		return false;
	}
	
	removeErrorText($('#introducedErr'));
	removeErrorText($('#discontinuedErr'));
	setSuccess($('#introduced'));
	setSuccess($('#discontinued'));
	return true;
	
}

function checkName(){

	var name = $.trim($('#computerName').val());

    if (name === '') {
    	return false;
    }
        
    return true;
}

function updateSubmit(){
	
	var nameOk = checkName();
	var introducedOk = checkDate($.trim($('#introduced').val()));
	var discontinuedOk = checkDate($.trim($('#discontinued').val()));
	
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