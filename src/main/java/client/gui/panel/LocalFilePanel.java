package client.gui.panel;

import client.action.Flush;
import client.action.LocalFileChange;
import client.action.MouseClickedTwiceListener;
import client.gui.table.LocalFileTable;
import client.util.GetFiles;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * @author LvHao
 * @Description : 本地文件遍历的实现
 * @date 2020-07-03 2:01
 */
@Data
public class LocalFilePanel extends JPanel {

    private JLabel jLabel = new JLabel("本地文件",JLabel.CENTER);
    private JButton jButton1 = new JButton(" 上传 ");
    private JButton jButton2 = new JButton("   刷新  ");
    private JTextField jTextField = new JTextField(" ",10);
    private JComboBox jComboBox;
    private JTable jTable;
    private final String[] tableInfo = {"文件名","大小","日期"};
    private String[][] data = null;

    public LocalFilePanel(){
        init();
    }

    private void init(){
        setLayout(new GridBagLayout());

        jTextField.setEditable(false);
        jComboBox = new JComboBox();
        File[] roots = File.listRoots();
        for(int i = 0;i < roots.length;i++){
            jComboBox.addItem(roots[i]);
        }
        jTextField.setText(String.valueOf(jComboBox.getSelectedItem()));
        data = GetFiles.getFiles(jComboBox);
        DefaultTableModel model=new DefaultTableModel(data, tableInfo);
        jTable = new LocalFileTable(model);
        jTable.addMouseListener(new MouseClickedTwiceListener(this,model));
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jComboBox.addItemListener(new LocalFileChange(this,model));
        jButton2.addActionListener(new Flush(this,model));

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

        jPanel1.add(jButton1,gb1);
        jPanel1.add(jButton2,gb2);
        jPanel1.add(jComboBox,gc);

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

