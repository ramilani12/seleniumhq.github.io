package dev.selenium.spedfiscal;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class SelectOptWait {
	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.tutorialspoint.com/selenium/selenium_automation_practice.htm");
		// expected condition presenceOfNestedElementsLocatedBy on options

		// WebDriverWait w = new WebDriverWait(driver,3);

		Wait<WebDriver> w = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(300)).ignoring(ElementNotInteractableException.class);

		try {

			w.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(By.xpath("//select[@name='continents']"),
					By.tagName("option")));
			// identify dropdown
			WebElement l = driver.findElement(By.xpath("//select[@name='continents']"));
			// select option by Select class
			Select s = new Select(l);
			// selectByVisibleText to choose an option
			s.selectByVisibleText("Africa");

		} catch (Exception e) {
			System.out.println("Options not available");
		}
		driver.quit();
	}
}