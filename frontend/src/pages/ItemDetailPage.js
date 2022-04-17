import React, { useEffect, useState } from "react";
import { useParams } from "../../node_modules/react-router-dom/index";
import { getItemDetail } from "../api/Api";
import { UseApi } from "../api/UseApi";
import ItemDetailBookStateInfo from "../components/contents/itemDetail/ItemDetailBookStateInfo";
import ItemDetailDescription from "../components/contents/itemDetail/ItemDetailDescription";
import ItemDetailImgs from "../components/contents/itemDetail/ItemDetailImgs";
import ItemDetailLectureInfo from "../components/contents/itemDetail/ItemDetailLectureInfo";
import ItemDetailMemberInfo from "../components/contents/itemDetail/ItemDetailMemberInfo";
import ItemDetailPrice from "../components/contents/itemDetail/ItemDetailPrice";
import ItemDetailTitle from "../components/contents/itemDetail/ItemDetailTitle";

const ItemDetailPage = ({token, setToken}) => {
    const params = useParams('localhost:3000/item/:itemId');
    const [content, setContent] = useState({imgList:[]});
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
                    <ItemDetailPrice price={content.itemDetailDto.price} />
                    <ItemDetailDescription description={content.itemDetailDto.description} />
                    <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} />
                    </>
                );
            } else {
                switch (content.itemDetailDto.itemCategoryInfo.itemCategoryName) {
                    case "대학 교재" :
                        result = (
                            <>
                            <ItemDetailTitle title={content.itemDetailDto.title} />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <ItemDetailLectureInfo />
                            <ItemDetailBookStateInfo />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    case "강의 관련 물품" :
                        result = (
                            <>
                            <ItemDetailTitle title={content.itemDetailDto.title} />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <ItemDetailLectureInfo />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    case "서적" :
                        result = (
                            <>
                            <ItemDetailTitle title={content.itemDetailDto.title} />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <ItemDetailBookStateInfo />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                    default:
                        result = (
                            <>
                            <ItemDetailTitle title={content.itemDetailDto.title} />
                            <ItemDetailPrice price={content.itemDetailDto.price} />
                            <ItemDetailDescription description={content.itemDetailDto.description} />
                            <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} />
                            </>
                        );
                        break;
                }
            }
            return result;
        }
    }

    return (
        <div>
            {renderItemImgs()}
            {/* <ItemDetailTitle title={content.itemDetailDto.title} />
            <ItemDetailPrice price={content.itemDetailDto.price} />
            <ItemDetailLectureInfo />
            <ItemDetailBookStateInfo />
            <ItemDetailDescription description={content.itemDetailDto.description} />
            <ItemDetailMemberInfo description={content.itemDetailDto.sellerInfo} /> */}
            {renderDetailPage()}
        </div>
    );
};

export default ItemDetailPage;