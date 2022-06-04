import React, { useEffect, useState } from "react";
import { Button, Divider, Paper } from "../../node_modules/@material-ui/core/index";
import { useParams } from "../../node_modules/react-router-dom/index";
import { getItemDetail, setWishItem } from "../api/Api";
import { UseApi } from "../api/UseApi";
import jwt_decode from "jwt-decode";
import ItemDetailBookStateInfo from "../components/contents/itemDetail/ItemDetailBookStateInfo";
import ItemDetailDescription from "../components/contents/itemDetail/ItemDetailDescription";
// import ItemDetailImgs from "../components/contents/itemDetail/ItemDetailImgs";
import ItemDetailLectureInfo from "../components/contents/itemDetail/ItemDetailLectureInfo";
import ItemDetailMemberInfo from "../components/contents/itemDetail/ItemDetailMemberInfo";
import ItemDetailPrice from "../components/contents/itemDetail/ItemDetailPrice";
import ItemDetailTitle from "../components/contents/itemDetail/ItemDetailTitle";
import BarWithBackOnTop from "../components/nav/top/BarWithBackOnTop";
import HeaderContainer from "../containers/HeaderContainer";
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import ChatIcon from '@mui/icons-material/Chat';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';

import './ItemDetailPage.scss'
import { useNavigate } from "../../node_modules/react-router/index";
import ItemDetailDatetime from "../components/contents/itemDetail/ItemDetailDatetime";
import RCarousel from "../components/contents/itemDetail/RCarousel";

