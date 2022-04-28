import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailPrice = (props) => {
    return (
        <Paper className="div_class">
            <div className="label">금액</div><div className="content">{props.price.toLocaleString()}원</div>
        </Paper>
    );
};

export default ItemDetailPrice;