import React from "react";
import { Box, Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailPrice = (props) => {
    return (
        <Box className="div_class">
            <div className="label" id="lb_item_price">금액</div><div className="content">{props.price.toLocaleString()}원</div>
        </Box>
    );
};

export default ItemDetailPrice;