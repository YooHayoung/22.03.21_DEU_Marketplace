
import React, { useRef } from "react";
import { Backdrop, Box, Button, Checkbox, Chip, CircularProgress, Divider, FormControl, FormControlLabel, Grid, IconButton, Paper, TextField, ToggleButton, Typography } from "../../node_modules/@material-ui/core/index";
import { useNavigate, useParams } from "../../node_modules/react-router/index";
import { deletePost, deletePostComment, getPostCommentPage, getPostDetail, savePostComment, setPostRecommend } from "../api/Api";
import { UseApi } from "../api/UseApi";
import jwt_decode from "jwt-decode";
import PostDetailContent from "../components/contents/postDetail/PostDetailContent";
import PostDetailMemberInfo from "../components/contents/postDetail/PostDetailMemberInfo";
import PostDetailTitle from "../components/contents/postDetail/PostDetailTitle";
import HeaderContainer from "../containers/HeaderContainer";
import { styled } from '@mui/material/styles';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import KeyboardDoubleArrowLeftIcon from '@mui/icons-material/KeyboardDoubleArrowLeft';
import KeyboardDoubleArrowRightIcon from '@mui/icons-material/KeyboardDoubleArrowRight';
import DeleteIcon from '@mui/icons-material/Delete';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';

import './PostDetailPage.scss';
import PostDetailCommentList from "../components/contents/postDetail/PostDetailCommentList";
import { Stack } from "../../node_modules/@mui/material/index";
import RCarousel from "../components/contents/itemDetail/RCarousel";

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
    const [comments, setComments] = React.useState([]);
    const [commentPage, setCommentPage] = React.useState(0);
    const [commentListPage, setCommentListPage] = React.useState(1);
    const [commentTotalPage, setCommentTotalPage] = React.useState(0);
    const [commentCount, setCommentCount] = React.useState(0);
    const [content, setContent] = React.useState({});
    const [loading, setLoading] = React.useState(false);
    const [commentFeild, setCommentFeild] = React.useState('');
    const [open, setOpen] = React.useState(false);
    const [recommend, setRecommend] = React.useState(false);

    const workAfterGet = (res) => {
        console.log(res.data.body.result);
        setContent(res.data.body.result);
        setRecommend(res.data.body.result.myRecommend);
        setLoading(true);
    };
    const workAfterGetComment = (res) => {
        console.log(res.data.body.result);
        setCommentPage(res.data.body.result.number+1);
        setComments([...res.data.body.result.content]);
        setCommentTotalPage(res.data.body.result.totalPages);
        setCommentCount(res.data.body.result.totalElements);
        
    };

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
    const afterPostDeleteBtnClick = () => {
        window.location.pathname="/board"
    };
    const onDeleteBtnClick = () => {
        if (window.confirm("게시물을 삭제하시겠습니까?")) {
            console.log("delete");
            setOpen(true);
            // (async () => {
                UseApi(deletePost, token, setToken, afterPostDeleteBtnClick, {postId: params.postId});
            // })();
        }
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

    const afterGetPage = (res) => {
        console.log(res);
        console.log(res.data.body.result);
        // setContents([...contents, ...res.data.body.result.content]);            
        setComments([...res.data.body.result.content]);
        setCommentPage(res.data.body.result.number+1);
        setCommentTotalPage(res.data.body.result.totalPages);
        setCommentCount(res.data.body.result.totalElements);
    };
    const getCommentPages = (pageNum) => {
        console.log(pageNum);
        (async () => {
           UseApi(getPostCommentPage, token, setToken, afterGetPage, {
                postId: params.postId,
                page: pageNum
           });
        })();
    };

    const onNextBtnClick = () => {
        if (commentPage == commentListPage*5) {
            setCommentListPage(commentListPage+1);
        }
        getCommentPages(commentPage);
    };
    const onBackBtnClick = () => {
        if (commentPage%5 == 1) {
            setCommentListPage(commentListPage-1);
        }
        getCommentPages(commentPage-2);
    };
    const onDoubleNextBtnClick = () => {
        setCommentListPage(commentListPage+1);
        getCommentPages((commentListPage*5));
    };
    const onDoubleBackBtnClick = () => {
        setCommentListPage(commentListPage-1);
        getCommentPages((commentListPage-1)*5-1);
    };
    const renderPostListBottom = () => {
        let result = [];
        if (commentListPage != 1) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onDoubleBackBtnClick()}><KeyboardDoubleArrowLeftIcon fontSize="small"/></IconButton>)
        }
        if (commentPage != 1) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onBackBtnClick()}><NavigateBeforeIcon fontSize="small"/></IconButton>)
        }
        for(let i = (commentListPage-1)*5; i<commentListPage*5; i++) {
            if (i>=commentTotalPage) break;
            if (i==commentPage-1) {
                result.push(<Button disabled style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={() =>getCommentPages(i)}>{i+1}</Button>);
            } else {
                result.push(<Button style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={() =>getCommentPages(i)}>{i+1}</Button>);
            }
        }
        if (commentPage != commentTotalPage) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onNextBtnClick()}><NavigateNextIcon fontSize="small"/></IconButton>)
        }
        if (commentListPage*5 < commentTotalPage) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onDoubleNextBtnClick()}><KeyboardDoubleArrowRightIcon fontSize="small"/></IconButton>)
        }
        return result;
    };

    const onCommentFeildHandleChange = (e) => {
        setCommentFeild(e.target.value);
    };
    const onCommentSubmitBtnClick = () => {
        if(commentFeild == '') {
            console.log("내용을 입력해주세요");
        } else {
            (async () => {
                UseApi(savePostComment, token, setToken, workAfterGetComment, {postId: params.postId, comment: commentFeild});
            })();
            setCommentFeild('');
            homeRef.current?.scrollIntoView({ behavior: 'smooth' });
        }
    };

    const renderPostImgs = () => {
        // console.log(content.imgList);
        if (loading === true && content.postImgs.length!=0)
        // if (content.imgList.length !== 0)
            // return <div className="div_imgs"><ItemDetailImgs imgList={content.imgList}/></div>;
            return <div className="div_imgs"><RCarousel imgList={content.postImgs}/></div>;
        // else return <ItemDetailImgs imgList={[{img:""}]}/>;
    };

    const afterSetRecommend = (res) => {
        console.log(res);
        if (res.data.body.result!=null) {
            setRecommend(true);
            setContent((prevState) => ({
                ...prevState,
                postRecommendCount: content.postRecommendCount+1
            }));
        } else {
            setRecommend(false);
            setContent((prevState) => ({
                ...prevState,
                postRecommendCount: content.postRecommendCount-1
            }));
        }
    };
    const onRecommendBtnClick = (e) => {
        e.stopPropagation();
        UseApi(setPostRecommend, token, setToken, afterSetRecommend, {postId: params.postId})
    };

    const homeRef = useRef();
    const renderCon = () => {
        if (loading === true) {
            return (
                <>
                <div className="btn_compo">
                    {renderBtns()}
                </div>
                <PostDetailTitle title={content.postInfo.title} createdDate={content.postInfo.createdDate} category={content.postInfo.postCategoryInfo} />
                <Divider />
                <PostDetailContent content={content.postInfo.content} />
                {renderPostImgs()}
                    {recommend?(<Button onClick={onRecommendBtnClick} variant="contained" startIcon={<ThumbUpIcon fontSize="small" />}>
                        {`추천 ${content.postRecommendCount}`}
                    </Button>):(<Button onClick={onRecommendBtnClick} variant="outlined" color="inherit" startIcon={<ThumbUpIcon fontSize="small" />}>
                        {`추천 ${content.postRecommendCount}`}
                    </Button>)}
                <Divider />
                <div ref={homeRef} />
                <PostDetailMemberInfo memberInfo={content.postInfo.memberShortInfo}/>
                <Divider />
                {/* <PostDetailCommentList commentCount={commentCount} comments={comments} /> */}
                <Box className="div_paper">
                    <div className="div_class">
                        <div className="label">댓글</div><div className="content">{commentCount}개</div>
                    </div>
                    <Divider/>
                    {commentCount!=0?renderComments():null}
                    {commentCount!=0?renderPostListBottom():null}
                </Box>
                <Divider/>
                <FormControl fullWidth id="div_writeComment" style={{marginBottom: 30}}>
                    <TextField multiline placeholder="댓글을 입력하세요" id="input_comment" variant="outlined" onChange={onCommentFeildHandleChange} value={commentFeild} style={{padding: 0}} />
                    <Button variant="contained" id="btn_sub" onClick={onCommentSubmitBtnClick}>작성</Button>
                </FormControl>
                </>
            );
        };
    };

    const changeDate = (date) => {
        const now = new Date();
        const nowYear = now.getFullYear();
        const nowMonth = now.getMonth()+1;
        const nowDate = now.getDate();

        const ddd = new Date(Date.parse(date.replace(' ', 'T')));
        const dddYear = ddd.getFullYear();
        const dddMonth = ddd.getMonth()+1;
        const dddDate = ddd.getDate();
        const dddTime = `${ddd.getHours().toString().padStart(2,'0')}:${ddd.getMinutes().toString().padStart(2,'0')}`

        if (dddYear == nowYear && dddMonth == nowMonth && dddDate == nowDate) {
            return (
            <>
            <div classNam="comment_timeIcon" >
                <AccessTimeIcon sx={{fontSize:"1rem", color: "gray"}} />
            </div>
            <div className="comment_createdDate">
                {dddTime}
            </div>
            </>
            );
        } else if (dddYear == nowYear ) {
            return (
            <>
            <div classNam="comment_timeIcon">
                <CalendarMonthIcon sx={{fontSize:"1rem", color: "gray"}}/>
            </div>
            <div className="comment_createdDate">
                {`${dddMonth.toString().padStart(2,'0')}-${dddDate.toString().padStart(2,'0')}`}
            </div>
            </>
            );
        } else {
            return (
                <>
                <div classNam="comment_timeIcon">
                    <CalendarMonthIcon sx={{fontSize:"1rem", color: "gray"}}/>
                </div>
                <div className="comment_createdDate">
                    {`${dddYear}-${dddMonth}-${dddDate}`}
                </div>
                </>
            );
        }
    };

    const onCommentDeleteBtnClick = (comment) => {
        if (window.confirm("댓글을 삭제하시겠습니까?")) {
            (async () => {
                UseApi(deletePostComment, token, setToken, () => getCommentPages(commentPage-1),{postCommentId: comment.postCommentId});
            })();
            // console.log(comment);
        }
    };
    const renderNewChip = (date) => {
        let now = new Date();
        let commentDate = new Date(Date.parse(date.replace(' ', 'T')));
        if ((now - commentDate)/1000/60 < 3) {
            return (<Chip color="info" label="NEW" size="small" id="chip_new" sx={{fontSize: "0.5rem"}} style={{hegiht: '20px', marginRight: '5px'}} />);
        }
    };
    const renderComments = () => {
        return (comments.map((comment, idx) => {
            return (
                <>
                <Box
                sx={{
                    p: 2,
                    margin: 'auto',
                    maxWidth: "100%",
                    flexGrow: 1,
                    backgroundColor: (theme) =>
                    theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                }}
                key={`pp${comment.postCommentId}`}
                >
                    {/* {renderNewChip(comment.createdDate)} */}
                    <div className="div_comment" key={`dc${comment.postCommentId}`}>
                        {renderNewChip(comment.createdDate)}
                        <div className="comment_memberNickname" key={"nick"+comment.postCommentId}>{comment.memberInfo.nickname}</div>
                        {changeDate(comment.createdDate)}
                        {jwt_decode(token).sub==comment.memberInfo.memberId?<div className="comment_delIcon" key={"cdi"+comment.postCommentId}><DeleteIcon onClick={()=>onCommentDeleteBtnClick(comment)} sx={{fontSize:"1rem", color: "gray"}} key={"delI"+comment.postCommentId} /></div>:null}
                    </div>
                    <div className="comment_content" key={"cc"+comment.postCommentId}>{comment.content}</div>
                </Box>
                <Divider />
                </>
            );
        }));
    };

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <>
        <HeaderContainer pageName={"게시물 상세"} />
        <div className="div_contents" style={{"paddingBottom": 0}}>
            <div className="div_postContents">
            {renderCon()}
            </div>
        </div>
        <Backdrop
            sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
            open={open}
            onClick={handleClose}
        >
            <CircularProgress color="inherit" />
        </Backdrop>
        </>
    );
};

export default PostDetailPage;