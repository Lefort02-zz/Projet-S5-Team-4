package projets5t4;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

public class Tartiflette extends ApplicationFrame {

    public Tartiflette(final String title) {
        
        super(title);
        final PieDataset dateFromage = createSampleDataset();
        final JFreeChart chart = createChart(dateFromage);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(750, 550));//taille de la fenêtre
        setContentPane(chartPanel);

    }

    private PieDataset createSampleDataset() {

        RDVDAO rdvD = new RDVDAO(SQL.getInstance());//récuperation liste RDV

        List<RDV> RdvList = new ArrayList<>();

        RdvList = rdvD.find();

        //variables statistiques
        double main = 0;
        double pied = 0;
        double tete = 0;
        double dos = 0;
        double cou = 0;
        double bras = 0;
        double torse = 0;
        double autre = 0;

        for (int i = 0; i < RdvList.size(); i++)//comptabilise les raisons de RDV
        {
            if (RdvList.get(i).getInformations().contains("main")) {
                main++;
            } else if (RdvList.get(i).getInformations().contains("pied")) {
                pied++;
            } else if (RdvList.get(i).getInformations().contains("tete")) {
                tete++;
            } else if (RdvList.get(i).getInformations().contains("dos")) {
                dos++;
            } else if (RdvList.get(i).getInformations().contains("cou")) {
                cou++;
            } else if (RdvList.get(i).getInformations().contains("bras")) {
                bras++;
            } else if (RdvList.get(i).getInformations().contains("torse")) {
                torse++;
            }else
                autre++;
        }

        double total = main + pied + tete + dos + cou + bras + torse + autre; //somme total des raisons

        final DefaultPieDataset result = new DefaultPieDataset();//affiche les resultats dans le camembert
        if (main != 0)//permet de limiter l'affichage en fonction des resultats
        {
            result.setValue("Main", (100 * main) / total);
        }
        if (tete != 0) {
            result.setValue("Tête", (100 * tete) / total);
        }
        if (pied != 0) {
            result.setValue("Pied", (100 * pied) / total);
        }
        if (dos != 0) {
            result.setValue("Dos", (100 * dos) / total);
        }
        if (cou != 0) {
            result.setValue("Cou", (100 * cou) / total);
        }
        if (bras != 0) {
            result.setValue("Bras", (100 * bras) / total);
        }
        if (torse != 0) {
            result.setValue("Torse", (100 * torse) / total);
        }
        if (autre != 0) {
            result.setValue("Autre", (100 * autre) / total);
        }
        return result;

    }

    private JFreeChart createChart(final PieDataset dataset) {

        final JFreeChart chart = ChartFactory.createPieChart3D("Problèmes par Recurrence ", dataset, true, true, false);//initialise le camembert

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();//effets visuel 3d 
        plot.setStartAngle(300);
        plot.setForegroundAlpha(0.7f);
        plot.setNoDataMessage("No data to display");//dans le cas ou il n'y a pas de rdv
        return chart;
    }

    public static void main(final String[] args) {

        final Tartiflette fromageStat = new Tartiflette("Statistique utilisation");
        fromageStat.pack();
        //fromageStat.setSize(750, 550);
        RefineryUtilities.centerFrameOnScreen(fromageStat);
        fromageStat.setVisible(true);

    }
}
