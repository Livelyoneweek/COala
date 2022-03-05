package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.PostRequestDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.repository.AnswerRepository;
import com.clone.finalProject.repository.CommentRepository;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
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


    // post 생성
    public Long postCreate(PostRequestDto postRequestDto, User user) {

        Post post = new Post(postRequestDto, user);
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

            PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount);

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

            PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount);

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
                ()-> new NullPointerException("유저가 존재하지 않습니다.")
        );

        if (post.getUser().getUid() == uid) {
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

        PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount);

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

            PostResponseDto postResponseDto2 = new PostResponseDto(post, uid,postLikeCount);

            postResponseDtos.add(postResponseDto2);

        }
        return postResponseDtos;

    }
}
