package com.abc.tool_rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@ActiveProfiles("test")
@Transactional
public class CheckoutControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutControllerIntegrationTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of("TestCaseNumber_2", "LADW", 3, "10%", "7/2/20", "07/02/20", "Ladder", "Werner", 3, "07/04/20", "$1.99", 2, "$3.98", "$0.40", "$3.58"),
                Arguments.of("TestCaseNumber_3", "CHNS", 5, "25%", "7/2/15", "07/02/15", "Chainsaw", "Stihl", 5, "07/06/15", "$1.49", 3, "$4.47", "$1.12", "$3.35"),
                Arguments.of("TestCaseNumber_4", "JAKD", 6, "0%", "9/3/15", "09/03/15", "Jackhammer", "DeWalt", 6, "09/08/15", "$2.99", 3, "$8.97", "$0.00", "$8.97"),
                Arguments.of("TestCaseNumber_5", "JAKR", 9, "0%", "7/2/15", "07/02/15", "Jackhammer", "Ridgid", 9, "07/10/15", "$2.99", 6, "$17.94", "$0.00", "$17.94"),
                Arguments.of("TestCaseNumber_6", "JAKR", 4, "50%", "7/2/20", "07/02/20", "Jackhammer", "Ridgid", 4, "07/05/20", "$2.99", 1, "$2.99", "$1.50", "$1.50")
        );
    }

    private static Stream<Arguments> provideErrorTestData() {
        return Stream.of(
                Arguments.of("TestCaseNumber_1", "JAKR", 5, "101%", "9/3/15", "DISCOUNT_PERCENT_ERROR", "Discount percent must be between 0 and 100.")
        );
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print()) // Print request and response
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testCheckoutEndpoint(String testCaseNumber, String toolCode, int rentalDayCount, String discountPercent, String checkoutDate, String checkoutDateRes,
                                     String expectedToolType, String expectedToolBrand, int expectedRentalDays,
                                     String expectedDueDate, String expectedDailyRentalCharge, int expectedChargeDays,
                                     String expectedPreDiscountCharge, String expectedDiscountAmount, String expectedFinalCharge) throws Exception {

        logger.info("Running test case number: {}", testCaseNumber);

        String checkoutRequestJson = String.format("{\n" +
                "  \"toolCode\": \"%s\",\n" +
                "  \"rentalDayCount\": %d,\n" +
                "  \"discountPercent\": \"%s\",\n" +
                "  \"checkoutDate\": \"%s\"\n" +
                "}", toolCode, rentalDayCount, discountPercent, checkoutDate);

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutRequestJson)
                        .characterEncoding("utf-8")) // Ensure UTF-8 encoding
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.toolCode").value(toolCode))
                .andExpect(jsonPath("$.toolType").value(expectedToolType))
                .andExpect(jsonPath("$.toolBrand").value(expectedToolBrand))
                .andExpect(jsonPath("$.rentalDays").value(expectedRentalDays))
                .andExpect(jsonPath("$.checkoutDate").value(checkoutDateRes))
                .andExpect(jsonPath("$.dueDate").value(expectedDueDate))
                .andExpect(jsonPath("$.dailyRentalCharge").value(expectedDailyRentalCharge))
                .andExpect(jsonPath("$.chargeDays").value(expectedChargeDays))
                .andExpect(jsonPath("$.preDiscountCharge").value(expectedPreDiscountCharge))
                .andExpect(jsonPath("$.discountAmount").value(expectedDiscountAmount))
                .andExpect(jsonPath("$.finalCharge").value(expectedFinalCharge));
    }

    @ParameterizedTest
    @MethodSource("provideErrorTestData")
    public void testCheckoutEndpointError(String testCaseNumber, String toolCode, int rentalDayCount, String discountPercent, String checkoutDate,
                                          String expectedErrorCode, String expectedErrorMessage) throws Exception {

        logger.info("Running test case number: {}", testCaseNumber);

        String checkoutRequestJson = String.format("{\n" +
                "  \"toolCode\": \"%s\",\n" +
                "  \"rentalDayCount\": %d,\n" +
                "  \"discountPercent\": \"%s\",\n" +
                "  \"checkoutDate\": \"%s\"\n" +
                "}", toolCode, rentalDayCount, discountPercent, checkoutDate);

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutRequestJson)
                        .characterEncoding("utf-8")) // Ensure UTF-8 encoding
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(expectedErrorCode))
                .andExpect(jsonPath("$.errorMessage").value(expectedErrorMessage));
    }
}
