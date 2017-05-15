package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Вспомогательный класс для управления webDriver
 */
public class WebDriverFactory {

    public static WebDriver createWebDriver(String browserName, String driverPath, String gridHubUrl) throws Exception {
        browserName = browserName != null ? browserName : "firefox";
        return WebDriverProvider.valueOf(browserName.toUpperCase()).createWebDriver(driverPath, gridHubUrl);
    }

    /**
     * Выбор конкретного браузера
     *
     * @return WebDriver
     */
    public enum WebDriverProvider {

        CHROME("chrome") {
            @Override
            public WebDriver createWebDriver(String driverPath, String gridHubUrl) throws MalformedURLException {
                return WebDriverProvider.WebDriverCreator.createChromeDriver(driverPath, gridHubUrl);
            }
        },
        IE("ie") {
            @Override
            public WebDriver createWebDriver(String driverPath, String gridHubUrl) throws MalformedURLException {
                return WebDriverProvider.WebDriverCreator.createIEDriver(driverPath, gridHubUrl);
            }
        },
        FIREFOX("firefox") {
            @Override
            public WebDriver createWebDriver(String driverPath, String gridHubUrl) throws MalformedURLException {
                return WebDriverProvider.WebDriverCreator.createFireFoxDriver(gridHubUrl);
            }
        };

        private final String name;

        WebDriverProvider(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract WebDriver createWebDriver(String driverPath, String gridHubUrl) throws MalformedURLException;

        private static class WebDriverCreator {

            /**
             * Проверяет существует ли указанный путь
             *
             * @param seleniumDriverPath
             */
            private static String ensureDriverPath(String seleniumDriverPath) {
                String path = new File(seleniumDriverPath).getAbsolutePath();
                if (!new File(path).exists()) {
                    throw new RuntimeException("Selenium browser driver file did not exists: " + path);
                }
                return path;
            }

            /**
             * Создает инстанс chromeDriver
             *
             * @param driverPath обязательно для chromeDriver
             * @param gridHubUrl опциональный параметр
             * @return WebDriver
             * @throws MalformedURLException
             */
            private static WebDriver createChromeDriver(String driverPath, String gridHubUrl) throws MalformedURLException {
                ensureDriverPath(driverPath);
                String file = ensureDriverPath(driverPath);
                System.setProperty("webdriver.chrome.driver", file);
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                // In case there is Hub use
                if ((null != gridHubUrl) && !gridHubUrl.isEmpty()) {
                    return createRemoteWebDriver(gridHubUrl, capabilities);
                }
                return new ChromeDriver(capabilities);
            }

            /**
             * Создает инстанс ieDriver
             *
             * @param driverPath обязательно для IEServerDriver
             * @param gridHubUrl опциональный
             * @return WebDriver
             * @throws MalformedURLException
             */

            private static WebDriver createIEDriver(String driverPath, String gridHubUrl) throws MalformedURLException {
                String file = ensureDriverPath(driverPath);
                System.setProperty("webdriver.ie.driver", file);
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                if ((null != gridHubUrl) && !gridHubUrl.isEmpty()) {
                    return createRemoteWebDriver(gridHubUrl, capabilities);
                }
                return new InternetExplorerDriver(capabilities);
            }

            /**
             * Создает инстанс ForeFox
             *
             * @param gridHubUrl опциональный
             * @return WebDriver
             * @throws MalformedURLException
             */

            private static WebDriver createFireFoxDriver(String gridHubUrl) throws MalformedURLException {
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                FirefoxProfile ffProfile = new FirefoxProfile();
                ffProfile.setPreference("network.http.phishy-userpass-length", 255);
                capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);
                capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                if ((null != gridHubUrl) && !gridHubUrl.isEmpty()) {
                    return createRemoteWebDriver(gridHubUrl, capabilities);
                }
                return new FirefoxDriver(ffProfile);
            }

            /**
             * Создает инстанс Remote WebDriver
             *
             * @param gridHubUrl   обязательный grid Hub url
             * @param capabilities обязательный browser capabilities
             * @return
             * @throws MalformedURLException
             */

            private static WebDriver createRemoteWebDriver(String gridHubUrl, DesiredCapabilities capabilities) throws MalformedURLException {
                WebDriver webDriver = new RemoteWebDriver(new URL(gridHubUrl), capabilities);
                return webDriver;
            }
        }
    }

}
