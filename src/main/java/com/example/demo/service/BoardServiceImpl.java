package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardImg;
import com.example.demo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    //repository 주입
    private final ModelMapper modelMapper;
    //entity 와 dto간의 서로 변환작업을 위해서
    private final BoardImgService boardImgService;

    @Override
    public Long register(BoardDTO boardDTO) {
        //BoardDTO를 입력받아서 주입받은 boardRepository의
        //save()를 이용해서 저장을 한다.

        Board board = modelMapper.map(boardDTO, Board.class);
        //파라미터로 입력받은 boardDTO를 Board 객체로 변경
        //boardRepository.save(board); 변환한 board객체를 save(board)로 저장

        Long bno =  boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public Long register(BoardDTO boardDTO, List<MultipartFile> boardImgFileList) throws Exception{
        //BoardDTO를 입력받아서 주입받은 boardRepository의
        //save()를 이용해서 저장을 한다.

        Board board = modelMapper.map(boardDTO, Board.class);
        //파라미터로 입력받은 boardDTO를 Board 객체로 변경
        //boardRepository.save(board); 변환한 board객체를 save(board)로 저장

        Long bno =  boardRepository.save(board).getBno();

        //이미지 등록

        for (int i = 0; i < boardImgFileList.size(); i++) {
            BoardImg boardImg = new BoardImg();
            boardImg.setBoard(board);
            if(i== 0){
                boardImg.setRepimgYn("Y");
            }else {
                boardImg.setRepimgYn("N");
            }
            boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
        }



        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> board = boardRepository.findById(bno);

        Board board1 = board.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board1, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow(); //기존에 존재하던 값
        
        //수정될값
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(boardDTO.getWriter());

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<Board> boardList = result.getContent();
        log.info("서비스 임플에서 넘어온 boardList : " +boardList);

        List<BoardDTO> dtoList = boardList.stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        log.info("변환된 boardDTOList : " +dtoList);

        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO =
                new PageResponseDTO<>(pageRequestDTO,dtoList,(int)result.getTotalElements());
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
