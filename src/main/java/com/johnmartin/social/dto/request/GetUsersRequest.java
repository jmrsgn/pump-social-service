package com.johnmartin.social.dto.request;

import java.util.List;

public record GetUsersRequest(List<String> userIds) {
}
