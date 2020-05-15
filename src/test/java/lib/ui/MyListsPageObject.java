package lib.ui;
import lib.Platform;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class MyListsPageObject extends MainPageObject
{
  protected static String
          FOLDER_BY_NAME_TPL,
          ARTICLE_BY_TITLE_TPL,
          ARTICLE_IN_LIST_TITLE,
          REMOVE_FROM_SAVED_BUTTON;

  public MyListsPageObject(RemoteWebDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getFolderXpathByName(String name_of_folder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
  }

  private static String getRemoveButtonByTitle(String article_title) {
    return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
  }

  private static String getSavedArticleXpathByTitle(String article_title) {
    return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
  }
  /* TEMPLATES METHODS */

  public void openFolderByName(String name_of_folder) {
    String folder_name_xpath = getFolderXpathByName(name_of_folder);
    this.waitForElementAndClick(folder_name_xpath,"Cannot find folder by name " + name_of_folder,15);
  }

  public void waitForArticleToAppearByTitle(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementPresent(article_title_xpath,"Cannot find saved article by title " + article_title,15);
  }

  public void waitForArticleToDisappearByTitle(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementNotPresent(article_title_xpath,"Saved article still present by title " + article_title,15);
  }

  public void swipeByArticleToDelete(String article_title) {
    this.waitForArticleToAppearByTitle(article_title);
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);

    if(Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
      this.swipeElementToLeft(article_title_xpath,"Cannot find saved article by title " + article_title);
    } else {
      String remove_locator = getRemoveButtonByTitle(article_title);
      this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved", 10);
    }
    if (Platform.getInstance().isIOS()) {
      this.clickElementToTheRightUpperCorner(article_title_xpath, "Cannot find saved article");
    }

    if (Platform.getInstance().isMW()) {
      driver.navigate().refresh();
    }

    this.waitForArticleToDisappearByTitle(article_title);
  }

  public void waitForArticleByTitleAndClick(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementAndClick(article_title_xpath,"Cannot find article in list folder " + article_title,15);
  }

  public int amountOfAllArticlesTitleOnList() throws InterruptedException {
    Thread.sleep(3000);
    List<WebElement> articles_title = findElementsByLocator(ARTICLE_IN_LIST_TITLE);
    return articles_title.size();
  }
}
