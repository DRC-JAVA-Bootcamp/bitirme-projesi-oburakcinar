package com.burak.recipe.util.converter;

import com.burak.recipe.entity.Comment;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.requestDto.comment.CommentSaveRequestDto;
import com.burak.recipe.responseDto.comment.CommentResponseDto;
import com.burak.recipe.service.RecipeService;
import com.burak.recipe.util.AuthenticationInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final AuthenticationInfoUtil authenticationInfoUtil;

    private final MemberRepository memberRepository;

    private final RecipeService recipeService;


    public Comment convertCommentSaveRequestDtoToComment(CommentSaveRequestDto commentSaveRequestDto) {
        //create empty comment object
        Comment comment = new Comment();

        //get member
        String userName = authenticationInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(userName);

        //get recipe
        long recipeId = commentSaveRequestDto.getRecipeId();
        Recipe recipe = recipeService.findById(recipeId);

        //get text
        String text = commentSaveRequestDto.getText();

        //set all fields
        comment.setMember(member);
        comment.setRecipe(recipe);
        comment.setText(text);

        return comment;
    }


    public CommentResponseDto convertFromCommentToCommentResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setId(comment.getId());
        commentResponseDto.setMemberId(comment.getMember().getId());
        commentResponseDto.setRecipeId(comment.getRecipe().getId());
        commentResponseDto.setText(comment.getText());

        return commentResponseDto;
    }

}
