package com.company.google;

import com.company.Coordinates;
import com.company.Quest;
import com.company.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.util.*;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.stream.Collectors;


public class BotSheets {
    private static final String ApplicationName="Geo Bot";
    private static final JsonFactory AuthFactory = JacksonFactory.getDefaultInstance();
    private static final String TokenDirectoryPath = "";
    public static String SpreadSheetId;
    private static Sheets service;
    private static final List<String> Scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String credentialsFilePath = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException{
        var in =BotSheets.class.getResourceAsStream(credentialsFilePath);
        if(in == null){
            throw new FileNotFoundException("Resource not found:"+credentialsFilePath);
        }
        var clientSecrets = GoogleClientSecrets.load(AuthFactory,new InputStreamReader(in));
        var flow  = new GoogleAuthorizationCodeFlow
                .Builder(httpTransport,AuthFactory,clientSecrets,Scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TokenDirectoryPath)))
                .setAccessType("offline").build();
        var receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow,receiver).authorize("user");
    }
    // вот тут создают новые таблицы
    public static StringBuilder createNewSheets(String title, Sheets service) throws IOException {
        var newSpreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
        newSpreadsheet = service.spreadsheets().create(newSpreadsheet).setFields("spreadsheetId").execute();
        SpreadSheetId = newSpreadsheet.getSpreadsheetId();
        return new StringBuilder().append("https://docs.google.com/spreadsheets/d/").append(SpreadSheetId);
    }

    public static Quest getQuest() throws IOException {
        var response = service.spreadsheets().values()
                .get(SpreadSheetId, "Лист1!A2:J")
                .execute();
        var values = response.getValues();
        var createdQuest = new Quest();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            createQuest(values, createdQuest);
            createTask(values, createdQuest);
        }
        return createdQuest;
    }

    private static void createQuest(List<List<Object>> values, Quest createdQuest) {
        for (var row : values) {

            createdQuest.name = (String) row.get(0);
            createdQuest.questDescription = (String) row.get(1);
            System.out.println(createdQuest.name);
        }
    }

    private static void createTask(List<List<Object>> values, Quest createdQuest) {
        for (var row : values) {
            var taskDescription= (String) row.get(4);
            var taskId = Integer.parseInt((String) row.get(2));
            var taskName= (String) row.get(3);
            var taskX = Double.parseDouble((String) row.get(5));
            var taskY = Double.parseDouble((String) row.get(6));
            var taskAnswer = new ArrayList<>(Arrays.asList(((String) row.get(7)).split(",")));
            var tasksForAccess = (ArrayList<Integer>) Arrays
                    .stream(((String) row.get(8)).split(","))
                    .filter(i-> !i.equals(""))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            var distanceForOffset = Integer.parseInt((String) row.get(9));
            createdQuest.allTasks.add(new Task(taskDescription,new Coordinates(taskX,taskY),distanceForOffset,taskAnswer,taskId,tasksForAccess,taskName));
        }
    }

    //вот эта штука делается первой
    public static void initSheetService() throws IOException, GeneralSecurityException{
        var HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, AuthFactory, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(ApplicationName)
                .build();

    }

}

