package com.bloodybaker.poc.paytelegram;

import org.telegram.telegrambots.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.api.methods.AnswerShippingQuery;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendInvoice;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.api.objects.payments.ShippingOption;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPaymentBot extends TelegramLongPollingBot {
    public List<String> arrayTest = new ArrayList();
    public List<String> arrayVip = new ArrayList();
    public List<String> arrayVipTemp = new ArrayList();
    public List<String> arrayMvp = new ArrayList();
    public List<String> arrayMvpTemp = new ArrayList();
    private final static String BOT_TOKEN = "1006785577:AAHOQ_utD3wHt1NR7miLqDlS64JGMv7J8cw";
    private final static String PROVIDER_TOKEN = "635983722:LIVE:i20043681521";
    private int variant;
    public int t = 0, v = 0, vm = 0, m = 0, mm = 0;

    public void onUpdateReceived(Update update) {

        System.out.println("Message received: " + update);
        Message message = update.getMessage();
        if (update.hasMessage()) {
            String s = update.getMessage().toString();
            String[] words = s.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
            }
            System.out.println(Arrays.toString(words));
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(" successfulPaymentSuccessfulPaymentcurrencyUAH") || words[i].equals("successfulPaymentSuccessfulPaymentcurrencyUAH")
                        || words[i] == " successfulPaymentSuccessfulPaymentcurrencyUAH" || words[i] == "successfulPaymentSuccessfulPaymentcurrencyUAH") {
                    receiver(true, message);
               /* if(words[i].equals(" totalAmount100") || words[i].equals("totalAmount100") || words[i] == " totalAmount100" || words[i] == "totalAmount100"){
                    sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                            "```" + arrayTest.get(t) + "```" + "\n" +
                            "Информация по активации: /faq"
                    );
                    t++;
                }*/
                    System.out.println("Успех");
                }
            }
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (message.getText().equals("/start")) {
                sendMess(message, " Здравствуйте, " + message.getFrom().getFirstName() + ".\n" + " Оплата производиться через ИНВОЙСЫ.");
                sendMess(message, " Перед началом работы с ботом, рекомендуем Вам ознакомиться с:\n" +
                        "1. /faq по активации привилегии\n" +
                        "2. /methods методы оплаты\n" +
                        "3. /info информация о проекте и как получить товар\n" +
                        "Для начала работы, нажмите: /help");
                executeData();
            }
            if (message.getText().equals("/buy")) {
                sendMess(message, " Здравствуйте, " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + ".\n" +
                        "Список доступных привилегий:\n" +
                        "/buyvip - VIP навсегда\n" +
                        "/buyvipmonth - VIP на месяц\n" +
                        "/buymvp - MVP навсегда\n" +
                        "/buymvpmonth - MVP на месяц\n" +
                        "\n" +
                        "/prices - цены на привилегии\n" +
                        "/methods - методы оплаты\n" +
                        "/info - информация о проекте и методы получения товара");
            }
            if (message.getText().equals("/buyvipmonth")) {
                setVariant(1);
                sendInvoice(update);
            }
            if (message.getText().equals("/buyvip")) {
                setVariant(2);
                sendInvoice(update);
            }
            if (message.getText().equals("/buymvpmonth")) {
                setVariant(3);
                sendInvoice(update);
            }
            if (message.getText().equals("/buymvp")) {
                setVariant(4);
                sendInvoice(update);
            }
            if (message.getText().equals("/test")) {
                setVariant(100);
                sendInvoice(update);
            }
            if (message.getText().equals("/help")) {
                sendMess(message,
                        "Вот список команд бота:\n" +
                                "/buy - преобрести привилегию\n" +
                                "/prices - цены на привилегии\n" +
                                "/methods - методы оплаты и получение товара\n" +
                                "/info - информация о проекте \n" +
                                "/policy - договор публичной оферты \n" +
                                "/contact - контакты продавца");
                executeData();
            }
            if (message.getText().equals("/policy")) {
                try {
                    sendDocUploadingAFile(message.getChatId(), "Публичная офферта");
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                sendMess(message, "Для показа команд введите: /help");
            }
            if (message.getText().equals("/prices")) {
                sendMess(message, " Описание и цены на привилегии:");
                sendMess(message,
                        "VIP:\n" +
                                "1. 10000$ каждый раунд\n" +
                                "2. +5% к скорости\n" +
                                "3. Тег VIP в чате\n" +
                                "4. 2 метательных ножа\n" +
                                "5. Возможность выбрать медальку\n" +
                                "Цены:\n" +
                                "VIP на месяц: 25 грн., навсегда: 50 грн.\n" +
                                "/buy преобрести привилегию\n" +
                                "/methods - методы оплаты и получение товара\n" +
                                "/info - информация о проекте");
                sendMess(message,
                        "MVP:\n" +
                                "1. 16000$ каждый раунд\n" +
                                "2. +5% к скорости\n" +
                                "3. Тег MVP в чате\n" +
                                "4. 3 метательных ножа\n" +
                                "5. Возможность выбрать медальку\n" +
                                "6. Возможность выбрать питомца\n" +
                                "7. Возможность выбрать музыкальный набор\n" +
                                "8. Возможность кикать игроков\n" +
                                "Цены:\n" +
                                "MVP на месяц: 40 грн., навсегда: 80 грн.\n" +
                                "/buy преобрести привилегию\n" +
                                "/methods - методы оплаты и получение товара\n" +
                                "/info - информация о проекте");
                sendMess(message, "Для показа команд введите: /help");
            }
            if (message.getText().equals("/methods")) {
                sendMess(message,
                        "Методы оплаты:\n" +
                                "1. Оплата с помощью карт VISA / MasterCard c поддержкой 3D Secure.\n" +
                                "2. Для клиентов Приват24, оплатить можно в своем аккаунте.\n" +
                                "3. Оплата с помощью Liqpay кошелька, не нужно вводить данные по карте, достаточно выбрать нужную карту из списка.\n" +
                                "4. Электронный кошелек от Visa, который позволяет упростить процесс оплаты: клиенту нужно лишь выбрать карту и подтвердить оплату в своем электронном кошельке. \n" +
                                "5. Электронный кошелек от MasterCard, который позволяет упростить процесс оплаты: клиенту нужно лишь выбрать карту и подтвердить оплату в своем электронном кошельке.\n" +
                                "\n" +
                                "ТОВАР ВЫ ПОЛУЧИТЕ АВТОМАТИЧЕСКИ ОТВЕТОМ НА УСПЕШНУЮ ОПЛАТУ");
                sendMess(message, "Для показа команд введите: /help");
            }
            if (message.getText().equals("/info")) {
                sendMess(message,
                        "Steel Servers\n" +
                                "Мы компания-проект занимающаяся разработкой и управлением серверов в сфере игровой индустрии.\n" +
                                "Все товары предоставленные данным ботом и цены вы можете найти тут: /prices.\n" +
                                "При приобретении привилегии вы можете использовать любой удобный метод оплаты.\n" +
                                "В случае успешной оплаты вы автоматически получаете ключ. Что делать дальше? /faq \n" +
                                "Если ключ невалиден, вы можете написать администратору @opcoder или обратиться за телефоном: +380951171755\n" +
                                "\n" +
                                "Публичная офферта: /policy\n" +
                                "Все права защищены 2019.\n" +
                                "MasterCard & VISA");
                sendMess(message, "Для показа команд введите: /help");
            }
            if (message.getText().equals("/contact")) {
                sendMess(message,
                        "Steel Servers контакты:\n" +
                                "Telegram администратора - @opcoder\n" +
                                "Номер телефона - +380951171755\n" +
                                "\n" +
                                "Публичная офферта: /policy\n" +
                                "Все права защищены 2019.\n" +
                                "MasterCard & VISA");
                sendMess(message, "Для показа команд введите: /help");
            }
            if (message.getText().equals("/faq")) {
                sendMess(message, "После успешной оплаты перейдите\n" +
                        "на сервер с таким сокетом: 91.211.118.90:27024\n" +
                        "Откройте консоль");
                sendImageFromUrl("http://steels.icu/1.png",message.getChatId().toString());
                sendMess(message, "Вернитесь обратно в телеграмм\n" +
                        "скопируйте ваш ключ (после успешной оплаты)\n" +
                        "Вставьте в консколь и нажмите 'Отправить'");
                sendImageFromUrl("http://steels.icu/2.png",message.getChatId().toString());
                sendMess(message, "Готово!\n" +
                        "Теперь введите в чате !vip и пользуйтесь.");
                sendMess(message, "Для показа команд введите: /help");
            }
            if (update.getMessage().hasSuccessfulPayment()) {

            }

        } else if (update.hasShippingQuery()) {

            selectDeliveryOptions(update);

        } else if (update.hasPreCheckoutQuery()) {

            sendPreChekout(update);
        }
    }
    public void sendImageFromUrl(String url, String chatId) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(url);
        try {
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
}
    void sendDocUploadingAFile(Long chatId,String caption) throws TelegramApiException {

        SendDocument sendDocumentRequest = new SendDocument();
        File save = new File("policy.pdf");
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setNewDocument(save);
        sendDocumentRequest.setCaption(caption);
        sendDocument(sendDocumentRequest);
    }
    public void receiver(boolean valid,Message message){
        int variant = getVariant();
        boolean set;
        set = valid;
        if(variant == 1 && set == true ){
            sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayVipTemp.get(vm) + "```" + "\n" +
                    "Информация по активации: /faq"
            );
            vm++;
            set = false;
        }
        if(variant == 2 && set == true  ){
            sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayVip.get(v) + "```" + "\n" +
                    "Информация по активации: /faq"
            );
            v++;
            set = false;
        }
        if(variant == 3 && set == true  ){
            sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayMvpTemp.get(mm) + "```" + "\n" +
                    "Информация по активации: /faq"
            );
            mm++;
            set = false;
        }
        if(variant == 4 && set == true  ){
            sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayMvp.get(m) + "```" + "\n" +
                    "Информация по активации: /faq"
            );
            m++;
            set = false;
        }
        if(variant == 100 && set == true  ){
            sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayTest.get(t) + "```" + "\n" +
                    "Информация по активации: /faq"
            );
            t++;
            set = false;
        }
    }
    public void executeData(){
       arrayMvp.add("oiuhoashdkjhkjhkjhuikhj");
       arrayMvp.add("ovbf985409iuhoashdkjhkjhkjhuikhj");
       arrayMvpTemp.add("oiuh650982hddfgjhuikhj");
       arrayMvpTemp.add("oiuhlkjnsahkjhuikhj");
       arrayVip.add("oiuho0987hkjhuikhj");
       arrayVip.add("oiuho2349098kjhkjhkjhuikhj");
       arrayVipTemp.add("oiuho76082dkjhkjhkjhuikhj");
       arrayVipTemp.add("oiuhoash35345jhkjhkjhuikhj");
       arrayTest.add("oiuhoashdkjh787jhuikhj");
       arrayTest.add("blkjsa21344545j");
    }
    public void sendMess(Message message, String  string){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(string);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void setVariant(int variant) {
        this.variant = variant;
    }

    public int getVariant() {
        return variant;
    }

    public String getBotUsername() {
        return "mypaymentbot";
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    private void sendInvoice(Update update) {
        int checker = getVariant();
        if (checker == 100){
            List<LabeledPrice> labeledPriceList = new ArrayList<LabeledPrice>();
            labeledPriceList.add(new LabeledPrice("TEST", 0));
            labeledPriceList.add(new LabeledPrice("TEST", 100));

            SendInvoice sendInvoiceObject = new SendInvoice(
                    Integer.parseInt(update.getMessage().getChatId().toString()),
                    "TEST",
                    "TEST",
                    "Happy Discount Beeva",
                    PROVIDER_TOKEN,
                    "clean-code-example",
                    "UAH",
                    labeledPriceList);
            sendInvoiceObject.setPhotoUrl("http://3.bp.blogspot" +
                    ".com/_WQMxntNMWT0/TSRbBBi43LI/AAAAAAAALCw/Ub0vyDHg54Y/s1600/CleanCode.jpg");
            sendInvoiceObject.setPhotoHeight(512);
            sendInvoiceObject.setPhotoSize(512);
            sendInvoiceObject.setPhotoWidth(512);
            sendInvoiceObject.setNeedName(true);
            sendInvoiceObject.setNeedPhoneNumber(true);
            sendInvoiceObject.setNeedEmail(true);
            sendInvoiceObject.setFlexible(true);
            sendInvoiceObject.setNeedShippingAddress(false);
            try {
                sendInvoice(sendInvoiceObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (checker == 2){
            List<LabeledPrice> labeledPriceList = new ArrayList<LabeledPrice>();
            labeledPriceList.add(new LabeledPrice("VIP", 0));
            labeledPriceList.add(new LabeledPrice("VIP", 5000));

            SendInvoice sendInvoiceObject = new SendInvoice(
                    Integer.parseInt(update.getMessage().getChatId().toString()),
                    "VIP",
                    "Привилегия VIP на сервере навсегда.",
                    "Happy Discount Beeva",
                    PROVIDER_TOKEN,
                    "clean-code-example",
                    "UAH",
                    labeledPriceList);
            sendInvoiceObject.setPhotoUrl("http://steels.icu/vip.png");
            sendInvoiceObject.setPhotoHeight(512);
            sendInvoiceObject.setPhotoSize(512);
            sendInvoiceObject.setPhotoWidth(512);
            sendInvoiceObject.setNeedName(true);
            sendInvoiceObject.setNeedPhoneNumber(true);
            sendInvoiceObject.setNeedEmail(true);
            sendInvoiceObject.setFlexible(true);
            sendInvoiceObject.setNeedShippingAddress(false);

            try {
                sendInvoice(sendInvoiceObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (checker == 1){
            List<LabeledPrice> labeledPriceList = new ArrayList<LabeledPrice>();
            labeledPriceList.add(new LabeledPrice("VIP на мес", 0));
            labeledPriceList.add(new LabeledPrice("VIP на мес", 2500));

            SendInvoice sendInvoiceObject = new SendInvoice(
                    Integer.parseInt(update.getMessage().getChatId().toString()),
                    "VIP на мес",
                    "Привилегия VIP на сервере на месяц.",
                    "Happy Discount Beeva",
                    PROVIDER_TOKEN,
                    "clean-code-example",
                    "UAH",
                    labeledPriceList);
            sendInvoiceObject.setPhotoUrl("http://steels.icu/vipmes.png");
            sendInvoiceObject.setPhotoHeight(512);
            sendInvoiceObject.setPhotoSize(512);
            sendInvoiceObject.setPhotoWidth(512);
            sendInvoiceObject.setNeedName(true);
            sendInvoiceObject.setNeedPhoneNumber(true);
            sendInvoiceObject.setNeedEmail(true);
            sendInvoiceObject.setFlexible(true);

            try {
                sendInvoice(sendInvoiceObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (checker == 4){
            List<LabeledPrice> labeledPriceList = new ArrayList<LabeledPrice>();
            labeledPriceList.add(new LabeledPrice("MVP", 0));
            labeledPriceList.add(new LabeledPrice("MVP", 8000));

            SendInvoice sendInvoiceObject = new SendInvoice(
                    Integer.parseInt(update.getMessage().getChatId().toString()),
                    "MVP",
                    "Привилегия MVP на сервере навсегда.",
                    "Happy Discount Beeva",
                    PROVIDER_TOKEN,
                    "clean-code-example",
                    "UAH",
                    labeledPriceList);
            sendInvoiceObject.setPhotoUrl("http://steels.icu/mvp.png");
            sendInvoiceObject.setPhotoHeight(512);
            sendInvoiceObject.setPhotoSize(512);
            sendInvoiceObject.setPhotoWidth(512);
            sendInvoiceObject.setNeedName(true);
            sendInvoiceObject.setNeedPhoneNumber(true);
            sendInvoiceObject.setNeedEmail(true);
            sendInvoiceObject.setFlexible(true);

            try {
                sendInvoice(sendInvoiceObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (checker == 3){
            List<LabeledPrice> labeledPriceList = new ArrayList<LabeledPrice>();
            labeledPriceList.add(new LabeledPrice("MVP на мес", 0));
            labeledPriceList.add(new LabeledPrice("MVP на мес", 4000));

            SendInvoice sendInvoiceObject = new SendInvoice(
                    Integer.parseInt(update.getMessage().getChatId().toString()),
                    "MVP на мес",
                    "Привилегия MVP на сервере на месяц.",
                    "Happy Discount Beeva",
                    PROVIDER_TOKEN,
                    "clean-code-example",
                    "UAH",
                    labeledPriceList);
            sendInvoiceObject.setPhotoUrl("http://steels.icu/mvpmes.png");
            sendInvoiceObject.setPhotoHeight(512);
            sendInvoiceObject.setPhotoSize(512);
            sendInvoiceObject.setPhotoWidth(512);
            sendInvoiceObject.setNeedName(true);
            sendInvoiceObject.setNeedPhoneNumber(true);
            sendInvoiceObject.setNeedEmail(true);
            sendInvoiceObject.setFlexible(true);

            try {
                sendInvoice(sendInvoiceObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private void selectDeliveryOptions(Update update) {
        AnswerShippingQuery answerShippingQuery = new AnswerShippingQuery(
                update.getShippingQuery().getId(),
                true
        );

        List<ShippingOption> shippingOptionList = new ArrayList<ShippingOption>();
        List<LabeledPrice> labeledPricesCorreos = new ArrayList<LabeledPrice>();
        labeledPricesCorreos.add(new LabeledPrice("Telegram Key", 0));
        List<LabeledPrice> labeledPricesSeur = new ArrayList<LabeledPrice>();

        shippingOptionList.add(new ShippingOption("correos", "Telegram Key", labeledPricesCorreos));

        answerShippingQuery.setShippingOptions(shippingOptionList);

        try {
            answerShippingQuery(answerShippingQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPreChekout(Update update) {
        AnswerPreCheckoutQuery preCheckoutQuery = new AnswerPreCheckoutQuery(
                update.getPreCheckoutQuery().getId(),
                true
        );

        try {
            answerPreCheckoutQuery(preCheckoutQuery);
            Message message = update.getMessage();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
