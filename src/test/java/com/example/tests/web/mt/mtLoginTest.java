package com.example.tests.web.mt;

import com.example.base.Actions;
import com.example.base.BaseTest;
import com.example.base.DriverManager;
import com.example.modules.amazon.AmazonModule;
import com.example.utils.Helper;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;

import java.util.Map;

import static com.example.objects.mt.MtObjects.*;

public class mtLoginTest extends BaseTest {

  // In line web actions example
  @Test()
  public void mtLoginTestVerify(Map<String, String> data) {
    Actions.openUrl(data.get("Url"));
//    DriverManager.getDriver().switchTo().alert().dismiss();
    Actions.takeScreenshot();
    Actions.enterText(MT_LOGINPAGE_USERNAME, data.get("UserName"), "Entered Username ");
    Actions.click(MT_CUSTOMLOGIN_CHECKBOX, "Selected Custom login Checkbox");
    Actions.click(MT_CUSTOMLOGIN_BUTTON_NEXT, "Clicked on Next Button");
    Actions.enterText(MT_LOGINPAGE_PASSWORD, "Entered password");
    Actions.click(MT_PASSWORD_REMEMBERME_CHECKBOX, "Selected Rememberme Checkbox");
    Actions.click(MT_LOGIN_BUTTON, "Clicked on Login Button");
    Actions.takeScreenshot();
    Helper.log("Login Completed for MT application for User Ecos");
  }
}
