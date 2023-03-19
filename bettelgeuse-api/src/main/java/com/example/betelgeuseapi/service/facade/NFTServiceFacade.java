package com.example.betelgeuseapi.service.facade;

import com.example.betelgeuseapi.entity.NftEntity;
import com.example.betelgeuseapi.model.InfuraUploadResponse;
import com.example.betelgeuseapi.model.Metadata;
import com.example.betelgeuseapi.service.CardProcessorService;
import com.example.betelgeuseapi.service.IpfsService;
import com.example.betelgeuseapi.service.SPService;
import com.example.betelgeuseapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class NFTServiceFacade {
    private final IpfsService ipfsService;
    private final CardProcessorService cardProcessorService;
    private final SPService spService;
    private final UserService userService;

    @SneakyThrows
    public static void convertInputStreamToFile(InputStream inputStream, File file) {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Creates NFT metadata by uploading an image to IPFS and generating metadata based on that image.
     *
     * @return An InfuraUploadResponse containing the hash of the uploaded image and other relevant information.
     */
    @SneakyThrows
    //dummy TODO
    public InfuraUploadResponse createMetadata(int tokenId, int level, String rankN, int xpx) {


        if (level != 0) {
            NftEntity entity = userService.getById((long) tokenId);
            String imageUrl;
            Integer rank = level;
            BigDecimal xp = BigDecimal.valueOf(xpx);
            InputStream inputStream = new ClassPathResource("static/card-template.png").getInputStream();

            BufferedImage image = ImageIO.read(inputStream);

            cardProcessorService.render(image, "test sp", level, rankN, BigInteger.valueOf(xpx));
            File outputfile = new File("test_sp_" + level + "png");
            ImageIO.write(image, "png", outputfile);
            InfuraUploadResponse infuraUploadResponse = ipfsService.uploadImage(outputfile);
            return ipfsService.updateMetadata(new Metadata(ipfsService.getImageUrl(infuraUploadResponse.getHash()),
                    rank, rankN, xp));
        }

        InputStream inputStream = new ClassPathResource("static/card-template.png").getInputStream();
        File file = new File("generated.png");
        convertInputStreamToFile(inputStream, file);
        InfuraUploadResponse infuraUploadResponse = ipfsService.uploadImage(file);
        return ipfsService.updateMetadata(new Metadata(ipfsService.getImageUrl(infuraUploadResponse.getHash()),
                0, "", new BigDecimal("0")));
    }


}
