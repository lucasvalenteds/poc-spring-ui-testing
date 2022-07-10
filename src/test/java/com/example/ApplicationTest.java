package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ApplicationTest {

    @Container
    private static final BrowserWebDriverContainer<?> FIREFOX_CONTAINER =
            new BrowserWebDriverContainer<>()
                    .withAccessToHost(true)
                    .withCapabilities(new FirefoxOptions());

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    private static void setApplicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @LocalServerPort
    private int localServerPort;

    private RemoteWebDriver driver;

    @BeforeEach
    public void beforeEach() {
        // Allowing the container to access the server hosted outside its network
        org.testcontainers.Testcontainers.exposeHostPorts(localServerPort);

        // Creating a Firefox WebDriver instance and navigating to the page under test
        this.driver = FIREFOX_CONTAINER.getWebDriver();
        this.driver.get("http://host.testcontainers.internal:" + localServerPort);
    }

    @DisplayName("First render display title and search form only")
    @Test
    void firstRender() {
        final var title = driver.findElement(By.cssSelector("header > h1"));
        final var searchInput = driver.findElement(By.id("search-form-text-input"));
        final var submitButton = driver.findElement(By.id("search-form-submit-button"));
        final var searchResult = driver.findElements(By.id("search-result"));

        assertAll(
                "Title should be visible and be the application name",
                () -> assertTrue(title.isDisplayed()),
                () -> assertEquals("Search.it", title.getText())
        );

        assertAll(
                "Search input should be visible, contains a placeholder and be empty",
                () -> assertTrue(searchInput.isDisplayed()),
                () -> assertFalse(searchInput.getDomAttribute("placeholder").isEmpty()),
                () -> assertEquals("", searchInput.getText())
        );

        assertAll(
                "Submit button should be visible and enabled",
                () -> assertTrue(submitButton.isDisplayed()),
                () -> assertTrue(submitButton.isEnabled())
        );

        assertTrue(searchResult.isEmpty(), "Empty state and results list should not be rendered");
    }

    @DisplayName("Search without results display empty state")
    @Test
    void searchingReturnedNoneResults() {
        // Searching for links containing "Hello World!" in title or description
        final var searchInput = driver.findElement(By.id("search-form-text-input"));
        final var submitButton = driver.findElement(By.id("search-form-submit-button"));
        searchInput.sendKeys("Hello World!");
        submitButton.click();

        // Asserting none link found and the page is rendered as expected
        final var searchResult = driver.findElement(By.cssSelector("#search-result-empty-state > p"));
        assertAll(
                "Empty state should be visible and contains text explaining no results were found",
                () -> assertTrue(searchResult.isDisplayed()),
                () -> assertEquals("None links found", searchResult.getText())
        );
    }

    @DisplayName("Search with at least one result display links found")
    @Test
    void searchingReturnedResults() {
        // Searching for links containing "a" in title or description
        final var searchInput = driver.findElement(By.id("search-form-text-input"));
        final var submitButton = driver.findElement(By.id("search-form-submit-button"));
        searchInput.sendKeys("a");
        submitButton.click();

        // Asserting we found at least one link
        final var searchResultLinks = driver.findElements(By.cssSelector("#search-result-links-list > li > a"));
        assertThat(searchResultLinks.size()).isGreaterThanOrEqualTo(1);
        for (final var result : searchResultLinks) {
            assertTrue(result.isDisplayed(), "Search result link should be visible");
            assertFalse(result.getText().isEmpty(), "Search result link should have a text");
            assertFalse(result.getAttribute("href").isEmpty(), "Search result link should be clickable");
        }

        // Asserting every link found presents its description
        final var searchResultDescriptions = driver.findElements(By.cssSelector("#search-result-links-list > li > span"));
        assertEquals(searchResultLinks.size(), searchResultDescriptions.size(), "Every search result link should have a description");
        for (final var result : searchResultDescriptions) {
            assertTrue(result.isDisplayed(), "Search result link description should be visible");
            assertFalse(result.getText().isEmpty(), "Search result link description should have a text");
        }
    }
}
