package com.johnmartin.social.dto.response.common;

import java.util.List;

public record PagedResponse<T>(List<T> content,
                               int page,
                               int size,
                               long totalElements,
                               int totalPages,
                               boolean hasNext) {
}
