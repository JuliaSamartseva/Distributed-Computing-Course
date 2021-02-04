package org.example.first;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SliderWindow extends JFrame {
  private final JPanel panel;
  private final ArrayList<Thread> threads = new ArrayList<>();

  public SliderWindow() {
    super("Slider window");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 200);
    panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    setContentPane(panel);

    addSliderLayout();
    addThreadsLayout(Direction.LEFT, 1);
    addThreadsLayout(Direction.RIGHT, 2);

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(
        dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
  }

  public void startThreads() {
    for (Thread thread : threads) {
      thread.start();
    }
  }

  private void addSliderLayout() {
    GridBagConstraints gridConstraints = new GridBagConstraints();
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.ipady = 40;
    gridConstraints.gridwidth = 5;
    JSlider slider = new JSlider();
    panel.add(slider, gridConstraints);
    SliderRunnable.slider = slider;
  }

  private void addThreadsLayout(Direction direction, int height) {
    GridBagConstraints threadConstraints = new GridBagConstraints();
    final Thread thread = addThread(direction);

    threadConstraints.fill = GridBagConstraints.HORIZONTAL;
    threadConstraints.insets = new Insets(10, 10, 10, 10);

    threadConstraints.gridx = 0;
    threadConstraints.gridy = height;
    panel.add(new JLabel(direction == Direction.LEFT ? "Left" : "Right"), threadConstraints);

    threadConstraints.gridx = 1;
    threadConstraints.gridy = height;
    panel.add(new JLabel("Priority: "), threadConstraints);

    threadConstraints.gridx = 2;
    threadConstraints.gridy = height;
    final JLabel priorityLabel = new JLabel();
    priorityLabel.setText(String.valueOf(thread.getPriority()));
    panel.add(priorityLabel, threadConstraints);

    JButton increasePriorityButton = new JButton("Increase priority");
    threadConstraints.gridx = 3;
    threadConstraints.gridy = height;
    panel.add(increasePriorityButton, threadConstraints);
    increasePriorityButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int newPriority = thread.getPriority() + 1;
            if (newPriority <= Thread.MAX_PRIORITY) {
              thread.setPriority(newPriority);
              priorityLabel.setText(String.valueOf(thread.getPriority()));
            }
          }
        });

    JButton decreasePriorityButton = new JButton("Decrease priority");
    threadConstraints.gridx = 4;
    threadConstraints.gridy = height;
    panel.add(decreasePriorityButton, threadConstraints);
    decreasePriorityButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int newPriority = thread.getPriority() - 1;
            if (newPriority >= Thread.MIN_PRIORITY) {
              thread.setPriority(newPriority);
              priorityLabel.setText(String.valueOf(thread.getPriority()));
            }
          }
        });
  }

  private Thread addThread(Direction direction) {
    Thread thread = new Thread(new SliderRunnable(direction));
    thread.setDaemon(true);
    threads.add(thread);
    return thread;
  }
}
