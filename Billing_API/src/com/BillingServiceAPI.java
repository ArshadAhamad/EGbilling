package com;

import model.Billing;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProjectAPI
 */
@WebServlet("/Billing")
public class BillingServiceAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Billing BillingObj = new Billing();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillingServiceAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String output = BillingObj.insertBilling(request.getParameter("billingID"),
				request.getParameter("userID"),
				request.getParameter("billingName"),
				request.getParameter("billingDate"),
				request.getParameter("billingUnits"),
				request.getParameter("billingTotal")); 

		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);

		String output = BillingObj.updateBilling(paras.get("hidProjectIDSave").toString(),
				paras.get("userID").toString(),
				paras.get("billingID").toString(), 
				paras.get("billingName").toString(),
				paras.get("billingDate").toString(),
				paras.get("billingUnits").toString(),
				paras.get("billingTotal").toString()
				

		);

		response.getWriter().write(output);
	}


	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> paras = getParasMap(request);

		String output = BillingObj.deleteBilling(paras.get("billingID").toString());

		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map<String, String> getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {

		}
		return map;

	}
}
