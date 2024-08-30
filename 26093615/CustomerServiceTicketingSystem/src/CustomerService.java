import java.sql.*;
import java.util.Scanner;
public class CustomerService {

	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Customer Service Ticketing System");
            System.out.println("1. Create a new ticket");
            System.out.println("2. View ticket details");
            System.out.println("3. Update ticket information");
            System.out.println("4. Delete a ticket");
            System.out.println("5. Assign a ticket to a representative");
            System.out.println("6. View assigned tickets");
            System.out.println("7. Update assignment information");
            System.out.println("8. Delete assignment records");
            System.out.println("9. Resolve a ticket");
            System.out.println("10. View resolved tickets");
            System.out.println("11. Update resolution information");
            System.out.println("12. Delete resolution records");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createTicket(scanner);
                    break;
                case 2:
                    viewTicketDetails(scanner);
                    break;
                case 3:
                    updateTicketInformation(scanner);
                    break;
                case 4:
                    deleteTicket(scanner);
                    break;
                case 5:
                    assignTicket(scanner);
                    break;
                case 6:
                    viewAssignedTickets(scanner);
                    break;
                case 7:
                    updateAssignmentInformation(scanner);
                    break;
                case 8:
                    deleteAssignmentRecords(scanner);
                    break;
                case 9:
                    resolveTicket(scanner);
                    break;
                case 10:
                    viewResolvedTickets(scanner);
                    break;
                case 11:
                    updateResolutionInformation(scanner);
                    break;
                case 12:
                    deleteResolutionRecords(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}


private static void createTicket(Scanner scanner) {
    // Implement ticket creation logic here
	System.out.print("Enter customer ID: ");
    int customerId = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter issue description: ");
    String issueDescription = scanner.nextLine();

    String sql = "INSERT INTO Ticket (customer_id, creation_date, issue_description, status) VALUES (?, NOW(),? , 'Open')";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) 
    
    {
    	statement.setInt(1, customerId);
        statement.setString(2, issueDescription);
        statement.executeUpdate();
        System.out.println("Ticket created successfully!");

        
    } catch (SQLException e) {
        System.out.println("Error creating ticket.");
        e.printStackTrace();
    }
}

private static void viewTicketDetails(Scanner scanner) {
    // Implement ticket viewing logic here
	System.out.print("Enter ticket ID: ");
    int ticketId = scanner.nextInt();

    String sql = "SELECT * FROM Ticket WHERE ticket_id = ?";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, ticketId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Ticket ID: " + resultSet.getInt("ticket_id"));
            System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
            System.out.println("Creation Date: " + resultSet.getTimestamp("creation_date"));
            System.out.println("Issue Description: " + resultSet.getString("issue_description"));
            System.out.println("Status: " + resultSet.getString("status"));
        } else {
            System.out.println("Ticket not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving ticket details.");
        e.printStackTrace();
    }
}

private static void updateTicketInformation(Scanner scanner) {
    // Implement ticket update logic here
	System.out.print("Enter ticket ID: ");
    int ticketId = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter new issue description: ");
    String newIssueDescription = scanner.nextLine();
    

    String sql = "UPDATE Ticket SET issue_description = ? WHERE ticket_id = ?";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, newIssueDescription);
        statement.setInt(2, ticketId);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Ticket updated successfully!");
        } else {
            System.out.println("Ticket not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error updating ticket.");
        e.printStackTrace();
    }
	
}

private static void deleteTicket(Scanner scanner) {
    // Implement ticket deletion logic here
	System.out.print("Enter ticket ID: ");
    int ticketId = scanner.nextInt();

    String sql = "DELETE FROM Ticket WHERE ticket_id = ?";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, ticketId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Ticket deleted successfully!");
        } else {
            System.out.println("Ticket not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting ticket.");
        e.printStackTrace();
    }
}

private static void assignTicket(Scanner scanner) {
    // Implement ticket assignment logic here
	 System.out.print("Enter ticket ID: ");
	    int ticketId = scanner.nextInt();

	    String checkTicketSql = "SELECT * FROM Ticket WHERE ticket_id = ?";
	    try (Connection connection = DbConnect.getConnection();
	         PreparedStatement checkStatement = connection.prepareStatement(checkTicketSql)) {
	        checkStatement.setInt(1, ticketId);
	        ResultSet resultSet = checkStatement.executeQuery();
	        if (!resultSet.next()) {
	            System.out.println("Error: Ticket ID not found. Cannot assign ticket.");
	            return; // Exit the method if ticket ID does not exist
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking ticket ID.");
	        e.printStackTrace();
	        return; // Exit the method if there is an error checking the ticket ID
	    }

	    System.out.print("Enter representative ID: ");
	    int representativeId = scanner.nextInt();

	    String sql = "INSERT INTO Assignment (ticket_id, representative_id, assignment_date, status) VALUES (?, ?, NOW(), 'Assigned')";
	    try (Connection connection = DbConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, ticketId);
	        statement.setInt(2, representativeId);
	        statement.executeUpdate();
	        System.out.println("Ticket assigned successfully!");
	    } catch (SQLException e) {
	        System.out.println("Error assigning ticket.");
	        e.printStackTrace();
	    }
}

