$(document).ready(function() {
	
	updateSubmit();
});

$("#computerName").bind( "change input propertychange", function() {
	
	var res = checkName();
	
	if(res == true){
	    $("#computerNameErr").removeClass("alert alert-danger");
	    $("#computerNameErr").text("");
	    
	    $("#computerName").parent().removeClass("has-error")
	    $("#computerName").parent().addClass("has-success");
		
		updateSubmit();
		
	}else{
    	$("#computerName").parent().addClass("has-error");
    	$("#computerNameErr").text("The computer name must be specified !");
    	$("#computerNameErr").addClass("alert alert-danger");
    	
    	$("#addComputerSubmit").attr('disabled',true);
	}
});

$("#introduced").bind( "change input propertychange", function() {
	var res = checkDate($.trim($('#introduced').val()));
	
	if(res == true){
		
		$("#introducedErr").removeClass("alert alert-danger");
	    $("#introducedErr").text("");
	    
	    $("#introduced").parent().removeClass("has-error");
	    $("#introduced").parent().addClass("has-success");
		
		
		updateSubmit();
	}else{
		$("#introduced").parent().addClass("has-error");
		$("#introducedErr").text("Wrong format entered");
		$("#introducedErr").addClass("alert alert-danger");
		
		$("#addComputerSubmit").attr('disabled',true);
	}
});

$("#discontinued").bind( "change input propertychange", function() {
	var res = checkDate($.trim($('#discontinued').val()));
	
	if(res == true){
		
		$("#discontinuedErr").removeClass("alert alert-danger");
	    $("#discontinuedErr").text("");
	    
	    $("#discontinued").parent().removeClass("has-error");
	    $("#discontinued").parent().addClass("has-success");
		
		
		updateSubmit();
	}else{
		$("#discontinued").parent().addClass("has-error");
		$("#discontinuedErr").text("Wrong format entered");
		$("#discontinuedErr").addClass("alert alert-danger");
		
		$("#addComputerSubmit").attr('disabled',true);
	}
});

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
	
	console.log("checkDateConsistency");
	
	var introducedStr = $.trim($('#introduced').val());
	var discontinuedStr = $.trim($('#discontionued').val());
	
	if(discontinuedStr === '' && introducedStr === ''){
		return true;
	}else if(introducedStr === ''){
		return true;
	}
	else if(discontinuedStr !== ''){	
		$('#introducedErr').text("The introduced value must be specified if you put the discontinued one");
		return false;
	}
	
	var introducedData = introducedStr.split('-');
	var discontinuedData = discontinuedStr.split('-');
	
	var introducedDate = new Date(parseInt(introducedData[0]), parseInt(introducedData[1])-1, parseInt(introducedData[2]));
	var discontinuedDate = new Date(parseInt(discontinuedData[0]), parseInt(discontinuedData[1])-1, parseInt(discontinuedData[2]));
	
	console.log("introduced date : " + introducedDate);
	console.log("discontinued date : " + discontinuedDate);
	
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
	
	console.log("updateSubmit");

	var nameOk = checkName();
	var introducedOk = checkDate($.trim($('#introduced').val()));
	var discontinuedOk = checkDate($.trim($('#discontinued').val()));
	
	console.log(nameOk);
	console.log(introducedOk);
	console.log(discontinuedOk);
	
	var dateConsistence = false;
	
	/*if(introducedOk == true && discontinuedOk == true){
		checkDateConsistency();
	}*/
	
	if(nameOk == true && checkDateConsistency()){
		$("#addComputerSubmit").attr('disabled',false);
	}
	else{
		$("#addComputerSubmit").attr('disabled',true);
	}
}