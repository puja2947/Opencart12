package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider method 1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path=".//testdata//Opencart_LoginData.xlsx"; //taking excel file from testdata
		
		ExcelUtility xlutil= new ExcelUtility(path);  //creating an object of ExcelUtility class
	int tot_rows=xlutil.getRowCount("Sheet1");
	int tot_col=xlutil.getCellCount("Sheet1", 1);
	
	String logindata[][]= new String[tot_rows][tot_col]; //created for 2d array which can store data from table 
	
	for(int i=1; i<=tot_rows;i++)  //1 //read the data from excel storing in 2d array
	{
		for(int j=0;j<tot_col;j++)   //0  //i is row and j is column
		{
			logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);
		}
	}
	return logindata;  //returning 2d array
	}
	
	
	//Data Provider 2
	
	//DataProvider3
	
	//DataProvider 4
}
