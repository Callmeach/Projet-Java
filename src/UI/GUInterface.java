/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
//import java.util.Date;


/**
 *
 * @author Utilisateur
 */
public class GUInterface extends javax.swing.JFrame {

    /**
     * Creates new form GUInterface
     */
    static final int seuil_approv = 10;
    //Variables de connexion
    String dbUsername = "root";
    String dbPassword = "";
    String dbServer = "jdbc:mysql://localhost:3306/projet_java";
    String dbClassPathUrl = "com.mysql.jdbc.Driver";
    Connection connx;
    //Variables de recuperation
    String CodeArticle;
    String nom;
    double prix;
    int quantite = 0;
    LocalDate dateCreation;
    //Autres Variables
    DefaultTableModel model,model2,model3;
    
    public GUInterface() {
        this.dateCreation = LocalDate.now();
        initComponents();
        connx = databaseConnexion();
    }
    
    public Connection databaseConnexion(){
        Connection conn;
        try {
            //Chargement Driver
            Class.forName(dbClassPathUrl);
            JOptionPane.showMessageDialog(null, "Driver Chargé");
            //Connexion
            conn = DriverManager.getConnection(dbServer,dbUsername,dbPassword);
            JOptionPane.showMessageDialog(null, "Connecté");
            return conn;
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    //Creer la liste des articles
    public ArrayList<Articles> ArticlesList(){
        ArrayList<Articles> ArticlesList = new ArrayList<>();
        String SelectQuery = "SELECT codeArticle,libelleArticle,prix,dateCreation,qteStock FROM articles WHERE qteStock !=0 ORDER BY dateCreation DESC";
        Statement stmt;
        ResultSet rs;
        
        try{
            stmt = connx.createStatement();
            rs = stmt.executeQuery(SelectQuery);
            
            while(rs.next()){
                Articles articles = new Articles();
                articles.setCodeArticle(rs.getString("codeArticle"));
                articles.setNom(rs.getString("libelleArticle"));
                articles.setPrix(rs.getDouble("prix"));
                //articles.setDateCreation(rs.getDate("Date de Creation").toLocalDate());
                articles.setDateCreation(rs.getObject("dateCreation", LocalDate.class));
                articles.setQuantite(rs.getInt("qteStock"));
                ArticlesList.add(articles);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return ArticlesList;
    }
    
    public ArrayList<Articles> ArticlesSeuil(){
        ArrayList<Articles> ArticlesSeuil = new ArrayList<>();
        String SelectQuery = "SELECT codeArticle,libelleArticle,prix,dateCreation,qteStock FROM articles WHERE qteStock <" +seuil_approv+" ORDER BY dateCreation DESC";
        Statement stmt;
        ResultSet rs;
        
        try{
            stmt = connx.createStatement();
            rs = stmt.executeQuery(SelectQuery);
            
            while(rs.next()){
                Articles articl = new Articles();
                articl.setCodeArticle(rs.getString("codeArticle"));
                articl.setNom(rs.getString("libelleArticle"));
                articl.setPrix(rs.getDouble("prix"));
                //articles.setDateCreation(rs.getDate("Date de Creation").toLocalDate());
                articl.setDateCreation(rs.getObject("dateCreation", LocalDate.class));
                articl.setQuantite(rs.getInt("qteStock"));
                ArticlesSeuil.add(articl);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return ArticlesSeuil;
    }
    
    public void remplirTableSeuil(){
        ArrayList<Articles> datasArray = ArticlesSeuil();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[5];
        for(int i=0; i <datasArray.size(); i++){
            rows[0]=datasArray.get(i).getCodeArticle();
            rows[1]=datasArray.get(i).getNom();
            rows[2]=datasArray.get(i).getPrix();
            rows[3]=datasArray.get(i).getDateCreation();
            rows[4]=datasArray.get(i).getQuantite();
            model.addRow(rows);
        }
    }
    
     public ArrayList<Articles> ArticlesListWith0(){
        ArrayList<Articles> ArticlesListWith0 = new ArrayList<>();
        String SelectQuery = "SELECT codeArticle,libelleArticle,prix,dateCreation,qteStock FROM articles ORDER BY dateCreation DESC";
        Statement stmt;
        ResultSet rs;
        
        try{
            stmt = connx.createStatement();
            rs = stmt.executeQuery(SelectQuery);
            
            while(rs.next()){
                Articles articles = new Articles();
                articles.setCodeArticle(rs.getString("codeArticle"));
                articles.setNom(rs.getString("libelleArticle"));
                articles.setPrix(rs.getDouble("prix"));
                //articles.setDateCreation(rs.getDate("Date de Creation").toLocalDate());
                articles.setDateCreation(rs.getObject("dateCreation", LocalDate.class));
                articles.setQuantite(rs.getInt("qteStock"));
                ArticlesListWith0.add(articles);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return ArticlesListWith0;
    }
    
    //Remplir la Table 1
    public void remplirTable(){
        ArrayList<Articles> dataArray = ArticlesList();
        model = (DefaultTableModel) jTable1.getModel();
        model2 = (DefaultTableModel) jTable2.getModel();
        model3 = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);
        model3.setRowCount(0);
        Object[] rows = new Object[5];
        for(int i=0; i <dataArray.size(); i++){
            rows[0]=dataArray.get(i).getCodeArticle();
            rows[1]=dataArray.get(i).getNom();
            rows[2]=dataArray.get(i).getPrix();
            rows[3]=dataArray.get(i).getDateCreation();
            rows[4]=dataArray.get(i).getQuantite();
            model.addRow(rows);
            model2.addRow(rows);
            model3.addRow(rows);
        }
    }
    
    public void remplirTableWith0(){
        ArrayList<Articles> dataArrays = ArticlesListWith0();
        model2 = (DefaultTableModel) jTable2.getModel();
        model3 = (DefaultTableModel) jTable3.getModel();
        model2.setRowCount(0);
        model3.setRowCount(0);
        Object[] rows = new Object[5];
        for(int i=0; i <dataArrays.size(); i++){
            rows[0]=dataArrays.get(i).getCodeArticle();
            rows[1]=dataArrays.get(i).getNom();
            rows[2]=dataArrays.get(i).getPrix();
            rows[3]=dataArrays.get(i).getDateCreation();
            rows[4]=dataArrays.get(i).getQuantite();
            model2.addRow(rows);
            model3.addRow(rows);
        }
    }
    
    //Creation de la liste qui va contenir les resultats de recherche
    public ArrayList<Articles> ArticlesFiltered(){
        ArrayList<Articles> ArticlesFiltered = new ArrayList<>();
        String selectQuery = "SELECT codeArticle,libelleArticle,prix,dateCreation,qteStock FROM articles WHERE libelleArticle LIKE '"+jTextField4.getText()+"%' ORDER BY dateCreation DESC ";
        Statement stmt;
        ResultSet rs;
        
        try{
            stmt = connx.createStatement();
            rs = stmt.executeQuery(selectQuery);
            
            while(rs.next()){
                Articles articles2 = new Articles();
                articles2.setCodeArticle(rs.getString("codeArticle"));
                articles2.setNom(rs.getString("libelleArticle"));
                articles2.setPrix(rs.getDouble("prix"));
                //articles.setDateCreation(rs.getDate("Date de Creation").toLocalDate());
                articles2.setDateCreation(rs.getObject("dateCreation", LocalDate.class));
                articles2.setQuantite(rs.getInt("qteStock"));
                ArticlesFiltered.add(articles2);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return ArticlesFiltered;
    }
    
    //Remplissage de la table de resultats de recherche
    public void remplirTableRech(){
        ArrayList<Articles> dataArray2 = ArticlesFiltered();
        model3 = (DefaultTableModel) jTable3.getModel();
        model3.setRowCount(0);
        Object[] rows = new Object[5];
        for(int i=0; i <dataArray2.size(); i++){
            rows[0]=dataArray2.get(i).getCodeArticle();
            rows[1]=dataArray2.get(i).getNom();
            rows[2]=dataArray2.get(i).getPrix();
            rows[3]=dataArray2.get(i).getDateCreation();
            rows[4]=dataArray2.get(i).getQuantite();
            model3.addRow(rows);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        BoutonAccueil = new javax.swing.JButton();
        BoutonEnregistrer = new javax.swing.JButton();
        BoutonStock = new javax.swing.JButton();
        BoutonApprov = new javax.swing.JButton();
        BoutonModifier = new javax.swing.JButton();
        BoutonQuitter = new javax.swing.JButton();
        Parent = new javax.swing.JPanel();
        PanelAccueil = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        PanelEnregistrer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jTextFieldQte = new javax.swing.JTextField();
        jTextFieldNom = new javax.swing.JTextField();
        jTextFieldPrix = new javax.swing.JTextField();
        jTextFieldCode = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextFieldAnnee = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMois = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldJour = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Reinitialiser = new javax.swing.JButton();
        PanelStock = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        PanelApprovisionner = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButtonApprovisionner = new javax.swing.JButton();
        jButtonVendre = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButtonRechercher = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        QteInit = new javax.swing.JTextField();
        PanelModifier = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        ModifyButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        Libelle = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Prix = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Qte = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Annee = new javax.swing.JTextField();
        Mois = new javax.swing.JTextField();
        Jour = new javax.swing.JTextField();
        PanelQuitter = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(54, 33, 89));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BoutonAccueil.setBackground(new java.awt.Color(64, 43, 100));
        BoutonAccueil.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonAccueil.setForeground(new java.awt.Color(204, 204, 204));
        BoutonAccueil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_home_26px_1.png"))); // NOI18N
        BoutonAccueil.setText("Accueil");
        BoutonAccueil.setBorderPainted(false);
        BoutonAccueil.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BoutonAccueil.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonAccueil.setIconTextGap(15);
        BoutonAccueil.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonAccueil.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BoutonAccueil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonAccueilActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonAccueil, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 151, 260, -1));

        BoutonEnregistrer.setBackground(new java.awt.Color(64, 43, 100));
        BoutonEnregistrer.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonEnregistrer.setForeground(new java.awt.Color(204, 204, 204));
        BoutonEnregistrer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_data_recovery_25px_2.png"))); // NOI18N
        BoutonEnregistrer.setText("Enregistrer Article");
        BoutonEnregistrer.setBorderPainted(false);
        BoutonEnregistrer.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonEnregistrer.setIconTextGap(15);
        BoutonEnregistrer.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonEnregistrer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BoutonEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonEnregistrerActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonEnregistrer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 260, -1));

        BoutonStock.setBackground(new java.awt.Color(64, 43, 100));
        BoutonStock.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonStock.setForeground(new java.awt.Color(204, 204, 204));
        BoutonStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_shopping_cart_25px.png"))); // NOI18N
        BoutonStock.setText("Stock");
        BoutonStock.setBorderPainted(false);
        BoutonStock.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonStock.setIconTextGap(15);
        BoutonStock.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BoutonStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonStockActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 260, -1));

        BoutonApprov.setBackground(new java.awt.Color(64, 43, 100));
        BoutonApprov.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonApprov.setForeground(new java.awt.Color(204, 204, 204));
        BoutonApprov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_add_26px.png"))); // NOI18N
        BoutonApprov.setText("Approvisionner/Vendre");
        BoutonApprov.setBorderPainted(false);
        BoutonApprov.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonApprov.setIconTextGap(12);
        BoutonApprov.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonApprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonApprovActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonApprov, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 260, -1));

        BoutonModifier.setBackground(new java.awt.Color(64, 43, 100));
        BoutonModifier.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonModifier.setForeground(new java.awt.Color(204, 204, 204));
        BoutonModifier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_Delete_26px.png"))); // NOI18N
        BoutonModifier.setText("Modifier/Supprimer");
        BoutonModifier.setBorderPainted(false);
        BoutonModifier.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonModifier.setIconTextGap(12);
        BoutonModifier.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonModifier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BoutonModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonModifierActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonModifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 260, -1));

