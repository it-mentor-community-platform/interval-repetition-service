package com.itmentorcommunityplatform.intervalrepetitionservice.docs;

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
        summary = "Delete selected category",
        description = """
                Removes a category from the current user's selected categories.

                After successful deletion, the category will no longer be used
                when selecting questions for interval repetition.

                The user is identified by the `X-Telegram-User-Id` request header.
                The category to remove is identified by the `categoryId` path variable.
                """
)
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Selected category deleted successfully"
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
                description = "Selected category not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDto.class),
                        examples = @ExampleObject(
                                name = "Category not found",
                                value = """
                                        {
                                          "message": "Selected category not found"
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
public @interface DeleteSelectedCategoryDocs {

}




