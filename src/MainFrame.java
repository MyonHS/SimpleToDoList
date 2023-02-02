import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class MainFrame extends JFrame implements ActionListener {

    ImageIcon iconAdd = new ImageIcon("plus.png");
    Vector<String> topicsVector;

    MainFrame() {

        this.setTitle("SimpleToDoList");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,500);
        this.setResizable(false);
        this.setLayout(null); //set Layout to null for manual position management
        this.setBackground(Color.WHITE);

        this.setVisible(true);

        TopicHandler.loadTopics(); //fill the topicVector from file
        refreshTopics(); //load GUI components
    }

    private void refreshTopics()
    {

        topicsVector = TopicHandler.getTitles();

        this.getContentPane().removeAll(); //clear all old elements
        this.repaint();

        int currentTopicIndex; //we need the loop variable for our manual positioning of the components

        for(currentTopicIndex=0; currentTopicIndex<topicsVector.size(); currentTopicIndex++)
        {

            //One Checkbox per Topic to mark it as done
            JCheckBox deleteCheckBox = new JCheckBox();
            deleteCheckBox.setBounds(5, 15+(currentTopicIndex*30), 20,20);
            deleteCheckBox.setActionCommand("C"+Integer.toString(currentTopicIndex));
            deleteCheckBox.addActionListener(this);
            this.add(deleteCheckBox);

            //add the topics as button to make them clickable
            String currentTopicName = topicsVector.elementAt(currentTopicIndex);
            JButton newTopic = new JButton(currentTopicName);
            newTopic.setFont(new Font("Arial", Font.PLAIN, 16));
            newTopic.setBounds(20, 10+(currentTopicIndex*30),  300,30);
            newTopic.setActionCommand("T"+Integer.toString(currentTopicIndex));
            newTopic.setHorizontalAlignment(JLabel.LEFT);
            newTopic.addActionListener(this);

            //adding hover effect for topics
            newTopic.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    newTopic.setForeground(Color.green);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    newTopic.setForeground(Color.black);
                }
            });

            //make the topic buttons look like labels
            newTopic.setContentAreaFilled(false);
            newTopic.setBorderPainted(false);
            newTopic.setOpaque(false);
            newTopic.setFocusPainted(false);
            this.add(newTopic);

        }

        //add button to create new topic

        JButton addNewTopicButton = new JButton();
        addNewTopicButton.setBounds(5, 15+(currentTopicIndex*30), 20,20);
        addNewTopicButton.addActionListener(this);
        addNewTopicButton.setActionCommand("A");
        this.add(addNewTopicButton);


        //make it look like only the icon
        addNewTopicButton.setBorderPainted(false);
        addNewTopicButton.setBorder(null);
        addNewTopicButton.setMargin(new Insets(0,0,0,0));
        addNewTopicButton.setContentAreaFilled(false);
        addNewTopicButton.setIcon(iconAdd);
        addNewTopicButton.setRolloverIcon(iconAdd);
        addNewTopicButton.setPressedIcon(iconAdd);
        addNewTopicButton.setDisabledIcon(iconAdd);


        //refresh the frame
        this.repaint();
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if(command.charAt(0)=='C') //Checkbox clicked
        {
            int index = Integer.parseInt(command.substring(1));  //get index of Topic to delete
            TopicHandler.deleteTopicByIndex(index);
            refreshTopics();
        }

        else if(command.charAt(0)=='T') //Topic clicked
        {
            int index = Integer.parseInt(command.substring(1)); //get index of the pressed Topic

            //open new TopicFrame
            TopicFrame newTopicFrame = new TopicFrame(TopicHandler.returnTitleByIndex(index));

        }

        else if(command.equals("A")) //add Button clicked
        {
            String newTopicName = JOptionPane.showInputDialog("New topic name: ");

            if(newTopicName.length()<1) return;

            TopicHandler.addNewEmptyTopic(newTopicName);
            refreshTopics();
        }

    }
}
