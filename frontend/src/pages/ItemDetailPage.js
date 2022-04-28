import React, { useEffect, useState } from "react";
import { Button, Divider, Paper } from "../../node_modules/@material-ui/core/index";
import { useParams } from "../../node_modules/react-router-dom/index";
import { getItemDetail, setWishItem } from "../api/Api";
import { UseApi } from "../api/UseApi";
import ItemDetailBookStateInfo from "../components/contents/itemDetail/ItemDetailBookStateInfo";
import ItemDetailDescription from "../components/contents/itemDetail/ItemDetailDescription";
import ItemDetailImgs from "../components/contents/itemDetail/ItemDetailImgs";
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

import './ItemDetailPage.scss'

const ItemDetailPage = ({token, setToken}) => {
    const params = useParams('localhost:3000/item/:itemId');
    const [content, setContent] = useState({wishInfo:{wishCount: 0, myWish: false}}, {imgList:[]});
    const [loading, setLoading] = useState(false);

    const workAfterGet = (res) => {
        console.log(res.data.body.result);
        setContent(res.data.body.result);
        setLoading(true);
    }

    useEffect(() => {
        (async() => {UseApi(getItemDetail, token, setToken, workAfterGet, params);})();
        // return () => setContent({});
    }, []);

    const renderItemImgs = () => {
        // console.log(content.imgList);
        if (loading === true)
        // if (content.imgList.length !== 0)
            return <ItemDetailImgs imgList={content.imgList}/>;
        // else return <ItemDetailImgs imgList={[{img:""}]}/>;
    };

    const renderDetailPage = () => {
        if (loading === true) {
            let result;
            if (content.itemDetailDto.classification === "BUY") {
                result = (
                    <>
                    <ItemDetailTitle title={content.itemDetailDto.title} />
                    <Divider />
                    <ItemDetailPrice price={content.itemDetailDto.price} />
                    <Divider />
                    <ItemDetailDescription description={content.itemDetailDto.description} />
                    <Divider />
                    <ItemDetailMemberInfo memberInfo={content.itemDetailDto.sellerInfo} />
                    </>
                );
            } else {
                switch (content.itemDetailDto.itemCategoryInfo.itemCategoryName) {
                    case "대학 교재" :
                        result = (
                            <>
                            <ItemDetailTitle title={content.itemDetailDto.title} />
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
                            <ItemDetailTitle title={content.itemDetailDto.title} />
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
                            <ItemDetailTitle title={content.itemDetailDto.title} />
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
                            <ItemDetailTitle title={content.itemDetailDto.title} />
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

    return (
        <div>
            <HeaderContainer pageName={"상품 상세"} />
            {renderItemImgs()}
            {renderDetailPage()}
            <Divider/>
            <Paper className="footer">
                <Button className="btn_wish" variant="contained" onClick={handleChange}>
                    <div className="wish_icon">{content.wishInfo.myWish?<FavoriteIcon />:<FavoriteBorderIcon />}</div>
                    <div className="wish_label">{content.wishInfo.myWish?"찜 취소":"찜 하기"}</div>
                </Button>
                <Button className="btn_chat" variant="contained">
                    <div className="chat_icon">{content.myChatRoomId?<ChatIcon />:<ChatBubbleOutlineIcon />}</div>
                    <div className="chat_label">채팅하기</div>
                </Button>
            </Paper>
        </div>
    );
};

export default ItemDetailPage;