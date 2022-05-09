import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import Carousel from "./Carousel";
import noImg from "../../../noImg.png";


const ItemDetailImgs = (props) => {
    const [imgs, setImgs] = useState([]);

    useEffect(() => {
        // console.log(props);
        // setImgs([]);
        if (props.imgList.length === 0) {
            setImgs([{src: noImg}]);
        } else {
            props.imgList.map(img => setImgs(imgs => [...imgs, {src: img.img}]));
        }
    }, [])

    const renderCarousel = () => {
        if (imgs.length !== 0)
            return <Carousel items={imgs} />
    }

    return (
        <div>
            {/* <Carousel items={imgs} /> */}
            {renderCarousel()}
        </div>
    );
};

export default ItemDetailImgs;