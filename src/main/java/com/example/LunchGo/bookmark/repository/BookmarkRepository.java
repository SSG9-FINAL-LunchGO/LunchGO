package com.example.LunchGo.bookmark.repository;

import com.example.LunchGo.bookmark.dto.SharedBookmarkItem;
import com.example.LunchGo.bookmark.entity.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /**
     * 사용자 즐겨찾기 삭제
     * */
    @Modifying
    @Query("DELETE FROM Bookmark b WHERE b.restaurantId = :restaurantId AND b.userId = :userId")
    int deleteByRestaurantIdAndUserId(Long restaurantId, Long userId);

    /**
     * 즐겨찾기 여부 확인
     * */
    boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);

    Optional<Bookmark> findTopByUserIdAndRestaurantIdOrderByBookmarkIdDesc(Long userId, Long restaurantId);

    @Query("""
        SELECT new com.example.LunchGo.bookmark.dto.SharedBookmarkItem(
            r.restaurantId, r.name, r.roadAddress, r.detailAddress
        )
        FROM Bookmark b
        JOIN Restaurant r ON b.restaurantId = r.restaurantId
        WHERE b.userId = :userId AND b.isPublic = true
        ORDER BY b.bookmarkId DESC
        """)
    List<SharedBookmarkItem> findPublicBookmarksWithRestaurant(Long userId);
}
