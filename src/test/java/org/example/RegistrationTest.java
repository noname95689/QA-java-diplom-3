package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.commands.IsDisplayed;
import org.example.model.RegistrationData;
import org.example.pageobject.*;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.page;

public class RegistrationTest {

    MainPage mainPage = page(MainPage.class);
    HeaderPage headerPage = page(HeaderPage.class);
    RegistrationPage registrationPage = page(RegistrationPage.class);
    LoginPage loginPage = page(LoginPage.class);
    ProfilePage profilePage = page(ProfilePage.class);

    @Before
    public void beforeTests() {
        //Подключение Firefox
        //Configuration.browser = "Firefox";
        //Открытие браузера в полноэкранном режиме
        Configuration.startMaximized = true;
        //Открываем главную страницу
        mainPage.openMainPage();
        //Нажимаем на кнопку принятия cookies
    }

    @Test
    public void userCanBeRegistered() {
        headerPage.clickOnProfileButton();
        loginPage.clickOnRegistrationButton();
        registrationPage.inputRegistrationData();
        MatcherAssert.assertThat("Кнопка 'Оформить заказ' не отобразилась - авторизация не произошла",
                loginPage.getLoginPageHeader().isDisplayed());
    }

    @Test
    public void userWithShortPasswordCantBeRegistered() {

    }
}
