package com.deu.marketplace.s3;

import com.deu.marketplace.web.itemImg.dto.ItemImgDto;
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
public class ItemImgUpdateRequestDto {
    private Long itemId;
    private List<ItemImgDto> delImgs = new ArrayList<>();
    private List<ItemImgDto> origImgs = new ArrayList<>();
    private List<MultipartFile> files = new ArrayList<>();
}
