package com.epam.izh.rd.online.service;

public class SimpleTextService implements TextService {

    /**
     * Реализовать функционал удаления строки из другой строки.
     *
     * Например для базовой строки "Hello, hello, hello, how low?" и строки для удаления ", he"
     * метод вернет "Hellollollo, how low?"
     *
     * @param base - базовая строка с текстом
     * @param remove - строка которую необходимо удалить
     */
    @Override
    public String removeString(String base, String remove) throws IllegalArgumentException {
        if (base == null || remove == null) {
            throw new IllegalArgumentException("All arguments should be not null.");
        }

        // this may fail for regex-like `remove` argument
        // return base.replaceAll(remove, "");

        String result = base;

        while (result.contains(remove)) {
            result = result.replace(remove, "");
        }

        return result;
    }

    /**
     * Реализовать функционал проверки на то, что строка заканчивается знаком вопроса.
     *
     * Например для строки "Hello, hello, hello, how low?" метод вернет true
     * Например для строки "Hello, hello, hello!" метод вернет false
     */
    @Override
    public boolean isQuestionString(String text) throws IllegalArgumentException {
        if (text == null) {
            throw new IllegalArgumentException("Argument should be not null.");
        }

        return text.endsWith("?");
    }

    /**
     * Реализовать функционал соединения переданных строк.
     *
     * Например для параметров {"Smells", " ", "Like", " ", "Teen", " ", "Spirit"}
     * метод вернет "Smells Like Teen Spirit"
     */
    @Override
    public String concatenate(String... elements) {
        return String.join("", elements);
    }

    /**
     * Реализовать функционал изменения регистра в вид лесенки.
     * Возвращаемый текст должен начинаться с прописного регистра.
     *
     * Например для строки "Load Up On Guns And Bring Your Friends"
     * метод вернет "lOaD Up oN GuNs aNd bRiNg yOuR FrIeNdS".
     */
    @Override
    public String toJumpCase(String text) throws IllegalArgumentException {
        if (text == null) {
            throw new IllegalArgumentException("Argument should be not null.");
        }

        StringBuilder result = new StringBuilder(text.length());
        boolean lowerCase = true;

        for (char c : text.toCharArray()) {
            result.append(lowerCase ? Character.toLowerCase(c) : Character.toUpperCase(c));
            lowerCase = !lowerCase;
        }

        return result.toString();
    }

    /**
     * Метод определяет, является ли строка палиндромом.
     *
     * Палиндром - строка, которая одинаково читается слева направо и справа налево.
     *
     * Например для строки "а роза упала на лапу Азора" вернется true, а для "я не палиндром" false
     */
    @Override
    public boolean isPalindrome(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Argument should be not null.");
        }

        if (string.length() == 0) {
            return false;
        }

        String stringWithoutSpaces = string.replaceAll("\\s", "");

        return stringWithoutSpaces.equalsIgnoreCase(new StringBuilder(stringWithoutSpaces).reverse().toString());
    }
}
