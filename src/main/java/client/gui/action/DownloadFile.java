package client.gui.action;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 23:12
 */
@Data
@RequiredArgsConstructor
public class DownloadFile implements ActionListener {

    @NonNull
    private ClientFrame clientFrame;

    @NonNull
    private ServerFilePanel serverFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        String[][] serverFile = {
                {"asdsad","asdsad","Asdsadsa"},
                {"asdsad","sadasd","asdsadsads"}
        };
    }
}
