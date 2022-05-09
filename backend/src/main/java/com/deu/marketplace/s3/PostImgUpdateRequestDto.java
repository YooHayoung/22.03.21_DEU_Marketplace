package com.deu.marketplace.s3;

import com.deu.marketplace.web.postImg.dto.PostImgDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImgUpdateRequestDto {
    private Long postId;
    private List<PostImgDto> delImgs = new ArrayList<>();
    private List<PostImgDto> origImgs = new ArrayList<>();
    private List<MultipartFile> files = new ArrayList<>();
}
