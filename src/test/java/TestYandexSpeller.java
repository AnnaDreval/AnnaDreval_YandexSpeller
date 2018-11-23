import core.YandexSpellerAPI;
import beans.YandexSpellerAnswer;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerAPI.responseWithMistakes;
import static core.YandexSpellerAPI.successResponse;
import static core.YandexSpellerConstants.ErrorCodes.ERROR_CAPITALIZATION;
import static core.YandexSpellerConstants.ErrorCodes.ERROR_REPEAT_WORD;
import static core.YandexSpellerConstants.ErrorCodes.ERROR_UNKNOWN_WORD;
import static core.YandexSpellerConstants.Examples.*;
import static core.YandexSpellerConstants.InvalidParameter.LANG;
import static core.YandexSpellerConstants.Language.EN;
import static core.YandexSpellerConstants.Language.WRONG_LANGUAGE;
import static core.YandexSpellerConstants.Options.IGNORE_CAPITALIZATION;
import static core.YandexSpellerConstants.Options.IGNORE_DIGITS;
import static core.YandexSpellerConstants.StatusLine.BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("unchecked")
public class TestYandexSpeller {

    @Test
    public void wrongTexts() {
        YandexSpellerAPI.with()
                .language(EN)
                .text(BANANA.word, APPLE.word, CUCUMBER.word)
                .callApi()
                .then()
                .specification(successResponse(Matchers.stringContainsInOrder(
                                Arrays.asList(BANANA.word, BANANA.s, APPLE.word, APPLE.s, CUCUMBER.word, CUCUMBER.s)
                )));
    }

    @Test
    public void correctTexts() {
        List<List<YandexSpellerAnswer>> answer = YandexSpellerAPI.getYandexSpellerAnswers(
                YandexSpellerAPI.with()
                        .language(EN)
                        .text(BANANA_CORRECT.word, APPLE_CORRECT.word, CUCUMBER_CORRECT.word)
                        .callApi());
        assertThat(answer.get(0).size(), equalTo(0));
    }

    @Test
    public void ignoreDigits() {
        List<List<YandexSpellerAnswer>> answer = YandexSpellerAPI.getYandexSpellerAnswers(
                YandexSpellerAPI.with()
                        .language(EN)
                        .text(BANANA_DIGIT.word, APPLE_DIGIT.word, CUCUMBER_DIGIT.word)
                        .options(IGNORE_DIGITS.toString())
                        .callApi());
        assertThat(answer.get(0).size(), equalTo(0));
    }

    @Test
    public void ignoreCapitalization() {
        List<List<YandexSpellerAnswer>> answer = YandexSpellerAPI.getYandexSpellerAnswers(
                YandexSpellerAPI.with()
                        .language(EN)
                        .text(BANANA_CAPITAL.word, APPLE_CAPITAL.word, CUCUMBER_CAPITAL.word)
                        .options(IGNORE_CAPITALIZATION.toString())
                        .callApi());
        assertThat(answer.get(0).size(), equalTo(0));
    }

    @Test
    public void wrongLanguage() {
        YandexSpellerAPI.with()
                .language(WRONG_LANGUAGE)
                .text(BANANA_CORRECT.word)
                .callApi()
                .then()
                .specification(responseWithMistakes(HttpStatus.SC_BAD_REQUEST, BAD_REQUEST.toString(), LANG.toString()));
    }

    //============================== ERROR CODES TESTS ================================================================

    @Test
    public void unknownWordsError() {
        YandexSpellerAPI.with()
                .language(EN)
                .text(BANANA.word, APPLE.word, CUCUMBER.word)
                .callApi()
                .then()
                .specification(successResponse(Matchers.containsString(ERROR_UNKNOWN_WORD.toString())));
    }

    @Test
    public void wordsWithCapitalizationError() {
        YandexSpellerAPI.with()
                .language(EN)
                .text(BANANA_CAPITAL.word, APPLE_CAPITAL.word, CUCUMBER_CAPITAL.word)
                .callApi()
                .then()
                .specification(successResponse(Matchers.containsString(ERROR_CAPITALIZATION.toString())));
    }

    @Test
    public void repeatWordsError() {
        YandexSpellerAPI.with()
                .language(EN)
                .text(BANANA_REPEAT.word, BANANA_REPEAT.word)
                .callApi()
                .then()
                .specification(successResponse(Matchers.containsString(ERROR_REPEAT_WORD.toString())));
    }

}
