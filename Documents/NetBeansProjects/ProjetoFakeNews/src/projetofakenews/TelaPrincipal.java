package projetofakenews;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import twitter4j.TwitterException;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import static weka.core.TechnicalInformation.Field.URL;
import weka.core.converters.ConverterUtils;


public class TelaPrincipal extends javax.swing.JFrame {
 
    
    public TelaPrincipal() throws IOException {
        initComponents();
        setIcon(); //chamado ao procedimento de alterar icone do frame
    }

    
    @SuppressWarnings("unchecked")
    
    



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtTeste = new javax.swing.JTextArea();
        btnAvaliar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Classificador de Fake News");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);

        txtTeste.setColumns(20);
        txtTeste.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTeste.setRows(5);
        txtTeste.setToolTipText("");
        jScrollPane1.setViewportView(txtTeste);

        btnAvaliar.setText("Avaliar");
        btnAvaliar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAvaliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvaliarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(btnAvaliar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnAvaliar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvaliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarActionPerformed

        try {
            ConverterUtils.DataSource data = new ConverterUtils.DataSource("src/projetofakenews/fakenews.arff");
            Instances instancia = data.getDataSet(); //capturar todas as instancias do arquivo .arff
            //System.out.println(instancia.toString());

            instancia.setClassIndex(1); // informando que desejo utilizar o atributo 1 (fake) para fazer a classificação/previsão

            NaiveBayesMultinomialText nb = new NaiveBayesMultinomialText();
            nb.buildClassifier(instancia);

            String textoTeste = txtTeste.getText().toString();
            if (textoTeste.toLowerCase().contains("covid") || 
                textoTeste.toLowerCase().contains("corona") || 
                textoTeste.toLowerCase().contains("quarentena")){

            Instance novaInstancia = new DenseInstance(2); //criando uma nova instancia para classificação
            novaInstancia.setDataset(instancia);
            novaInstancia.setValue(0, textoTeste);

            double probabilidade [] = nb.distributionForInstance(novaInstancia);

            JOptionPane.showMessageDialog(null, "Chances de ser fake:" + probabilidade[1]*100 + "% \n" +  "Chances de ser real:" + probabilidade[0]*100 + "%", "Resultado",JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(null, "Nehuma palavra chave encontrada! \n Tente novamente","Erro",JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAvaliarActionPerformed

 
    public static void main(String args[]) throws TwitterException, InterruptedException, IOException {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaPrincipal().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
         
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvaliar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtTeste;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
    }
}
