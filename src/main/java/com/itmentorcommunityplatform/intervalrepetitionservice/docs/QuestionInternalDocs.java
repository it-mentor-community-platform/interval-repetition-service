package com.itmentorcommunityplatform.intervalrepetitionservice.docs;


import com.itmentorcommunityplatform.intervalrepetitionservice.dto.QuestionRequestDto;
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
        summary = "Insert or update question (internal)",
        description = """
                Create or update a question from external sources (Google Spreadsheet).
                
                If a question with the same `title` already exists, its data will be updated.
                Otherwise, a new question will be created.
                
                Specialization and category will be created automatically if they do not exist.
                
                ### Example request body
                ```json
                {
                  "specialization": "Java",
                  "category": "Java Core",
                  "title": "Какие типы ссылок существуют в Java?",
                  "answer": "Сильная ссылка, мягкая ссылка, слабая ссылка..."
                }
                ```
                """
        ,
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = QuestionRequestDto.class),
                        examples = @ExampleObject(
                                name = "Question insert or update example",
                                value = """
                                        {
                                          "specialization": "Java",
                                          "category": "Java Core",
                                          "title": "Какие типы ссылок существуют в Java?",
                                          "answer": "Сильная ссылка, мягкая ссылка, слабая ссылка..."
                                        }
                                        """
                        )
                )
        )
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Question processed successfully"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                        schema = @Schema(
                                example = "{\"message\":\"Title must not be blank\"}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "An unexpected error occurred"
        )
})
public @interface QuestionInternalDocs {
}
