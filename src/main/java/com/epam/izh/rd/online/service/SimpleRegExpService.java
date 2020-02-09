package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.repository.FileRepository;
import com.epam.izh.rd.online.repository.SimpleFileRepository;

public class SimpleRegExpService implements RegExpService {

    private static FileRepository fileRepository = new SimpleFileRepository();

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        return fileRepository.readFileFromResources("sensitive_data.txt")
            .replaceAll("(\\d{4} )(\\d{4} ){2}(\\d{4})", "$1**** **** $3");
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        return fileRepository.readFileFromResources("sensitive_data.txt")
            .replaceAll("\\$\\{payment_amount}", String.format("%.0f", paymentAmount))
            .replaceAll("\\$\\{balance}", String.format("%.0f", balance));
    }
}
