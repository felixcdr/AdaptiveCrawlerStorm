/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import crawler.util.Item;

/**
 *
 * @author ziggy
 */
public class PieChart {

    private List<Item> data;
    private DefaultPieDataset piedataset;
    private JFreeChart chart;
    private String pieTitle;
    private ChartPanel panel;
//    private final Rotator rotator;
    private PiePlot3D plot;

    public PieChart(String title, Color background, Dimension size) {
        piedataset = new DefaultPieDataset();
        data = new ArrayList<Item>();
        pieTitle = title;
        chart = ChartFactory.createPieChart3D(pieTitle, piedataset, true, true, false);
        chart.setBackgroundPaint(background);


        plot = (PiePlot3D) chart.getPlot();
        plot.setForegroundAlpha(0.5f);

//        plot.setDirection(Rotation.ANTICLOCKWISE);

        // add the chart to a panel...
        panel = new ChartPanel(chart);
        panel.setPreferredSize(size);

//        rotator = new Rotator(plot);
//        rotator.setDelay(1);
//        rotator.start();
    }

    public void updateData(List<Item> list) {

//        rotator.stop();

        try {
            data = list;

            piedataset.clear();
            for (Item item : data) {
                piedataset.setValue(item.getEntry(), item.getCount());

            }
        } catch (UnknownKeyException ex) {
            System.out.println("");
        }
//        rotator.start();


    }

    public ChartPanel getChart() {
        return panel;
    }

    class Rotator extends Timer implements ActionListener {

        private PiePlot3D plot;
        private int angle = 270;

        Rotator(final PiePlot3D plot) {
            super(100, null);
            this.plot = plot;
            addActionListener(this);
        }

        public void actionPerformed(final ActionEvent event) {
            try {
                this.plot.setStartAngle(this.angle);
                this.angle = this.angle + 1;
                if (this.angle == 360) {
                    this.angle = 0;
                }
            }catch(UnknownKeyException ex){
                
            }
        }
    }
}
