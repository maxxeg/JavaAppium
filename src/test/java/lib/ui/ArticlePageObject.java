package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject
{
  protected static String
          TITLE,
          FOOTER_ELEMENT,
          OPTIONS_BUTTON,
          OPTIONS_ADD_TO_MY_LIST_BUTTON,
          OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
          ADD_TO_MY_LIST_OVERLAY,
          MY_LIST_NAME_INPUT,
          MY_LIST_OK_BUTTON,
          CLOSE_ARTICLE_BUTTON,
          MY_LIST_EXISTING_FOLDER_TPL;

  public ArticlePageObject(RemoteWebDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getFolderInListsElement(String name_of_folder) {
    return MY_LIST_EXISTING_FOLDER_TPL.replace("{FOLDER}", name_of_folder);
  }
  /* TEMPLATES METHODS */

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(TITLE,"Cannot find article title on page",15);
  }

  public String getArticleTitle() {
    WebElement title_element = waitForTitleElement();
    if (Platform.getInstance().isAndroid()) {
      return title_element.getAttribute("text");
    } else if (Platform.getInstance().isIOS()) {
      return title_element.getAttribute("name");
    } else {
      return title_element.getText();
    }
  }

  public void swipeToFooter() {
    if (Platform.getInstance().isAndroid()) {
      this.swipeUpToFindElement(FOOTER_ELEMENT,"Cannot find the end of article",40);
    } else if (Platform.getInstance().isIOS()) {
      this.swipeUpTillElementAppear(FOOTER_ELEMENT,"Cannot find the end of article",40);
    } else {
      this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
    }
  }

  public void addArticleToMyList(String name_of_folder) {
    this.waitForElementAndClick(OPTIONS_BUTTON,"Cannot find button to open article options",5);

    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list",5);

    this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,"Cannot find button 'GOT IT' tip overlay",15);

    this.waitForElementAndClear(MY_LIST_NAME_INPUT,"Cannot find text field on name of list popup",5);

    this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT, name_of_folder,"Cannot put any text in the text field on name of list popup",5);

    this.waitForElementAndClick(MY_LIST_OK_BUTTON,"Cannot press OK button",5);
  }

  public void addArticle2ToMyList(String name_of_folder) {
    this.waitForElementAndClick(OPTIONS_BUTTON,"Cannot find button to open article options",5);

    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list",5);

    String folder_in_lists = getFolderInListsElement(name_of_folder);
    this.waitForElementAndClick(folder_in_lists,"Cannot find folder in lists",5
    );
  }

  public void addArticlesToMySaved() {
    if (Platform.getInstance().isMW()) {
      this.removeArticleFromSavedIfItAdded();
    }
    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
  }

  public void clickOnEmptyPlaceToClosePopup(int x, int y) {
    this.clickOnEmptyPlace(x, y);
  }

  public void removeArticleFromSavedIfItAdded() {
    if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
      this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click button to remove an article from saved", 1);
      this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add an article to saved after removing it from this list before");
    }
  }

  public void closeArticle() {
    if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
      this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,"Cannot find Close button om article page",5);
    } else {
      System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }
  }

  public void assertElementTitlePresent() {
    this.assertElementPresent(TITLE,"We've not found title element");
  }
}
