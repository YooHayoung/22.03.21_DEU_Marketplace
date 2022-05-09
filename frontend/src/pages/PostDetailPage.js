
import React from "react";
import { Button, Divider, FormControl, Paper, TextField } from "../../node_modules/@material-ui/core/index";
import { useNavigate, useParams } from "../../node_modules/react-router/index";
import { getPostCommentPage, getPostDetail } from "../api/Api";
import { UseApi } from "../api/UseApi";
import jwt_decode from "jwt-decode";
import PostDetailContent from "../components/contents/postDetail/PostDetailContent";
import PostDetailMemberInfo from "../components/contents/postDetail/PostDetailMemberInfo";
import PostDetailTitle from "../components/contents/postDetail/PostDetailTitle";
import HeaderContainer from "../containers/HeaderContainer";
import { styled } from '@mui/material/styles';

import './PostDetailPage.scss';
import PostDetailCommentList from "../components/contents/postDetail/PostDetailCommentList";
import { Stack } from "../../node_modules/@mui/material/index";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

const PostDetailPage = ({token, setToken}) => {
    const navigate = useNavigate();
    const params = useParams('localhost:3000/board/:postId');
    const [nowPage, setNowPage] = React.useState(0);
    const [commentPage, setCommentPage] = React.useState(0);
    const [comments, setComments] = React.useState([]);
    const [commentCount, setCommentCount] = React.useState(0);
    const [content, setContent] = React.useState({});
    const [loading, setLoading] = React.useState(false);

    const workAfterGet = (res) => {
        console.log(res.data.body.result);
        setContent(res.data.body.result);
        setLoading(true);
    };
    const workAfterGetComment = (res) => {
        console.log(res.data.body.result);
        setCommentPage(commentPage+1);
        setComments([...comments, ...res.data.body.result.content]);
        setCommentCount(res.data.body.result.totalElements);
    }

    const onUpdateBtnClick = () => {
        console.log("update");
        navigate('/update', {
            state: {
                targetId: content.postInfo.postId,
                classification: 'board'.toLowerCase(),
                title: content.postInfo.title,
                category: content.postInfo.postCategoryInfo,
                contents: content.postInfo.content,
                imgList: content.postImgs
            }
        });
    };
    const onDeleteBtnClick = () => {
        console.log("delete");
    };

    const renderBtns = () => {
        if (loading) {
            if (jwt_decode(token).sub == content.postInfo.memberShortInfo.memberId) {
                // return 'same';
                return (
                    <>
                    <Button className="btn_delete" onClick={() => onDeleteBtnClick()}>삭제</Button>
                    <Button className="btn_update" onClick={() => onUpdateBtnClick()}>수정</Button>
                    </>
                );
            } else {
                // return 'diff';
            }
        }
    };

    React.useEffect(() => {
        (async() => {
            UseApi(getPostDetail, token, setToken, workAfterGet, params);
            UseApi(getPostCommentPage, token, setToken, workAfterGetComment, {postId: params.postId, page: 0})
        })();
    }, []);

    const renderComment = () => {
        return (
            // 댓글 div
            // 댓글 목록 div
            // 댓글 작성 div
            <div className="div_comment">
                
            </div>
        );
    };

    const renderCon = () => {
        if (loading === true) {
            return (
                <>
                <div className="btn_compo">
                    {renderBtns()}
                </div>
                <PostDetailTitle title={content.postInfo.title} />
                <Divider />
                <PostDetailContent content={content.postInfo.content} />
                <Divider />
                <PostDetailMemberInfo memberInfo={content.postInfo.memberShortInfo} />
                <Divider />
                {/* <PostDetailCommentList commentCount={commentCount} comments={comments} /> */}
                <Paper className="div_paper">
                    <div className="div_class">
                        <div className="label">댓글</div><div className="content">{commentCount}개</div>
                    </div>
                    {renderComments()}
                </Paper>
                <FormControl fullWidth id="div_writeComment">
                    <TextField multiline placeholder="댓글을 입력하세요" id="input_comment" variant="outlined" />
                    <Button variant="contained" id="btn_sub">작성</Button>
                </FormControl>
                <Button onClick={() => console.log(comments)}>asd</Button>
                </>
            );
        };
    };

    const renderComments = () => {
        return (comments.map((comment, idx) => {
            return (
                <>
                    <Stack
                        key={"st"+idx}
                        direction="row"
                        divider={<Divider orientation="vertical" flexItem />}
                        spacing={0}
                    >
                        <Item key={"nick" + idx}>{comment.memberInfo.nickname}</Item>
                        <Item key={"con" + idx}>{comment.content}</Item>
                        <Item key={"time" + idx}>{comment.createdDate}</Item>
                    </Stack>
                </>
            );
        }));
    };

    return (
        <>
        <HeaderContainer pageName={"게시물 상세"} />
        <div className="div_contents" style={{"paddingBottom": 0}}>
            {renderCon()}
        </div>
        </>
    );
};

export default PostDetailPage;