        BoutonQuitter.setBackground(new java.awt.Color(64, 43, 100));
        BoutonQuitter.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        BoutonQuitter.setForeground(new java.awt.Color(204, 204, 204));
        BoutonQuitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_sign_out_26px.png"))); // NOI18N
        BoutonQuitter.setText("Quitter");
        BoutonQuitter.setBorderPainted(false);
        BoutonQuitter.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BoutonQuitter.setIconTextGap(11);
        BoutonQuitter.setPreferredSize(new java.awt.Dimension(250, 50));
        BoutonQuitter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BoutonQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonQuitterActionPerformed(evt);
            }
        });
        jPanel2.add(BoutonQuitter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 260, -1));

        Parent.setBackground(new java.awt.Color(255, 255, 255));
        Parent.setLayout(new java.awt.CardLayout());

        PanelAccueil.setBackground(new java.awt.Color(0, 204, 204));

        jLabel18.setFont(new java.awt.Font("Bookman Old Style", 1, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("LOGICIEL DE GESTION DE STOCKS");

        jLabel19.setFont(new java.awt.Font("Constantia", 1, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("MEMBRES DU GROUPE");

        jLabel20.setFont(new java.awt.Font("Constantia", 2, 14)); // NOI18N
        jLabel20.setText("DJAKPO ACHILLE KOMI GODWILL");

        jLabel21.setFont(new java.awt.Font("Constantia", 2, 14)); // NOI18N
        jLabel21.setText("COUBAGEAT-TOURE MAHDIYA");

        jLabel22.setFont(new java.awt.Font("Constantia", 2, 14)); // NOI18N
        jLabel22.setText("SEMODJI KOMLAN MATHIEU");

        javax.swing.GroupLayout PanelAccueilLayout = new javax.swing.GroupLayout(PanelAccueil);
        PanelAccueil.setLayout(PanelAccueilLayout);
        PanelAccueilLayout.setHorizontalGroup(
            PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelAccueilLayout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addGroup(PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(349, Short.MAX_VALUE))
        );
        PanelAccueilLayout.setVerticalGroup(
            PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAccueilLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        Parent.add(PanelAccueil, "card2");

        PanelEnregistrer.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(110, 89, 222));

        jLabel56.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("ENREGISTREMENT D'UN NOUVEL ARTICLE");
        jLabel56.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(153, 153, 255));

        jLabel14.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Code Article");

        jLabel52.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Nom Article");

        jLabel53.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Prix Unitaire");

        jLabel54.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Quantite en stock");

        jTextFieldQte.setEditable(false);
        jTextFieldQte.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jTextFieldQte.setText("0");

        jTextFieldNom.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N

        jTextFieldPrix.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jTextFieldPrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrixActionPerformed(evt);
            }
        });

        jTextFieldCode.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Date de creation");

        jTextFieldAnnee.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jTextFieldAnnee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAnneeActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 204, 51));
        jButton10.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jButton10.setText("Enregistrer");
        jButton10.setBorderPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Bell MT", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Annee");

        jTextFieldMois.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMoisActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Bell MT", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mois");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Bell MT", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Jour");

        Reinitialiser.setBackground(new java.awt.Color(255, 255, 255));
        Reinitialiser.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        Reinitialiser.setText("Reinitialiser");
        Reinitialiser.setBorderPainted(false);
        Reinitialiser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReinitialiserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(165, 165, 165)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAnnee, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldMois, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldJour, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldPrix)
                            .addComponent(jTextFieldNom)
                            .addComponent(jTextFieldQte)
                            .addComponent(jTextFieldCode))
                        .addGap(267, 267, 267))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(Reinitialiser, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrix, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldQte, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAnnee, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMois, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldJour, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Reinitialiser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout PanelEnregistrerLayout = new javax.swing.GroupLayout(PanelEnregistrer);
        PanelEnregistrer.setLayout(PanelEnregistrerLayout);
        PanelEnregistrerLayout.setHorizontalGroup(
            PanelEnregistrerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEnregistrerLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelEnregistrerLayout.setVerticalGroup(
            PanelEnregistrerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEnregistrerLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parent.add(PanelEnregistrer, "card3");

        PanelStock.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(110, 89, 222));

        jLabel4.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("LISTE DES ARTICLES EN STOCK");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Article", "Libellé", "Prix", "Date de Creation", "Quantite"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.setRowMargin(5);
        jTable1.setShowGrid(false);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Constantia", 1, 18)); // NOI18N
        jLabel11.setText("TOTAL");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Bookman Old Style", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("En dessous du seuil d'approvisionnement");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelStockLayout = new javax.swing.GroupLayout(PanelStock);
        PanelStock.setLayout(PanelStockLayout);
        PanelStockLayout.setHorizontalGroup(
            PanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelStockLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelStockLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelStockLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253))))
        );
        PanelStockLayout.setVerticalGroup(
            PanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelStockLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Parent.add(PanelStock, "card4");

        PanelApprovisionner.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(110, 89, 222));
        jPanel9.setPreferredSize(new java.awt.Dimension(1032, 51));

        jLabel12.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("VENDRE-APPROVISIONNER-RECHERCHER");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jLabel13.setText("Code");

        jTextField2.setEditable(false);

        jLabel15.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jLabel15.setText("Quantité");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButtonApprovisionner.setBackground(new java.awt.Color(51, 102, 255));
        jButtonApprovisionner.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApprovisionner.setText("Approvisionner");
        jButtonApprovisionner.setBorderPainted(false);
        jButtonApprovisionner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApprovisionnerActionPerformed(evt);
            }
        });

        jButtonVendre.setBackground(new java.awt.Color(0, 204, 51));
        jButtonVendre.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVendre.setText("Vendre");
        jButtonVendre.setBorderPainted(false);
        jButtonVendre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVendreActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jLabel16.setText("Rechercher");

        jButtonRechercher.setBackground(new java.awt.Color(204, 204, 204));
        jButtonRechercher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/images/icons8_search_26px.png"))); // NOI18N
        jButtonRechercher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercherActionPerformed(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Code Article", "Libellé", "Prix", "Date de Creation", "Quantite"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowHeight(30);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel17.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jLabel17.setText("Qte Initiale");

        QteInit.setEditable(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(QteInit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jButtonApprovisionner, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jButtonVendre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonRechercher, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonVendre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButtonApprovisionner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField4)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRechercher, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(QteInit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PanelApprovisionnerLayout = new javax.swing.GroupLayout(PanelApprovisionner);
        PanelApprovisionner.setLayout(PanelApprovisionnerLayout);
        PanelApprovisionnerLayout.setHorizontalGroup(
            PanelApprovisionnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelApprovisionnerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelApprovisionnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        PanelApprovisionnerLayout.setVerticalGroup(
            PanelApprovisionnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelApprovisionnerLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parent.add(PanelApprovisionner, "card5");

        PanelModifier.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(110, 89, 222));

        jLabel5.setBackground(new java.awt.Color(51, 51, 51));
        jLabel5.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SELECTIONNER LES ARTICLES A MODIFIER OU SUPPRIMER");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        ModifyButton.setBackground(new java.awt.Color(0, 102, 255));
        ModifyButton.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        ModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        ModifyButton.setText("Modifier");
        ModifyButton.setBorderPainted(false);
        ModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyButtonActionPerformed(evt);
            }
        });

        DeleteButton.setBackground(new java.awt.Color(255, 0, 51));
        DeleteButton.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        DeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        DeleteButton.setText("Supprimer");
        DeleteButton.setBorderPainted(false);
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable2.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Code Article", "Libellé", "Prix", "Date Creation", "Quantité"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(30);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel6.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel6.setText(" Code Article");

        ID.setEditable(false);
        ID.setBackground(new java.awt.Color(255, 255, 255));
        ID.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N

        Libelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LibelleActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel7.setText(" Libelle");
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 14));

        jLabel8.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel8.setText(" Prix");

        jLabel9.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel9.setText(" Quantite");

        Qte.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel10.setText(" Date de Creation");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ID)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Libelle)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Prix)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Qte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(Annee, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(Mois, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Jour, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Libelle)
                            .addComponent(Prix)
                            .addComponent(ID, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(Jour)
                            .addComponent(Qte)
                            .addComponent(Annee)
                            .addComponent(Mois))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelModifierLayout = new javax.swing.GroupLayout(PanelModifier);
        PanelModifier.setLayout(PanelModifierLayout);
        PanelModifierLayout.setHorizontalGroup(
            PanelModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PanelModifierLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelModifierLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        PanelModifierLayout.setVerticalGroup(
            PanelModifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelModifierLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Parent.add(PanelModifier, "card7");

        PanelQuitter.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelQuitterLayout = new javax.swing.GroupLayout(PanelQuitter);
        PanelQuitter.setLayout(PanelQuitterLayout);
        PanelQuitterLayout.setHorizontalGroup(
            PanelQuitterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1019, Short.MAX_VALUE)
        );
        PanelQuitterLayout.setVerticalGroup(
            PanelQuitterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );

        Parent.add(PanelQuitter, "card8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Parent, javax.swing.GroupLayout.PREFERRED_SIZE, 1019, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Parent, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonQuitterActionPerformed
        // TODO add your handling code here:
        Parent.removeAll();
        Parent.add(PanelQuitter);
        Parent.repaint();
        Parent.revalidate();
        System.exit(0);
    }//GEN-LAST:event_BoutonQuitterActionPerformed

    private void BoutonAccueilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonAccueilActionPerformed
        // TODO add your handling code here:
        Parent.removeAll();
        Parent.add(PanelAccueil);
        Parent.repaint();
        Parent.revalidate();
    }//GEN-LAST:event_BoutonAccueilActionPerformed

    private void BoutonEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonEnregistrerActionPerformed
        // TODO add your handling code here:
        //Gestion du CardLayout
        Parent.removeAll();
        Parent.add(PanelEnregistrer);
        Parent.repaint();
        Parent.revalidate();
        
    }//GEN-LAST:event_BoutonEnregistrerActionPerformed

    private void BoutonStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonStockActionPerformed
        // TODO add your handling code here:
        Parent.removeAll();
        Parent.add(PanelStock);
        Parent.repaint();
        Parent.revalidate();
        Statement stmt;
        ResultSet rs;
        String query = "SELECT COUNT(*) FROM articles WHERE qteStock!=0";
        try{
            stmt = connx.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                jTextField1.setText(Integer.toString(rs.getInt(1)));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        remplirTable();
        
    }//GEN-LAST:event_BoutonStockActionPerformed

    private void BoutonApprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonApprovActionPerformed
        // TODO add your handling code here:
        Parent.removeAll();
        Parent.add(PanelApprovisionner);
        Parent.repaint();
        Parent.revalidate();
        remplirTableWith0();
        clearAllInputFields();
    }//GEN-LAST:event_BoutonApprovActionPerformed

    private void BoutonModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonModifierActionPerformed
        // TODO add your handling code here:
        Parent.removeAll();
        Parent.add(PanelModifier);
        Parent.repaint();
        Parent.revalidate();
        remplirTableWith0();
        clearAllInputFields();
    }//GEN-LAST:event_BoutonModifierActionPerformed

    private void jTextFieldPrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPrixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPrixActionPerformed

    private void jTextFieldAnneeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAnneeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAnneeActionPerformed

    private void jTextFieldMoisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMoisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMoisActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        
        //Recuperation des valeurs des champs
        CodeArticle = jTextFieldCode.getText();
        nom = jTextFieldNom.getText();
        try{
            prix = Double.parseDouble(jTextFieldPrix.getText());
            quantite = Integer.parseInt(jTextFieldQte.getText());
        }
        catch(java.lang.NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs entieres pour le Prix et la Quantite", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        
        //Controles sur la date
        if((jTextFieldAnnee.getText().isEmpty() || jTextFieldMois.getText().isEmpty() || jTextFieldJour.getText().isEmpty()) == false){
            int annee = Integer.parseInt(jTextFieldAnnee.getText());
            int mois = Integer.parseInt(jTextFieldMois.getText());
            int jour = Integer.parseInt(jTextFieldJour.getText());
        
            if((annee < 1990 || annee > 2022) && (mois < 01 || mois >12) && (jour < 01 || jour > 31)){
                JOptionPane.showMessageDialog(null,"Format de date incorrect","Alert" , JOptionPane.ERROR_MESSAGE);
            }
            else if(annee < 1990 || annee > 2022){
                JOptionPane.showMessageDialog(null,"L'annee doit etre comprise entre 1990 et 2022","Alert", JOptionPane.ERROR_MESSAGE);
            }
            else if(mois < 01 || mois >12){
                JOptionPane.showMessageDialog(null, "Le mois doit etre compris entre 1 et 12","Alert", JOptionPane.ERROR_MESSAGE);
            }
            else if(jour < 01 || jour > 31){
                JOptionPane.showMessageDialog(null, "Le jour doit etre compris entre 1 et 31","Alert", JOptionPane.ERROR_MESSAGE);
            }
            else if(mois == 02 && jour > 29){
                JOptionPane.showMessageDialog(null, "Jour invalide pour le mois Fevrier", "Alert", JOptionPane.ERROR_MESSAGE);
            }
            else if(LocalDate.of(annee,mois,jour).compareTo(LocalDate.now()) > 0){
                JOptionPane.showMessageDialog(null, "Date superieure a la date d'aujourd'hui", "Alert", JOptionPane.ERROR_MESSAGE);
            }
            else{
                dateCreation = LocalDate.of(annee,mois,jour);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "La date ne peut etre vide", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        //Fin du controle
        
        //Verifier si tous les champs ont ete remplis
        if(CodeArticle.isEmpty() || nom.isEmpty() || jTextFieldPrix.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tous les champs doivent etre remplis", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Insertion en base
            try{
                String InsertQuery = "INSERT INTO articles VALUES (?,?,?,?,?)";
                PreparedStatement articlesPST = this.connx.prepareStatement(InsertQuery);
                articlesPST.setString(1,CodeArticle);
                articlesPST.setString(2,nom);
                articlesPST.setDouble(3,prix);
                articlesPST.setInt(4,quantite);
                articlesPST.setObject(5,dateCreation);
                int articleInserted = articlesPST.executeUpdate();
                if(articleInserted > 0){
                    JOptionPane.showMessageDialog(null,"Inseré avec Succès...");
                    clearAllInputFields();
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'enregistrement", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void ReinitialiserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReinitialiserActionPerformed
        // TODO add your handling code here:
        clearAllInputFields();
    }//GEN-LAST:event_ReinitialiserActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        // TODO add your handling code here:
        Connection dbConnexion = null;
        Statement state = null;
        String DeleteQuery = "DELETE FROM articles WHERE codeArticle = \'" + ID.getText()  +"\'";
        dbConnexion = this.connx;
        try{
            state = dbConnexion.createStatement();
            int deleteST = state.executeUpdate(DeleteQuery);
            if(deleteST > 0){
                JOptionPane.showMessageDialog(null, "Supression effectuee avec succes");
                remplirTableWith0();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                if(state != null)
                    state.close();
                if(dbConnexion != null)
                    state.close();
            }
            catch(SQLException except){
                except.getMessage();
            }
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int jTableSelectedRow = jTable2.getSelectedRow();
        selection(jTableSelectedRow);
    }//GEN-LAST:event_jTable2MouseClicked

    private void ModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyButtonActionPerformed
        // TODO add your handling code here:
        CodeArticle = ID.getText();
        if(!Libelle.getText().isEmpty()){
            nom = Libelle.getText();
        }
        try{
            prix = Double.parseDouble(Prix.getText());
            quantite = Integer.parseInt(Qte.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs entieres", "Alert", JOptionPane.ERROR_MESSAGE);
        }

        if((Annee.getText().isEmpty() || Mois.getText().isEmpty() || Jour.getText().isEmpty()) == false){
            int annee = Integer.parseInt(Annee.getText());
            int mois = Integer.parseInt(Mois.getText());
            int jour = Integer.parseInt(Jour.getText());
            dateCreation = LocalDate.of(annee,mois,jour);
        }
        else{
            JOptionPane.showMessageDialog(null, "Terminer la saisie de l'annee", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        
        Connection dbConnexion = null;
        PreparedStatement reqprep = null;
        String UpdateQuery = "UPDATE articles SET `libelleArticle`=?,`prix`=?,`qteStock`=?,`dateCreation`=? WHERE `codeArticle`=?";
        dbConnexion = this.connx;
        
        try{
            reqprep = dbConnexion.prepareStatement(UpdateQuery);
            reqprep.setString(1,nom);
            reqprep.setDouble(2, prix);
            reqprep.setInt(3, quantite);
            reqprep.setObject(4, dateCreation);
            reqprep.setString(5, CodeArticle);
            
            int updateResult = reqprep.executeUpdate();
            if(updateResult > 0){
                JOptionPane.showMessageDialog(null, "Mise a jour reussie");
                clearAllInputFields();
                remplirTableWith0();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try {
                    if(reqprep!=null){
                    reqprep.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }//GEN-LAST:event_ModifyButtonActionPerformed

    private void LibelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LibelleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LibelleActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        int jTableSelectedRow3 = jTable3.getSelectedRow();
        selection(jTableSelectedRow3);
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButtonApprovisionnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApprovisionnerActionPerformed
        // TODO add your handling code here:
        Connection dbConnexion = null;
        PreparedStatement reqprep = null;
        
        CodeArticle = jTextField2.getText();
        if(Integer.parseInt(jTextField3.getText()) >= 0){
            quantite = Integer.parseInt(jTextField3.getText());
     
            int qteFinal = Integer.parseInt(QteInit.getText()) + quantite;
            String UpdateQuery = "UPDATE articles SET `qteStock`=? WHERE `codeArticle`=?";
            dbConnexion = this.connx;

            try{
                reqprep = dbConnexion.prepareStatement(UpdateQuery);
                reqprep.setInt(1, qteFinal);
                reqprep.setString(2, CodeArticle);

                int updateResult = reqprep.executeUpdate();
                if(updateResult > 0){
                    JOptionPane.showMessageDialog(null, "Approvisionnement reussi");
                    clearAllInputFields();
                    remplirTableWith0();
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            finally{
                try {
                        if(reqprep!=null){
                        reqprep.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "La quantite doit etre positive", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonApprovisionnerActionPerformed

    private void jButtonVendreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVendreActionPerformed
        // TODO add your handling code here:
        CodeArticle = jTextField2.getText();
        quantite = Integer.parseInt(jTextField3.getText());
        
        Connection dbConnexion = null;
        PreparedStatement reqprep = null;
        
        if(Integer.parseInt(QteInit.getText()) > quantite){
            int qteFinal = Integer.parseInt(QteInit.getText()) - quantite;
            String UpdateQuery = "UPDATE articles SET `qteStock`=? WHERE `codeArticle`=?";
            dbConnexion = this.connx;

            try{
                reqprep = dbConnexion.prepareStatement(UpdateQuery);
                reqprep.setInt(1, qteFinal);
                reqprep.setString(2, CodeArticle);

                int updateResult = reqprep.executeUpdate();
                if(updateResult > 0){
                    JOptionPane.showMessageDialog(null, "Vente effectuee avec succes");
                    clearAllInputFields();
                    remplirTableWith0();
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            finally{
                try {
                        if(reqprep!=null){
                            reqprep.close();
                        }
                    } 
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Quantite en stock insuffisante", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonVendreActionPerformed

    private void jButtonRechercherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercherActionPerformed
        // TODO add your handling code here:
        remplirTableRech();
    }//GEN-LAST:event_jButtonRechercherActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        remplirTableSeuil();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Annee;
    private javax.swing.JButton BoutonAccueil;
    private javax.swing.JButton BoutonApprov;
    private javax.swing.JButton BoutonEnregistrer;
    private javax.swing.JButton BoutonModifier;
    private javax.swing.JButton BoutonQuitter;
    private javax.swing.JButton BoutonStock;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JTextField ID;
    private javax.swing.JTextField Jour;
    private javax.swing.JTextField Libelle;
    private javax.swing.JButton ModifyButton;
    private javax.swing.JTextField Mois;
    private javax.swing.JPanel PanelAccueil;
    private javax.swing.JPanel PanelApprovisionner;
    private javax.swing.JPanel PanelEnregistrer;
    private javax.swing.JPanel PanelModifier;
    private javax.swing.JPanel PanelQuitter;
    private javax.swing.JPanel PanelStock;
    private javax.swing.JPanel Parent;
    private javax.swing.JTextField Prix;
    private javax.swing.JTextField Qte;
    private javax.swing.JTextField QteInit;
    private javax.swing.JButton Reinitialiser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButtonApprovisionner;
    private javax.swing.JButton jButtonRechercher;
    private javax.swing.JButton jButtonVendre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextFieldAnnee;
    private javax.swing.JTextField jTextFieldCode;
    private javax.swing.JTextField jTextFieldJour;
    private javax.swing.JTextField jTextFieldMois;
    private javax.swing.JTextField jTextFieldNom;
    private javax.swing.JTextField jTextFieldPrix;
    private javax.swing.JTextField jTextFieldQte;
    // End of variables declaration//GEN-END:variables

//Cette methode efface tous les champs de saisie et les remets a defaut
private void clearAllInputFields(){
    jTextFieldCode.setText("");
    jTextFieldNom.setText("");
    jTextFieldPrix.setText("");
    jTextFieldQte.setText("");
    jTextFieldAnnee.setText("");
    jTextFieldMois.setText("");
    jTextFieldJour.setText("");
    ID.setText("");
    Libelle.setText("");
    Prix.setText("");
    Qte.setText(null);
    Annee.setText(null);
    Mois.setText(null);
    Jour.setText(null);
    jTextField2.setText(null);
    QteInit.setText("");
    jTextField3.setText("");
}

private void selection(int compteur){
    //Recuperation des valeurs de la ligne selectionnee dans le tableau 
    //afin de les afficher dsns le formulaire de modification
    ID.setText(ArticlesListWith0().get(compteur).getCodeArticle());
    Libelle.setText(ArticlesListWith0().get(compteur).getNom());
    Prix.setText(Double.toString(ArticlesListWith0().get(compteur).getPrix()));
    Qte.setText(Integer.toString(ArticlesListWith0().get(compteur).getQuantite()));
    
   
    
    String str = ArticlesFiltered().get(compteur).getCodeArticle();
    String str2 = ArticlesListWith0().get(compteur).getCodeArticle();
    
    if(!(str.isEmpty())){
        jTextField2.setText(ArticlesFiltered().get(compteur).getCodeArticle());
        QteInit.setText(Integer.toString(ArticlesFiltered().get(compteur).getQuantite()));
    }
    else{
        jTextField2.setText(ArticlesListWith0().get(compteur).getCodeArticle());
        QteInit.setText(Integer.toString(ArticlesListWith0().get(compteur).getQuantite()));
    }
    //Recuperation de la quantite existante de l'article selectionne
    
    
    //Recuperation des valeurs de la date
    LocalDate dateCreation = ArticlesListWith0().get(compteur).getDateCreation();
    int annee = dateCreation.getYear();
    int mois = dateCreation.getMonthValue();
    int jour = dateCreation.getDayOfMonth();
    Annee.setText(Integer.toString(annee));
    Mois.setText(Integer.toString(mois));
    Jour.setText(Integer.toString(jour));
    
    
    
}
}
