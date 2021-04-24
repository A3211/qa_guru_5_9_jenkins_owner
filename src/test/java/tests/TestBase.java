package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.*;

public class TestBase {
    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        addListener("AllureSelenide", new AllureSelenide());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        //gradle clean test -Dweb.browser=opera
        Configuration.browser = (System.getProperty("web.browser", "chrome"));

        String remoteWebDriver = System.getProperty("remote.web.driver");
        if (remoteWebDriver != "") {
            Configuration.remote = remoteWebDriver;
        }

        //Запускаем тесты локально
        //gradle clean test

        //Selenoid удаленно
        //gradle clean test -Dremote.web.driver="https://user1:1234@selenoid.autotests.cloud/wd/hub"
        //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
        closeWebDriver();
    }
}
