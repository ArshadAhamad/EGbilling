$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateProjectForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "BillingServiceAPI",
		type : type,
		data : $("#formBillManagement").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onProjectSaveComplete(response.responseText, status);
		}
	});
});

function onProjectSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divProjectGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidProjectIDSave").val("");
	$("#formProduct")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidProjectIDSave").val(
					$(this).closest("tr").find('#hidProjectIDUpdate').val());
			
			$("#billingID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#billingName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#billingDate").val($(this).closest("tr").find('td:eq(2)').text());
			$("#billingEmail").val($(this).closest("tr").find('td:eq(3)').text());
			$("#billingUnits").val($(this).closest("tr").find('td:eq(3)').text());
			$("#billingTotal").val($(this).closest("tr").find('td:eq(4)').text());
			
		});

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "BillingServiceAPI",
		type : "DELETE",
		data : "id=" + $(this).data("billid"),
		dataType : "text",
		complete : function(response, status) {
			onProjectDeleteComplete(response.responseText, status);
		}
	});
});

function onProjectDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divProjectGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL=========================================================================
function validateProjectForm() {
	
	if ($("#billingID").val().trim() == "") {
		return "Insert User ID:";
	}

	
	if ($("#billingName").val().trim() == "") {
		return "Insert Date:";
	}
	if ($("#billingDate").val().trim() == "") {
		return "Insert Date:";
	}
	if ($("#billingEmail").val().trim() == "") {
		return "Insert Date:";
	}
	
	
	
	
	var Billing_Units = $("#billingUnits").val().trim();
	 if (!$.isNumeric(Billing_Units)) 
	 {
		 return "Insert Unit Usage.";
	 }

	
	 var billing_Total = $("#billingTotal").val().trim();
	 if (!$.isNumeric(billing_Total)) 
	 {
		 return "Insert Price.";
	 }
	 

	return true;
}