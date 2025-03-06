package Intership_Project_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BillLogics {

	
	static double tax;
	static String accountNo;
	static double billAmount;
	static String transactionID;
	static String paymentType;
	static double dueFine;
	static String date;
	
	
	public void getDetails(String accountNumber) throws SQLException
	{
		accountNo=accountNumber;
		 ConnnectionJDBC con = new ConnnectionJDBC();
	       String query ="select * from bill where accountNo='"+accountNumber+"'"+";";
	       System.out.println(query);
	       ResultSet rs=con.s.executeQuery(query);
	       if(rs.next())
	       {
	    	   tax=rs.getDouble("gstTax");
	    	  billAmount= rs.getDouble("billAmount");
	    	  transactionID= rs.getString("transactionID");
	    	   paymentType=rs.getString("paymentType");
	    	  dueFine= rs.getDouble("dueFine");
	    	   date=rs.getString("date");	    	  
	       }
	       
	     
	}
	
	public void generateTransactionID()
	{
		int min=1;
		long max=100000000l;
		
		Long billAmt=(long) (Math.random()*(max-min))+1;
		System.out.println(billAmt);
		this.transactionID=billAmt.toString();
	}
	
	
	
	public void pay() throws SQLException
	{
		generateTransactionID();
		 ConnnectionJDBC con = new ConnnectionJDBC();
	        String query=null;
	        billAmount=0.0;
	        dueFine=0.0;
	       
	        LocalDate currentDate = LocalDate.now();
	        // Convert the date to a String
	        String dateAsString = currentDate.toString();
	        this.date=dateAsString;
	       
	        query = "UPDATE bill SET " +"billAmount = " + billAmount + ", " +"transactionID = '" + transactionID + "', " +
	                "paymentType = '" + paymentType + "', " +
	                "dueFine = " + dueFine + ", " +
	                "date = '" + date + "' " +
	                "WHERE accountNo = '" + User_DashBoard.accountNo + "';";
	        try {
				con.s.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}
	
	public void generateBill()
	{
		double tax=4.90;
		
	}

}