private static void viewAssignedTickets(Scanner scanner) {
    // Implement viewing assigned tickets logic here
	String sql = "SELECT * FROM Assignment";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            System.out.println("Assignment ID: " + resultSet.getInt("assignment_id"));
            System.out.println("Ticket ID: " + resultSet.getInt("ticket_id"));
            System.out.println("Representative ID: " + resultSet.getInt("representative_id"));
            System.out.println("Assignment Date: " + resultSet.getTimestamp("assignment_date"));
            System.out.println("Status: " + resultSet.getString("status"));
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving assigned tickets.");
        e.printStackTrace();
    }
}

private static void updateAssignmentInformation(Scanner scanner) {

	System.out.print("Enter assignment ID: ");
    int assignmentId = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter new representative ID: ");
    int newRepresentativeId = scanner.nextInt();

    String sql = "UPDATE Assignment SET representative_id = ? WHERE assignment_id = ?";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, newRepresentativeId);
        statement.setInt(2, assignmentId);
        
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Assignment updated successfully!");
        } else {
            System.out.println("Assignment not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error updating assignment.");
        e.printStackTrace();
    }
	
}

private static void deleteAssignmentRecords(Scanner scanner) {
	
	System.out.print("Enter assignment ID: ");
    int assignmentId = scanner.nextInt();

    String sql = "DELETE FROM Assignment WHERE assignment_id = ?";
    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, assignmentId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Assignment record deleted successfully!");
        } else {
            System.out.println("Assignment record not found.");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting assignment record.");
        e.printStackTrace();
    }
}


    // Implement ticket resolution logic here
	private static void resolveTicket(Scanner scanner) {
		System.out.print("Enter ticket ID: ");
	    int ticketId = scanner.nextInt();
	    scanner.nextLine(); // Consume newline

	    // Check if the ticket exists
	    String checkSql = "SELECT COUNT(*) FROM resolution WHERE ticket_id = ?";
	    try (Connection connection = DbConnect.getConnection();
	         PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
	        checkStatement.setInt(1, ticketId);
	        ResultSet resultSet = checkStatement.executeQuery();
	        resultSet.next();
	        int count = resultSet.getInt(1);

	        if (count == 0) {
	            System.out.println("Error: Ticket ID not found.");
	            return; 
	        }

	        System.out.print("Enter resolution description: ");
	        String resolutionDescription = scanner.nextLine();

	        String resolveSql = "UPDATE resolution SET status = 'Resolved', resolution_details = ?, resolution_date = NOW() WHERE ticket_id = ?";
	        try (PreparedStatement resolveStatement = connection.prepareStatement(resolveSql)) {
	            resolveStatement.setString(1, resolutionDescription);
	            resolveStatement.setInt(2, ticketId);
	            resolveStatement.executeUpdate();
	            System.out.println("Ticket resolved successfully!");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error resolving ticket.");
	        e.printStackTrace();
	    }
	}


private static void viewResolvedTickets(Scanner scanner) {
    // Implement viewing resolved tickets logic here
	String sql = "SELECT * FROM resolution";

    try (Connection connection = DbConnect.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            System.out.println("Resolution ID: " + resultSet.getInt("resolution_id"));
            System.out.println("Ticket ID: " + resultSet.getInt("ticket_id"));
            System.out.println("Resolution Date: " + resultSet.getTimestamp("resolution_date"));
            System.out.println("Resolution Details: " + resultSet.getString("resolution_details"));
            System.out.println("Status: " + resultSet.getString("status"));
            System.out.println("----------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving resolved tickets.");
        e.printStackTrace();
    }
	
}

private static void updateResolutionInformation(Scanner scanner) {
	 System.out.print("Enter resolution ID: ");
	    int resolutionId = scanner.nextInt();
	    scanner.nextLine(); // Consume newline

	    // Check if the resolution ID exists
	    String checkSql = "SELECT COUNT(*) FROM resolution WHERE resolution_id = ?";
	    try (Connection connection = DbConnect.getConnection();
	         PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
	        checkStatement.setInt(1, resolutionId);
	        ResultSet resultSet = checkStatement.executeQuery();
	        resultSet.next();
	        int count = resultSet.getInt(1);

	        if (count == 0) {
	            System.out.println("Error: Resolution ID not found.");
	            return; // Exit the method if resolution ID does not exist
	        }

	        // Proceed with updating the resolution information
	        System.out.print("Enter new resolution details: ");
	        String newResolutionDetails = scanner.nextLine();
	        System.out.print("Enter new status: ");
	        String newStatus = scanner.nextLine();

	        String sql = "UPDATE resolution SET resolution_details = ?, status = ? WHERE resolution_id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, newResolutionDetails);
	            statement.setString(2, newStatus);
	            statement.setInt(3, resolutionId);

	            int rowsUpdated = statement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Resolution information updated successfully!");
	            } else {
	                System.out.println("Resolution not found.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error updating resolution.");
	        e.printStackTrace();
	    }
	
}

private static void deleteResolutionRecords(Scanner scanner) {
    // Implement resolution deletion logic here
	 System.out.print("Enter resolution ID: ");
	    int resolutionId = scanner.nextInt();

	    String sql = "DELETE FROM resolution WHERE resolution_id = ?";

	    try (Connection connection = DbConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, resolutionId);

	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Resolution record deleted successfully!");
	        } else {
	            System.out.println("Resolution record not found.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error deleting resolution record.");
	        e.printStackTrace();
	    }
}
}
    


