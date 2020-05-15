package tests;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import java.util.List;

public class HomeWork extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;
  private NavigationUI NavigationUI;
  private MyListsPageObject MyListsPageObject;
  private static final String
          login = "mmaxxeg",
          password = "epyfq2000";


  @Test
  public void testCancelSearch_Ex3() throws InterruptedException {
    SearchPageObject = SearchPageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Earth");
    int amount_articles_title = SearchPageObject.waitForSearchAmountResults("Earth"); // ищем вхождения в заголовках результатов поиска

    Assert.assertTrue(
              "No any articles title with search word",
              amount_articles_title > 1
    );

    SearchPageObject.clickCancelSearch(); // удаляем значение из поисковой строки

    int amount_articles_title_after_cancel = SearchPageObject.waitForSearchAmountResults("Earth"); // повторно ищем вхождения в заголовках результатов поиска

    Assert.assertFalse(
            "The are any articles title with search word",
            amount_articles_title_after_cancel > 1
    );
  }

  @Test
  public void testSave2ArticlesToList_Ex5_Ex11_Ex17() throws InterruptedException
  {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);
    NavigationUI = NavigationUIFactory.get(driver);
    MyListsPageObject = MyListsPageObjectFactory.get(driver);
    AuthorizationPageObject Auth = new AuthorizationPageObject(driver);

    String search_word = "Languages of";
    String name_of_folder = "My list";

    // добавляем первую статью
    SearchPageObject.initSearchInput(); // кликаем на строку поиска
    SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос
    int amount_articles_title = SearchPageObject.amountOfAllArticlesTitleOnSearchPage(); // получаем количество заголовков

    // проверяем что статей больше чем 1
    Assert.assertTrue(
            "Cannot find any articles with title included search word " + search_word,
            amount_articles_title > 1
    );

    // Android
    if (Platform.getInstance().isAndroid()) {
      List<WebElement> articles_title = SearchPageObject.allArticlesTitleOnSearchPage(); // получаем список заголовков статей
      String article_title_from_search_1 = articles_title.get(0).getText(); // название первой статьи
      String article_title_from_search_2 = articles_title.get(1).getText(); // название второй статьи
      SearchPageObject.clickByArticleWithSubstring(article_title_from_search_1); // кликаем на заголовок 1-й статьи
      ArticlePageObject.addArticleToMyList(name_of_folder); // создаем папку списка и добавляем в нее первую статью
      ArticlePageObject.closeArticle(); // возврат на Главную
      SearchPageObject.initSearchInput(); // кликаем на строку поиска
      SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос
      SearchPageObject.clickByArticleWithTitle(article_title_from_search_2); // кликаем на заголовок 2-й статьи
      ArticlePageObject.addArticle2ToMyList(name_of_folder); // добавляем в папку вторую статью
      ArticlePageObject.closeArticle(); // возврат на Главную
      NavigationUI.clickMyLists(); // переходим к спискам статей
      MyListsPageObject.openFolderByName(name_of_folder); // переходим в папку

      int amount_articles_title_in_list = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // убеждаемся что добавлено 2-е статьи
      Assert.assertTrue(
              "Amount of articles in list <> 2",
              amount_articles_title_in_list == 2
      );

      MyListsPageObject.swipeByArticleToDelete(article_title_from_search_1); // делаем свайп для первой статьи и удаляем ее

      int amount_articles_title_in_list_after_delete = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // проверяем что осталась только одна статья в списке
      Assert.
              assertTrue(
                      "Amount of articles in list <> 1",
                      amount_articles_title_in_list_after_delete == 1
              );

      MyListsPageObject.waitForArticleByTitleAndClick(article_title_from_search_2); // переходим на статью которая осталась

      WebElement title_element = ArticlePageObject.waitForTitleElement(); // получаем элемент с названием на экране статьи
      String article_title = title_element.getAttribute("text"); // получаем название статьи

      // сравниваем название статьи 2 из поиска и на экране статьи
      Assert.assertEquals(
                "We see unexpected title",
                article_title_from_search_2,
                article_title
      );
    }
    // iOS
    else if (Platform.getInstance().isIOS()) {
      List<WebElement> articles_title = SearchPageObject.allArticlesTitleOnSearchPage(); // получаем список заголовков статей
      String article_title_from_search_1 = articles_title.get(0).getText(); // название первой статьи
      String article_title_from_search_2 = articles_title.get(1).getText(); // название второй статьи
      SearchPageObject.clickByArticleWithSubstring(article_title_from_search_1); // кликаем на заголовок 1-й статьи
      ArticlePageObject.addArticlesToMySaved();
      ArticlePageObject.clickOnEmptyPlaceToClosePopup(50, 50);
      ArticlePageObject.closeArticle(); // возврат на Главную
      SearchPageObject.initSearchInput(); // кликаем на строку поиска
      SearchPageObject.clickByArticleWithSubstring(article_title_from_search_2); // кликаем на заголовок 2-й статьи
      ArticlePageObject.addArticlesToMySaved();
      ArticlePageObject.closeArticle(); // возврат на Главную
      NavigationUI.clickMyLists(); // переходим к спискам статей

      int amount_articles_title_in_list = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // убеждаемся что добавлено 2-е статьи
      Assert.assertTrue(
              "Amount of articles in list <> 2",
              amount_articles_title_in_list == 2
      );

      MyListsPageObject.swipeByArticleToDelete(article_title_from_search_1); // делаем свайп для первой статьи и удаляем ее

      int amount_articles_title_in_list_after_delete = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // проверяем что осталась только одна статья в списке
      Assert.
              assertTrue(
                      "Amount of articles in list <> 1",
                      amount_articles_title_in_list_after_delete == 1
              );

      MyListsPageObject.waitForArticleByTitleAndClick(article_title_from_search_2); // переходим на статью которая осталась

      ArticlePageObject.waitForElementPresent(
                "id:" + article_title_from_search_2,
                "Article title on page is not equal by '" + article_title_from_search_2 + "'");
    }
    // Mobile WEB
    else if (Platform.getInstance().isMW()) {
      List<WebElement> articles_title = SearchPageObject.allArticlesTitleOnSearchPage(); // получаем список заголовков статей
      String article_title_from_search_1 = articles_title.get(0).getAttribute("title"); // название первой статьи
      String article_title_from_search_2 = articles_title.get(1).getAttribute("title"); // название второй статьи
      SearchPageObject.clickByArticleWithTitle(article_title_from_search_1); // кликаем на заголовок 1-й статьи
      ArticlePageObject.addArticlesToMySaved(); // кликаем на иконку добавления статьи в список
      // авторизуемся
      Auth.clickAuthButton();
      Auth.enterLoginData(login, password);
      Auth.submitForm();
      ArticlePageObject.waitForTitleElement(); // ждем появления названия статьи на странице после авторизации
      assertEquals("We are not on the same page after login", article_title_from_search_1, ArticlePageObject.getArticleTitle()); // убеждаемся что это нужная статья
      ArticlePageObject.addArticlesToMySaved(); // повторно кликаем на иконку добавления статьи в список

      ///////// добавление второй статьи
      SearchPageObject.initSearchInput(); // кликаем на строку поиска
      SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос
      SearchPageObject.clickByArticleWithTitle(article_title_from_search_2); // кликаем на заголовок 2-й статьи
      ArticlePageObject.addArticlesToMySaved(); // кликаем на иконку добавления статьи в список

      ///////// список сохраненных статей
      NavigationUI.openNavigation(); // кликаем на бургер меню
      NavigationUI.clickMyLists(); // кликаем на пункт перехода в список

      int amount_articles_title_in_list = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // убеждаемся что добавлено 2-е статьи
      Assert.assertTrue(
              "Amount of articles in list <> 2",
              amount_articles_title_in_list == 2
      );

      MyListsPageObject.swipeByArticleToDelete(article_title_from_search_1); // удаляем первую статью и делаем обновление страницы

      int amount_articles_title_in_list_after_delete = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

      // проверяем что осталась только одна статья в списке
      Assert.
              assertTrue(
                      "Amount of articles in list <> 1",
                      amount_articles_title_in_list_after_delete == 1
              );

      MyListsPageObject.waitForArticleByTitleAndClick(article_title_from_search_2); // переходим на статью которая осталась

      WebElement title_element = ArticlePageObject.waitForTitleElement(); // получаем элемент с названием на экране статьи
      String article_title = title_element.getAttribute("text"); // получаем название статьи

      System.out.println(title_element);
      System.out.println(article_title);

      // сравниваем название статьи 2 из поиска и на экране статьи
      Assert.assertEquals(
              "We see unexpected title",
              article_title_from_search_2,
              article_title
      );
    }
  }

  @Test
  public void testAssertTitle_Ex6() {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject.assertElementTitlePresent();
  }

  @Test
  public void testTemplateRefactoring_Ex9_Ex12_Ex18() {
    SearchPageObject = SearchPageObjectFactory.get(driver);

    String search_word = "Languages of";

    SearchPageObject.initSearchInput(); // кликаем на строку поиска
    SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос

    if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
      String[][] titlesAndDescriptions = {
              {"Languages of India", "Languages of a geographic region"},
              {"Languages of the United States", "Languages of a geographic region"},
              {"Languages of the Philippines", "Languages of a geographic region"}
      };
      String title;
      String description;
      // в цикле проверяем есть ли блок с таким названием и описанием, сравнивая со значениями из массива
      for (int i = 0; i < 3; i ++) {
        title = titlesAndDescriptions[i][0];
        description = titlesAndDescriptions[i][1];

        // обрезаем первый символб т.к. в веб не угадаешь загалваня или строчная первая буква описания
        if (Platform.getInstance().isMW()) {
          description = description.substring(1);
        }
        SearchPageObject.waitForElementByTitleAndDescription(title, description);
      }
    } else if (Platform.getInstance().isIOS()) {
      String [][] titles = {
              {"Languages of India\nLanguages of a geographic region"},
              {"Languages of the United States\nLanguages of a geographic region"},
              {"Languages of the Philippines\nLanguages of a geographic region"}
      };
      // в цикле проверяем есть ли блок с таким названием и описанием, сравнивая со значениями из массива
      for (int i = 0; i < 3; i ++) {
        SearchPageObject.waitForElementByTitle(titles[i][0]);
        System.out.println(titles[i][0]);
      }
    }
  }
}
