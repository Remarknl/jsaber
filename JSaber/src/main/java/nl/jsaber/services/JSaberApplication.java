package nl.jsaber.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@EnableAutoConfiguration
public class JSaberApplication {

    @RequestMapping("/testappsService")
    String testAppsService(@RequestParam("direction") String direction) {
        byte[] data = ("testappDirection:" + direction).getBytes();
        clientSocket(data);
        
        String Json = new JSONObject().put("direction", direction).toString();
        return Json;
    }

    @RequestMapping("/gCodeService")
    String gCodeService(@RequestParam("file") MultipartFile file) {

        uploadGcodeFile(file);
        String fileName = file.getOriginalFilename();

        URL path = getClass().getProtectionDomain().getCodeSource().getLocation();
        System.out.println("path: " + path);

        StringBuilder b = new StringBuilder(path.toString());
        b.delete((path.toString()).length() - 23, (path.toString()).length());
        byte[] data = ("gcodeFilePath:" + b + "/gcodefiles/" + fileName).getBytes();

        clientSocket(data);
        
        String Json = new JSONObject().put("uploaded", "You successfully uploaded " + file.getOriginalFilename()+"!").toString();
        return Json;       
    }

    @RequestMapping("/calibrateService")
    String calibrateService() {

        return "calibrateService";
    }

    public static void main(String[] args) {

        SpringApplication.run(JSaberApplication.class, args);

    }

    private void uploadGcodeFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream
                        = new BufferedOutputStream(new FileOutputStream(new File("../gcodefiles/" + file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                System.out.println("You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (Exception e) {
                System.out.println("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            System.out.println("You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
        }
    }

    private void clientSocket(byte[] data) {
        //client socket code
        OutputStream os = null;
        SocketConnection sc = null;
        try {
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   --> has got to be localhost when running on raspberry pi
            sc = (SocketConnection) Connector.open("socket://192.168.0.106:64111");
            //timeout om ervoor te zorgen dat de socket voldoende tijd heeft om te connecten
            try {
                Thread.sleep(2000);//2 seconds
            } catch (InterruptedException ex) {
                Logger.getLogger(JSaberApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            os = sc.openOutputStream();

            os.write(data);

        } catch (IOException x) {
            x.printStackTrace();

        } finally {
            try {
                sc.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(JSaberApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
