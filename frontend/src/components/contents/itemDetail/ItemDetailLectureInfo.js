import React from "react";
import { Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailLectureInfo = (props) => {
    return (
        // <div className="div_lecture">
        <Paper className="div_lecture">
            <div className="label" id="lb_item_lecture">강의정보</div>
            <div className="div_content">
                <div className="con_label">강의명</div><div className="con_content">{props.lectureInfo.lectureName}</div>
                <div className="con_label">교수명</div><div className="con_content">{props.lectureInfo.professorName}</div>
            </div>
        </Paper>
        // </div>
    );
};

export default ItemDetailLectureInfo;