package com.burak.recipe.controller;

import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.like.LikeRequestDto;
import com.burak.recipe.responseDto.like.LikeResponseDto;
import com.burak.recipe.service.LikeService;
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
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/recipeLikes")
    public ResponseEntity<List<LikeResponseDto>> getRecipeLikes(@RequestParam("recipeId") long recipeId) {

        List<LikeResponseDto> likeResponseDtoList = likeService.findAllByRecipeId(recipeId);

        return new ResponseEntity<>(likeResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/memberLikes")
    public ResponseEntity<List<LikeResponseDto>> getMemberLikes(@RequestParam("memberId") long memberId) {

        List<LikeResponseDto> likeResponseDtoList = likeService.findAllByMemberId(memberId);

        return new ResponseEntity<>(likeResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/likes")
    public ResponseEntity<LikeResponseDto> likeRecipe(@Valid @RequestBody LikeRequestDto likeSaveRequestDto,
                                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }

        LikeResponseDto likeResponseDto = likeService.save(likeSaveRequestDto);

        return new ResponseEntity<>(likeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/likes")
    public ResponseEntity<LikeResponseDto> deleteLikeRecipe(@RequestParam("recipeId") long recipeId) {

        LikeResponseDto likeResponseDto = likeService.delete(recipeId);

        return new ResponseEntity<>(likeResponseDto, HttpStatus.OK);
    }
}
