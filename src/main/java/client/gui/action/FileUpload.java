package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetTaskFilePath;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 19:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j(topic = "FileUpload")
public class FileUpload implements ActionListener {

    private JTable jTable;
    private String filePath;

    @NonNull
    private LocalFilePanel localFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        jTable = localFilePanel.getJTable();
        filePath = localFilePanel.getJTextField().getText();
        String[] fileNames = GetTaskFilePath.getUploadName(jTable,filePath);

        for(int i = 0;i < fileNames.length;i++){
            log.info("请求上传文件名：" + fileNames[i]);
        }
    }
}
