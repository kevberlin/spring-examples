package com.example.webmvc.selenium;

import com.example.webmvc.model.NewsUser;
import com.example.webmvc.service.NewsUserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationIntegrationTest {

    @LocalServerPort
    private int port;
    private WebDriver driver;

    @SpyBean
    private NewsUserRepository userRepository;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "target/drivers/chromedriver-windows-32bit.exe");
        ChromeOptions options = new ChromeOptions();
        /*
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
// docker...
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");*/
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testRegistration() {
        driver.get("http://localhost:" + port + "/user/register");
        driver.findElement(By.id("firstname")).sendKeys("test");
        driver.findElement(By.id("lastname")).sendKeys("test");
        driver.findElement(By.id("birthday")).sendKeys("01.01.1970");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.tagName("button")).click();
        NewsUser expected = new NewsUser("test","test",
                LocalDate.of(1970,1,1),"test","test");
        verify(userRepository, times(1))
                .save(expected);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
