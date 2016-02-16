$(document).ready(function() {
	
	updateSubmit();
	
});

$("#computerName").bind( "input propertychange", function() {
	checkName();
    updateSubmit();
});

$("#introduced").bind( "input propertychange", function() {
	  
});

function checkName(){

	var name = $.trim($('#computerName').val());

    if (name === '') {
    	$("#computerName").parent().addClass("has-error");
    	$("#computerNameErr").text("The computer name must be specified !");
    	$("#computerNameErr").addClass("alert alert-danger");
    	
    	$("#addComputerSubmit").attr('disabled',true);
    	
    	return false;
    }
    
    $("#computerNameErr").removeClass("alert alert-danger");
    $("#computerNameErr").text("");
    
    $("#computerName").parent().removeClass("has-error")
    $("#computerName").parent().addClass("has-success");
    
    return true;
}

function updateSubmit(){

	var nameOk = checkName();
    
	if(nameOk == true){
		$("#addComputerSubmit").attr('disabled',false);
	}
}