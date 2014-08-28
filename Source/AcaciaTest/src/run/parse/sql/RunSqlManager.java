/*
 * Copyright 2014 Acacia Man
 * The program is distributed under the terms of the GNU General Public License
 * 
 * This file is part of acacia-lex.
 *
 * acacia-lex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * acacia-lex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package run.parse.sql;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import parse.sql.SqlManager;

public class RunSqlManager extends javax.swing.JFrame {

    private final static String NEW_LINE = "\n";
    
    private Properties prop = new Properties();
    private SqlManager manager = new SqlManager();
    
    /**
     * Creates new form RunSqlManager
     */
    public RunSqlManager() {
        initComponents();

        loadProp();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storeProp();
                
            }
        });
    
    }
    
    
    public void loadManager() {
            jTextPane1.setText(null);
            StyledDocument doc = jTextPane1.getStyledDocument();
        
        try {

            manager = new SqlManager();
            File csv = new File(jTextField2.getText());
            manager.init(csv, doc);

            File folder = new File(jTextField1.getText());
            listFilesForFolder(folder);

            doc.insertString(doc.getLength(), "Done!"+NEW_LINE, null );
        } catch (Exception ex) {
                try {
                    doc.insertString(doc.getLength(),
                            ex.getLocalizedMessage() +NEW_LINE, null );
                } catch (BadLocationException ex1) {
                    Logger.getLogger(RunSqlManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            Logger.getLogger(RunSqlManager.class.getName()).log(Level.SEVERE, null, ex);
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

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Run SQL Manager");

        jButton1.setText("Run");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("SQL files Folder:");

        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Progress:");

        jScrollPane2.setViewportView(jTextPane1);

        jLabel3.setText("DB Objects:");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton1)))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(119, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	int returnVal = chooser.showOpenDialog(this);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		try {
			jTextField1.setText(chooser.getSelectedFile()
					.getCanonicalPath());
		} catch (IOException ex) {
                        jTextField1.setText(null);
		}
	}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadManager();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter csvfilter;
        csvfilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
            chooser.setFileFilter(csvfilter);
            chooser.setDialogTitle("Database objects csv file");
 // set selected filter
        chooser.setFileFilter(csvfilter);
        
        
        
	int returnVal = chooser.showOpenDialog(this);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		try {
			jTextField2.setText(chooser.getSelectedFile()
					.getCanonicalPath());
		} catch (IOException ex) {
                        jTextField2.setText(null);
		}
	}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    
    public void listFilesForFolder(final File folder) {

        FileFilter directoryFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory() && !file.getName().equals(".svn");
            }
        };

        for (final File fileEntry : folder.listFiles(directoryFilter)) {
            listFilesForFolder(fileEntry);
        }

        for (final File fileEntry : folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".sql");
            }
        })) {
            try {
                StyledDocument doc = jTextPane1.getStyledDocument();
                doc.insertString(doc.getLength(), fileEntry.getCanonicalPath()+NEW_LINE, null );
                
                manager.parse(fileEntry, doc);
            } catch (IOException ex) {
                Logger.getLogger(RunSqlManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(RunSqlManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
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
            java.util.logging.Logger.getLogger(RunSqlManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RunSqlManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RunSqlManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RunSqlManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RunSqlManager().setVisible(true);
            }
        });
    }
    
    public void loadProp() {
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            jTextField1.setText(prop.getProperty("SQLFolder","c:\\Temp"));
            jTextField2.setText(prop.getProperty("DBObjects","c:\\Temp\\objects.csv"));

            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    public void storeProp() {
        OutputStream output = null;
 
	try {
 
		output = new FileOutputStream("config.properties");
 
		// set the properties value
		prop.setProperty("SQLFolder", jTextField1.getText());
		prop.setProperty("DBObjects", jTextField2.getText());
                
		// save properties to project root folder
		prop.store(output, null);
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
    }

    /**
     * @return the prop
     */
    public Properties getProp() {
        return prop;
    }

    /**
     * @param prop the prop to set
     */
    public void setProp(Properties prop) {
        this.prop = prop;
    }

    /**
     * @return the manager
     */
    public SqlManager getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(SqlManager manager) {
        this.manager = manager;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
