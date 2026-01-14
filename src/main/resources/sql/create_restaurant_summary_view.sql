CREATE OR REPLACE VIEW restaurant_summary_view AS
SELECT
    r.restaurant_id AS id,
    r.name,
    r.road_address,
    r.detail_address,
    r.avg_main_price AS price,
    (SELECT img.image_url 
     FROM restaurant_images img 
     WHERE img.restaurant_id = r.restaurant_id 
     ORDER BY img.restaurant_image_id ASC 
     LIMIT 1) AS image,
    COALESCE((SELECT AVG(rv.rating) 
              FROM reviews rv 
              WHERE rv.restaurant_id = r.restaurant_id AND rv.status = 'PUBLIC'), 0.0) AS rating,
    (SELECT COUNT(*) 
     FROM reviews rv 
     WHERE rv.restaurant_id = r.restaurant_id AND rv.status = 'PUBLIC') AS reviews,
    COALESCE(
        (SELECT MIN(st.content)
         FROM search_tags st
         JOIN restaurant_tag_maps rtm ON st.tag_id = rtm.tag_id
         WHERE rtm.restaurant_id = r.restaurant_id
           AND st.category = 'MENUTYPE'),
        '기타'
    ) AS category
FROM restaurants r
WHERE r.status = 'OPEN';
