package com.example.LunchGo.bookmark.service;

import com.example.LunchGo.bookmark.dto.BookmarkInfo;
import com.example.LunchGo.bookmark.dto.BookmarkVisibilityRequest;
import com.example.LunchGo.bookmark.dto.SharedBookmarkItem;
import java.util.List;

public interface BookmarkService {
    void save(BookmarkInfo bookmarkInfo);

    void delete(BookmarkInfo bookmarkInfo);

    void updateVisibility(BookmarkVisibilityRequest request);

    List<SharedBookmarkItem> getSharedBookmarks(Long requesterId, Long targetUserId);
}
