import com.company.Coordinates;
import com.company.Distance;
import com.company.Quest;
import com.company.Task;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class TestQuest {
    @Test
    public void tryTest(){
        var some = "good";
        assertEquals("good",some);
    }
    @Test
    public void testGetQuestFromJSON(){
        var testQuest = Quest.questDeserializer("TestQuest.json");

        assertEquals(Objects.requireNonNull(testQuest).name,"test");
        var v = new ArrayList<Integer>(Arrays.asList(1,2,3,4));

        System.out.println(v.indexOf(5));
    }

    @Test
    public void testToJSON(){
        var testQuest = Quest.questDeserializer("TestQuest.json");

            for (Task task:testQuest.allTask){
                if (task.tasksForAccess.isEmpty() && !task.complete) {
                    System.out.println(task.serialNumber);
                }
            }

    }

}
