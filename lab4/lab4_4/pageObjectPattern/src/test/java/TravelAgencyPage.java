import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TravelAgencyPage {
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPort;

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;

    @FindBy(css = "tr:nth-child(4) .btn")
    private WebElement chooseFlightButton;

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "address")
    private WebElement inputAddress;

    @FindBy(id = "city")
    private WebElement inputCity;

    @FindBy(id = "state")
    private WebElement inputState;

    @FindBy(id = "zipCode")
    private WebElement inputZipCode;

    @FindBy(id = "cardType")
    private WebElement inputCardType;

    @FindBy(id = "creditCardNumber")
    private WebElement inputCreditCardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement inputCreditCardMonth;

    @FindBy(id = "creditCardYear")
    private WebElement inputCreditCardYear;

    @FindBy(id = "nameOnCard")
    private WebElement inputNameOnCard;

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    // Constructor
    public TravelAgencyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Methods to interact with elements
    public void selectFromPort(String city) {
        fromPort.findElement(By.xpath("//option[. = '" + city + "']")).click();
    }

    public void selectToPort(String city) {
        toPort.findElement(By.xpath("//option[. = '" + city + "']")).click();
    }

    public void findFlights() {
        findFlightsButton.click();
    }

    public void chooseFlight() {
        chooseFlightButton.click();
    }

    public void fillPassengerDetails(String name, String address, String city, String state, String zipCode) {
        inputName.sendKeys(name);
        inputAddress.sendKeys(address);
        inputCity.sendKeys(city);
        inputState.sendKeys(state);
        inputZipCode.sendKeys(zipCode);
    }

    public void fillPaymentDetails(String cardType, String cardNumber, String expMonth, String expYear, String nameOnCard) {
        inputCardType.findElement(By.xpath("//option[. = '" + cardType + "']")).click();
        inputCreditCardNumber.sendKeys(cardNumber);
        inputCreditCardMonth.sendKeys(expMonth);
        inputCreditCardYear.sendKeys(expYear);
        inputNameOnCard.sendKeys(nameOnCard);
    }

    public void agreeToTerms() {
        rememberMeCheckbox.click();
    }

    public void purchaseFlight() {
        purchaseButton.click();
    }

    public boolean isConfirmationPageDisplayed() {
        return driver.getTitle().equals("BlazeDemo Confirmation");
    }
}
