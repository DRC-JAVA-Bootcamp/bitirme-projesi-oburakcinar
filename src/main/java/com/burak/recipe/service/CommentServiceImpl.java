package com.burak.recipe.service;

import com.burak.recipe.entity.Comment;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.repository.CommentRepository;
import com.burak.recipe.requestDto.comment.CommentSaveRequestDto;
import com.burak.recipe.responseDto.comment.CommentResponseDto;
import com.burak.recipe.util.AuthenticationInfoUtil;
import com.burak.recipe.util.converter.CommentConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements  CommentService{

    private final AuthenticationInfoUtil authInfoUtil;

    private final MemberService memberService;

    private final RecipeService recipeService;

    private final CommentConverter commentConverter;

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    @Override
    public CommentResponseDto save(CommentSaveRequestDto commentSaveRequestDto) {
        Comment comment = commentConverter.convertCommentSaveRequestDtoToComment(commentSaveRequestDto);
        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = modelMapper.map(commentSaveRequestDto, CommentResponseDto.class);
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setMemberId(comment.getMember().getId());
        return commentResponseDto;
    }


    @Override
    public Comment findByMemberIdAndRecipeId(long memberId, long recipeId) {
        Member member = memberService.findById(memberId);
        Recipe recipe = recipeService.findById(recipeId);

        Comment comment = commentRepository.findByMemberAndRecipe(member, recipe);

        if (comment == null) {
            throw new BadRequestException("No comment with member id " + memberId + " and recipe id " + recipeId + " is found");
        }

        return comment;
    }

    @Override
    public CommentResponseDto findCommentResponseDtoByMemberIdAndRecipeId(long memberId, long recipeId) {
        Comment comment = findByMemberIdAndRecipeId(memberId, recipeId);

        return commentConverter.convertFromCommentToCommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> findAllByRecipeId(long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        List<Comment> comments = commentRepository.findAllByRecipe(recipe);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            responseDtoList.add(commentConverter.convertFromCommentToCommentResponseDto(comment));
        }

        return  responseDtoList;
    }

    @Override
    public List<CommentResponseDto> findAllByMemberId(long memberId) {
        Member member = memberService.findById(memberId);
        List<Comment> comments = commentRepository.findAllByMember(member);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            responseDtoList.add(commentConverter.convertFromCommentToCommentResponseDto(comment));
        }

        return  responseDtoList;
    }

    @Override
    public CommentResponseDto deleteByMemberIdAndRecipeId(long memberId, long recipeId) {
        Comment comment = findByMemberIdAndRecipeId(memberId, recipeId);
        CommentResponseDto commentResponseDto= findCommentResponseDtoByMemberIdAndRecipeId(memberId, recipeId);

        commentRepository.delete(comment);

        return commentResponseDto;
    }
}
