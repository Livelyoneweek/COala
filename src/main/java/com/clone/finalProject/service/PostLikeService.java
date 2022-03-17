package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.PostTags;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
import com.clone.finalProject.repository.PostTagsRepository;
import com.clone.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostTagsRepository postTagsRepository;


    //관심 등록 할 때 있으면 삭제 없으면 생성
    public HashMap<String, String> postLike(AnswerLikeResponseDto answerLikeResponseDto) {

        Long uid =answerLikeResponseDto.getUid();
        Long pid = answerLikeResponseDto.getPid();

        HashMap<String, String> result = new HashMap<>();

        // 있는 경우 삭제
        if(postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).isPresent()){
            Long postLikeId = postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).get().getPostLikeId();
            postLikeRepository.deleteById(postLikeId);

            result.put("postLike","false");

        //없으니 생성
        } else {
            User user = userRepository.findById(uid).orElseThrow(
                    ()-> new NullPointerException("User가 존재하지 않습니다.")
            );
            Post post = postRepository.findById(pid).orElseThrow(
                    ()-> new NullPointerException("Post가 존재하지 않습니다.")
            );

            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);

            result.put("postLike","true");

        }
        return result;
    }

    //마이페이지에서 관심 글 조회
    public List<PostResponseDto> postLikeGet(Long uid) {

        List<PostLike> postLikes = postLikeRepository.findAllByUser_Uid(uid);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (PostLike postList : postLikes) {
            Post post = postRepository.findByPid(postList.getPost().getPid()).orElseThrow(
                    ()-> new NullPointerException("Post가 존재하지 않습니다.")
            );

            // 게시글 조회용 메소드
            PostResponseDto postResponseDto = postGetMethod(post);

            postResponseDtos.add(postResponseDto);
        }

        return postResponseDtos;

    }

    // 게시글 조회용 메소드
    private PostResponseDto postGetMethod(Post post) {
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


}