package telran.employees;

import java.util.*;

import telran.employees.controller.CompanyController;
import telran.employees.service.*;
import telran.net.TcpHandler;
import telran.view.*;

public class CompanyClientAppl {

	private static final String HOST = "localhost";
	private static final int PORT = 5000;

	public static void main(String[] args) {
		InputOutput ioMain = new ConsoleInputOutput();

		try {

			final TcpHandler tcpHandler = new TcpHandler(HOST, PORT);
			Company company = new CompanyNetProxy(tcpHandler);

			ArrayList<Item> companyItems = CompanyController.getCompanyItems(company);
			companyItems.add(Item.of("Exit ", io -> {
				try {
					
					tcpHandler.close();
					
				} catch (Exception e) {
					
					System.out.println(e.getMessage());
				}
			}));
			Menu menu = new Menu("Company Application", companyItems);

			menu.perform(ioMain);
		} catch (Exception e) {
			ioMain.writeLine(String.format("Error during the application lauching: Server %s is not listening on port %d", HOST, PORT));
		}

	}

}