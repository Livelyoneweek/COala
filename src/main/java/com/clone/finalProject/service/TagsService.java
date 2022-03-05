package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.PostTags;
import com.clone.finalProject.domain.Tags;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.dto.TagsResponseDto;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
import com.clone.finalProject.repository.PostTagsRepository;
import com.clone.finalProject.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsRepository tagsRepository;
    private final PostTagsRepository postTagsRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    //태그 검색하여 조회
    @Transactional
    public List<PostResponseDto> searchTag(String tag) {

        List<PostTags> postTagsList = postTagsRepository.findAllByTags_TagName(tag);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(PostTags postTags : postTagsList) {
            Long pid = postTags.getPost().getPid();

            Post post = postRepository.findByPid(pid).orElseThrow(
                    ()-> new NullPointerException("post가 존재하지 않습니다.")
            );
            Long uid = post.getUser().getUid();

            List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(pid);
            Long postLikeCount = Long.valueOf(postLikes.size());

            //태그 추가
            List<PostTags> postTagsList2 = postTagsRepository.findAllByPost_Pid(post.getPid());
            List<String> tags = new ArrayList<>();
            for (PostTags postTags2 : postTagsList2) {
                String tag2 = postTags2.getTags().getTagName();
                tags.add(tag2);
            }

            PostResponseDto postResponseDto = new PostResponseDto(post, uid,postLikeCount,tags);

            postResponseDtos.add(postResponseDto);
        }

        return postResponseDtos;
    }
}