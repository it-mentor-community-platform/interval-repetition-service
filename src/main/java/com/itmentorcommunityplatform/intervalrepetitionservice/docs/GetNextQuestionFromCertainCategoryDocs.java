package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ErrorDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.NextQuestionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get next question from a specific category",
        description = """
                Returns the next available question from the specified category.

                The user is identified by the `X-Telegram-User-Id` request header.

                If there are no new or ready-to-repeat questions in the category,
                the service returns `204 No Content`.
                """
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Next question found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = NextQuestionResponseDto.class),
                        examples = @ExampleObject(
                                name = "Next question",
                                value = """
                                        {
                                          "questionId": 123,
                                          "categoryId": 2,
                                          "title": "Как устроен HashMap в Java?",
                                          "answer": "Бакеты...",
                                          "questions_left": 32
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "204",
                description = "No questions available for repetition"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid or missing X-Telegram-User-Id header",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = @ExampleObject(
                                name = "Missing user ID header",
                                value = """
                                        {
                                          "message": "User ID header is required"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Category not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = @ExampleObject(
                                name = "Category not found",
                                value = """
                                        {
                                          "message": "Category with id 101 not found"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "An unexpected error occurred",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class)
                )
        )
})
public @interface GetNextQuestionFromCertainCategoryDocs {

}
