import React from "react";
import { useLocation, useNavigate } from "../../node_modules/react-router/index";
import { getPostList } from "../api/Api";
import EnhancedTable from "../components/contents/Test";
import BottomNav from "../components/nav/bottom/BottomNav";
import HeaderContainer from "../containers/HeaderContainer";
import jwt_decode from "jwt-decode";

import './PostListPage.scss'
import { UseApi } from "../api/UseApi";
import axios from "../../node_modules/axios/index";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import KeyboardDoubleArrowLeftIcon from '@mui/icons-material/KeyboardDoubleArrowLeft';
import KeyboardDoubleArrowRightIcon from '@mui/icons-material/KeyboardDoubleArrowRight';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import { Button, ButtonBase, Divider, IconButton, Typography } from "../../node_modules/@material-ui/core/index";

const PostListPage = ({ token, setToken, onClear, oauth, code, state, accessToken, refreshToken, updateToken, remove }) => {
    let navigate = useNavigate();
    const location = useLocation();
    const [searchCond, setSearchCond] = React.useState({
        postCategoryId: (location.state?(location.state.category.categoryId?location.state.category.categoryId:''):''),
        title: (location.state?location.state.title:''),
    });
    // console.log(searchCond);
    const [page, setPage] = React.useState(0);
    const [listPage, setListPage] = React.useState(1);
    const [contents, setContents] = React.useState([]);
    const [totalPage, setTotalPage] = React.useState(0);
    const [totalElement, setTotalElement] = React.useState(0);

    React.useEffect(() => {
        setPage(0);
        console.log(token);
        if (token === '') {
           (async () => {
              axios.get('http://localhost:8000/oauth/refresh', { withCredentials: true })
                 .catch((error) => {
                    if (error.response.status === 307) {
                       console.log(error.response.headers.authorization);
                       setToken(error.response.headers.authorization);
                       getPages();
                       // return Promise.reject(error);
                    } else if (error.response.status === 401) {
                       console.log(error.response.status);
                       navigate('/oauth');
                       // return Promise.reject(error);
                    }
                 })
           })();
        } else {
           console.log(jwt_decode(token))
           console.log(jwt_decode(token).exp)
           console.log(Date.now() / 1000);
           getPages();
        }
    }, [searchCond]);

    const afterGetPage = (res) => {
        console.log(res);
        console.log(res.data.body.result);
        // setContents([...contents, ...res.data.body.result.content]);            
        setContents([...res.data.body.result.content]);
        setPage(res.data.body.result.number+1);
        setTotalPage(res.data.body.result.totalPages);
        setTotalElement(res.data.body.result.totalElement);
    };
    const getPages = (pageNum) => {
        console.log(pageNum);
        console.log(page);
        console.log(searchCond);
        (async () => {
           UseApi(getPostList, token, setToken, afterGetPage, {
              postCategoryId: searchCond.postCategoryId,
              title: searchCond.title,
              page: pageNum
           });
        })();
    };

    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    }));

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
            <span id="sp_icon" style={{paddingLeft: 8, paddingRight: 3}}>
                <AccessTimeIcon style={{fontSize:"1rem", marginBottom: 2}}/>
            </span>
            <span>
                {dddTime}
            </span>
            </>
            );
        } else if (dddYear == nowYear) {
            return (
            <>
            <span id="sp_icon" style={{paddingLeft: 8, paddingRight: 3}}>
                <CalendarMonthIcon style={{fontSize:"1rem", marginBottom: 2}}/>
            </span>
            <span>
                {`${dddMonth.toString().padStart(2,'0')}-${dddDate.toString().padStart(2,'0')}`}
            </span></>);
        } else {
            return (
                <>
                <span id="sp_icon" style={{paddingLeft: 8, paddingRight: 3}}>
                    <CalendarMonthIcon style={{fontSize:"1rem", marginBottom: 2}}/>
                </span>
                <span>
                    {`${dddYear}-${dddMonth}-${dddDate}`}
                </span>
                </>
            );
        }
    };
    
    const onPaperClick = (postId) => {
        navigate('/board/' + postId);
    };
    const renderPostListMain = () => {
        return contents.map((content, idx) => {
            return (
                <>
                <Paper
                sx={{
                    p: 1,
                    margin: 'auto',
                    maxWidth: "100%",
                    flexGrow: 1,
                    backgroundColor: (theme) =>
                    theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                }}
                onClick={() => onPaperClick(content.postId)}
                >
                <Grid container spacing={0}>
                    <Grid item xs container direction="column" spacing={0} style={{paddingLeft: "0.7rem", textAlign: "left"}}>
                        <Grid item xs>
                        <Typography className="ty_title" component="div" style={{fontSize: "1.3rem", marginBottom: 4}}>
                            <span className="sp_categoryName" style={{fontSize: "1rem"}}>{`[${content.postCategoryName}]`}&nbsp;</span>
                            <span className="sp_title" style={{
                                // display: 'block',
                                // alignItems: 'center',
                                // overflow:'hidden',
                                // textOverflow:'ellipsis',
                                // whiteSpace:'nowrap'
                                }}>{`${content.title}`}</span>
                        </Typography>
                        <Typography className="ty_nick" component="div" style={{fontSize: "0.8rem", overflow: "hidden", whiteSpace: "nowrap"}}>
                            {`${content.memberNickname} `}{changeDate(content.createdDate)}
                        </Typography>
                        </Grid>
                    </Grid>
                </Grid>
                </Paper>
                <Divider />
                </>
            );
        });
    };
    const onNextBtnClick = () => {
        if (page == listPage*5) {
            setListPage(listPage+1);
        }
        getPages(page);
    };
    const onBackBtnClick = () => {
        if (page%5 == 1) {
            setListPage(listPage-1);
        }
        getPages(page-2);
    };
    const onDoubleNextBtnClick = () => {
        setListPage(listPage+1);
        getPages((listPage*5));
    };
    const onDoubleBackBtnClick = () => {
        setListPage(listPage-1);
        getPages((listPage-1)*5-1);
    };
    
    const renderPostListBottom = () => {
        let result = [];
        if (listPage != 1) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onDoubleBackBtnClick()}><KeyboardDoubleArrowLeftIcon fontSize="small"/></IconButton>)
        }
        if (page != 1) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onBackBtnClick()}><NavigateBeforeIcon fontSize="small"/></IconButton>)
        }
        for(let i = (listPage-1)*5; i<listPage*5; i++) {
            if (i>=totalPage) break;
            if (i==page-1) {
                result.push(<Button disabled style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={() =>getPages(i)}>{i+1}</Button>);
            } else {
                result.push(<Button style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={() =>getPages(i)}>{i+1}</Button>);
            }
        }
        if (page != totalPage) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onNextBtnClick()}><NavigateNextIcon fontSize="small"/></IconButton>)
        }
        if (listPage*5 < totalPage) {
            result.push(<IconButton style={{padding: 5, fontSize: "0.8rem", minWidth: 30, width: 40, hegiht: 40}} onClick={()=>onDoubleNextBtnClick()}><KeyboardDoubleArrowRightIcon fontSize="small"/></IconButton>)
        }
        return result;
    };
    
    return (
        <>
        <HeaderContainer pageName={"게시판"}/>
        <div className="div_contents">
            {/* {renderPostListHeader()}
            <Divider /> */}
            <div className="div_postListMain">
            {renderPostListMain()}
            </div>
            <Divider />
            <div className="bottomBtn">
                {renderPostListBottom()}
            </div>
        </div>
        {<BottomNav />}
        </>
    );
};

export default PostListPage;