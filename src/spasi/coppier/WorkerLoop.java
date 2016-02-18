/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spasi.coppier;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author SPASI Mini 1
 */
public class WorkerLoop extends SwingWorker<Void,Void> {
    private final JTextField txt_destination;
    private final JTextField txt_process;
    private final JTextField txt_current;
    private final JTextArea txt_source;
    private final JTextArea txt_console;
    private final JButton btn_batal;

    WorkerLoop(JTextArea txt_source, JTextField txt_destination, JTextArea txt_console, JButton btn_batal, JTextField txt_process, JTextField txt_current) {
        this.txt_destination = txt_destination;
        this.txt_source = txt_source;
        this.txt_console = txt_console;
        this.btn_batal = btn_batal;
        this.txt_process = txt_process;
        this.txt_current = txt_current;
    }
    
    @Override
    protected Void doInBackground() throws Exception {           
        boolean loop = false;
        // TODO add your handling code here:
        for(String line : txt_source.getText().split("\\n")) {
            // stop looping
            if(loop) break;

            // set play loop
            loop = false;

            // set editable
            txt_source.setEditable(false);
            txt_destination.setEditable(false);
            txt_console.setEditable(false);
            txt_process.setEditable(false);
            txt_process.setText(line);
            
            // using copyfolder class
            CopyFolder cf = new CopyFolder(txt_current);
            try {
                File src = new File(line);
                File dst = null;
                if(src.isFile()) dst = new File(txt_destination.getText()+"\\"+src.getName());
                if(src.isDirectory()) dst = new File(txt_destination.getText());
                cf.copyFolder(src, dst);
                this.txt_console.append(line+"\n");
                this.txt_console.setCaretPosition(this.txt_console.getDocument().getLength());
            } catch (IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                txt_console.append("Copy data dari "+line+" ke "+txt_destination.getText()+" : gagal! \n");
            }

            //  Using CMD Windows            
            //            String cmd = "xcopy "+line+" /Y  "+txt_destination.getText()+"  /s /i";
            //            File file = new File(line);
            //            if(file.isDirectory()) {
            //              System.out.println(cmd);
            //                try 
            //                { 
            //                    Process p=Runtime.getRuntime().exec("xcopy "+line+"  "+txt_destination.getText()+"  /s /i"); 
            //                    p.waitFor(); 
            //                    BufferedReader reader=new BufferedReader(
            //                        new InputStreamReader(p.getInputStream())
            //                    ); 
            //                    String lines; 
            //                    while((lines = reader.readLine()) != null) 
            //                    { 
            //                        txt_result.append(lines+"\n");
            //                    } 
            //
            //                }
            //                catch(IOException e1) {} 
            //                catch(InterruptedException e2) {} 
            //            } else {
            //                System.out.println("BUKAN DIRECTORY");
            //            }
        }

        txt_source.setEditable(true);
        txt_destination.setEditable(true);
        txt_console.setEditable(true);
        txt_process.setEditable(true);
        btn_batal.setText("Selesai");
        return null;
    }
}
