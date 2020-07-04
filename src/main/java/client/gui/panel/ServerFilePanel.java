package client.gui.panel;

import client.gui.MyGridBagConstraints;
import client.gui.table.LocalFileTable;
import client.util.GetFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * @author LvHao
 * @Description : 远程文件的遍历
 * @date 2020-07-03 2:09
 */
public class ServerFilePanel extends JPanel {
    public ServerFilePanel(){
        init();
    }

    private void init(){
        setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel("远程文件",JLabel.CENTER);
        JButton jButton1 = new JButton("下载");
        JButton jButton2 = new JButton("获取远程文件目录");
        JTextField jTextField = new JTextField("远程文件目录",10);
        jTextField.setEditable(false);
        JComboBox jComboBox = new JComboBox();
        File[] roots = File.listRoots();
        for(int i = 0;i < roots.length;i++){
            jComboBox.addItem(roots[i]);
        }
        String[][] datas = GetFiles.getFiles(jComboBox);
        String[] tableInfo = {"文件名","大小","日期"};
        DefaultTableModel model=new DefaultTableModel(null, tableInfo);
        JTable jTable = new LocalFileTable(model);
        //jTable.addMouseListener(new MouseClickedTiwceListener(jTable,jTextField,model));
        JScrollPane jScrollPane = new JScrollPane(jTable);
        //jComboBox.addItemListener(new LocalFileChange(jComboBox,model,jTable,jTextField));
        //jButton2.addActionListener(new Flush(jTable,jTextField,model,jComboBox));

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new GridBagLayout());

        GridBagConstraints gb1 = new GridBagConstraints();
        gb1.gridx = 0;
        gb1.gridy = 0;
        gb1.weightx= 1;
        gb1.weighty = 1;
        gb1.fill = GridBagConstraints.BOTH;

        GridBagConstraints gb2 = new GridBagConstraints();
        gb2.gridx = 1;
        gb2.gridy = 0;
        gb2.weightx= 1;
        gb2.weighty = 1;
        gb2.fill = GridBagConstraints.BOTH;

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 2;
        gc.gridy = 0;
        gc.weightx= 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;

        jPanel1.add(jButton1,new MyGridBagConstraints(0,0,1,1).init2());
        jPanel1.add(jButton2,new MyGridBagConstraints(1,0,1,1).init2());
//        jPanel1.add(jComboBox,new MyGridBagConstraints(2,0,1,1).init2());

        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 1;
        g1.gridheight = 1;
        g1.fill = GridBagConstraints.BOTH;
        add(jLabel,g1);

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 0;
        g2.gridy = 1;
        g2.gridwidth = 1;
        g2.gridheight = 1;
        g2.fill = GridBagConstraints.BOTH;
        add(jPanel1,g2);

        GridBagConstraints g3 = new GridBagConstraints();
        g3.gridx = 0;
        g3.gridy = 2;
        g3.weightx = 10;
        g3.weighty = 10;
        g3.fill = GridBagConstraints.BOTH;
        add(jScrollPane,g3);

        GridBagConstraints g4 = new GridBagConstraints();
        g4.gridx = 0;
        g4.gridy = 3;
        g4.gridwidth = 1;
        g4.gridheight = 1;
        g4.fill = GridBagConstraints.BOTH;
        add(jTextField,g4);
    }
}

