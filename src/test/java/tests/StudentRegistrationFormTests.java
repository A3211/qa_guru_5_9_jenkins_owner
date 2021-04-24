package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormTests extends TestBase {
    Faker faker = new Faker();

    @Test
    void studentRegistrationTest() {
        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                email = faker.internet().emailAddress(),
                gender = "Male",
                number = "1234567890",
                subject = "Maths",
                hobbie1 = "Sports",
                hobbie2 = "Reading",
                hobbie3 = "Music",
                image = "image.png",
                address = faker.address().fullAddress(),
                state = "Haryana",
                city = "Karnal";

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(email);
                $(byText(gender)).click();
                $("#userNumber").setValue(number);
            });

            step("Set date", () -> {
                //Выбираем Date of Birth
                $("#dateOfBirthInput").click();
                $(".react-datepicker__month-select").selectOptionContainingText("January");
                $(".react-datepicker__year-select").selectOptionContainingText("1994");
                $(byText("10")).click();
            });

            step("Set subjects", () -> {
                //Выбираем Subjects
                $("#subjectsContainer").click();
                $("#subjectsInput").setValue(subject);
                $("#react-select-2-option-0").click();
            });

            step("Set hobbies", () -> {
                //Выбираем Hobbies
                $(byText(hobbie1)).click();
                $(byText(hobbie2)).click();
                $(byText(hobbie3)).click();
            });

            step("Load file", () -> {
                //Загружаем файл, пишем адрес
                $("#uploadPicture").uploadFromClasspath(image);
                $("#currentAddress").setValue(address);
            });

            step("Set adress", () -> {
                //Выбираем штат и город, жмем сабмит
                $(byText("Select State")).click();
                $(byText(state)).click();
                $(byText("Select City")).click();
                $(byText(city)).click();
            });

            step("Submit form", () -> {
                $("#submit1").click();
            });
        });

        step("Check form data", () -> {
            //Делаем проверки
            $(".modal-content").shouldHave(text(firstName + " " + lastName),
                    text(email),
                    text(gender),
                    text(number),
                    text("10 January,1994"),
                    text(hobbie1 + ", " + hobbie2 + ", " + hobbie3),
                    text(subject),
                    text(image),
                    text(address),
                    text(state + " " + city));
        });
    }
}
