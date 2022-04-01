package com.clone.finalProject.service;

import com.clone.finalProject.domain.*;
import com.clone.finalProject.dto.postDto.PostRequestDto;
import com.clone.finalProject.dto.postDto.PostResponseDto;
import com.clone.finalProject.exceptionHandler.CustomException;
import com.clone.finalProject.exceptionHandler.ErrorCode;
import com.clone.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final TagsRepository tagsRepository;
    private final PostTagsRepository postTagsRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final AlarmRepository alarmRepository;

    // post 생성
    @Transactional
    public Long createPost(PostRequestDto postRequestDto, User user) {

        if(postRequestDto.getCategory().equals("Javascript")) {
            postRequestDto.setCategory("자바스크립트");
        } else if (postRequestDto.getCategory().equals("C#")){
            postRequestDto.setCategory("씨샵");
        }

        Post post = new Post(postRequestDto, user);

        //태그 확인 후 태그 생성
        if(postRequestDto.getTags() == null){
            log.info("게시판 태그 없음");
        }else {
            creatTags(postRequestDto, post);
        }

        postRepository.save(post);
        log.info("포스트 생성 완료 포스트 타이틀 : {}",  postRequestDto.getPostTitle());
        return post.getPid();
    }

    //post 삭제
    @Transactional
    public void deletePost (Long pid, Long uid) {

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        if (post.getUser().getUid() == uid) {

            //댓글 및 대댓글 추가 시 포스트 밑에 있는 댓글 , 대댓글 부터 삭제 해야 함
            commentRepository.deleteAllByPid(pid);

            answerLikeRepository.deleteByPid(pid);

            answerRepository.deleteAllByPost_pid(pid);

            postTagsRepository.deleteAllByPost_Pid(pid);

            postLikeRepository.deleteAllByPost_pid(pid);

            alarmRepository.deleteAllByPid(pid);

            postRepository.deleteById(pid);
            log.info("포스트 삭제 완료 pid : {}",  pid);
        }

    }

    //post 수정
    @Transactional
    public void editPost(Long pid, PostRequestDto postRequestDto, Long uid) {

        if(postRequestDto.getCategory().equals("Javascript")) {
            postRequestDto.setCategory("자바스크립트");
        } else if (postRequestDto.getCategory().equals("C#")){
            postRequestDto.setCategory("씨샵");
        }

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_POST)
        );

        if (post.getUser().getUid() == uid) {

            postTagsRepository.deleteAllByPost_Pid(pid);
            log.info("포스트 수정전 postTags 전부 삭제");

            creatTags(postRequestDto, post);

            post.update(postRequestDto);
            log.info("포스트 수정 완료 pid :{}", pid);

        }
    }

    //post 상세 조회
    public PostResponseDto getPostDetail(Long pid) {

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_POST)
        );

        // 게시글 조회용 메소드
        PostResponseDto postResponseDto = getPostMethod(post);

        if(postResponseDto.getCategory().equals("자바스크립트")) {
            postResponseDto.setCategory("Javascript");
        } else if (postResponseDto.getCategory().equals("씨샵")){
            postResponseDto.setCategory("C#");
        }

        return postResponseDto;
    }

    // 게시글 조회용에 사용되는 메소드
    public PostResponseDto getPostMethod(Post post) {
        User user = post.getUser();

        List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(post.getPid());
        Long postLikeCount = Long.valueOf(postLikes.size());

        //태그 추가
        List<PostTags> postTagsList = postTagsRepository.findAllByPost_Pid(post.getPid());
        List<String> tags = new ArrayList<>();
        for (PostTags postTags : postTagsList) {
            String tag = postTags.getTags().getTagName();
            tags.add(tag);
        }


        PostResponseDto postResponseDto = new PostResponseDto(post, user,postLikeCount,tags);
        return postResponseDto;
    }


    // 태그 및 포스트태그 생성 메소드
    public void creatTags(PostRequestDto requestDto, Post post) {

        List<Tags>tagsList = new ArrayList<>();

        for(int i = 0; i < requestDto.getTags().size(); i++){
            String tagName = requestDto.getTags().get(i);
            Tags tag = new Tags();

            if (tagsRepository.findByTagName(tagName).isPresent()) {
                tag = tagsRepository.findByTagName(tagName).orElseThrow(
                        ()-> new CustomException(ErrorCode.NOT_FOUND_TAG)
                );

            } else {
                tag.setTagName(tagName);
                tagsRepository.save(tag);
                log.info("tag 없어서 객체 새로 생성 tagName : {}",  tagName);
            }
            tagsList.add(tag);
        }
        for(Tags tags : tagsList) {
            creatPostTags(post, tags);
        }

    }

    // 태그 및 포스트태그 생성 메소드
    public void creatPostTags(Post post, Tags tag) {
        PostTags postTags = new PostTags(post, tag);
        postTagsRepository.save(postTags);
        log.info("postTags 객체 생성");
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // post 조회 (답변채택)
    public List<PostResponseDto> getPostCheck(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post> postList = postRepository.findAllByStatusContaining(pageable,"selection");
        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : postList) {
            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = getPostMethod(post);

            if(postResponseDto.getCategory().equals("자바스크립트")) {
                postResponseDto.setCategory("Javascript");
            } else if (postResponseDto.getCategory().equals("씨샵")){
                postResponseDto.setCategory("C#");
            }

            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;
    }

//  post 조회 (답변대기)
    public List<PostResponseDto> getPostWait(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post> postList = postRepository.findAllByStatusContains(pageable,"noCheck");

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {

            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = getPostMethod(post);

            if(postResponseDto.getCategory().equals("자바스크립트")) {
                postResponseDto.setCategory("Javascript");
            } else if (postResponseDto.getCategory().equals("씨샵")){
                postResponseDto.setCategory("C#");
            }

            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }

    //게시글 타이틀 검색하여 조회
    public List<PostResponseDto> getPostTitle(String postTitle, int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post> postList = postRepository.findAllByPostTitleContaining(pageable,postTitle);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {

            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = getPostMethod(post);

            if(postResponseDto.getCategory().equals("자바스크립트")) {
                postResponseDto.setCategory("Javascript");
            } else if (postResponseDto.getCategory().equals("씨샵")){
                postResponseDto.setCategory("C#");
            }

            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;

    }

    //카게고리로 검색하여 게시글 조회
    public List<PostResponseDto>getPostCategory(String category, int page, int size, String sortBy, boolean isAsc) {

        if(category.equals("Javascript")) {
            category ="자바스크립트";
        } else if (category.equals("C")) {
            category ="씨샵";
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post> postList = postRepository.findAllByCategoryContaining(pageable,category);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {

            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = getPostMethod(post);

            if(postResponseDto.getCategory().equals("자바스크립트")) {
                postResponseDto.setCategory("Javascript");
            } else if (postResponseDto.getCategory().equals("씨샵")){
                postResponseDto.setCategory("C#");
            }

            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;

    }


    // post 관심글 조회
    public List<PostResponseDto> getPostLike(Long uid, int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PostLike>postLikeList = postLikeRepository.findAllByUser_Uid(pageable,uid);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (PostLike postLike : postLikeList) {
            Post post = postLike.getPost();
            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = getPostMethod(post);

            if(postResponseDto.getCategory().equals("자바스크립트")) {
                postResponseDto.setCategory("Javascript");
            } else if (postResponseDto.getCategory().equals("씨샵")){
                postResponseDto.setCategory("C#");
            }

            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;

    }

}

