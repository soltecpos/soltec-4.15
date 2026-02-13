/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SwingWorkerExample
extends JFrame
implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final JButton startButton;
    private final JButton stopButton;
    private final JScrollPane scrollPane = new JScrollPane();
    private JList listBox = null;
    private final DefaultListModel listModel = new DefaultListModel();
    private final JProgressBar progressBar;
    private mySwingWorker swingWorker;

    public SwingWorkerExample() {
        super("SwingWorkerExample");
        this.setDefaultCloseOperation(3);
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.startButton = this.makeButton("Start");
        this.stopButton = this.makeButton("Stop");
        this.stopButton.setEnabled(false);
        this.progressBar = this.makeProgressBar(0, 99);
        this.listBox = new JList(this.listModel);
        this.scrollPane.setViewportView(this.listBox);
        this.getContentPane().add(this.scrollPane);
        this.pack();
        this.setVisible(true);
    }

    private JButton makeButton(String caption) {
        JButton b = new JButton(caption);
        b.setActionCommand(caption);
        b.addActionListener(this);
        this.getContentPane().add(b);
        return b;
    }

    private JProgressBar makeProgressBar(int min, int max) {
        JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setMinimum(min);
        progressBar1.setMaximum(max);
        progressBar1.setStringPainted(true);
        progressBar1.setBorderPainted(true);
        this.getContentPane().add(progressBar1);
        return progressBar1;
    }

    private void startButton() {
        this.startButton.setEnabled(true);
        this.stopButton.setEnabled(false);
        System.out.println("SwingWorker - Done");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Start".equals(e.getActionCommand())) {
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(true);
            this.swingWorker = new mySwingWorker();
            this.swingWorker.execute();
        } else if ("Stop".equals(e.getActionCommand())) {
            this.startButton.setEnabled(true);
            this.stopButton.setEnabled(false);
            this.swingWorker.cancel(true);
            this.swingWorker = null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingWorkerExample swingWorkerExample = new SwingWorkerExample();
        });
    }

    private class mySwingWorker
    extends SwingWorker<ArrayList<Integer>, Integer> {
        private mySwingWorker() {
        }

        @Override
        protected ArrayList<Integer> doInBackground() {
            if (SwingUtilities.isEventDispatchThread()) {
                System.out.println("javax.swing.SwingUtilities.isEventDispatchThread() returned true.");
            }
            Integer tmpValue = 1;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < 100; ++i) {
                for (int j = 0; j < 100; ++j) {
                    tmpValue = this.FindNextPrime(tmpValue);
                    if (!this.isCancelled()) continue;
                    System.out.println("SwingWorker - isCancelled");
                    return list;
                }
                this.publish(i);
                list.add(tmpValue);
            }
            return list;
        }

        @Override
        protected void process(List<Integer> progressList) {
            if (!SwingUtilities.isEventDispatchThread()) {
                System.out.println("javax.swing.SwingUtilities.isEventDispatchThread() + returned false.");
            }
            Integer percentComplete = progressList.get(progressList.size() - 1);
            SwingWorkerExample.this.progressBar.setValue(percentComplete);
        }

        @Override
        protected void done() {
            System.out.println("doInBackground is complete");
            if (!SwingUtilities.isEventDispatchThread()) {
                System.out.println("javax.swing.SwingUtilities.isEventDispatchThread() + returned false.");
            }
            try {
                ArrayList results = (ArrayList)this.get();
                results.stream().forEach(i -> SwingWorkerExample.this.listModel.addElement(i.toString()));
            }
            catch (InterruptedException | ExecutionException e) {
                System.out.println("Caught an exception: " + e);
            }
            SwingWorkerExample.this.startButton();
        }

        boolean IsPrime(int num) {
            for (int i = 2; i <= num / 2; ++i) {
                if (num % i != 0) continue;
                return false;
            }
            return true;
        }

        protected Integer FindNextPrime(int num) {
            do {
                if (num % 2 == 0) {
                    ++num;
                    continue;
                }
                num += 2;
            } while (!this.IsPrime(num));
            return num;
        }
    }
}

