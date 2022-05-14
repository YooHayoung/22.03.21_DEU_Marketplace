import React from "react";
import { Box, Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailDescription = (props) => {
    return (
        <Box className="div_class">
            <div className="label" id="lb_itemDes">내용</div>
            <div className="content" id="item_des" style={{whiteSpace: "pre-wrap"}}>{props.description}</div>
        </Box>
    );
};

export default ItemDetailDescription;