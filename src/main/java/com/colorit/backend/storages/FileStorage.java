package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.StorageResponse;
import com.colorit.backend.storages.statuses.StorageStatus;
import com.colorit.backend.storages.storageimpls.CloudinaryStorage;
import com.colorit.backend.storages.storageimpls.LocalStorage;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;

@Component
public class FileStorage {
    private IStorage storage;
    private static final String USER_HOME = System.getProperty("os.name");

    @Autowired
    FileStorage(@Value("${heroku_var}") String herokuVar, @Value("${api_secret}") String apiSecret,
                @Value("${api_key}") String apiKey, @Value("${cloud_name}") String cloudName) {
        Map<String, String> envVars = System.getenv();
        if (herokuVar != null) {
            this.storage = new CloudinaryStorage(apiKey, apiSecret, cloudName);
        } else {
            this.storage = new LocalStorage(USER_HOME);
        }
    }


    /**
     * Crops image. Make it square (w == h).
     *
     * @param image - input image buffer.
     * @return new image buffer.
     */
    private BufferedImage cropImage(BufferedImage image) {
        Integer startX = 0, endX = image.getWidth();
        Integer startY = 0, endY = image.getHeight();
        Integer step = Math.abs(image.getHeight() - image.getWidth());
        if (step.equals(0)) {
            return image;
        }

        if (image.getHeight() > image.getWidth()) {
            startY += step / 2;
            endY -= step;
        } else {
            startX += step / 2;
            endX -= step;
        }

        BufferedImage img = image.getSubimage(startX, startY, endX, endY); //fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage;
    }


    /**
     * Checks file content by consisting data (for fututre).
     *
     * @param file - temp file which .
     * @return String - type of file
     * @throws IOException
     */
    private String getFileContent(File file) throws IOException {
        try (InputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is);) {
            AutoDetectParser parser = new AutoDetectParser();
            Detector detector = parser.getDetector();
            Metadata md = new Metadata();
            MediaType mediaType = detector.detect(bis, md);
            return mediaType.getType();
        }
    }


    /**
     * Saves user image. Before save checks file and crops it.
     *
     * @param multipartFile - user image.
     * @return StorageResponse.
     */
    public StorageResponse saveImage(MultipartFile multipartFile) {
        StorageResponse<String> response = new StorageResponse<>();
        try {
            if (!multipartFile.getContentType().contains("image")) {
                response.setStatus(StorageStatus.INCORRECT_FILE_TYPE_STATE);
                return response;
            }

            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            BufferedImage image = cropImage(ImageIO.read(multipartFile.getInputStream()));
            ImageIO.write(image, org.apache.commons.io.FilenameUtils.getExtension(file.getName()), file);

            String name = storage.writeFile(file);
            response.setStatus(StorageStatus.OK_STATE);
            response.setData(name);
        } catch (Exception ex) {
            response.setStatus(StorageStatus.SERVICE_ERROR_STATE);
        }
        return response;
    }


    /**
     * Save all type of fil.
     *
     * @param multipartFile - user file
     * @return StoreageResponse
     */
    public StorageResponse saveFile(MultipartFile multipartFile) {
        StorageResponse<String> response = new StorageResponse<>();
        try {
            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            multipartFile.transferTo(file);
            String name = storage.writeFile(file);
            response.setStatus(StorageStatus.OK_STATE);
            response.setData(name);
        } catch (Exception e) {
            response.setStatus(StorageStatus.ERROR_STATE);
        }
        return response;
    }
}
