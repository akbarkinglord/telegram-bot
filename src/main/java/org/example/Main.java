package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.model.DictModel;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.lang.reflect.Type;
import java.util.List;

// github ornatildi

public class Main extends TelegramLongPollingBot {

    public static void main(String[] args) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            Main main = new Main();
            api.registerBot(main);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@englishwordspronunciation_bot";
    }

    @Override
    public String getBotToken() {
        return "7650062416:AAGMgOnWJP6tHReI5Q3kTO6hcIPY_TbEylk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if ("/start".equals(message)) {
            try {
                SendMessage msg = new SendMessage();
                msg.setChatId(chatId);
                msg.setText("Welcome to our Dictionary bot");
                execute(msg);
            } catch (Exception e) {
                System.out.println("Xatolik yuzberdi!!!!");
            }
        } else {
            try {
                String[] words = message.split(" ");
                String str = "";
                for (int i = 0; i < words.length; i++) {
                    if (i < words.length - 1) {
                        str = str + words[i] + "%20";
                    } else {
                        str = str + words[i];
                    }
                }
                System.out.println(str);
                String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + str;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet request = new HttpGet(url);
                CloseableHttpResponse response = httpClient.execute(request);

                String json = EntityUtils.toString(response.getEntity());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<DictModel>>() {
                }.getType();
                List<DictModel> model = gson.fromJson(json, listType);
                DictModel dict = model.get(0);
                String audio = dict.getPhonetics().get(0).getAudio();
                String word = dict.getWord();
                String phonetic = dict.getPhonetic();
                String definition = dict.getMeanings()
                        .get(0)
                        .getDefinitions()
                        .get(0)
                        .getDefinition();

                SendMessage msg = new SendMessage();
                msg.setChatId(chatId);
                msg.setParseMode("HTML");
                msg.setText(
                        "Word: " + word + "\n" +
                                "Phonetics: " + phonetic + "\n" +
                                "Definition: " + "<b>" + definition + "</b>" + "\n" +
                                "Audio: " + "<a href=\"" + audio + "\">" + audio + "</a>"
                );
                execute(msg);
            } catch (Exception e) {
                SendMessage msg = new SendMessage();
                msg.setChatId(chatId);
                msg.setText("So'z Topilmadi!!!");
                try {
                    execute(msg);
                } catch (TelegramApiException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}