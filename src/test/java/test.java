import com.github.javafaker.Faker;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {
    public static void main(String[] args) throws InterruptedException {
        Faker faker = new Faker();
        String username = faker.name().username();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            Page page = browser.newPage();
            page.navigate("https://www.collegekickstart.com/");
            page.setViewportSize(1920, 1080);
            Thread.sleep(2000);

            page.locator("(//a[@href='#'][normalize-space()='Pricing'])[1]").hover();
            page.locator("(//a[normalize-space()='Students & Parents'])[2]").click();
            page.locator("(//a[normalize-space()='Sign up'])[3]").click();
            page.locator("//input[@id='payplansRegisterAutoFname']").type("Thaw");
            page.locator("(//input[@id='payplansRegisterAutoLname'])[1]").type("Zin");
            page.locator("//input[@id='payplansRegisterAutoUsername']").type(username.toLowerCase());
            page.locator("//input[@id='payplansRegisterAutoEmail']").type(username.toLowerCase() + "@gmail.com");
            page.locator("//input[@id='payplansRegisterAutoPassword']").type("@#Thaw_Zin@#");
            page.locator("//select[@id='payplansRegisterAutoRole']").selectOption("Student (Class of 2024)");
            page.locator("//input[@id='payplansRegisterAutoCeeb']").type("Aaron School (New York, NY)");
            page.locator("//span[@class='pui-autocomplete-query']").click();
            page.locator("//button[@id='payplans-order-confirm']").click();
            Thread.sleep(3000);
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Error/Error-Screenshot/screenshoot.jpeg")).setFullPage(true));

            page.onDialog(dialog -> {
                String text = dialog.message();
                System.out.println(text);
                Path path = Paths.get("./Error/Error-Text/Error.txt");
                try {
                    Files.writeString(path, text,
                            StandardCharsets.UTF_8);
                }
                catch (IOException ex) {
                    System.out.print("Invalid Path");
                }
                dialog.accept();
            });

            page.locator("//button[@id='pp-payment-app-buy']").click();
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    }


