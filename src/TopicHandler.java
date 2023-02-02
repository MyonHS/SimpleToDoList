import java.io.*;
import java.util.Vector;

public class TopicHandler {

    private static Vector<Topic> topicVector = new Vector<>();

    public static void loadTopics()  //load Topics from File when starting
    {
        topicVector.removeAllElements();
        try {
            FileReader reader = new FileReader("Topics.txt");
            BufferedReader bufferReader = new BufferedReader(reader);

            String currentLine = bufferReader.readLine();

            while(currentLine!= null)
            {
                if(currentLine.charAt(0)=='₿') //new Topic
                {
                    String newTopicName=currentLine.substring(1);
                    Vector<String> newTaskVector = new Vector<>();

                    currentLine= bufferReader.readLine(); //next Task

                    while(currentLine!= null && currentLine.charAt(0)!='₿')
                    {
                        newTaskVector.add(currentLine);
                        currentLine= bufferReader.readLine();
                    }

                    Topic newTopic = new Topic(newTopicName, newTaskVector);
                    topicVector.add(newTopic);
                }
            }
            bufferReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTopics() //overwrite the file when Topic is added/deleted
    {

        try {
            PrintStream writer = new PrintStream("Topics.txt");

            for(Topic currentTopic : topicVector)
            {
                writer.println("₿"+currentTopic.titleOfTopic); //write topic. '₿' for parsing later

                for(String currentTask : currentTopic.tasksOfTopic)
                {
                    writer.println(currentTask);
                }
            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vector<String> getTitles()
    {
        Vector<String> result = new Vector<>();

        for(Topic currentTopic : topicVector)
        {
            result.add(currentTopic.titleOfTopic);
        }
        return result;
    }

    private static boolean TopicIsInList(String topicName)
    {
        for(Topic currentTopic : topicVector)
        {
            if(currentTopic.titleOfTopic.equals(topicName))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean addNewEmptyTopic(String topicName)
    {
        if(TopicIsInList(topicName)) return false;

        Vector<String> taskStub = new Vector<>();
        Topic newTopic = new Topic(topicName, taskStub);
        topicVector.add(newTopic);
        saveTopics();
        loadTopics();
        return true;

    }

    public static Vector<String> returnTasksByTopicName(String topicName)
    {
        Vector<String> result = new Vector<>();

        for(Topic currentTopic : topicVector)
        {
            if(currentTopic.titleOfTopic.equals(topicName))
            {
                for(String currentTask : currentTopic.tasksOfTopic)
                {
                    result.add(currentTask);
                }
            }
        }

        return result;
    }

    public static String returnTitleByIndex(int index)
    {
        return topicVector.elementAt(index).titleOfTopic;
    }

    public static boolean addTaskToTopic(String topicName, String task)
    {
        for(Topic currentTopic : topicVector)
        {
            if(currentTopic.titleOfTopic.equals(topicName))
            {
                //check if task already exists
                if(currentTopic.tasksOfTopic.contains(task)) return false;

                currentTopic.tasksOfTopic.add(task);
                saveTopics();
                loadTopics();
                return true;
            }
        }
        return false; //Topic doesn't exist (shouldn't happen)
    }

    public static void deleteTopicByIndex(int index)
    {
        topicVector.remove(index);
        saveTopics();
        loadTopics();
    }

    public static void deleteTasksByIndex(String topicName, int index)
    {
        for(int indexOfCurrentTopic=0; indexOfCurrentTopic<topicVector.size(); indexOfCurrentTopic++)
        {
            if(topicVector.elementAt(indexOfCurrentTopic).titleOfTopic.equals(topicName))
            {
                topicVector.elementAt(indexOfCurrentTopic).tasksOfTopic.remove(index);
                saveTopics();
                loadTopics();
            }
        }

    }

    public static boolean isTopicStillAvailable(String topicName)
    {
        for(Topic currentTopic : topicVector)
        {
            if(currentTopic.titleOfTopic.equals(topicName)) return true;
        }

        return false;
    }
}
