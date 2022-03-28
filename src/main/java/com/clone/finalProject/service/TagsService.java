package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.PostTags;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.postDto.PostResponseDto;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
import com.clone.finalProject.repository.PostTagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final PostTagsRepository postTagsRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    //태그 검색하여 조회
    @Transactional
    public List<PostResponseDto> searchTag(String tag,int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PostTags> postTagsList = postTagsRepository.findAllByTags_TagName(pageable,tag);

        ArrayList<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(PostTags postTags : postTagsList) {
            Long pid = postTags.getPost().getPid();

            Post post = postRepository.findByPid(pid).orElseThrow(
                    ()-> new NullPointerException("post가 존재하지 않습니다.")
            );
            User user = post.getUser();

            List<PostLike> postLikes = postLikeRepository.findAllByPost_Pid(pid);
            Long postLikeCount = Long.valueOf(postLikes.size());


            //태그 추가
            List<PostTags> postTagsList2 = postTagsRepository.findAllByPost_Pid(post.getPid());
            List<String> tags = new ArrayList<>();
            for (PostTags postTags2 : postTagsList2) {
                String tag2 = postTags2.getTags().getTagName();
                tags.add(tag2);
            }

            PostResponseDto postResponseDto = new PostResponseDto(post, user,postLikeCount,tags);

            postResponseDtos.add(postResponseDto);
        }

        return postResponseDtos;
    }

}