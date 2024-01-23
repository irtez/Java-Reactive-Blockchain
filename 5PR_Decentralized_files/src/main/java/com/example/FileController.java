package com.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileRepository fileEntityRepository;

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        List<File> files = fileEntityRepository.findAll();
        model.addAttribute("files", files);
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filenameWithoutExtension) {
        System.out.println("Сюда пришел запрос");
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = filenameWithoutExtension + fileExtension;

                File fileEntity = new File();
                fileEntity.setFileName(newFilename);
                fileEntity.setFileData(file.getBytes());
                fileEntity.setFileSize(file.getSize());
                fileEntityRepository.save(fileEntity);
                return "redirect:/";
            } catch (IOException e) {
                log.error("Ошибка обработки файла", e);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> serveFile(@PathVariable Long id) {
        Optional<File> fileEntityOptional = fileEntityRepository.findById(id);
        if (fileEntityOptional.isPresent()) {
            File fileEntity = fileEntityOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"");
            return new ResponseEntity<>(fileEntity.getFileData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        fileEntityRepository.deleteById(id);
        return "redirect:/";
    }
}