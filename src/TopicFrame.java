import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class TopicFrame extends JFrame implements ActionListener {

    ImageIcon iconAdd = new ImageIcon("plus.png");
    String topicName;
    Vector<String> taskVector;
    TopicFrame(String name)
    {
        topicName=name;

        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,500);
        this.setResizable(false);
        this.setLayout(null); //set Layout to null for manual position management
        this.setBackground(Color.WHITE);

        this.setVisible(true);

        refreshTasks();

    }

    private void refreshTasks()
    {
        this.getContentPane().removeAll(); //clear all old elements
        this.repaint();

        int currentTaskIndex;
        taskVector = TopicHandler.returnTasksByTopicName(topicName);

        for(currentTaskIndex=0; currentTaskIndex<taskVector.size(); currentTaskIndex++)
        {
            //One Checkbox per Task to mark it as done
            JCheckBox deleteCheckBox = new JCheckBox();
            deleteCheckBox.setBounds(5, 15+(currentTaskIndex*30), 20,20);
            deleteCheckBox.setActionCommand("C"+Integer.toString(currentTaskIndex));
            deleteCheckBox.addActionListener(this);
            this.add(deleteCheckBox);

            //One Label per Task. No Button needed so we use label
            String currentTask = taskVector.elementAt(currentTaskIndex);
            JLabel newTaskLabel = new JLabel(currentTask);
            newTaskLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            newTaskLabel.setBounds(30, 10+(currentTaskIndex*30),  300,30);
            newTaskLabel.setHorizontalAlignment(JLabel.LEFT);
            this.add(newTaskLabel);

        }

        //add button to create new topic
        JButton addNewTopicButton = new JButton();
        addNewTopicButton.setBounds(5, 15+(currentTaskIndex*30), 20,20);
        addNewTopicButton.addActionListener(this);
        addNewTopicButton.setActionCommand("A");

        //make it look like only the icon
        addNewTopicButton.setBorderPainted(false);
        addNewTopicButton.setBorder(null);
        addNewTopicButton.setMargin(new Insets(0,0,0,0));
        addNewTopicButton.setContentAreaFilled(false);
        addNewTopicButton.setIcon(iconAdd);
        addNewTopicButton.setRolloverIcon(iconAdd);
        addNewTopicButton.setPressedIcon(iconAdd);
        addNewTopicButton.setDisabledIcon(iconAdd);
        this.add(addNewTopicButton);
        this.repaint();
        this.revalidate();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().charAt(0)=='C')
        {
            if(!TopicHandler.isTopicStillAvailable(topicName))
            {
                JOptionPane.showMessageDialog(null, "Topic was deleted while open!", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                return;
            }

            int index = Integer.valueOf(e.getActionCommand().substring(1));
            TopicHandler.deleteTasksByIndex(topicName,index);
            refreshTasks();

        }

        if(e.getActionCommand().charAt(0)=='A') //add new Task to topic
        {
            if(!TopicHandler.isTopicStillAvailable(topicName))
            {
                JOptionPane.showMessageDialog(null, "Topic was deleted while open!", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                return;
            }

            String newTaskName = JOptionPane.showInputDialog("New task name: ");

            if(newTaskName.length()<1) return;

            TopicHandler.addTaskToTopic(topicName, newTaskName);
            refreshTasks();

        }

    }
}
