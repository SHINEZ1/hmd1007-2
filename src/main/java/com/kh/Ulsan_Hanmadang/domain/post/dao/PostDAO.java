package com.kh.Ulsan_Hanmadang.domain.post.dao;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.post.Post;
import com.kh.Ulsan_Hanmadang.domain.post.Reply;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
  /**
   * 원글작성
   * @param post
   * @return 게시글 번호
   */
  Long saveOrigin(Post post);

  /**
   * 목록
   * @return
   */
  List<Post> findAll();
  List<Post>  findAll(String category);

  List<Post>  findAll(int startRec, int endRec);
  List<Post>  findAll(String category, int startRec, int endRec);
  List<EventInfo> findAllEvents(int startRec, int endRec);
  /**
   * 홍보,후기 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Post>  findAll(PostFilterCondition filterCondition);

  /**
   * 이벤트검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<EventInfo> findAllEvents(PostFilterCondition filterCondition);

  /**
   * 상세 조회
   * @param id 게시글번호
   * @return
   */
  Post findByPostId(Long id);

  EventInfo findByEventId(Long id);

  FacInfo findByFacId(String id);
  /**
   * 삭제
   * @param id 게시글번호
   * @return 삭제건수
   */
  int deleteByPostId(Long id);

  /**
   * 수정
   * @param id 게시글 번호
   * @param bbs 수정내용
   * @return 수정건수
   */
  int updateByPostId(Long id, Post bbs);


  String facilityLink(String facid);

  /**
   * 댓글 작성
   * @param reply 댓글
   * @return 댓글번호
   */
  Long saveReply(Reply reply);

  /**
   * 댓글 수정
   * @param rid 댓글 번호
   * @return 댓글 내용
   */
  int updateReplyById(Long rid, String rcontent);
  /**
   * 댓글 조회
   * @param rid 댓글 번호
   * @return 댓글 정보
   */
  Optional<Reply> findReplyById(Long rid);

  /**
   * 댓글 삭제
   *
   * @param rid 댓글 번호
   * @return 삭제 건수
   */

  int deleteReplyById(Long rid);
  /**
   * 댓글 목록
   * @param postId 부모글
   * @return 댓글 그룹
   */
  List<Reply> findReplyByPostId(Long postId);
  /**
   * 홈화면에 디스플레이할 공연이미지 목록
   * @param date 현재 시간
   * @return 이미지 목록
   */
  List<PEvent> getDisplyEvents(String date);
  /**
   * 조회수증가
   * @param id 게시글번호
   * @return  수정건수
   */
  int increaseHitCount(Long id);

  /**
   * 좋아요증감
   * @param id 게시글번호
   * @return 수정건수
   */
  int increaseGoodCount(Long id);
  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
  int totalCount(String pcategory);
  int totalCount(PostFilterCondition filterCondition);

  int totalEventCount(PostFilterCondition filterCondition);

  /**
   * 전체건수
   * @return 등록 이벤트 건수
   */
  int totalPEventCount();

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  List<Post> myPost(String email);
}
