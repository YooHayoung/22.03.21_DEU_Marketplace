import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailBookStateInfo = (props) => {
    return (
        <Paper className="div_bookState">
            <div className="label">책상태</div>
            <div className="div_content">
                <div className="con_label">필기상태</div><div className="con_content">{props.bookStateInfo.writeState}</div>
                <div className="con_label">책상태</div><div className="con_content">{props.bookStateInfo.surfaceState}</div>
                <div className="con_label">정가</div><div className="con_content">{props.bookStateInfo.regularPrice.toLocaleString()}원</div>
            </div>
        </Paper>
    );
};

export default ItemDetailBookStateInfo;