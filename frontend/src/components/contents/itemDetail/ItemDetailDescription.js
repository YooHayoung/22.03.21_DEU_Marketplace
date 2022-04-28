import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailDescription = (props) => {
    return (
        <Paper className="div_class">
            <div className="label">내용</div>
            <div className="content" id="item_des">{props.description}</div>
        </Paper>
    );
};

export default ItemDetailDescription;