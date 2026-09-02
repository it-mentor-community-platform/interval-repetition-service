package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SavedSelectedCategoryResponseDto;
import com.itmentorcommunityplatform.intervalrepetitionservice.dto.SelectedCategoriesRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
        summary = "Save selected categories",
        description = """
                Saves categories selected by the user for spaced repetition.
                
                The user can add categories to their repetition set at any time.
                Each category must exist and must not already be selected by the user.
                
                The selected categories are stored in `user_category_selection`.
                
                ### Example request body
                ```json
                {
                  "categoryIds": [1, 2, 3]
                }
                ```
                """,
        parameters = {
                @Parameter(
                        name = "X-Telegram-User-Id",
                        description = "Telegram user identifier",
                        required = true,
                        in = ParameterIn.HEADER,
                        schema = @Schema(type = "integer", format = "int64"),
                        example = "4342423432"
                )
        },
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = SelectedCategoriesRequestDto.class
                        ),
                        examples = @ExampleObject(
                                name = "Save selected categories example",
                                value = """
                                        {
                                          "categoryIds": [1, 2, 3]
                                        }
                                        """
                        )
                )
        )
)
@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Selected categories saved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                type = "array",
                                implementation = SavedSelectedCategoryResponseDto.class
                        ),
                        examples = @ExampleObject(
                                name = "Saved categories response example",
                                value = """
                                        [
                                          {
                                            "id": 1,
                                            "name": "Java Core",
                                            "specialization": "Java Backend"
                                          },
                                          {
                                            "id": 2,
                                            "name": "Collections",
                                            "specialization": "Java Backend"
                                          },
                                          {
                                            "id": 3,
                                            "name": "OOP",
                                            "specialization": "Java Backend"
                                          }
                                        ]
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request or missing user identifier",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                example = "{\"message\":\"X-Telegram-User-Id header must not be null\"}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "One or more categories were not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                example = "{\"message\":\"Category with id 100 was not found\"}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "One or more categories have already been selected by the user",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                example = "{\"message\":\"Category with id 1 is already selected\"}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "An unexpected error occurred"
        )
})
public @interface SaveSelectedCategoriesDocs {
}
