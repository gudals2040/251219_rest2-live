package kr.java.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.java.restapi.model.dto.ErrorResponse;
import kr.java.restapi.model.dto.ItemCreateRequest;
import kr.java.restapi.model.dto.ItemResponse;
import kr.java.restapi.model.dto.ItemUpdateRequest;
import kr.java.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// #(1)
/**
 * 상품 REST API 컨트롤러
 *
 * URL 설계:
 * - POST   /api/items          : 생성
 * - GET    /api/items/{id}     : 단건 조회
 * - GET    /api/items          : 목록 조회
 * - PUT    /api/items/{id}     : 수정
 * - DELETE /api/items/{id}     : 삭제
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
// #(2)-2
//@CrossOrigin(origins = "*")
// #(2)-3
//@CrossOrigin(
//        origins = {
//        "http://127.0.0.1:5500",
//        "http://localhost:5500"
//        },
//        methods = {
//                RequestMethod.GET,
//                RequestMethod.POST,
//                RequestMethod.PUT,
//                RequestMethod.DELETE,
//        },
//        allowedHeaders = "*",
//        maxAge = 3600
//)
// #(3)-3-1
@Tag(name = "Item", description = "상품 관리 API")
public class ItemApiController {

    private final ItemService itemService;

    // CREATE: POST /api/items → 201 Created
    // #(3)-3-2
    @Operation(
            summary = "상품 생성",
            description = "새로운 상품을 등록합니다. 상품명과 가격은 필수입니다."
    )
    // #(3)-3-3
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "생성 성공",
            content = @Content(
                schema = @Schema(implementation = ItemResponse.class)
            )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검증 실패)",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
    })
    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody ItemCreateRequest request) {

        ItemResponse response = itemService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // READ: GET /api/items/{id} → 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        ItemResponse response = itemService.findById(id);
        return ResponseEntity.ok(response);
    }

    // READ: GET /api/items → 200 OK
    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll() {
        List<ItemResponse> responses = itemService.findAll();
        return ResponseEntity.ok(responses);
    }

    // UPDATE: PUT /api/items/{id} → 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemUpdateRequest request) {

        ItemResponse response = itemService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE: DELETE /api/items/{id} → 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}