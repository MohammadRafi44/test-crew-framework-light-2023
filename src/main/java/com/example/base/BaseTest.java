package com.example.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.example.report.ExtentTestManager;
import com.example.utils.ConfigManager;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

/**
 * Base Test class for test cases
 */
public abstract class BaseTest {

  private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

  /**
   * User to override and provide desired capabilities. If there are no capabilities to add then simply return null
   */
  public DesiredCapabilities addCapabilities() {
    return null;
  }

  @DataProvider(name = "testDataProvider")
  public static Object[] testDataProvider(Method method) throws IOException {
    List<Map<String, String>> controllerRowsList = ExcelManager.getControllerRowsList();
    List<Map<String, String>> rowMapList = controllerRowsList.stream()
        .filter(rowMap -> method.getName().equals(rowMap.get("TestMethodName"))).collect(Collectors.toList());
    Map<String, String> controllerRowMap = rowMapList.get(0);
    String testDataSheetName = controllerRowMap.get("Data_Sheet");
    LOGGER.debug("Will read from sheet [{}] for test method [{}]", testDataSheetName, method.getName());
    List<Map<String, String>> excelDataRowsAsListOfMap = ExcelManager
        .getExcelRowsAsListOfMap(Constants.RUN_MANAGER_WORKBOOK.toString(), testDataSheetName, method.getName());
    return excelDataRowsAsListOfMap.toArray(new Object[0]);
  }

  @BeforeMethod(description = "Set Up", alwaysRun = true)
  protected void setUp(ITestResult result, ITestContext context, Object[] objects) {
    Map<String, String> controllerRowMap = ExcelManager.getControllerRowMapByTestMethodName(result.getMethod().getMethodName());
    ExtentTestManager.startTest(result.getMethod().getMethodName(), controllerRowMap.get("Description"));
    ExtentTest extentTest = ExtentTestManager.getTest();
    extentTest.info("Test Started");
    extentTest.assignCategory(controllerRowMap.get("Data_Sheet"));
    LOGGER.info("Executing test method [{}]", result.getMethod().getMethodName());
    String tcType = controllerRowMap.get("TC_TYPE");
    context.setAttribute("TC_TYPE", tcType);
    if (tcType.equalsIgnoreCase("web") || tcType.equalsIgnoreCase("web-api")) {
      extentTest.info("Browser : " + ConfigManager.getBrowser());
      LOGGER.info("Current Test_Type is either web or web-api. Will instantiate web driver");
      DriverManager.initDriver(Browser.valueOf(ConfigManager.getBrowser()), addCapabilities());
    } else if (tcType.equalsIgnoreCase("mobile")) {
      LOGGER.info("Current Test_Type is Mobile. Will instantiate mobile driver");
      MobileDriverManager.initDriver(addCapabilities());
    }
  }

  @AfterMethod(description = "Tear Down", alwaysRun = true)
  protected void tearDown(ITestResult result, ITestContext context) {
    if (result.getStatus() == ITestResult.FAILURE) {
      if (DriverManager.getDriver() != null) {
        String base64Screenshot = ((TakesScreenshot) Objects.requireNonNull(DriverManager.getDriver()))
            .getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest()
            .fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
      } else {
        ExtentTestManager.getTest().fail(result.getThrowable());
        ExtentTestManager.getTest().fail("Test Failed");
      }
      LOGGER.info("Test method [{}] Failed", result.getMethod().getMethodName());
    } else if (result.getStatus() == ITestResult.SUCCESS) {
      LOGGER.info("Test method [{}] Passed", result.getMethod().getMethodName());
      ExtentTestManager.getTest().pass("Test Passed");
    } else if (result.getStatus() == ITestResult.SKIP) {
      LOGGER.info("Test method [{}] Skipped", result.getMethod().getMethodName());
      ExtentTestManager.getTest().pass("Test Skipped");
    } else {
      ExtentTestManager.getTest().fail("Test Failed");
      if(result.getThrowable() != null) {
        ExtentTestManager.getTest().fail(result.getThrowable());
      }
    }
    if (context.getAttribute("TC_TYPE").toString().equalsIgnoreCase("mobile")) {
      MobileDriverManager.quitDriver();
    } else {
      DriverManager.quitDriver();
    }
    ExcelManager.writeTestStatusToExcel(result);
  }

}
