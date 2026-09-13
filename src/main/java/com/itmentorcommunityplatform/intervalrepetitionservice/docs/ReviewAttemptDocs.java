package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ErrorDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ReviewAttemptDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Submit a review attempt",
        description = """
                Submits the user's answer quality for a question and updates
                the question's repetition schedule according to the SM-2 algorithm.

                The user is identified by the `X-Telegram-User-Id` request header.

                The `quality` value must be between 0 and 5.
                """
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Review attempt successfully submitted"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request body or missing/invalid X-Telegram-User-Id header",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Validation error",
                                        value = """
                                                {
                                                  "message": "Quality must be greater than or equal to 0"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing user ID header",
                                        value = """
                                                {
                                                  "message": "User ID header is required"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Question not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = @ExampleObject(
                                name = "Question not found",
                                value = """
                                        {
                                          "message": "Question with id 10000 not found"
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
@RequestBody(
        required = true,
        description = "Review attempt data",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReviewAttemptDto.class),
                examples = @ExampleObject(
                        name = "Review attempt",
                        value = """
                                {
                                  "questionId": 2,
                                  "quality": 5
                                }
                                """
                )
        )
)
public @interface ReviewAttemptDocs {

}
