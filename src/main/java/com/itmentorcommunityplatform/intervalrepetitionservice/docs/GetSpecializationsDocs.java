package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ErrorDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SpecializationResponseDto;
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
        summary = "Get all specializations with categories",
        description = """
                Returns all available specializations with their categories for the current user.

                            Each category contains information about:
                            
                            - whether the category is selected by the user;
                            - the number of new questions;
                            - the number of questions ready for repetition;
                            - the total number of questions.
                            
                            The user is identified by the `X-Telegram-User-Id` request header.
                            
                            ### Example response
                            
                            ```json
                            [
                              {
                                "id": 1,
                                "name": "Java",
                                "categories": [
                                  {
                                    "id": 10,
                                    "name": "Java Core",
                                    "selected": true,
                                    "new_questions": 15,
                                    "questions_ready_to_repeat": 5,
                                    "all_questions": 30
                                  },
                                  {
                                    "id": 11,
                                    "name": "Collections",
                                    "selected": false,
                                    "new_questions": 8,
                                    "questions_ready_to_repeat": 10,
                                    "all_questions": 25
                                  }
                                ]
                              }
                            ]
                            ```
                            """

)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Specializations retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = SpecializationResponseDto.class
                        ),
                        examples = @ExampleObject(
                                name = "Specializations response example",
                                value = """
                                        [
                                            {
                                                "id": 1,
                                                "name": "Java",
                                                "categories": [
                                                    {
                                                        "id": 10,
                                                        "name": "Java Core",
                                                        "selected": true,
                                                        "new_questions": 15,
                                                        "questions_ready_to_repeat": 5,
                                                        "all_questions": 30
                                                    }
                                                ]
                                            }
                                        ]
                                        """
                        )
                )
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
                responseCode = "500",
                description = "An unexpected error occurred",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class)
                )
        )
})
public @interface GetSpecializationsDocs {

}

