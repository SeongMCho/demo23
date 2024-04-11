package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.thymeleaf.Thymeleaf;

import java.util.List;

@Getter
@ToString

public class PageResponseDTO<E> { //일단 타입에 제네릭
    
    //컨트롤러에서 보낼때 각종정보를 보내는 객체

    private int page;
    private int size;
    private int total;
    
    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    //  [이전] [1][2][3][4][5][6][7][8][9][10][다음]
    private boolean prev;
    //다음페이지의 존재여부
    //  [이전] [1][2][3][4][5][6][7][8][9][10][다음]
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <=0) {
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;

        this.dtoList = dtoList;


    //  [이전] [start][2][3][4][5][6][7][8][9][end][다음]
        this.end = (int)(Math.ceil(this.page /10.0 )) * 10;
        //화면에서 마지막번호

        this.start = this.end - 9; //화면의 시작번호

        int last = (int) (Math.ceil( ( total/(double)size )  ));

        this.end = end > last ? last : end;

        this.prev = this.start > 1;  //시작페이지의 T F

        this.next = total > this.end * this.size;


    }









    
}
