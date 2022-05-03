package com.burak.recipe.controller;

import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.comment.CommentSaveRequestDto;
import com.burak.recipe.responseDto.comment.CommentResponseDto;
import com.burak.recipe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment")
    public ResponseEntity<CommentResponseDto> getCommentByMemberIdAndRecipeId(@RequestParam("memberId") long memberId,
                                                                               @RequestParam("recipeId") long recipeId) {
        CommentResponseDto commentResponseDto = commentService.findCommentResponseDtoByMemberIdAndRecipeId(memberId, recipeId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/recipeComments")
    public ResponseEntity<List<CommentResponseDto>> findCommendsByRecipeId(@RequestParam("recipeId") long recipeId) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByRecipeId(recipeId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/memberComments")
    public ResponseEntity<List<CommentResponseDto>> findCommendsByMemberId(@RequestParam("memberId") long memberId) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByMemberId(memberId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> postComment(@Valid @RequestBody CommentSaveRequestDto commentSaveRequestDto,
                                                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }

        CommentResponseDto commentResponseDto = commentService.save(commentSaveRequestDto);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/comments")
    public ResponseEntity<CommentResponseDto> deleteCommentByMemberAndRecipeId(@RequestParam("memberId") long memberId,
                                                                               @RequestParam("recipeId") long recipeId) {
        CommentResponseDto commentResponseDto = commentService.deleteByMemberIdAndRecipeId(memberId, recipeId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

}
