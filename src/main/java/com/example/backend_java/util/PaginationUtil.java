package com.example.backend_java.util;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;

public final class PaginationUtil {

    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        StringBuilder link = new StringBuilder();
        if (page.hasPrevious()) {
            link.append(buildLink(uriBuilder, page.getNumber() - 1, page.getSize(), "prev")).append(",");
        }
        link.append(buildLink(uriBuilder, 0, page.getSize(), "first")).append(",");
        if (page.hasNext()) {
            link.append(buildLink(uriBuilder, page.getNumber() + 1, page.getSize(), "next")).append(",");
        }
        link.append(buildLink(uriBuilder, page.getTotalPages() - 1, page.getSize(), "last"));

        headers.add(HttpHeaders.LINK, link.toString());

        return headers;
    }

    private static String buildLink(UriComponentsBuilder uriBuilder, int page, int size, String rel) {
        String uri = uriBuilder
                .replaceQueryParam("page", page)
                .replaceQueryParam("size", size)
                .toUriString();

        return "<" + uri + ">; rel=\"" + rel + "\"";
    }
}