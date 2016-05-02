package MyFirstServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {
	NewMemberSQL accessSQL = new NewMemberSQL();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson name = new Gson();
		ArrayList<Members> userResults = null;

		if (request.getParameter("getall") != null) {
			try {
				String getID = request.getParameter("MemberID");
				String getFirst = request.getParameter("FirstName");
				String getLast = request.getParameter("LastName");
				String getPhone = request.getParameter("Phone");
				String getAddress = request.getParameter("Address");
				String getDate = request.getParameter("LastVisitDate");
				userResults = accessSQL.readAllPatrons(getID, getFirst, getLast, getPhone, getAddress, getDate);
				response.getWriter().append(name.toJson(userResults));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {

				String userInput = request.getParameter("Phone");
				userResults = accessSQL.readPatrons(userInput);
				response.getWriter().append(name.toJson(userResults));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String Phone = request.getParameter("Phone");
		String Address = request.getParameter("Address");
		try {
			accessSQL.addLibraryPatron(firstName, lastName, Phone, Address);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().print("Thank you for registering with the Local Library " + firstName);
	}

}
