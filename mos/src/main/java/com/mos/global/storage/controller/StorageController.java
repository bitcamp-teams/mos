package com.mos.global.storage.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.study.dto.AttachedFileDto;
import com.mos.global.auth.LoginUser;
import com.mos.global.storage.service.StorageService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/storage")
public class StorageController implements InitializingBean {
    private final StorageService storageService;
    private String uploadDir;

    @Value("${ncp.ss.bucketname}")
    private String bucketName;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.uploadDir = "study/";

        log.debug("uploadDir ={}", this.uploadDir);
        log.debug("bucketname ={}", this.bucketName);
    }

//    @GetMapping("file/delete")
//    public String fileDelete(@LoginUser MemberDto loginUser, int fileNo) throws Exception {
//
//        if (loginUser == null) {
//            throw new Exception("로그인하시기 바랍니다!");
//        }
//
//        AttachedFileDto file = studyService.getAttachedFile(fileNo);
//        if (file == null) {
//            throw new Exception("첨부파일 번호가 유효하지 않습니다.");
//        }
//
//        int writerNo = studyService.getByStudyNo(file.getStudyNo()).getMemberNo();
//        if (writerNo != loginUser.getMemberNo()) {
//            throw new Exception("권한이 없습니다.");
//        }
//
//        studyService.deleteAttachedFile(fileNo);
//
//        storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());
//
//        return "redirect:/study/view?no=" + file.getStudyNo();
//    }


    @PostMapping("file/upload")
    @ResponseBody
    public Object fileUpload(@LoginUser MemberDto loginUser, MultipartFile[] files, Model model)
        throws Exception {
        // NCP Object Storage에 저장한 파일의 이미지 이름을 보관할 컬렉션을 준비한다.
        ArrayList<AttachedFileDto> attachedFiles = new ArrayList<>();

        if (loginUser == null) {
            // 로그인 하지 않았으면 빈 목록을 보낸다.
            return attachedFiles;
        }

        // 클라이언트가 보낸 멀티파트 파일을 NCP Object Storage에 업로드한다.
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            String filename = storageService.upload(this.bucketName, this.uploadDir, file);
            attachedFiles.add(AttachedFileDto.builder().filePath(filename).build());
        }

        // 업로드한 파일 목록을 세션에 보관한다.
        model.addAttribute("attachedFiles", attachedFiles);

        // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
        // 업로드한 파일의 이미지 정보를 보낸다.
        return attachedFiles;
    }

}
