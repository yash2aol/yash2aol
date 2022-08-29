package org.uarizona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.uarizona.dto.FileUploadInfo;
import org.uarizona.dto.TextFilterResult;
import org.uarizona.service.TextFilter;
import org.uarizona.util.FileUtils;

import java.io.IOException;

@Component
@RestController
public class TextFilterController {

    @Autowired
    TextFilter textFilter;

    @PostMapping(value = "/run", consumes = "MediaType.MULTIPART_FORM_DATA", produces = "application/json")
    public ResponseEntity<FileUploadInfo> uploadFileToProcess(
            @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileCode = FileUtils.saveFile(fileName, file);
        FileUploadInfo response = new FileUploadInfo();
        response.setFileName(fileName);
        response.setDownloadUri("/getFile/" + fileCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getFile/{fileCode}")
    public ResponseEntity<?> getFilterResult(
            @PathVariable("fileCode") String fileCode) {

        Resource resource;
        TextFilterResult result = new TextFilterResult();
        try {
            resource = FileUtils.getFileResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        try {
            textFilter.setupBefore(resource.getFilename());
            result.setTotalWords(textFilter.wordCount());
            result.setTotalSentences(textFilter.sentenceCount());
            result.setNounCount(textFilter.numberOfNouns());
            result.setSentimentResult(textFilter.sentimentAnalysis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
