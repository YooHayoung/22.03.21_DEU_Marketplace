package com.deu.marketplace.query.postListView.repository;

import com.deu.marketplace.common.PostSearchCond;
import com.deu.marketplace.domain.post.entity.QPost;
import com.deu.marketplace.query.postListView.dto.PostDetailViewDto;
import com.deu.marketplace.query.postListView.dto.PostListViewDto;
import com.deu.marketplace.query.postListView.dto.QPostDetailViewDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.member.entity.QMember.member;
import static com.deu.marketplace.domain.post.entity.QPost.post;
import static com.deu.marketplace.domain.postCategory.entity.QPostCategory.postCategory;
import static com.deu.marketplace.domain.postComment.entity.QPostComment.postComment;
import static com.deu.marketplace.domain.postImg.entity.QPostImg.postImg;
import static com.deu.marketplace.domain.postRecommend.entity.QPostRecommend.postRecommend;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Repository
public class PostViewRepository {

    private final JPAQueryFactory queryFactory;

    public PostViewRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    SELECT post.post_id, post.post_category_id, post.writer_id, post.title,
//    post.content, post.created_date, post_img.img_file, post_img.img_seq
//    FROM post LEFT JOIN post_img ON post.post_id = post_img.post_id AND post_img.img_seq = 1
//    GROUP BY post.post_id
//    ORDER BY post.created_date DESC, post.post_id desc;
//
//    SELECT post.post_id, COUNT(post_recommend.post_recommend_id)
//    FROM post left join post_recommend ON post.post_id = post_recommend.post_id
//    GROUP BY post.post_id
//    ORDER BY post.created_date desc, post.post_id desc;
//
//    SELECT post.post_id, COUNT(post_comment.post_comment_id)
//    FROM post LEFT join post_comment ON post.post_id = post_comment.post_id
//    GROUP BY post.post_id
//    ORDER BY post.created_date DESC, post.post_id desc;
    public Page<PostListViewDto> getPostsPages(PostSearchCond postSearchCond,
                                               Pageable pageable) {
        List<Tuple> postAndFirstImgs = queryFactory
                .select(post, postImg)
                .from(post)
                .leftJoin(post.writer, member)
                .fetchJoin()
                .leftJoin(post.postCategory, postCategory)
                .fetchJoin()
                .leftJoin(postImg).on(post.eq(postImg.post).and(postImg.imgSeq.eq(1)))
                .fetchJoin()
                .where(postCategoryEq(postSearchCond.getPostCategoryId()),
                        postTitleContains(postSearchCond.getTitle()))
//                .groupBy(post)
                .orderBy(post.createdDate.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Tuple> recommendCount = queryFactory
                .select(post.id, postRecommend.count())
                .from(post)
                .leftJoin(postRecommend).on(post.id.eq(postRecommend.post.id))
                .where(postCategoryEq(postSearchCond.getPostCategoryId()),
                        postTitleContains(postSearchCond.getTitle()))
                .groupBy(post)
                .orderBy(post.createdDate.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Tuple> commentCount = queryFactory
                .select(post.id, postComment.count())
                .from(post)
                .leftJoin(postComment).on(post.id.eq(postComment.post.id))
                .where(postCategoryEq(postSearchCond.getPostCategoryId()),
                        postTitleContains(postSearchCond.getTitle()))
                .groupBy(post)
                .orderBy(post.createdDate.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PostListViewDto> content = new ArrayList<>();
        for (int i = 0; i < postAndFirstImgs.size(); i++) {
            content.add(PostListViewDto.builder()
                    .post(postAndFirstImgs.get(i).get(post))
                    .postImg(postAndFirstImgs.get(i).get(postImg))
                    .recommendCount(recommendCount.get(i).get(postRecommend.count()).intValue())
                    .commentCount(commentCount.get(i).get(postComment.count()).intValue())
                    .build());
        }

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .where(postCategoryEq(postSearchCond.getPostCategoryId()),
                        postTitleContains(postSearchCond.getTitle()))
                .from(post);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

//    SELECT *,
//            (SELECT COUNT(*) FROM post_recommend WHERE post_recommend.post_id = 3),
//            (SELECT case when (SELECT member_id
//		FROM post_recommend
//                WHERE post_recommend.member_id = 4
//                AND post_recommend.post_id=3) IS NOT NULL then TRUE ELSE FALSE end)
//    FROM post
//    WHERE post.post_id = 3;
    public Optional<PostDetailViewDto> getPostDetail(Long postId, Long memberId) {
        PostDetailViewDto postDetailViewDto = queryFactory
                .select(new QPostDetailViewDto(
                        post,
                        JPAExpressions
                                .select(postRecommend.count().intValue())
                                .from(postRecommend)
                                .where(postRecommend.post.id.eq(postId)),
                        new CaseBuilder()
                                .when(JPAExpressions
                                        .select(postRecommend)
                                        .from(postRecommend)
                                        .where(postRecommend.member.id.eq(memberId)
                                                .and(postRecommend.post.id.eq(postId)))
                                        .isNotNull())
                                .then(true)
                                .otherwise(false)))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
        return Optional.ofNullable(postDetailViewDto);
    }

    private BooleanExpression postCategoryEq(Long postCategoryId) {
        return postCategoryId == null ? null : post.postCategory.id.eq(postCategoryId);
    }

    private BooleanExpression postTitleContains(String title) {
        return isEmpty(title) ? null : post.title.contains(title);
    }
}
