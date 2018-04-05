package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.StorageResponse;
import com.colorit.backend.storages.statuses.StorageStatus;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

@Component
public class FileStorage {
    private final IStorage storage;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorage.class);

    FileStorage(@NotNull IStorage storage) {
        this.storage = storage;
        LOGGER.info("Created storage {}", storage.getClass());
    }

    /**
     * Crops image. Make it square (w == h).
     *
     * @param image - input image buffer.
     * @return new image buffer.
     */
    private BufferedImage cropImage(BufferedImage image) {
        Integer endX = image.getWidth();
        Integer endY = image.getHeight();
        final Integer step = Math.abs(image.getHeight() - image.getWidth());

        if (step.equals(0)) {
            return image;
        }

        Integer startX = 0;
        Integer startY = 0;
        if (image.getHeight() > image.getWidth()) {
            startY += step / 2;
            endY -= step;
        } else {
            startX += step / 2;
            endX -= step;
        }

        //fill in the corners of the desired crop location here
        final BufferedImage subImage = image.getSubimage(startX, startY, endX, endY);
        final BufferedImage copyOfImage = new BufferedImage(subImage.getWidth(), subImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics graphics = copyOfImage.createGraphics();
        graphics.drawImage(subImage, 0, 0, null);
        return subImage;
    }


    /**
     * Saves user image. Before save checks file and crops it.
     *
     * @param multipartFile - user image.
     * @return StorageResponse.
     */
    public StorageResponse saveImage(MultipartFile multipartFile) {
        final StorageResponse<String> response = new StorageResponse<>();
        try {
            if (!multipartFile.getContentType().contains("image")) {
                response.setStatus(StorageStatus.INCORRECT_FILE_TYPE_STATE);
                return response;
            }

            final File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            final BufferedImage image = cropImage(ImageIO.read(multipartFile.getInputStream()));
            ImageIO.write(image, FilenameUtils.getExtension(file.getName()), file);

            final String name = storage.writeFile(file);
            response.setStatus(StorageStatus.OK_STATE);
            response.setData(name);
        } catch (Exception ex) {
            response.setStatus(StorageStatus.SERVICE_ERROR_STATE);
        }
        return response;
    }
}
