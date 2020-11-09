package com.company;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Quest {
    public List<Task> allTask;
    public String name;

    public String questDescription;

    public Quest(List<Task> allTask,String questDescription,String name){
        this.allTask = allTask;
        this.questDescription = questDescription;
        this.name = name;
    }

    public static Quest questDeserializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Quest.class,new ProgramDeserializer());
        objectMapper.registerModule(module);
        try {
            return objectMapper.readValue(new File("some_files.json"), Quest.class);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void questSerializer(Quest quest){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("some_files.json"),quest );
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }
}
class ProgramDeserializer extends JsonDeserializer<Quest> {

    @Override
    public Quest deserialize(JsonParser jasonParser, DeserializationContext context) throws IOException {
        var codec = jasonParser.getCodec();
        var node = codec.readTree(jasonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Task> tasks = objectMapper.readValue( String.valueOf(node.get("allTask")), new TypeReference<>() {});
        final String name = objectMapper.readValue( String.valueOf(node.get("name")), new TypeReference<>() {});
        final String description = objectMapper.readValue( String.valueOf(node.get("questDescription")), new TypeReference<>() {});
        return new Quest(tasks,description,name);
    }
}