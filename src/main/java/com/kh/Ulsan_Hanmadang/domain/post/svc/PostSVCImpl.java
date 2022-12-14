package com.kh.Ulsan_Hanmadang.domain.post.svc;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.common.file.svc.UploadFileSVC;
import com.kh.Ulsan_Hanmadang.domain.post.Post;
import com.kh.Ulsan_Hanmadang.domain.post.Reply;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostDAO;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostFilterCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostSVCImpl implements PostSVC{

  private final PostDAO postDAO;
  private final UploadFileSVC uploadFileSVC;
  @Override
  public Long saveOrigin(Post post) {
    return postDAO.saveOrigin(post);
  }

  @Override
  public Long saveOrigin(Post post, List<MultipartFile> files) {
    //1)원글 저장
    Long postId = saveOrigin(post);

    //2)첨부 저장
    uploadFileSVC.addFile(post.getPcategory(),postId,files);

    return postId;
  }
  //목록
  @Override
  public List<Post> findAll() {
    return postDAO.findAll();
  }

  @Override
  public List<Post> findAll(int startRec, int endRec) {
    return postDAO.findAll(startRec, endRec);
  }

  @Override
  public List<Post> findAll(String category, int startRec, int endRec) {
    return postDAO.findAll(category, startRec, endRec);
  }

  @Override
  public List<Post> findAll(PostFilterCondition filterCondition) {
    return postDAO.findAll(filterCondition);
  }

  @Override
  public List<EventInfo> findAllEvents(PostFilterCondition filterCondition) {
    return postDAO.findAllEvents(filterCondition);
  }

  @Override
  public List<EventInfo> findAllEvents(int startRec, int endRec) {
    return postDAO.findAllEvents(startRec, endRec);
  }
  //상세조회
  @Override
  public Post findByPostId(Long id) {
    Post findedItem = postDAO.findByPostId(id);
    postDAO.increaseHitCount(id);
    return findedItem;
  }

  @Override
  public EventInfo findByEventId(Long id) {
    EventInfo finedEvent = postDAO.findByEventId(id);
    postDAO.increaseHitCount(id);
    return finedEvent;
  }

  @Override
  public FacInfo findByFacId(String id) {
    FacInfo finedFac = postDAO.findByFacId(id);
    return finedFac;
  }
  //삭제
  @Override
  public int deleteByPostId(Long id) {
    //1)첨부파일 삭제
    String bcategory = postDAO.findByPostId(id).getPcategory();
    uploadFileSVC.deleteFileByCodeWithRid(bcategory, id);

    //2)게시글 삭제
    int affectedRow =  postDAO.deleteByPostId(id);

    return affectedRow;
  }
  //수정
  @Override
  public int updateByPostId(Long id, Post post) {
    return postDAO.updateByPostId(id, post);
  }
  //수정-첨부파일
  @Override
  public int updateByPostId(Long id, Post post, List<MultipartFile> files) {
    //1)수정
    int affectedRow = updateByPostId(id, post);
    //2)첨부 저장
    uploadFileSVC.addFile(post.getPcategory(),id,files);
    return affectedRow;
  }

  //댓글
  @Override
  public Long saveReply(Reply reply) {
    return postDAO.saveReply(reply);
  }

  @Override
  public int updateReplyById(Long rid, String rcontent) {
    return postDAO.updateReplyById(rid, rcontent);
  }

  @Override
  public Optional<Reply> findReplyById(Long rid) {
    return postDAO.findReplyById(rid);
  }

  @Override
  public int deleteReplyById(Long rid) {
    return postDAO.deleteReplyById(rid);
  }

  @Override
  public List<Reply> findReplyByPostId(Long postId) {
    return postDAO.findReplyByPostId(postId);
  }

  @Override
  public List<PEvent> getDisplyEvents(String date) {
    return postDAO.getDisplyEvents(date);
  }

  //전체건수
  @Override
  public int totalCount() {
    return postDAO.totalCount();
  }

  @Override
  public int totalCount(String pcategory) {
    return postDAO.totalCount(pcategory);
  }

  @Override
  public int totalCount(PostFilterCondition filterCondition) {
    return postDAO.totalCount(filterCondition);
  }

  @Override
  public int totalPEventCount() {
    return postDAO.totalPEventCount();
  }

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  @Override
  public List<Post> myPost(String email) {
    return postDAO.myPost(email);
  }
}
