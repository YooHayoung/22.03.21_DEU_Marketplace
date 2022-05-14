import React from "react";
import { Box, Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailDatetime = (props) => {
    console.log(props);
    return (
        <Box className="div_class">
            <div className="label" id="lb_item_writeTime">작성일시</div><div className="content">{props.lastModifiedDate}</div>
        </Box>
    );
};

export default ItemDetailDatetime;