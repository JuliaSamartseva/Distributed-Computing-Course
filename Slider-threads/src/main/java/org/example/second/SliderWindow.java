package org.example.second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SliderWindow extends JFrame {
  private final JPanel panel;
  private JLabel label;

  public SliderWindow() {
    super("Slider window");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 300);
    panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    setContentPane(panel);

    addSliderLayout();
    addMessageLayout();

    addThreadsLayout(Direction.LEFT, 1, 1);
    addThreadsLayout(Direction.RIGHT, 2, 10);

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(
        dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
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

  private void addThreadsLayout(Direction direction, int height, int priority) {
    GridBagConstraints threadConstraints = new GridBagConstraints();
    final Result result = addThread(direction, priority);

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
    priorityLabel.setText(String.valueOf(result.thread.getPriority()));
    panel.add(priorityLabel, threadConstraints);

    JButton startThreadButton = new JButton("Start");
    threadConstraints.gridx = 3;
    threadConstraints.gridy = height;
    panel.add(startThreadButton, threadConstraints);
    startThreadButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (result.thread.getState() == Thread.State.TERMINATED) {
              label.setText("This thread has been already terminated.");
              label.setVisible(true);
            } else {
              result.thread.start();
            }
          }
        });

    JButton stopThreadButton = new JButton("Stop");
    threadConstraints.gridx = 4;
    threadConstraints.gridy = height;
    panel.add(stopThreadButton, threadConstraints);
    stopThreadButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.out.println(result.thread.getName());
            result.runnable.stopThread();
            result.runnable.releaseSemaphore();
            label.setVisible(false);
          }
        });
  }

  private void addMessageLayout() {
    GridBagConstraints gridConstraints = new GridBagConstraints();
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.ipady = 40;
    gridConstraints.gridy = 3;
    gridConstraints.gridwidth = 5;
    label = new JLabel("The slider is occupied by another thread.");
    panel.add(label, gridConstraints);
    label.setVisible(false);
  }

  private Result addThread(Direction direction, int priority) {
    SliderRunnable sliderRunnable = new SliderRunnable(direction, label);
    Thread thread = new Thread(sliderRunnable);
    thread.setPriority(priority);
    thread.setDaemon(true);
    return new Result(thread, sliderRunnable);
  }
}

class Result {
  public Thread thread;
  public SliderRunnable runnable;

  Result(Thread thread, SliderRunnable runnable) {
    this.thread = thread;
    this.runnable = runnable;
  }
}
