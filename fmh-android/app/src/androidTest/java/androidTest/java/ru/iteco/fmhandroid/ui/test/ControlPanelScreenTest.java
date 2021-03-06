package androidTest.java.ru.iteco.fmhandroid.ui.test;

import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.Rand.randomNews;
import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.createNews;
import static androidTest.java.ru.iteco.fmhandroid.ui.data.Helper.deletingNewsUpToTheNumberOfTenControlPanelScreen;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidTest.java.ru.iteco.fmhandroid.ui.data.Helper;
import androidTest.java.ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.CreatingNewsScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.EditingNewsScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.FilterNewsScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.MainScreenStep;
import androidTest.java.ru.iteco.fmhandroid.ui.step.NewsScreenStep;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ControlPanelScreenTest {

    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    MainScreenStep mainScreenStep = new MainScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    NewsScreenStep newsScreenStep = new NewsScreenStep();
    CreatingNewsScreenStep creatingNewsScreenStep = new CreatingNewsScreenStep();
    FilterNewsScreenStep filterNewsScreenStep = new FilterNewsScreenStep();
    EditingNewsScreenStep editingNewsScreenStep = new EditingNewsScreenStep();

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
            SystemClock.sleep(3000);
        }
        deletingNewsUpToTheNumberOfTenControlPanelScreen(7);
        SystemClock.sleep(5000);
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }


    @Test
    @DisplayName("?? ???????????? ???????????? ???????? ????????????????")
    @Description("?? ???????? ???????? ?????????? ???? ?????????????????? ???????????????? ???????????? Control Panel")
    public void theScreenShouldHaveName() {
        mainScreenStep.switchingToTheControlPanel();
        controlPanelScreenStep.checkingTheNameOfTheControlPanelScreen();
    }

    @Test
    @DisplayName("News blocks should be swapped")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? \"?????????????? ?????????? ?? ????????\" ?? \"Control panel\"" +
            " ???????????? ???????????????????? ?????????????? ?????????????????? ?????????? ???????????? ???? ?????????? ?????????????? ???????? ???? ?????????????????????? (?????????? ???????????? ????????" +
            " ???????????? ?????????????????? ???? ?????????? ??????????  ?????????????????? ??????????), ???? ?????????? ???????????? (???? ????????????????, ?????????? ?????????? ???????? ???????????? " +
            "?????????????????? ?? ?????????? ???????? ?????????????????? ??????????) ?? ????????????????")
    public void newsBlocksShouldBeSwapped() {
        mainScreenStep.switchingToTheControlPanel();
        String firstNews = newsScreenStep.news();
        SystemClock.sleep(5000);
        controlPanelScreenStep.changeOfSorting();
        SystemClock.sleep(5000);
        String lastNews = newsScreenStep.news();
        SystemClock.sleep(5000);
        controlPanelScreenStep.changeOfSorting();
        SystemClock.sleep(5000);
        String firstNewsAgain = newsScreenStep.news();
        SystemClock.sleep(5000);
        controlPanelScreenStep.checkingTheNewsBeforeSortingAndAfter(firstNews, firstNewsAgain, lastNews);
    }

    @Test
    @DisplayName("must go to the News Filter")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? \"?????? ?????????????? ?? ????????????????\" ??  Control panel, ???????????????????????? ???????????????? ?? \"Filter news\" ")
    public void mustGoToTheNewsFilter() {
        mainScreenStep.switchingToTheControlPanel();
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        SystemClock.sleep(5000);
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
    }

    @Test
    @DisplayName("should go to the news creation section")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? \"+\" ??  Control panel, ???????????????????????? ???????????????? ??  \"Creating News\"")
    public void shouldGoToTheNewsCreationSection() {
        mainScreenStep.switchingToTheControlPanel();
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        SystemClock.sleep(5000);
        creatingNewsScreenStep.checkingTheNameOfTheCreatingNewsScreen();
    }

    @Test
    @DisplayName("must delete the news")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? ?? ?????????????? \"???????????????? ??????????\" ??  Control panel," +
            " ?????????? ?????????????????? ???????????????????????? ???????? ?? ???????????????? \"Are you sure you wont to permanently delete the document? " +
            "These changes cannot be reversed in the future.\" ?????? ?????????????? ???? ????????????  \"????\"  ???????????????????? ????????????????" +
            " ???????????????????? ?????????? ")
    public void mustDeleteTheNews() {
        SystemClock.sleep(3000);
        int position = randomNews(0);
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);

        createNews(text, category);

        String nameNewsItWas = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWas = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWas = controlPanelScreenStep.creationDateNews();
        String authorNewsItWas = controlPanelScreenStep.authorNews();
        String descriptionNewsItWas = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        SystemClock.sleep(3000);
        controlPanelScreenStep.clickingOnTheDeleteNewsButton();
        SystemClock.sleep(3000);
        controlPanelScreenStep.clickingOnTheConfirmationButtonToDeleteTheNews();
        SystemClock.sleep(3000);
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        SystemClock.sleep(3000);

        String nameNewsItWasHasBecomes = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = controlPanelScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = controlPanelScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.checkingTheDataOfTheFirstNewsInTheListBeforeAndAfterDeletingTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("Must not delete a news block from the news feed while in the Control panel")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? ?? ?????????????? \"???????????????? ??????????\" ??  Control panel," +
            " ?????????? ?????????????????? ???????????????????????? ???????? ?? ???????????????? \"Are you sure you wont to permanently delete the document?" +
            " These changes cannot be reversed in the future.\" ?????? ?????????????? ???? ????????????  \"CANCEL\" ???? ???????????????????? ????????????????" +
            " ???????????????????? ??????????")
    public void mustNotDeleteNewsBlockFromTheNewsFeedWhileInTheControlPanel() {
        mainScreenStep.switchingToTheControlPanel();
        SystemClock.sleep(3000);
        int position = randomNews(1);

        String nameNewsItWas = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWas = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWas = controlPanelScreenStep.creationDateNews();
        String authorNewsItWas = controlPanelScreenStep.authorNews();
        String descriptionNewsItWas = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        SystemClock.sleep(3000);
        controlPanelScreenStep.clickingOnTheDeleteNewsButton();
        SystemClock.sleep(3000);
        controlPanelScreenStep.clickingOnTheCancelConfirmationButtonToDeleteTheNews();
        SystemClock.sleep(3000);

        String nameNewsItWasHasBecomes = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = controlPanelScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = controlPanelScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheDataOfTheFirstNewsInTheListBeforeAndAfterCancelingTheDeletionOfTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("Must go to Filter news from Control panel")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? \"?????? ?????????????? ?? ????????????????\" ??  Control panel, ???????????????????????? ???????????????? ?? \"Filter news\" ")
    public void mustGoToFilterNewsFromControlPanel() {
        mainScreenStep.switchingToTheControlPanel();
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
    }

    @Test
    @DisplayName("must go to editing news")
    @Description("?? ???????? ???????? ?????????? ???? ??????????????????, ?????? ?????? ?????????????? ???? ???????????? ?? ?????????????? \"?????????????? ?? ??????????????????????\" ?? ?????????????????? ??????????, ??  Control panel, ???????????????????? ?????????????? ?? ???????????? \"Editing News\"")
    public void mustGoToEditingNews() {
        int position = randomNews( 0, 1, 2);

        mainScreenStep.switchingToTheControlPanel();
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        SystemClock.sleep(2000);
        controlPanelScreenStep.clickingOnTheButtonToGoToTheEditingNewsScreen(position);
        editingNewsScreenStep.checkingTheNameOfTheEditingNewsScreen();
    }
}

