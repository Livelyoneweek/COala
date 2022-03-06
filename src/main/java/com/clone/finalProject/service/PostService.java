package com.clone.finalProject.service;

import com.clone.finalProject.domain.*;
import com.clone.finalProject.dto.PostRequestDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final TagsRepository tagsRepository;
    private final PostTagsRepository postTagsRepository;



    // post 생성
    @Transactional
    public Long postCreate(PostRequestDto postRequestDto, User user) {

        Post post = new Post(postRequestDto, user);

        //태그 확인 후 태그 생성

        if(postRequestDto.getTags() == null){
            System.out.println("게시판 태그 없음");
        }else {
            creatTags(postRequestDto, post);
        }

        postRepository.save(post);
        System.out.println("포스트 생성 완료 포스트 타이틀 : " + postRequestDto.getPostTitle());
        return post.getPid();
    }


    // post 조회 (답변채택)
    public List<PostResponseDto> postGet() {
        List<Post>postList = postRepository.findAllByStatusContainingOrderByCreatedAtDesc("check");

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            Long uid = post.getUser().getUid();

            List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(post.getPid());
            Long postLikeCount = Long.valueOf(postLikes.size());

            //태그 추가
            List<PostTags> postTagsList = postTagsRepository.findAllByPost_Pid(post.getPid());
            List<String> tags = new ArrayList<>();
            for (PostTags postTags : postTagsList) {
                String tag = postTags.getTags().getTagName();
                tags.add(tag);
            }


            PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount,tags);

            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;
    }


    // post 조회 (답변대기)
    public List<PostResponseDto> postGet2() {
        List<Post>postList = postRepository.findAllByStatusContainsOrderByCreatedAtDesc("noCheck");

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            Long uid = post.getUser().getUid();

            List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(post.getPid());
            Long postLikeCount = Long.valueOf(postLikes.size());

            //태그 추가
            List<PostTags> postTagsList = postTagsRepository.findAllByPost_Pid(post.getPid());
            List<String> tags = new ArrayList<>();
            for (PostTags postTags : postTagsList) {
                String tag = postTags.getTags().getTagName();
                tags.add(tag);
            }

            PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount,tags);

            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;
    }
    
    

    //post 삭제
    @Transactional
    public void postDelete(Long pid, Long uid) {

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new NullPointerException("유저가 존재하지 않습니다.")
        );

        if (post.getUser().getUid() == uid) {

            //댓글 및 대댓글 추가 시 포스트 밑에 있는 댓글 , 대댓글 부터 삭제 해야 함
            commentRepository.deleteAllByPid(pid);

            answerRepository.deleteAllByPost_pid(pid);

            postRepository.deleteById(pid);
            System.out.println("포스트 삭제 완료 pid : " + pid);
        }

    }

    //post 수정
    @Transactional
    public void postEdit(Long pid, PostRequestDto postRequestDto, Long uid) {

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new NullPointerException("post가 존재하지 않습니다.")
        );

        if (post.getUser().getUid() == uid) {

            postTagsRepository.deleteAllByPost_Pid(pid);
            System.out.println("포스트 수정전 postTags 전부 삭제");

            creatTags(postRequestDto, post);


            post.update(postRequestDto);
            System.out.println("포스트 수정 완료 pid :"  + pid);

        }
    }

    //post 상세 조회
    public PostResponseDto detailPostGet(Long pid) {

        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new NullPointerException("post가 존재하지 않습니다.")
        );
        Long uid = post.getUser().getUid();

        List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(pid);
        Long postLikeCount = Long.valueOf(postLikes.size());

        //태그 추가
        List<PostTags> postTagsList = postTagsRepository.findAllByPost_Pid(post.getPid());
        List<String> tags = new ArrayList<>();
        for (PostTags postTags : postTagsList) {
            String tag = postTags.getTags().getTagName();
            tags.add(tag);
        }

        PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount,tags);

        return postResponseDto;
    }

    //게시글 타이틀 검색하여 조회
    public List<PostResponseDto> postTitleGet(PostResponseDto postResponseDto) {
        String postTitle = postResponseDto.getPostTitle();
        List<Post> postList = postRepository.findByPostTitleContaining(postTitle);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            Long uid = post.getUser().getUid();

            List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(post.getPid());
            Long postLikeCount = Long.valueOf(postLikes.size());

            //태그 추가
            List<PostTags> postTagsList = postTagsRepository.findAllByPost_Pid(post.getPid());
            List<String> tags = new ArrayList<>();
            for (PostTags postTags : postTagsList) {
                String tag = postTags.getTags().getTagName();
                tags.add(tag);
            }

            PostResponseDto postResponseDto2 = new PostResponseDto(post, uid,postLikeCount,tags);

            postResponseDtos.add(postResponseDto2);

        }
        return postResponseDtos;

    }

    // 태그 및 포스트태그 생성
    private void creatTags(PostRequestDto requestDto, Post post) {
        for(int i = 0; i < requestDto.getTags().size(); i++){

            String tagName = requestDto.getTags().get(i);
            Tags tag = new Tags();
            if (tagsRepository.findByTagName(tagName).isPresent()) {
                tag = tagsRepository.findByTagName(tagName).orElseThrow(
                        ()-> new NullPointerException("태그가 존재하지 않습니다.")
                );
            } else {
                tag.setTagName(tagName);
                tagsRepository.save(tag);
                System.out.println("tag 없어서 객체 새로 생성 tagName : " + tagName);
            }


            PostTags postTags = new PostTags();
            postTags.setPost(post);
            postTags.setTags(tag);
            postTagsRepository.save(postTags);
            System.out.println("postTags 객체 생성");
        }
    }


}
