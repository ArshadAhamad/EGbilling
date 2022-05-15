<%@page import="model.Billing"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/BillManagement.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Bill Management</h1>

				<form id="formBillManagement" name="formBillManagement" method="post" action="BillManagement.jsp">

					
					User ID: <input id="billingID" name="billingID" type="text"
						class="form-control form-control-sm"> 
						
						<br> User Name: <input id="billingName" name="billingName" type="text"
						class="form-control form-control-sm">
						
						<br> Billing Date: <input id="billingDate" name="billingDate" type="date"
						class="form-control form-control-sm"> 
						
						
						<br> Billing Units: <input id="billingUnits" name="billingUnits" type="text"
						class="form-control form-control-sm"> .
						
						<br> Total Price: <input id="billingTotal" name="billingTotal" type="text"
						class="form-control form-control-sm"> 
						
						
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
					Billing BillingObj = new Billing();
						out.print(BillingObj.readBilling());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
