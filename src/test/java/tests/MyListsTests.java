package tests;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;
  private NavigationUI NavigationUI;
  private MyListsPageObject MyListsPageObject;

  private static final String name_of_folder = "Learning programming";
  private static final String
          login = "mmaxxeg",
          password = "epyfq2000";

  @Test
  public void testSaveFirstArticleToMyList() throws InterruptedException {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);
    NavigationUI = NavigationUIFactory.get(driver);
    MyListsPageObject = MyListsPageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("bject-oriented programming language");
    SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();

    // Android
    if (Platform.getInstance().isAndroid()) {
      ArticlePageObject.addArticleToMyList(name_of_folder);
      ArticlePageObject.closeArticle();
      NavigationUI.openNavigation();
      NavigationUI.clickMyLists();
      MyListsPageObject.openFolderByName(name_of_folder);
      MyListsPageObject.swipeByArticleToDelete(article_title);
    }
    // iOS
    else if (Platform.getInstance().isIOS()) {
      ArticlePageObject.addArticlesToMySaved();
      ArticlePageObject.clickOnEmptyPlaceToClosePopup(50, 50);
      ArticlePageObject.closeArticle();
      NavigationUI.openNavigation();
      NavigationUI.clickMyLists();
      MyListsPageObject.swipeByArticleToDelete(article_title);
    }
    // Mobile WEB
    else {
      ArticlePageObject.addArticlesToMySaved();
      AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
      Auth.clickAuthButton();
      Auth.enterLoginData(login, password);
      Auth.submitForm();
      ArticlePageObject.waitForTitleElement();

      assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle());

      ArticlePageObject.addArticlesToMySaved();
      NavigationUI.openNavigation();
      NavigationUI.clickMyLists();
      MyListsPageObject.swipeByArticleToDelete(article_title);
    }
  }

}
