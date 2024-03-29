package irlab.triplan.repository;

import irlab.triplan.entity.memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Repository
public interface memoRepository extends JpaRepository<memo, Integer> {
    @Query(nativeQuery = true, value = "select c.trip_id, c.classification_id, c.user_id, u.user_name, c.content, c.image_path, c.content_datetime, c.is_url, c.like_count, c.category,IF(l.user_id = :user_id, 1, 0) as is_like from classification c " +
            "inner join `user` u on u.user_id = c.user_id LEFT JOIN `like` l on (c.classification_id = l.classification_id and l.user_id= :user_id)" +
            "where c.trip_id = :trip_id")
    List<Map<String, Object>> getClass(Integer trip_id, Integer user_id);
    @Query(nativeQuery = true, value = "select user_id, image_path, is_url from `classification` where classification_id = :classification_id")
    Map<String, Object> preprocessing(Integer classification_id);
    @Query(nativeQuery = true, value = "insert into `classification` (trip_id, category, user_id, content, image_path, content_datetime, is_url, like_count) values (:trip_id, :category, :user_id, :content, :image_path, now(), 1, 0)")
    void classificationURL(Integer trip_id, String category, Integer user_id, String content, String image_path);
    @Query(nativeQuery = true, value = "insert into `classification` (trip_id, user_id, content, image_path, content_datetime, is_url, like_count, category) values (:trip_id, :user_id, :content, :image_path, now(), 0, 0, :category)")
    void createMemo(Integer trip_id, Integer user_id, String content, String image_path, String category);
    @Query(nativeQuery = true, value = "INSERT INTO `classification` (trip_id, user_id, image_path, content_datetime, is_url, like_count, category) VALUES (:trip_id, :user_id, :image_path, now(), 0, 0, :category)")
    void createMemo_only_file(Integer trip_id, Integer user_id, String image_path, String category);
    @Query(nativeQuery = true, value = "INSERT INTO `classification` (trip_id, user_id, content, content_datetime, is_url, like_count, category) VALUES (:trip_id, :user_id, :content, now(), 0, 0, :category)")
    void createMemo_only_content(Integer trip_id, Integer user_id, String content, String category);
    @Query(nativeQuery = true, value = "update `classification` set category = :category, content = :content, image_path = :image_path where classification_id = :classification_id")
    void editMemo(Integer classification_id, String category, String content, String image_path);
    @Query(nativeQuery = true, value = "UPDATE `classification` SET category = :category, content = :content WHERE classification_id = :classification_id")
    void editMemo_only_content(Integer classification_id, String category, String content);
    @Query(nativeQuery = true, value = "DELETE FROM `classification` WHERE classification_id = :classification_id")
    void deleteMemo(Integer classification_id);
    @Query(nativeQuery = true, value = "select count(*) as cnt from `like` l where l.classification_id = :classification_id and l.user_id = :user_id")
    Integer existsLike(Integer classification_id, Integer user_id);
    @Query(nativeQuery = true, value = "insert into `like` (classification_id, user_id) values (:classification_id, :user_id)")
    void createLike(Integer classification_id, Integer user_id);
    @Query(nativeQuery = true, value = "delete from `like` where classification_id = :classification_id and user_id = :user_id")
    void deleteLike(Integer classification_id, Integer user_id);
    @Query(nativeQuery = true, value = "update `classification` set like_count = like_count + :value where classification_Id = :classification_id")
    void updateLikeCnt(Integer classification_id, Integer value);
}
