package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.CategoryWithQuestionsResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.ErrorDto;
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
        summary = "Get category with questions",
        description = """
                Returns a category with its questions for the current user.

                The response contains information about:

                - the category ID and name;
                - the specialization it belongs to;
                - the number of new questions;
                - the number of questions ready for repetition;
                - the total number of questions;
                - the list of questions belonging to the category.

                The user is identified by the `X-Telegram-User-Id` request header.

                The category is identified by the `categoryId` path parameter.

                ### Example response

                ```json
                {
                  "id": 4,
                  "name": "Java Core",
                  "specialization": "Java",
                  "new_questions": 15,
                  "questions_ready_to_repeat": 5,
                  "all_questions": 30,
                  "questions": [
                    {
                      "id": 101,
                      "title": "What is the difference between == and equals()?",
                      "answer": "The == operator compares primitive values or object references, while equals() compares object contents when overridden."
                    },
                    {
                      "id": 102,
                      "title": "What is String pool?",
                      "answer": "String pool is a special memory area where Java stores string literals to reuse identical strings."
                    }
                  ]
                }
                ```
                """
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Category with questions retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = CategoryWithQuestionsResponseDto.class
                        ),
                        examples = @ExampleObject(
                                name = "Category with questions response example",
                                value = """
                                        {
                                          "id": 4,
                                          "name": "Java Core",
                                          "specialization": "Java",
                                          "new_questions": 15,
                                          "questions_ready_to_repeat": 5,
                                          "all_questions": 30,
                                          "questions": [
                                            {
                                              "id": 101,
                                              "title": "What is the difference between == and equals()?",
                                              "answer": "The == operator compares primitive values or object references, while equals() compares object contents when overridden."
                                            },
                                            {
                                              "id": 102,
                                              "title": "What is String pool?",
                                              "answer": "String pool is a special memory area where Java stores string literals to reuse identical strings."
                                            }
                                          ]
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid or missing request parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Missing user ID header",
                                        value = """
                                                {
                                                  "message": "User ID header is required"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid category ID",
                                        value = """
                                                {
                                                  "message": "Invalid category ID"
                                                }
                                                """
                                )
                        }
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
                                          "message": "Category not found"
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
public @interface GetCategoryWithQuestionsDocs {

}
