package core;

public class YandexSpellerConstants {

    static final String YANDEX_SPELLER_API_URI = "https://speller.yandex.net/services/spellservice.json/checkTexts";
    static final String PARAM_TEXT = "text";
    static final String PARAM_OPTIONS = "options";
    static final String PARAM_LANG = "lang";

    public enum Language {
        EN("en"),
        WRONG_LANGUAGE("language");

        private String languageCode;
        String langCode() {
            return languageCode;
        }
        Language(String lang) {
            this.languageCode = lang;
        }
    }

    public enum ErrorCodes {
        ERROR_UNKNOWN_WORD("\"code\":1"),
        ERROR_REPEAT_WORD("\"code\":2"),
        ERROR_CAPITALIZATION("\"code\":3");

        private String value;
        ErrorCodes(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum StatusLine {
        BAD_REQUEST("HTTP/1.1 400 Bad request");

        private String value;
        StatusLine(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum InvalidParameter {
        LANG("Invalid parameter 'lang'");

        private String value;
        InvalidParameter(String s) {
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Options {
        IGNORE_DIGITS("2"),
        IGNORE_URLS("4"),
        FIND_REPEAT_WORDS("8"),
        IGNORE_CAPITALIZATION("512");

        String value;
        Options(String i) {
            value = i;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Examples {
        BANANA_REPEAT("banana banana", ""),

        BANANA("baanana", "banana"),
        APPLE("appple", "apple"),
        CUCUMBER("cucumbr", "cucumber"),

        // ========== Correct ===============
        BANANA_CORRECT("banana", "banana"),
        APPLE_CORRECT("apple", "apple"),
        CUCUMBER_CORRECT("cucumber", "cucumber"),

        // =========== Digits ===============
        BANANA_DIGIT("banana1", "banana"),
        APPLE_DIGIT("1apple", "apple"),
        CUCUMBER_DIGIT("cucu1mber", "cucumber"),

        // ======= Capitalization ===========
        BANANA_CAPITAL("bAnana", "banana"),
        APPLE_CAPITAL("aPPle", "apple"),
        CUCUMBER_CAPITAL("cuCUmber", "cucumber");


        public String word;
        public String s;

        Examples(String word, String s) {
            this.word = word;
            this.s = s;
        }
    }
}
