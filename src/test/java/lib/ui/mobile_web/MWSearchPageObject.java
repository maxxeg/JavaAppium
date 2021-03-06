package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject
{

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input.search";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_PLACEHOLDER = "css:form>input.search[contains(@placeholder, 'Search Wikipedia')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_IN_TITLE_TPL = "xpath://div[@class='results']//ul//li[@title='{TITLE}']";
        SEARCH_RESULT_LOCATOR_IN_TITLE = "css:div.results ul li";
        SEARCH_RESULT_IN_TITLE_AND_DESCRIPTION_TPL = "xpath://div[@class='results']//ul//li[contains(@title, '{TITLE}')]//div[contains(text(), '{DESCRIPTION}')]";
    }
    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
