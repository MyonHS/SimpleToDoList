import java.util.Vector;

public class Topic {

    Vector<String> tasksOfTopic;
    String titleOfTopic;
    Topic(String title, Vector<String> tasks)
    {
        tasksOfTopic = tasks;
        titleOfTopic = title;
    }
}