const ItemDetailPage = ({token, setToken}) => {
    let navigate = useNavigate();
    const params = useParams('localhost:3000/item/:itemId');
    const [content, setContent] = useState({wishInfo:{wishCount: 0, myWish: false}}, {imgList:[]});
    const [loading, setLoading] = useState(false);

    const workAfterGet = (res) => {
        console.log(res.data.body.result);
        setContent(res.data.body.result);
        setLoading(true);
    }

    useEffect(() => {
        // navigate();
        (async() => {UseApi(getItemDetail, token, setToken, workAfterGet, params);})();
        // return () => setContent({});
    }, []);

    const renderItemImgs = () => {
        // console.log(content.imgList);
        if (loading === true)
        // if (content.imgList.length !== 0)
            // return <div className="div_imgs"><ItemDetailImgs imgList={content.imgList}/></div>;
            return <div className="div_imgs"><RCarousel imgList={content.imgList}/></div>;
        // else return <ItemDetailImgs imgList={[{img:""}]}/>;
    };

    const renderDetailPage = () => {
        if (loading === true) {
            let result;
            if (content.itemDetailDto.classification === "BUY") {
                result = (
                    <>
                    <ItemDetailTitle 
                        title={content.itemDetailDto.title} 
                        lastModifiedDate={content.itemDetailDto.lastModifiedDate} 
                        dealState={content.dealState}
                    />
                    <Divider />
                    <ItemDetailPrice price={content.itemDetailDto.price} />
                    <Divider />
                    <ItemDetailDescription description={content.itemDetailDto.description} />
                    <Divider />
                    <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                    </>
                );
            } else {
                switch (content.itemDetailDto.itemCategoryInfo.categoryName) {
                    case "대학 교재" :
                        result = (
                            <>
                            <ItemDetailTitle 
                                title={content.itemDetailDto.title} 
                                lastModifiedDate={content.itemDetailDto.lastModifiedDate} 
                                dealState={content.dealState}
                            />
                            <Divider />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <Divider />
                            <ItemDetailLectureInfo lectureInfo={content.itemDetailDto.lectureInfo} />
                            <Divider />
                            <ItemDetailBookStateInfo bookStateInfo={content.itemDetailDto.bookStateInfo} />
                            <Divider />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <Divider />
                            <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    case "강의 관련 물품" :
                        result = (
                            <>
                            <ItemDetailTitle 
                                title={content.itemDetailDto.title} 
                                lastModifiedDate={content.itemDetailDto.lastModifiedDate} 
                                dealState={content.dealState}
                            />
                            <Divider />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <Divider />
                            <ItemDetailLectureInfo lectureInfo={content.itemDetailDto.lectureInfo} />
                            <Divider />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <Divider />
                            <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    case "서적" :
                        result = (
                            <>
                            <ItemDetailTitle 
                                title={content.itemDetailDto.title} 
                                lastModifiedDate={content.itemDetailDto.lastModifiedDate} 
                                dealState={content.dealState}
                            />
                            <Divider />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <Divider />
                            <ItemDetailBookStateInfo bookStateInfo={content.itemDetailDto.bookStateInfo} />
                            <Divider />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <Divider />
                            <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    default:
                        result = (
                            <>
                            <ItemDetailTitle 
                                title={content.itemDetailDto.title} 
                                lastModifiedDate={content.itemDetailDto.lastModifiedDate} 
                                dealState={content.dealState}
                            />
                            <Divider />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <Divider />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <Divider />
                            <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                }
            }
            return result;
        }
    }

    const work = (res) => {
        console.log(res);
        if (res.data.body.result){
            setContent((prevState) => ({
                ...prevState,
                wishInfo:{wishCount: content.wishInfo.wishCount+1, myWish: true}
            }));
        } else {
            setContent((prevState) => ({
                ...prevState,
                wishInfo:{wishCount: content.wishInfo.wishCount-1, myWish: false}
            }));
        }
    }

    const handleChange = () => {
        // console.log("button click");
        (async () => {
            UseApi(setWishItem, token, setToken, work, {itemId: content.itemDetailDto.itemId});
            // console.log(content.wishInfo);
        })();
    };

    const chatRoomBtnClick = () => {
        console.log(jwt_decode(token));
        if (content.itemDetailDto.sellerInfo.memberId == jwt_decode(token).sub) {
            navigate('/chatRooms');
        } else {
            if (content.myChatRoomId) {
                navigate(`/chatRooms/${content.myChatRoomId}`);
            } else {
                navigate('/chatRooms/new', {
                    state: {
                        itemInfo: {
                            itemId: content.itemDetailDto.itemId,
                            title: content.itemDetailDto.title,
                            price: content.itemDetailDto.price,
                            itemImg: content.imgList.length!=0?content.imgList[0].img:null,
                            dealState: content.dealState
                        },
                        itemSavedMemberId: content.itemDetailDto.sellerInfo.memberId
                    }
                });
            }
        }
    };

    const onUpdateBtnClick = () => {
        console.log("update");
        navigate('/update', {
            state: {
                targetId: content.itemDetailDto.itemId,
                classification: content.itemDetailDto.classification.toLowerCase(),
                title: content.itemDetailDto.title,
                category: content.itemDetailDto.itemCategoryInfo,
                lecture: content.itemDetailDto.lectureInfo,
                bookState: content.itemDetailDto.bookStateInfo?{
                    writeState: content.itemDetailDto.bookStateInfo.writeState?content.itemDetailDto.bookStateInfo.writeState:'',
                    surfaceState: content.itemDetailDto.bookStateInfo.surfaceState?content.itemDetailDto.bookStateInfo.surfaceState:'',
                    regularPrice: content.itemDetailDto.bookStateInfo.regularPrice?content.itemDetailDto.bookStateInfo.regularPrice:''
                }:{
                    writeState: '',
                    surfaceState: '',
                    regularPrice: ''
                },
                price: content.itemDetailDto.price,
                description: content.itemDetailDto.description,
                imgList: content.imgList
            }
        });
    };
    const onDeleteBtnClick = () => {
        console.log("delete");
    };

    const renderBtns = () => {
        // if (loading) {
            // if (jwt_decode(token).sub == content.itemDetailDto.sellerInfo.memberId) {
            //     // return 'same';
                return (
                    <div className="btn_compo">
                    <Button className="btn_delete" onClick={() => onDeleteBtnClick()} disabled>삭제</Button>
                    <Button className="btn_update" onClick={() => onUpdateBtnClick()}>수정</Button>
                    </div>
                );
            // } else {
            //     // return 'diff';
            // }
        // }
    };

    return (
        <div>
            <HeaderContainer pageName={"상품 상세"} classification={loading?content.itemDetailDto.classification:""} />
            <div className="div_contents" style={{"padding-bottom": 0}}>
                {renderItemImgs()}
                <Divider />
                {loading?(jwt_decode(token).sub == content.itemDetailDto.sellerInfo.memberId?renderBtns():null):null}
                <Divider />
                {/* <div className="btn_compo">
                    {renderBtns()}
                </div> */}
                {renderDetailPage()}
            </div>
            <Divider/>
            <Paper className="footer">
                <Button className="btn_wish" variant="contained" onClick={handleChange}>
                    <div className="wish_icon">{content.wishInfo.myWish?<FavoriteIcon />:<FavoriteBorderIcon />}</div>
                    <div className="wish_label">{content.wishInfo.myWish?"찜 취소":"찜 하기"}</div>
                </Button>
                <Button className="btn_chat" variant="contained" onClick={() => chatRoomBtnClick()} >
                    <div className="chat_icon">{content.myChatRoomId?<ChatIcon />:<ChatBubbleOutlineIcon />}</div>
                    <div className="chat_label">채팅하기</div>
                </Button>
            </Paper>
        </div>
    );
};

export default ItemDetailPage;