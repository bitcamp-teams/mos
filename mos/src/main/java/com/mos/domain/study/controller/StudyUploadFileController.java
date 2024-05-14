//package com.mos.domain.study.controller;
//
//import com.mos.global.storage.service.StorageService;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/toast-editor")
//public class StudyUploadFileController implements InitializingBean {
//
//    private final StorageService storageService;
//    private String uploadDir;
//
//    @Value("${ncp.ss.bucketname}")
//    private String bucketName;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        this.uploadDir = "study/";
//
//        log.debug("uploadDir ={}", this.uploadDir);
//        log.debug("bucketname ={}", this.bucketName);
//    }
//    /**
//     * 에디터 이미지 업로드
//     * @param image 파일 객체
//     * @return 업로드된 파일명
//     */
//    @PostMapping("/image-upload")
//    public String uploadEditorImage(@RequestParam final MultipartFile image) throws Exception {
//        if (image.isEmpty()) {
//            return "";
//        }
//
//        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
//        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
//        String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
//        String fileFullPath = storageService.upload(this.bucketName, this.uploadDir, image);                      // 디스크에 저장할 파일의 전체 경로
//
//        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
//        File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        try {
//            // 파일 저장 (write to disk)
//            File uploadFile = new File(fileFullPath);
//            image.transferTo(uploadFile);
//            return saveFilename;
//
//        } catch (IOException e) {
//            // 예외 처리는 따로 해주는 게 좋습니다.
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 디스크에 업로드된 파일을 byte[]로 반환
//     * @param filename 디스크에 업로드된 파일명
//     * @return image byte array
//     */
//    @GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
//    public byte[] printEditorImage(@RequestParam final String filename) {
//        // 업로드된 파일의 전체 경로
//        String fileFullPath = Paths.get(uploadDir, filename).toString();
//
//        // 파일이 없는 경우 예외 throw
//        File uploadedFile = new File(fileFullPath);
//        if (!uploadedFile.exists()) {
//            throw new RuntimeException();
//        }
//
//        try {
//            // 이미지 파일을 byte[]로 변환 후 반환
//            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
//            return imageBytes;
//
//        } catch (IOException e) {
//            // 예외 처리는 따로 해주는 게 좋습니다.
//            throw new RuntimeException(e);
//        }
//    }
//}
