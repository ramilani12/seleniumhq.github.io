package dev.selenium.spedfiscal;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScriptUniprofissionaisDF {

	public static void main(String[] args) {
		EdgeOptions options = new EdgeOptions();
		
		Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", "/home/rmilani/Downloads/test");
        prefs.put("browser.download.dir", "/home/rmilani/Downloads/test");
        prefs.put("browser.download.folderList", 2); // custom location
        prefs.put("browser.download.manager.showWhenStarting", false);
        prefs.put("browser.helperApps.alwaysAsk.force", false);
        
        options.setExperimentalOption("prefs", prefs);
		
        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.setExperimentalOption("prefs", prefs);
        
		var driver = new EdgeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		driver.get(
				"https://www.sped.fazenda.gov.br/spedtabelas/AppConsulta/publico/aspx/ConsultaTabelasExternas.aspx?CodSistema=SpedFiscal");

		// Combox Pacote by name - ctl00$ContentPlaceHolder1$ddlPacotes
		WebElement webElementPacotes = driver
				.findElement(By.xpath("//select[@name='ctl00$ContentPlaceHolder1$ddlPacotes']"));

		Select selectPacotes = new Select(webElementPacotes);
		selectPacotes.selectByVisibleText("Tabelas do Distrito Federal");

		// webElementPacotes.sendKeys("Tabelas do Distrito Federal");

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Aguardar até que o segundo select seja clicável
			WebElement webElementTabelas = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//select[@name='ctl00$ContentPlaceHolder1$ddlTabelas']")));

			// Aguardar até que as opções do segundo select sejam visíveis
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//select[@name='ctl00$ContentPlaceHolder1$ddlTabelas']")));

			Select s = new Select(webElementTabelas);
			s.selectByVisibleText(
					"4.6.1 - Tabela de Valores Mensais Devidos por Profissional Habilitado – Uniprofissionais - DF");

			WebElement webElementButtonDownload = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//input[@name='ctl00$ContentPlaceHolder1$btnBaixarTabela']")));

			webElementButtonDownload.click();
			
			File file = new File( "/home/rmilani/Downloads/test" + File.separator + "tb15074.txt" + ".crdownload");
			
			wait.until(d -> file.exists() && !file.getName().endsWith(".crdownload"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.quit();

	}

}
