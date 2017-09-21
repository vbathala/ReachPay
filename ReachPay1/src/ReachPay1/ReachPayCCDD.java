package ReachPay1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;


public class ReachPayCCDD {
	
	public WebDriver driver;
	
	@BeforeTest
	  public void driverSetup() {
		
		
		// Firefox Driver
		/*System.setProperty("webdriver.gecko.driver","C:/Users/vbathala/Downloads/geckodriver-v0.17.0-win32/geckodriver.exe");
		driver = new FirefoxDriver();*/
		
		//Chrome Driver
		//System.setProperty("webdriver.chrome.driver", "C:/Users/vbathala/Downloads/chromedriver_win32 (1)/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "chromedriver");
	    ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");
		driver = new ChromeDriver(options);
		return;
		
	   }
	
  @Test
    public void LoginReachPay() throws InterruptedException, SQLException {
	  
	  //Login To ReachPay
	  driver.get("https://reachsecurepay.qa.reachlocal.com/client/index.php"); 
	  	  
      driver.findElement(By.name("email")).sendKeys("reachprodtest1@gmail.com");
      driver.findElement(By.name("commit")).click();
      Thread.sleep(2000);
      driver.findElement(By.name("commit")).click();   
      
      driver.manage().window().maximize();
    
           
      driver.findElement(By.xpath(".//*[@id='px']")).sendKeys("Success1");     
      driver.findElement(By.name("commit")).click();
      Thread.sleep(5000);
   
  }
	//My Payment Methods
      @Test(dependsOnMethods = {"LoginReachPay"})
      public void CreateCCPaymentMethod() throws InterruptedException, SQLException {
   driver.findElement(By.linkText("VIEW PAYMENT METHODS")).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath(".//*[@id='mainContent']/tbody/tr[1]/td/table/tbody/tr[4]/td/a")).click();
	
	//Enter New Payment Method
	
	//Account Name
	driver.findElement(By.id("info_tag")).sendKeys("vb rp1"); 
	
	//Payment Type
	WebElement PaymentType = driver.findElement(By.id("info_type_0"));
	PaymentType.click();
	
	//Card Type
	Select dropdown =new Select (driver.findElement(By.id("info_credit_card_type")));
	dropdown.selectByIndex(0);
	
	//Card Name
	driver.findElement(By.id("credit_card_name")).sendKeys("vb cc"); 
	
	//Card Number
	driver.findElement(By.id("credit_card_number")).sendKeys("4570620139991944");
	
	//Exp Month
	Select dropdown1 =new Select (driver.findElement(By.name("info_exp_month")));
	dropdown1.selectByVisibleText("January"); 
	
	//Exp Year
	Select dropdown2 =new Select (driver.findElement(By.name("info_exp_year")));
	dropdown2.selectByIndex(4); 
	
	//cvv
	driver.findElement(By.id("verification_code")).sendKeys("123"); 
	
	//Password
	driver.findElement(By.id("password")).sendKeys("Success1");
	
	//Submit Payment Info
	driver.findElement(By.name("commit")).click();   
	
	WebDriverWait wait = new WebDriverWait(driver,1000);
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='AccountReport']")));
   
   System.out.println("Credit Card Added Successfully");
   System.out.println(" ");
   
   String Query1 = "select * from CreditCardInfo order by 1 desc limit 1;";
     
   Connection con = DriverManager.getConnection("jdbc:mysql://qdbs401.dyn.wh.reachlocal.com/rl_op","root","reach20");
   Statement stmt = con.createStatement();
	
	// Execute the SQL Query. Store results in ResultSet		
	ResultSet rs= stmt.executeQuery(Query1);	
	
	
	while (rs.next())
	{
		System.out.println("Credit Card Transaction Table:");
		System.out.println(" ");
		
		System.out.println("paymentId = " + rs.getInt(1)+ " , MAID= " + rs.getInt(4)+ " , payment Name= " + rs.getString(30 ));
		System.out.println(" ");
		
		System.out.println("CC Type = " + rs.getString(6)+ " , Exp Month= " + rs.getInt(8)+ " , Exp Year= " + rs.getString(9 ) +" , Exp Year= " + rs.getInt(21 ));
		System.out.println(" ");
	}
   
  }
  
  @Test(dependsOnMethods = {"CreateCCPaymentMethod"})
    public void CreateDDPaymentMethod() throws InterruptedException, SQLException, ClassNotFoundException {
	  
	  WebDriverWait wait1 = new WebDriverWait(driver,1000);
	   wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='mainContent']/tbody/tr[1]/td/table/tbody/tr[4]/td/a")));
	  
  driver.findElement(By.xpath(".//*[@id='mainContent']/tbody/tr[1]/td/table/tbody/tr[4]/td/a")).click();
	
	//Enter New Payment Method
	
	driver.findElement(By.id("info_tag")).sendKeys("vb dd1"); //Account Name
	
	WebElement PaymentType = driver.findElement(By.id("info_type_1"));
	PaymentType.click();//Payment Type
	
	driver.findElement(By.id("info_account_name")).sendKeys("vb dd1"); //Card Name
	
	driver.findElement(By.id("info_account_number")).sendKeys("123456789");//Card Number
		
	driver.findElement(By.id("info_routing_number")).sendKeys("021000021");
	
		
	driver.findElement(By.id("password")).sendKeys("Success1");//Password
	
	
	
	driver.findElement(By.name("commit")).click();   //Submit Payment Info
	
	WebDriverWait wait = new WebDriverWait(driver,1000);
 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='AccountReport']")));
   
 	
	//driver.findElement(By.tagName("input")).click(); //Mark as Preffered Payment Method
	
	Thread.sleep(3000);
	
	driver.manage().window().maximize();
	
	driver.findElement(By.id("HistoryToggleLink")).click();
		
	//driver.findElement(By.xpath(".//*[@id='mainContent']/tbody/tr[2]/td/table/tbody/tr[2]/td[1]/input[2]")).click();  //Return Main Menu
	driver.findElement(By.cssSelector(".Logon-Buttons")).click();//REturn to Main Menu
	
	Thread.sleep(3000);
		
	driver.findElement(By.linkText("SIGN OUT")).click();
	
	String Query1 = "select * from DirectDebitInfo order by 1 desc limit 1;";
	   // String Query2 = "select * from Campaign order by 1 desc limit 1;";
	
	//Added this for Mac
	//Class.forName("com.mysql.jdbc.Driver");
	
	    Connection con = DriverManager.getConnection("jdbc:mysql://qdbs401.dyn.wh.reachlocal.com/rl_op","root","reach20");
		Statement stmt = con.createStatement();
		

		// Execute the SQL Query. Store results in ResultSet		
		ResultSet rs= stmt.executeQuery(Query1);	
		
		
		while (rs.next())
		{
			System.out.println("Direct Debit Transaction Table:");
			System.out.println(" ");
			
			System.out.println("paymentId = " + rs.getInt(1)+ " , MAID= " + rs.getInt(4)+ " , payment Name= " + rs.getString(37 ));
			System.out.println(" ");
			
			System.out.println("Routing = "+ rs.getInt(9) + ", Exp Year= " + rs.getInt(22));
			System.out.println(" ");
		}
	    
	}

   
  @AfterTest
  public void closeDriver() {
	 driver.quit();
  }

}

