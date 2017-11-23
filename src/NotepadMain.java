/**
 * Created by madooding on 11/23/2017 AD.
 */
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class NotepadMain extends JFrame {
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu fileMenu, aboutMenu;
    private JMenuItem openFile, saveFile;
    private JTextArea textArea = new JTextArea();
    private JScrollPane jScrollPane = new JScrollPane(textArea);
    private String currentPath = "";

    public static void main(String[] args){
        JFrame frame = new NotepadMain();
    }

    public NotepadMain(){
        super();
        
        this.setTitle("Untitled - Notepad--");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");

        jMenuBar.add(fileMenu);
        jMenuBar.add(aboutMenu);

        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");

        fileMenu.add(openFile);
        fileMenu.add(saveFile);

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int returnVal = jFileChooser.showDialog(null, "Open");
                if(returnVal == jFileChooser.APPROVE_OPTION) {
                    String filename = jFileChooser.getSelectedFile().getAbsolutePath();
                    openFile(filename);
                }
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentPath.equals("")) {
                    JFileChooser jFileChooser = new JFileChooser();
                    int returnVal = jFileChooser.showSaveDialog(null);
                    if (returnVal == jFileChooser.APPROVE_OPTION) {
                        String filename = jFileChooser.getSelectedFile().getAbsolutePath();
                        saveFile(filename);
                    }
                }else{
                    saveFile(currentPath);
                }
            }
        });

        aboutMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Written by @madooding for using in education only. Not for sale.",
                        "About Notepad--",JOptionPane.INFORMATION_MESSAGE );
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}

        });

        jScrollPane.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        
        this.setJMenuBar(jMenuBar);

        this.add(jScrollPane);
        
        this.setVisible(true);
        
        Dimension actualSize = this.getContentPane().getSize();
        jScrollPane.setBounds(0, 0, (int)Math.ceil(actualSize.getWidth()) + 1, (int)Math.ceil(actualSize.getHeight())+1);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension actualSize = this.getContentPane().getSize();
        jScrollPane.setBounds(0, 0, (int)Math.ceil(actualSize.getWidth()) + 1, (int)Math.ceil(actualSize.getHeight())+1);
    }

    private void saveFile(String filename){
        File file = new File(filename);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textArea.getText());
            bw.close();
            currentPath = filename;
            this.setTitle(filename + " - Notepad--");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile(String filename){
        File file = new File(filename);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = null;
            ArrayList<String> arr = new ArrayList<>();
            while((line = bf.readLine()) != null){
                arr.add(line);
            }
            textArea.setText(String.join("\n", arr));
            bf.close();
            currentPath = filename;
            this.setTitle(filename + " - Notepad--");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
