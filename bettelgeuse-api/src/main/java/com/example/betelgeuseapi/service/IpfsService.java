package com.example.betelgeuseapi.service;

import com.example.betelgeuseapi.model.InfuraUploadResponse;
import com.example.betelgeuseapi.model.Metadata;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
@Slf4j
public class IpfsService {
    private RestTemplate restTemplate;
    private Gson gson;
    @Value("${ipfs.infura-project-id}")
    private String infuraProjectId;
    @Value("${ipfs.infura-project-secret}")
    private String infuraProjectSecret;

    @PostConstruct
    protected void init() {
        restTemplate = new RestTemplate();
        gson = new Gson();
    }

    /**
     * Uploads the specified image file to IPFS.
     *
     * @param file The image file to upload.
     * @return The response containing the uploaded file's name, hash and size.
     */
    public InfuraUploadResponse uploadImage(File file) {
        String url = "https://ipfs.infura.io:5001/api/v0/add";

        FileSystemResource fileSystemResource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(infuraProjectId, infuraProjectSecret);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileSystemResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, requestEntity, InfuraUploadResponse.class).getBody();
    }

    /**
     * Updates the metadata associated with the specified file on IPFS.
     *
     * @param metadata The metadata to update.
     * @return The response containing the uploaded file's name, hash and size.
     */
    public InfuraUploadResponse updateMetadata(Metadata metadata) {
        String jsonString = gson.toJson(metadata);

        String url = "https://ipfs.infura.io:5001/api/v0/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(infuraProjectId, infuraProjectSecret);
        ;

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(jsonString.getBytes()) {
            @Override
            public String getFilename() {
                return "metadata.json";
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, requestEntity, InfuraUploadResponse.class);
    }

    public String getImageUrl(String hash) {
        return "https://cloudflare-ipfs.com/ipfs/" + hash;
    }


}
