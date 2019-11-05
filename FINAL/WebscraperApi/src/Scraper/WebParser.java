package Scraper;

import Objects.Company;
import Objects.VolCards;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class WebParser {

    // Field Variables
    private WebDriver driver;

    // Static Variables
    private final String WEBSITE_HOME = "https://www.sparkontario.ca";
    private final String WEBSITE_SEARCH = "https://www.sparkontario.ca/search/site";
    private String LOCATION = "Hamilton, ON, Canada";
    private String windowName;
    private ArrayList<VolCards> opurtunities;
    private String query;


    // Initialize Web Driver
    public WebParser () {

        // Set the property for the chrome driver
        System.setProperty("webdriver.chrome.driver", "/home/cunniemm/Desktop/Hackathon/Drivers/chromedriver");

        // Create chrome driver
        driver = new ChromeDriver();
        opurtunities = new ArrayList<>();
    }


    // Get the homepage of the website
    public boolean getHomepage () {

        // Get the website
        driver.get(WEBSITE_HOME+"/");

        if (driver.getCurrentUrl().equals(WEBSITE_HOME+"/")) {
            //LOG STUFF

            // Get window handle
            windowName = driver.getWindowHandle();

            // Get the search portion of website
            if (getSearch()) {
                parseVolCards();
            }

        } else {
            //LOG STUFF
        }
        return false;
    }

    private boolean getSearch() {

        // Select option
        driver.findElement(By.xpath("//*[@id=\"findhelp-solr-build-view-opportunities-block-form\"]/div/div[3]/div/button/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"findhelp-solr-build-view-opportunities-block-form\"]/div/div[3]/div/div/ul/li[2]/a/span[1]")).click();


        // Try and get the search bar source and search our current location
        WebElement searchField = driver.findElement(By.id("edit-geolocation-address-input"));

        try {

            searchField.sendKeys(LOCATION);

            Thread.sleep(1000);

            searchField.sendKeys(Keys.TAB);
            searchField.sendKeys(Keys.RETURN);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // This method uses the result from the search method
    private void parseVolCards () {


        for (int i = 0; i < 3; i ++) {

            // Wait 1 second before checking for elements

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Parse all the search results from a page
            for (WebElement ele : driver.findElements(By.className("search-result"))) {

                // Get link to opportunity and open new tab
                String oppLink = ele.findElement(By.className("listing-item-opportunity-image")).findElement(By.tagName("a")).getAttribute("href");

                // Parse Card info
                parseCard(ele, oppLink);

            }

            // Goto the next page
            getNextPage();
        }
    }


    // Get sub link from the card, goto that link in a new tab get the info and close the tab
    private void parseCard (WebElement card, String oppLink) {

        // Get title and company of current card
        String title = card.findElement(By.className("search-result-item-node-content")).findElement(By.tagName("h4")).findElement(By.tagName("a")).getText();
        String companyName = card.findElement(By.className("search-result-item-node-content")).findElement(By.tagName("h5")).findElement(By.tagName("a")).getText();

        // Get opportunity tags of card
        ArrayList<String> tempTags = new ArrayList<>();
        for (WebElement temp : card.findElement(By.className("opportunity-tags")).findElement(By.tagName("ul")).findElements(By.tagName("li"))) {
            if (!temp.getText().equalsIgnoreCase("") && !temp.getText().equalsIgnoreCase("...") && !temp.getText().equalsIgnoreCase("tag")) {
                tempTags.add(temp.getText());
            }
        }

        // Get Image
        String imageUrl = card.findElement(By.tagName("img")).getAttribute("src");

        // Create a temp object of VolCards
        VolCards vol = new VolCards(title, companyName, imageUrl, tempTags);
        parseSubLink(vol, oppLink);

    }


    // Parse the opportunity Link
    private void parseSubLink(VolCards temp, String oppLink) {

        // Create a new tab and open link
        ((JavascriptExecutor)driver).executeScript("window.open();");

        // Change into new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(oppLink);

        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

        // Get description and company info
        String description = "";
        String address = driver.findElement(By.xpath("//*[@id=\"site-page-body\"]/section/div[3]/div/ul/li[2]")).getText();
        String phone = driver.findElement(By.xpath("//*[@id=\"site-page-body\"]/section/div[3]/div/ul/li[1]")).getText();

        // Description div can have multiple identifiers
        try {

            // Try class caller
            description = driver.findElement(By.className("about-this-opportunity-multiple")).getText();

            // Catch exception if fails
        } catch (Exception e) {
            System.out.println("[Catch]: Html TAG was ID");
            description = driver.findElement(By.id("about-this-opportunity")).getText();
        }




        // Check regex for phone number
        Pattern pattern = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
        phone = phone.replace('\n', ' ');
        address = address.replace('\n', ' ');
        description = description.replace('\n', ' ');

        // Convert to list separated by spaces
        String [] dataList = phone.split(" ");

        // Iterate through list to find match
        for (String p : dataList) {
            if (pattern.matcher(p).matches()) {
                phone = p;
            }
        }


        // Set company variables
        Company company = new Company(temp.getCompanyName(), phone, address);
        temp.setCompany(company);

        // Set description variable
        temp.setDescription(description);
        temp.setOppurtunityLocation(company.getAddress());

        // Add the opportunity
        opurtunities.add(temp);

        // Close the tab
        driver.close();
        driver.switchTo().window(tabs.get(0));

    }



    // Get the next page of results
    private void getNextPage() {

        // Flag
        boolean flag = false;

        // Iterate over which item is selected and find the next possible value
        for (WebElement ele : driver.findElements(By.tagName("input"))) {

            // Check for flag
            if (flag) {
                ele.click();
                flag = false;
                return;
            } else {

                // Check tpye attribute
                if (ele.getAttribute("type").equalsIgnoreCase("submit")) {

                    // Check what class the element has
                    if (ele.getAttribute("class").equalsIgnoreCase("pager-current-link pager-link form-submit form-button-disabled")) {
                        flag = true;
                    }

                }
            }

        }



    }


    // Getter

    public ArrayList<VolCards> getOpurtunities() {
        return opurtunities;
    }

    public void setOpurtunities(ArrayList<VolCards> opurtunities) {
        this.opurtunities = opurtunities;
    }
}