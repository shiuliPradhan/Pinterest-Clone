package com.infy.pinterest.service.content;

import java.util.List;

public interface TagService {
    void addTagToPin(Long pinId, String tagName);
    List<String> getTagsByPin(Long pinId);
    List<Long> getPinsByTag(String tag);
}
