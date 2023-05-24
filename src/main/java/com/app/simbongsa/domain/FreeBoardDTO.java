package com.app.simbongsa.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@NoArgsConstructor
public class FreeBoardDTO {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer replyCount;
    private MemberDTO memberDTO;
    private List<FileDTO> fileDTOS;

//    public FreeBoardDTO(){this.fileDTOS = new ArrayList<>();}

    @Builder
    public FreeBoardDTO(Long id, String boardTitle, String boardContent, LocalDateTime createdDate, LocalDateTime updatedDate, Integer replyCount, MemberDTO memberDTO, List<FileDTO> fileDTOS) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.replyCount = replyCount;
        this.memberDTO = memberDTO;
        this.fileDTOS = fileDTOS;
    }
}
