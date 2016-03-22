$(document).ready(function() {

	updateSubmit();
});

$("#username").bind( "change input propertychange", function() {
	
	var nameOk = checkNotEmpty($('#username').val());
	
	if(nameOk == true){
		removeErrorText($("#usernameErr"));
	    setSuccess($("#username"));
		updateSubmit();
		
	}else{
    	
		setError($("#username"));
		setErrorText($("#usernameErr"), strings['error.login.username']);
    	
    	$("#submit").attr('disabled',true);
	}
});

$("#passwd").bind( "change input propertychange", function() {
	
	var passwdOk = checkNotEmpty($('#passwd').val());
	
	if(passwdOk == true){
		removeErrorText($("#passwdErr"));
	    setSuccess($("#passwd"));
		updateSubmit();
		
	}else{
    	
		setError($("#passwd"));
		setErrorText($("#passwdErr"), strings['error.login.passwd']);
    	
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

function checkNotEmpty(value){
	
	var txt = $.trim(value);
	
    if (txt === '') {
    	return false;
    }
        
    return true;
}

function updateSubmit() {

	var usernameOk = checkNotEmpty($('#username').val());
	var passwordOk = checkNotEmpty($('#passwd').val());

	if (usernameOk == true && passwordOk == true) {
		$("#submit").attr('disabled', false);
	} else {
		$("#submit").attr('disabled', true);
	}
}