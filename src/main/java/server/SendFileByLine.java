package server;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author yinchao
 * @date 2020/7/4 18:50
 */
@AllArgsConstructor
@Slf4j()
public class SendFileByLine implements Runnable {
    private String filePath;

    @Override
    public void run() {
        try {
            FileReader fileReader = new FileReader(filePath);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
