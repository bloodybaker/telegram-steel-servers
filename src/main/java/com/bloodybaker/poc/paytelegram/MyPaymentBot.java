package com.bloodybaker.poc.paytelegram;

import org.telegram.telegrambots.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.api.methods.AnswerShippingQuery;
import org.telegram.telegrambots.api.methods.send.SendInvoice;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.api.objects.payments.ShippingOption;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyPaymentBot extends TelegramLongPollingBot {
    private List<String> arrayList = new ArrayList();
    private final static String BOT_TOKEN = "1006785577:AAHOQ_utD3wHt1NR7miLqDlS64JGMv7J8cw";
    private final static String PROVIDER_TOKEN = "635983722:LIVE:i20043681521";
    private int variant;
    public void onUpdateReceived(Update update) {

        System.out.println("Message received: " + update);
        Message message = update.getMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            if(message.getText().equals("/start")){
                sendMess(message," Здравствуйте, "  + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + ".\n" +
                        "Вот список команд бота:\n" +
                        "/buy - преобрести привилегию\n" +
                        "/prices - цены на привилегии\n" +
                        "/methods - методы оплаты\n" +
                        "/info - информация о проекте и методы получения товара\n" +
                        "/contact - контакты продавца");
            }
            if(message.getText().equals("/buy")){
                sendMess(message," Здравствуйте, "  + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + ".\n" +
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
            if(message.getText().equals("/buyvipmonth")){
                setVariant(1);
                sendInvoice(update);
            }
            if(message.getText().equals("/buyvip")){
                setVariant(2);
                sendInvoice(update);
            }
            if(message.getText().equals("/buymvpmonth")){
                setVariant(3);
                sendInvoice(update);
            }
            if(message.getText().equals("/buymvp")){
                setVariant(4);
                sendInvoice(update);
            }
            if(message.getText().equals("/test")){
                setVariant(100);
                sendInvoice(update);
            }
            if (update.getMessage().hasSuccessfulPayment()) {
                sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                    "```" + arrayList.get(0) + "```" + "\n" +
                        "Информация по активации: /faq"
                );
            }

        } else if (update.hasShippingQuery()) {

            selectDeliveryOptions(update);

        } else if (update.hasPreCheckoutQuery()) {

            sendPreChekout(update);
        }
    }
    public void executeData(){
        arrayList.add("6Qfwh6Rz6dwHnla80qN8Cx7dke5JK09m");
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

    public List<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
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
            message.getSuccessfulPayment();
            if (message.getSuccessfulPayment().equals(true)){
                sendMess(message," Благодарим за покупку. Вот ваш ключ:\n"+
                        "```" + "КУКУМБЕР" + "```" + "\n" +
                        "Информация по активации: /faq"
                );
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
